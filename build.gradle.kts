/*
 * Copyright 2026 The Authors
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
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.PrepareSandboxTask
import org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask.FailureLevel

fun properties(key: String) = providers.gradleProperty(key).get()

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.intellij.platform.grammarkit")
    id("org.jetbrains.changelog")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    intellijPlatform {
        intellijIdea(properties("platformVersion"))
        bundledModule("intellij.spellchecker")

        pluginComposedModule(implementation(project(":plugin-api")))
        pluginComposedModule(implementation(project(":psi-api")))
        pluginComposedModule(implementation(project(":amiga")))

        testFrameworks(TestFrameworkType.Platform, TestFrameworkType.Plugin.NavBar)
        testBundledPlugin("tanvd.grazi") // spellchecker

        jflex("1.10.17")
        grammarKit("2023.3.3")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.intellij.platform.module")
}

intellijPlatform {
    pluginVerification {
        ides {
            recommended()
        }
        failureLevel.set(listOf(FailureLevel.COMPATIBILITY_PROBLEMS))
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

idea {
    module {
        generatedSourceDirs.add(file("gen"))
    }
}

tasks {

    wrapper {
        gradleVersion = properties("gradleVersion")
    }

    generateLexer {
        sourceFile.set(file("src/grammar/_M68kLexer.flex"))
        skeleton.set(file("src/grammar/idea-flex.skeleton"))
        targetRootOutputDir.set(file("gen"))
    }

    // disable all bundled plugins except those we need
    withType<PrepareSandboxTask> {
        disabledPlugins.addAll(provider {
            intellijPlatform.productInfo.bundledPlugins.filter {
                it != "tanvd.grazi"
            }
        })
    }
}
