import java.util.*

plugins {
    kotlin("jvm") version "2.0.21"

    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "com.zp4rker"
description = "The revolutionary space-exploration plugin."
val mcVersion = "1.21.1"
project.ext["mcVersion"] = mcVersion

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
    }

    tasks.jar {
        archiveFileName = "${rootProject.name}-${this@subprojects.name}-${this@subprojects.version}.jar"
    }

    tasks.processResources {
        filteringCharset = "UTF-8"
        filesMatching("**/plugin.yml") {
            val capitalised = this@subprojects.name.replaceFirstChar { it.uppercase(Locale.getDefault()) }
            expand(
                "pluginName" to "InfinityAndBeyond-${capitalised}",
                "version" to this@subprojects.version,
                "description" to "${rootProject.description} [${this@subprojects.name}]",
                "pluginPrefix" to "IAB-${capitalised}",
                "mcVersion" to mcVersion
            )
        }
    }
}

evaluationDependsOnChildren()
runPaper.disablePluginJarDetection()
subprojects.forEach {
    try {
        tasks.runServer.get().pluginJars(it.tasks["shadowJar"].property("archiveFile")!!)
    } catch (e: Exception) {
        tasks.runServer.get().pluginJars(it.tasks["jar"].property("archiveFile")!!)
    }
}
tasks.runServer {
    dependsOn(subprojects.map { it.tasks.build })
    minecraftVersion(mcVersion)
}

gradle.startParameter.excludedTaskNames += ":build"