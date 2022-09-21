/*
 * Copyright 2021 The Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// basic Java setup, set JAR file name, disable non-main plugin module tasks

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("java")

    id("idea")

    // Gradle IntelliJ Plugin, configured in buildSrc/build.gradle.kts
    id("org.jetbrains.intellij")
}

version = properties("pluginVersion")

tasks {
    // Set the JVM compatibility versions
    properties("javaVersion").let {
        withType<JavaCompile> {
            sourceCompatibility = it
            targetCompatibility = it
        }
    }

    jar {
        archiveBaseName.set(properties("pluginName") + "-" + project.name)
    }


    prepareSandbox {
        enabled = false
    }

    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        enabled = false
    }

    runIde {
        enabled = false
    }

    runPluginVerifier {
        enabled = false
    }

    signPlugin {
        enabled = false
    }

    publishPlugin {
        enabled = false
    }

    listProductsReleases {
        enabled = false
    }
    
    verifyPlugin {
        enabled = false
    }

    verifyPluginConfiguration {
        enabled = false
    }

}

repositories {
    mavenCentral()
}

intellij {
    type.set(properties("platformType"))
    version.set(properties("platformVersion"))
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}