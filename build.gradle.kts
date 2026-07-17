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
import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask.FailureLevel

fun properties(key: String) = providers.gradleProperty(key).get()

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.intellij.platform.grammarkit")
    id("org.jetbrains.changelog")
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    intellijPlatform {
        intellijIdeaCommunity(properties("platformVersion"))

        pluginComposedModule(implementation(project(":plugin-api")))
        pluginComposedModule(implementation(project(":amiga")))
        testFramework(TestFrameworkType.Platform)

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
            select {
                types = listOf(IntelliJPlatformType.IntellijIdea)
                sinceBuild = "253"
            }
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

}
