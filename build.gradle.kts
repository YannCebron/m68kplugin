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
import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.intellij.tasks.RunPluginVerifierTask.FailureLevel

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    // Java support
    id("java")
    id("idea")
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.2.0"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "1.3.1"
    // GrammarKit
    id("org.jetbrains.grammarkit") version "2021.1.3"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

dependencies {
    implementation("org.commonmark", "commonmark", "0.18.0")
    implementation("org.commonmark", "commonmark-ext-gfm-tables", "0.18.0")
    testImplementation("junit", "junit", "4.13.2")
}

// Configure project's dependencies
repositories {
    mavenCentral()
}

idea {
    module {
        generatedSourceDirs.add(file("gen"))
        excludeDirs.add(file(".sandbox"))
    }
}

sourceSets {
    main {
        java.srcDirs("src", "gen")
        resources.srcDirs("plugin-resources")
    }
    test {
        java.srcDirs("tests")
    }
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))
    updateSinceUntilBuild.set(true)

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))

    sandboxDir.set(rootDir.canonicalPath + "/.sandbox")
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version.set(properties("pluginVersion"))
    groups.set(emptyList())
}

grammarKit {
    jflexRelease = "1.7.0-2"
}

tasks {
    // Set the JVM compatibility versions
    properties("javaVersion").let {
        withType<JavaCompile> {
            sourceCompatibility = it
            targetCompatibility = it
        }
    }

    wrapper {
        gradleVersion = properties("gradleVersion")
    }


    task<GenerateLexer>("generateM68KLexer") {
        source = "src/grammar/_M68kLexer.flex"
        skeleton = "src/grammar/idea-flex.skeleton"
        targetDir = "gen/com/yanncebron/m68kplugin/lexer/"
        targetClass = "_M68kLexer"
        purgeOldFiles = true
    }

    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))

        // Get the latest available change notes from the changelog file
        changeNotes.set(provider {
            changelog.run {
                getOrNull(properties("pluginVersion")) ?: getLatest()
            }.toHTML()
        })
    }

    runIde {
        if (project.hasProperty("ideDir")) {
            ideDir.set(file(project.property("ideDir")!!))
            jbrVersion.set(project.property("ideJBR")!! as String)
        }
        autoReloadPlugins.set(false)
    }

    runPluginVerifier {
        ideVersions.set(properties("pluginVerifierIdeVersions").split(',').map(String::trim).filter(String::isNotEmpty))
        failureLevel.set(listOf(FailureLevel.COMPATIBILITY_PROBLEMS))
    }

    // Configure UI tests plugin
    // Read more: https://github.com/JetBrains/intellij-ui-test-robot
    runIdeForUiTests {
        systemProperty("robot-server.port", "8082")
        systemProperty("ide.mac.message.dialogs.as.sheets", "false")
        systemProperty("jb.privacy.policy.text", "<!--999.999-->")
        systemProperty("jb.consents.confirmation.enabled", "false")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token.set(System.getenv("PUBLISH_TOKEN"))
        // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(listOf(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first()))
    }
}
