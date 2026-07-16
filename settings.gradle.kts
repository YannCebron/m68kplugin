@file:Suppress("UnstableApiUsage")
import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

rootProject.name = "m68kplugin"

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.4.10"
        id("org.jetbrains.changelog") version "2.5.0"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("org.jetbrains.intellij.platform.settings") version "2.18.1"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()

        intellijPlatform {
            defaultRepositories()
        }
    }
}

include(":plugin-api")
project(":plugin-api").projectDir = File("api/plugin-api")

include(":amiga")
project(":amiga").projectDir = File("platforms/amiga")