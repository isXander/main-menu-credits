plugins {
    `java-library`

    alias(libs.plugins.fabric.loom) apply false
    alias(libs.plugins.neoforged.gradle) apply false

    alias(libs.plugins.modstitch.multiloader)
    alias(libs.plugins.modstitch.manifests)
    alias(libs.plugins.modstitch.modrepos)

    `maven-publish`
    alias(libs.plugins.mod.publish.plugin)
    alias(libs.plugins.central.portal.publishing)
    signing

    alias(libs.plugins.spotless)
}

val minecraftVersion = libs.versions.minecraft.get()

group = "dev.isxander"
version = "1.4.0+$minecraftVersion"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
    isxander()
    terraformersMC()
}

dependencies {
    minecraft(libs.minecraft)
    fabricLoader(libs.fabric.loader)

    fabricImplementation(libs.mod.menu)

    neoforgeImplementation(libs.neoforge)
}

runs.register("neoforgeClient") {
    runType("client")
}

val minecraftVersionRange = "[26.1,)"
val supportedMinecraftVersions = manifests.minecraftReleasesMatching(minecraftVersionRange)

manifests {
    val common = manifest {
        modId = providers.gradleProperty("mod.id")
        version = project.version.toString()
        displayName = providers.gradleProperty("mod.name")
        description = providers.gradleProperty("mod.description")
        authors.add("isXander")
        iconPath = "icon.png"
        licenses.add("MPL-2.0")
        issueTrackerUrl = providers.gradleProperty("mod.issuesUrl")
        sourcesUrl = providers.gradleProperty("mod.sourcesUrl")
        homepage = sourcesUrl
        mixin("main_menu_credits.mixins.json")
        dependency("minecraft", REQUIRED, minecraftVersionRange)
    }

    fabricModJson(sourceSets.fabric.get()) {
        from(common)
        provides.add("isxander-main-menu-credits") // legacy mod id
        environment = CLIENT
        entrypoint("client", "dev.isxander.mainmenucredits.fabric.MainMenuCreditsFabric")
        dependency("fabricloader", REQUIRED, "[0.19,)")
        mixin("main_menu_credits.fabric.mixins.json")
    }

    neoForgeModsToml(sourceSets.neoforge.get()) {
        from(common)
    }
}

val clientGameTestManifest = manifests.fabricModJson {
    modId = "main_menu_credits_test"
    displayName = "Main Menu Credits Test"
    version = "1.0.0"
    environment = CLIENT
    entrypoint("fabric-client-gametest", "dev.isxander.mainmenucredits.test.MainMenuCreditsTest")
}

fabricApi {
    @Suppress("UnstableApiUsage")
    configureTests {
        createSourceSet = true
        modId = clientGameTestManifest.modId
        enableGameTests = false
        enableClientGameTests = true
        eula = true
    }
}

manifests.fabricModJson(sourceSets.getByName("gametest")) {
    from(clientGameTestManifest)
}

sourceSets.named("gametest") {
    compileClasspath += sourceSets.fabric.get().output
    runtimeClasspath += sourceSets.fabric.get().output
}
configurations.named("gametestCompileClasspath") {
    extendsFrom(configurations.named("fabricCompileClasspath"))
}
configurations.named("gametestRuntimeClasspath") {
    extendsFrom(configurations.named("fabricRuntimeClasspath"))
}

tasks.withType<Jar>().configureEach {
    from(rootProject.file("LICENSE")) {
        into("META-INF")
    }
}

tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:none", true)
}

publishMods {
    file = tasks.universalJar.flatMap { it.archiveFile }
    additionalFiles.from(tasks.fabricJar.flatMap { it.archiveFile })
    additionalFiles.from(tasks.neoforgeJar.flatMap { it.archiveFile })

    val projectVersion = project.version.toString()

    displayName = providers.gradleProperty("mod.name").map { "$it $projectVersion" }
    version = project.version.toString()
    modLoaders.addAll("fabric", "neoforge")
    type = STABLE

    changelog = providers.fileContents(rootProject.layout.projectDirectory.file("CHANGELOG.md")).asText
        .map { it.replace("{version}", projectVersion) }

    modrinth {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = providers.gradleProperty("modrinth.id")
        minecraftVersions.addAll(supportedMinecraftVersions)
        announcementTitle = "Download from Modrinth"
    }

    curseforge {
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        projectId = providers.gradleProperty("curseforge.id")
        projectSlug = providers.gradleProperty("curseforge.slug")
        minecraftVersions.addAll(supportedMinecraftVersions)
        announcementTitle = "Download from Curseforge"
        client = true
        javaVersions.add(JavaVersion.VERSION_25)
    }

    discord {
        webhookUrl = providers.environmentVariable("DISCORD_WEBHOOK_URL")
        dryRunWebhookUrl = providers.environmentVariable("DISCORD_WEBHOOK_URL_DRY_RUN")
        username = providers.gradleProperty("mod.name")
        avatarUrl = "https://raw.githubusercontent.com/isXander/main-menu-credits/main/src/main/resources/icon.png"
        content = changelog.zip(providers.gradleProperty("discord.ping")) { c, p -> "$c\n\n$p" }
    }
}

centralPortalPublishing.bundle("main") {
    username = providers.environmentVariable("MAVEN_CENTRAL_USERNAME")
    password = providers.environmentVariable("MAVEN_CENTRAL_PASSWORD")

    publishingType = "AUTOMATIC"
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks.universalJar)
            artifact(tasks.universalSourcesJar)
        }
    }

    repositories {
        // Darn you, Kotlin!
        val repos = this as ExtensionAware
        repos.extensions.getByType<dev.lukebemish.centralportalpublishing.CentralPortalRepositoryHandlerExtension>()
            .portalBundle(":", "main")
    }
}

val shouldSign = providers.environmentVariable("SIGN")
    .map { it.toBoolean() }
    .orElse(false)

signing {
    isRequired = shouldSign.get()
    useInMemoryPgpKeys(
        providers.environmentVariable("GPG_PRIVATE_KEY").orNull,
        providers.environmentVariable("GPG_PASSPHRASE").orNull,
    )
    sign(publishing.publications["mavenJava"])
}

tasks.register("publishMainMenuCredits") {
    group = "publishing"
    dependsOn("publishMods")
    dependsOn("publishMavenJavaPublicationToCentralPortalMainRepository")
}

spotless {
    java {
        target("src/**/*.java")
        licenseHeaderFile(rootProject.layout.projectDirectory.file("HEADER"))

        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
        formatAnnotations()
        leadingSpacesToTabs(4)
    }
}
