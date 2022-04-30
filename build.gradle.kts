plugins {
    java

    id("fabric-loom") version "0.12.+"
    id("org.quiltmc.quilt-mappings-on-loom") version "4.+"
    id("io.github.juuxel.loom-quiltflower") version "1.7.+"

    id("com.modrinth.minotaur") version "2.+"
    id("com.matthewprenger.cursegradle") version "1.+"
    id("com.github.breadmoirai.github-release") version "2.+"
    `maven-publish`
}

group = "dev.isxander"
version = "1.0.2"

repositories {
    mavenCentral()
}

val minecraftVersion: String by project

dependencies {
    val fabricLoaderVersion: String by project

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.layered {
        officialMojangMappings()
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:$minecraftVersion+build.+:v2"))
    })

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
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
    gameVersions.set(listOf(minecraftVersion, "22w17a"))
    loaders.set(listOf("fabric", "quilt"))
    changelog.set(changelogText)
    syncBodyFrom.set(file("README.md").readText())
}

if (hasProperty("curseforge.token")) {
    curseforge {
        apiKey = findProperty("curseforge.token")
        project(closureOf<com.matthewprenger.cursegradle.CurseProject> {
            mainArtifact(tasks["remapJar"], closureOf<com.matthewprenger.cursegradle.CurseArtifact> {
                displayName = "${project.version}"
            })

            id = "618812"
            releaseType = "release"
            addGameVersion(minecraftVersion)
            addGameVersion("1.19-Snapshot")
            addGameVersion("Fabric")
            addGameVersion("Java 17")

            changelog = changelogText
            changelogType = "markdown"
        })

        options(closureOf<com.matthewprenger.cursegradle.Options> {
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
        if (hasProperty("woverflow.username") && hasProperty("woverflow.token")) {
            maven(url = "https://repo.woverflow.cc/releases") {
                credentials {
                    username = property("woverflow.username")?.toString()
                    password = property("woverflow.token")?.toString()
                }
            }
        }
    }
}
