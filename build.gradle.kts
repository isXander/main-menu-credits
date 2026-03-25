plugins {
    `java-library`
    id("dev.isxander.modstitch.base") version "0.8.4"

    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
    id("dev.isxander.secrets") version "0.1.0"
    `maven-publish`
    signing
    id("com.gradleup.nmcp") version "1.4.4"
    id("com.gradleup.nmcp.aggregation") version "1.4.4"
}

modstitch {
    minecraftVersion = "26.1"

    metadata {
        modId = "isxander-main-menu-credits"
        modName = "Main Menu Credits"
        modVersion = "1.3.0"
        modGroup = "dev.isxander"
        modDescription = "Add text to the main menu."
        modAuthor = "isXander"
        modLicense = "LGPLv3"
    }

    loom {
        fabricLoaderVersion = "0.18.5"
    }

    mixin {
        addMixinsToModManifest = true
        configs.register("isxander-main-menu-credits")
    }
}

repositories {
    mavenCentral()
    maven("https://maven.terraformersmc.com/releases")
    maven("https://maven.nucleoid.xyz/releases")
}

dependencies {
    modstitchModImplementation("com.terraformersmc:modmenu:18.0.0-alpha.7")

    nmcpAggregation(project)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.javadoc {
    isFailOnError = false
}


// ------- PUBLISHING ---------

val modVersion = modstitch.metadata.modVersion.get()
val mcVersion = modstitch.minecraftVersion.get()

publishMods {
    dryRun.set(false)

    displayName.set("$modVersion for Fabric $mcVersion")

    file = modstitch.finalJarTask.flatMap { it.archiveFile }

    fun versionList(prop: String) = findProperty(prop)?.toString()
        ?.split(',')
        ?.map { it.trim() }
        ?: emptyList()

    // modrinth and curseforge use different formats for snapshots. this can be expressed globally
    val stableMCVersions = versionList("pub.stableMC")

    changelog = rootProject.file("changelog.md").readText()
    type = STABLE

    modLoaders.add("fabric")

    modrinth {
        projectId = providers.gradleProperty("pub.modrinthId")
        accessToken = secrets.gradleProperty("modrinth.accessToken")

        minecraftVersions.addAll(stableMCVersions)
        minecraftVersions.addAll(versionList("pub.modrinthMC"))

        announcementTitle = "Download $mcVersion for Fabric from Modrinth"
    }

    curseforge {
        projectId = providers.gradleProperty("pub.curseforgeId")
        projectSlug = providers.gradleProperty("pub.curseforgeSlug")
        accessToken = secrets.gradleProperty("curseforge.accessToken")

        minecraftVersions.addAll(stableMCVersions)
        minecraftVersions.addAll(versionList("pub.curseMC"))

        announcementTitle = "Download $mcVersion for Fabric from CurseForge"
    }
}

publishing {
    publications {
        register<MavenPublication>("mod") {
            from(components["java"])

            groupId = "dev.isxander"
            artifactId = "main-menu-credits"
            version = modstitch.metadata.modVersion.get()

            pom {
                name = modstitch.metadata.modName
                description = modstitch.metadata.modDescription
                url = "https://www.isxander.dev/projects/main-menu-credits"
                licenses {
                    license {
                        name = "LGPL-3.0-or-later"
                        url = "https://www.gnu.org/licenses/lgpl-3.0.en.html"
                    }
                }
                developers {
                    developer {
                        id = "isXander"
                        name = "Xander"
                        email = "business@isxander.dev"
                    }
                }
                scm {
                    url = "https://github.com/isXander/main-menu-credits"
                    connection = "scm:git:git//github.com/isXander/main-menu-credits.git"
                    developerConnection = "scm:git:ssh://git@github.com/isXander/main-menu-credits.git"
                }
            }
        }
    }
    repositories {
        mavenLocal()
    }
}

val signingKeyProvider = secrets.gradleProperty("signing.secretKey")
val signingPasswordProvider = secrets.gradleProperty("signing.password")
signing {
    sign(publishing.publications["mod"])
}
// not configuration cache friendly, but neither is the whole of signing plugin
// this plugin does not support lazy configuration of signing keys
gradle.taskGraph.whenReady {
    val willSign = allTasks.any { it.name.startsWith("sign") }
    if (willSign) {
        signing {
            val signingKey = signingKeyProvider.orNull
            val signingPassword = signingPasswordProvider.orNull

            isRequired = signingKey != null && signingPassword != null
            if (isRequired) {
                useInMemoryPgpKeys(signingKey, signingPassword)
            } else {
                logger.error("Signing keys not found; skipping signing!")
            }
        }
    }
}

nmcpAggregation {
    centralPortal {
        username = secrets.gradleProperty("mcentral.username")
        password = secrets.gradleProperty("mcentral.password")

        publicationName = "main-menu-credits:$version"
    }
}