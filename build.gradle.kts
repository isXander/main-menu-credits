plugins {
    java
    id("fabric-loom") version "0.12.+"
    id("org.quiltmc.quilt-mappings-on-loom") version "4.+"
    id("io.github.juuxel.loom-quiltflower") version "1.7.+"
}

group = "dev.isxander"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    val minecraftVersion: String by project
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
}