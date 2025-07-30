plugins {
    java

    id("fabric-loom") version "1.11.+"

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
}

val minecraftVersion: String by project

dependencies {
    val fabricLoaderVersion: String by project

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$minecraftVersion+build.1:v2")

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

    // compat
    modImplementation("com.terraformersmc:modmenu:15.0.0-beta.3") // fix button shifting

    modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:0.130.0+1.21.8")
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
    gameVersions.set(listOf("1.21.8", "1.21.7", "1.21.6"))
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
            addGameVersion("1.21.8")
            addGameVersion("1.21.7")
            addGameVersion("1.21.6")
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
