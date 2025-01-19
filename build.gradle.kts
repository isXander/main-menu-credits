plugins {
    java

    id("fabric-loom") version "1.9.+"

    id("com.modrinth.minotaur") version "2.+"
    id("me.hypherionmc.cursegradle") version "2.+"
    id("com.github.breadmoirai.github-release") version "2.+"
    `maven-publish`
}

group = "dev.isxander"
version = "1.2.0"

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
    mappings("net.fabricmc:yarn:$minecraftVersion+build.2")

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

    // compat
    modImplementation("com.terraformersmc:modmenu:13.0.0") // fix button shifting

    modCompileOnly("curse.maven:minimal-menu-405795:3826009") // minimal-menu-1.19-0.1.5 - fix bottom right offset
    modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:0.114.3+1.21.4")
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

java.withSourcesJar()

val changelogText = file("changelogs/${project.version}.md").takeIf { it.exists() }?.readText() ?: "No changelog provided"

modrinth {
    token.set(findProperty("modrinth.token")?.toString())
    projectId.set("qJDfP7WN")
    versionNumber.set("${project.version}")
    versionType.set("release")
    uploadFile.set(tasks["remapJar"])
    gameVersions.set(listOf(minecraftVersion, "1.21.4"))
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
            addGameVersion("1.21.4")
            addGameVersion("Fabric")
            addGameVersion("Quilt")
            addGameVersion("Java 21")

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

    owner.set("isXander")
    repo.set("main-menu-credits")
    tagName.set("${project.version}")
    targetCommitish.set("master")
    body.set(changelogText)
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
