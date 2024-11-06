plugins {
    kotlin("jvm") version "2.0.21"

    id("com.gradleup.shadow") version "8.3.3"

    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "com.zp4rker"
description = "The revolutionary space-exploration plugin"
version = "0.0.1-alpha"
val mcVersion = "1.21.1"
project.ext["mcVersion"] = mcVersion

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    api("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
    api("com.zp4rker:bukkot:2.1.1-k2.0.21")

    api("co.aikar:acf-paper:0.5.1-SNAPSHOT")

    api("com.j256.ormlite:ormlite-jdbc:6.1")
    compileOnly("org.apache.logging.log4j:log4j-core:2.22.1") // To change ormlite logger level

    api("org.jetbrains:annotations:24.1.0")
}

tasks {
    jar {
        archiveClassifier.set("original")
    }

    shadowJar {
        archiveClassifier.set("")
        relocate("co.aikar.acf", "com.zp4rker.infinityandbeyond.acf")
        relocate("co.aikar.locales", "com.zp4rker.infinityandbeyond.locales")
    }
}

tasks.processResources {
    filteringCharset = "UTF-8"
    filesMatching("**/plugin.yml") {
        expand(
            "pluginName" to "InfinityAndBeyond",
            "version" to project.version,
            "description" to "${project.description} [${project.name}]",
            "pluginPrefix" to "IAB",
            "mcVersion" to mcVersion
        )
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://repo.aikar.co/content/groups/aikar/")
    }

    dependencies {
        compileOnly(rootProject)
    }

    tasks.jar {
        archiveFileName = "${rootProject.name}-${this@subprojects.name}-${rootProject.version}.jar"
    }

    tasks.processResources {
        filteringCharset = "UTF-8"
        filesMatching("**/plugin.yml") {
            expand(
                "pluginName" to "InfinityAndBeyond-${this@subprojects.name}",
                "version" to rootProject.version,
                "description" to "${rootProject.description} [${this@subprojects.name}]",
                "pluginPrefix" to "IAB-${this@subprojects.name}",
                "mcVersion" to mcVersion
            )
        }
    }
}

subprojects.forEach {
    try {
        tasks.runServer.get().pluginJars(it.tasks["shadowJar"].property("archiveFile")!!)
    } catch (e: Exception) {
        tasks.runServer.get().pluginJars(it.tasks["jar"].property("archiveFile")!!)
    }
}

tasks.runServer {
    minecraftVersion(mcVersion)
    downloadPlugins {
        github("zp4rker", "bukkot", "v2.1.1-k2.0.21", "bukkot-2.1.1-k2.0.21.jar")
    }
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}