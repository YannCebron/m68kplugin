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

package com.yanncebron.m68kplugin.settings.ide

import com.intellij.model.search.SearchContext
import com.intellij.model.search.SearchService
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.yanncebron.m68kplugin.lang.M68kFileType

/**
 * Detect the matching target platform from project information (files, text search, ...).
 * The project is guaranteed to contain at least one assembly source file.
 */
@Suppress("UnstableApiUsage")
abstract class M68kTargetPlatformDetector {

    abstract fun detect(project: Project): M68kTargetPlatform?

    /**
     * @return if any given word (case-insensitive) was found inside code context of an assembly source file
     */
    protected fun findAnyWord(project: Project, vararg words: String): Boolean {
        for (word in words) {
            val query = SearchService.getInstance().searchWord(project, word)
                .caseSensitive(false)
                .inScope(GlobalSearchScope.allScope(project))
                .restrictFileTypes(M68kFileType.INSTANCE)
                .inContexts(SearchContext.IN_CODE)
                .buildLeafOccurrenceQuery()

            if (query.findFirst() != null) {
                return true
            }

        }
        return false
    }
}