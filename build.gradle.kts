plugins {
    java

    id("fabric-loom") version "0.12.+"
    id("org.quiltmc.quilt-mappings-on-loom") version "4.+"
    id("io.github.juuxel.loom-quiltflower") version "1.7.+"

    id("com.modrinth.minotaur") version "2.+"
    id("me.hypherionmc.cursegradle") version "2.+"
    id("com.github.breadmoirai.github-release") version "2.+"
    id("io.github.p03w.machete") version "1.1.2"
    `maven-publish`
}

group = "dev.isxander"
version = "1.1.1"

repositories {
    mavenCentral()
    maven("https://maven.terraformersmc.com")
    maven("https://maven.shedaniel.me")
    maven("https://cursemaven.com") {
        content { includeGroup("curse.maven") }
    }
}

val minecraftVersion: String by project

dependencies {
    val fabricLoaderVersion: String by project

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$minecraftVersion+build.+:v2")

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

    // compat
    modImplementation("com.terraformersmc:modmenu:3.2.2") // fix button shifting

    modImplementation("curse.maven:minimal-menu-405795:3798011") // minimal-menu-1.18.2-0.1.5 - fix bottom right offset
    modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:0.53.0+1.18.2")
    modRuntimeOnly("me.shedaniel.cloth:cloth-config-fabric:6.2.+")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching(listOf("fabric.mod.json", "quilt.mod.json")) {
            expand(
                "version" to project.version
            )
        }
    }

    register("releaseMod") {
        group = "main-menu-credits"

        dependsOn("modrinth")
        dependsOn("modrinthSyncBody")
        dependsOn("curseforge")
        dependsOn("publish")
        dependsOn("githubRelease")
    }
}

val changelogText = file("changelogs/${project.version}.md").takeIf { it.exists() }?.readText() ?: "No changelog provided"

modrinth {
    token.set(findProperty("modrinth.token")?.toString())
    projectId.set("qJDfP7WN")
    versionNumber.set("${project.version}")
    versionType.set("release")
    uploadFile.set(tasks["remapJar"])
    gameVersions.set(listOf(minecraftVersion, "1.19"))
    loaders.set(listOf("fabric", "quilt"))
    changelog.set(changelogText)
    syncBodyFrom.set(file("README.md").readText())
}

if (hasProperty("curseforge.token")) {
    curseforge {
        apiKey = findProperty("curseforge.token")
        project(closureOf<me.hypherionmc.cursegradle.CurseProject> {
            mainArtifact(tasks["remapJar"], closureOf<me.hypherionmc.cursegradle.CurseArtifact> {
                displayName = "${project.version}"
            })

            id = "618812"
            releaseType = "release"
            addGameVersion(minecraftVersion)
            addGameVersion("1.19")
            addGameVersion("Fabric")
            addGameVersion("Quilt")
            addGameVersion("Java 17")

            changelog = changelogText
            changelogType = "markdown"
        })

        options(closureOf<me.hypherionmc.cursegradle.Options> {
            forgeGradleIntegration = false
        })
    }
}

githubRelease {
    token(findProperty("github.token")?.toString())

    owner("isXander")
    repo("main-menu-credits")
    tagName("${project.version}")
    targetCommitish("master")
    body(changelogText)
    releaseAssets(tasks["remapJar"].outputs.files)
}

publishing {
    publications {
        create<MavenPublication>("main-menu-credits") {
            groupId = group.toString()
            artifactId = base.archivesName.get()

            from(components["java"])
        }
    }

    repositories {
        if (hasProperty("xander-repo.username") && hasProperty("xander-repo.password")) {
            maven(url = "https://maven.isxander.dev/releases") {
                credentials {
                    username = property("xander-repo.username")?.toString()
                    password = property("xander-repo.password")?.toString()
                }
            }
        }
    }
}
