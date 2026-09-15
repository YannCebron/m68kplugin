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

}

val runIdeWithoutBundledPlugins by intellijPlatformTesting.runIde.registering {
    plugins {
        disablePlugins(
            "AngularJS",
            "ByteCodeViewer",
            "Coverage",
            "Docker",
            "Git4Idea",
            "HtmlTools",
            "JBoss",
            "JSIntentionPowerPack",
            "JUnit",
            "JavaScript",
            "JavaScriptDebugger",
            "Karma",
            "Lombook Plugin",
            "NodeJS",
            "PerforceDirectPlugin",
            "Refactor-X",
            "Subversion",
            "TestNG-J",
            "Tomcat",
            "XPathView",
            "com.android.tools.gradle.dcl",
            "com.deadlock.scsyntax",
            "com.intellij",
            "com.intellij.LineProfiler",
            "com.intellij.aop",
            "com.intellij.beanValidation",
            "com.intellij.cdi",
            "com.intellij.code.provenance",
            "com.intellij.completion.ml.ranking",
            "com.intellij.compose",
            "com.intellij.configurationScript",
            "com.intellij.copyright",
            "com.intellij.cron",
            "com.intellij.css",
            "com.intellij.database",
            "com.intellij.debugger.collections.visualizer",
            "com.intellij.dev",
            "com.intellij.diagram",
            "com.intellij.findusages.ml",
            "com.intellij.flyway",
            "com.intellij.freemarker",
            "com.intellij.gradle",
            "com.intellij.hibernate",
            "com.intellij.ja",
            "com.intellij.java",
            "com.intellij.java-i18n",
            "com.intellij.java.ide",
            "com.intellij.javaee",
            "com.intellij.javaee.app.servers.integration",
            "com.intellij.javaee.el",
            "com.intellij.javaee.extensions",
            "com.intellij.javaee.jakarta.data",
            "com.intellij.javaee.jpa",
            "com.intellij.javaee.reverseEngineering",
            "com.intellij.javaee.web",
            "com.intellij.jpa.jpb.model",
            "com.intellij.jsonpath",
            "com.intellij.jsp",
            "com.intellij.ko",
            "com.intellij.kubernetes",
            "com.intellij.liquibase",
            "com.intellij.mcpServer",
            "com.intellij.micronaut",
            "com.intellij.microservices.jvm",
            "com.intellij.microservices.ui",
            "com.intellij.modules.json",
            "com.intellij.modules.ultimate",
            "com.intellij.notebooks.core",
            "com.intellij.persistence",
            "com.intellij.platform.images",
            "com.intellij.plugins.eclipsekeymap",
            "com.intellij.plugins.netbeanskeymap",
            "com.intellij.plugins.visualstudiokeymap",
            "com.intellij.plugins.webcomponents",
            "com.intellij.properties",
            "com.intellij.quarkus",
            "com.intellij.react",
            "com.intellij.searcheverywhere.ml",
            "com.intellij.settingsSync",
            "com.intellij.spring",
            "com.intellij.spring.boot",
            "com.intellij.spring.boot.initializr",
            "com.intellij.spring.cloud",
            "com.intellij.spring.data",
            "com.intellij.spring.integration",
            "com.intellij.spring.messaging",
            "com.intellij.spring.modulith",
            "com.intellij.spring.mvc",
            "com.intellij.spring.security",
            "com.intellij.stylelint",
            "com.intellij.swagger",
            "com.intellij.tailwindcss",
            "com.intellij.tasks",
            "com.intellij.tasks.timeTracking",
            "com.intellij.thymeleaf",
            "com.intellij.velocity",
            "com.intellij.zh",
            "com.jetbrains.codeWithMe",
            "com.jetbrains.gateway",
            "com.jetbrains.performancePlugin",
            "com.jetbrains.performancePlugin.async",
            "com.jetbrains.plugins.webDeployment",
            "com.jetbrains.remoteDevServer",
            "com.jetbrains.restClient",
            "com.jetbrains.restWebServices",
            "com.jetbrains.sh",
            "com.jetbrains.station",
            "hg4idea",
            "idea.plugin.protoeditor",
            "intellij.git.commit.modal",
            "intellij.grid.plugin",
            "intellij.indexing.shared",
            "intellij.indexing.shared.core",
            "intellij.jupyter",
            "intellij.ktor",
            "intellij.nextjs",
            "intellij.platform.ijent.impl",
            "intellij.prettierJS",
            "intellij.vitejs",
            "intellij.webp",
            "intellij.webpack",
            "org.editorconfig.editorconfigjetbrains",
            "org.intellij.groovy",
            "org.intellij.plugins.markdown",
            "org.intellij.plugins.postcss",
            "org.intellij.qodana",
            "org.jetbrains.completion.full.line",
            "org.jetbrains.debugger.streams",
            "org.jetbrains.idea.eclipse",
            "org.jetbrains.idea.gradle.dsl",
            "org.jetbrains.idea.maven",
            "org.jetbrains.idea.reposearch",
            "org.jetbrains.java.decompiler",
            "org.jetbrains.kotlin",
            "org.jetbrains.plugins.docker.gateway",
            "org.jetbrains.plugins.github",
            "org.jetbrains.plugins.gitlab",
            "org.jetbrains.plugins.gradle",
            "org.jetbrains.plugins.javaFX",
            "org.jetbrains.plugins.kotlin.jupyter",
            "org.jetbrains.plugins.less",
            "org.jetbrains.plugins.node-remote-interpreter",
            "org.jetbrains.plugins.remote-run",
            "org.jetbrains.plugins.sass",
            "org.jetbrains.plugins.terminal",
            "org.jetbrains.plugins.textmate",
            "org.jetbrains.plugins.vue",
            "org.jetbrains.plugins.yaml",
            "org.jetbrains.security.package-checker",
            "org.toml.lang",
            "training",
            "tslint"
        )
    }
}
