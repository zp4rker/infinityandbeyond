import java.util.*

plugins {
    kotlin("jvm") version "2.0.21"

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
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
    compileOnly("com.zp4rker:bukkot:2.1.0-k2.0.21")

    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")
    compileOnly("org.apache.logging.log4j:log4j-core:2.22.1") // To change ormlite logger level

    compileOnly("org.jetbrains:annotations:24.1.0")
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

// subprojects {
//     apply(plugin = "org.jetbrains.kotlin.jvm")
//
//     repositories {
//         mavenCentral()
//         maven("https://papermc.io/repo/repository/maven-public/")
//     }
//
//     dependencies {
//         compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
//         compileOnly("com.zp4rker:bukkot:2.1.0-k2.0.21")
//     }
//
//     tasks.jar {
//         archiveFileName = "${rootProject.name}-${this@subprojects.name}-${this@subprojects.version}.jar"
//     }
//
//     tasks.processResources {
//         filteringCharset = "UTF-8"
//         filesMatching("**/plugin.yml") {
//             val capitalised = this@subprojects.name.replaceFirstChar { it.uppercase(Locale.getDefault()) }
//             expand(
//                 "pluginName" to "InfinityAndBeyond-${capitalised}",
//                 "version" to this@subprojects.version,
//                 "description" to "${rootProject.description} [${this@subprojects.name}]",
//                 "pluginPrefix" to "IAB-${capitalised}",
//                 "mcVersion" to mcVersion
//             )
//         }
//     }
// }
//
// evaluationDependsOnChildren()
// subprojects.forEach {
//     try {
//         tasks.runServer.get().pluginJars(it.tasks["shadowJar"].property("archiveFile")!!)
//     } catch (e: Exception) {
//         tasks.runServer.get().pluginJars(it.tasks["jar"].property("archiveFile")!!)
//     }
// }

tasks.runServer {
    // dependsOn(subprojects.map { it.tasks.build })
    minecraftVersion(mcVersion)
    downloadPlugins {
        github("zp4rker", "bukkot", "v2.0.0-k2.0.21", "bukkot-2.0.0-k2.0.21.jar")
    }
}