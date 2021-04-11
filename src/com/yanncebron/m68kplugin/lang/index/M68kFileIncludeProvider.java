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

package com.yanncebron.m68kplugin.lang.index;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.impl.include.FileIncludeInfo;
import com.intellij.psi.impl.include.FileIncludeProvider;
import com.intellij.util.Consumer;
import com.intellij.util.PathUtilRt;
import com.intellij.util.SmartList;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.text.StringSearcher;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncbinDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncludeDirective;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class M68kFileIncludeProvider extends FileIncludeProvider {

  @Override
  public @NotNull String getId() {
    return "m68k_include";
  }

  @Override
  public int getVersion() {
    return 1;
  }

  @Override
  public boolean acceptFile(@NotNull VirtualFile file) {
    return FileTypeRegistry.getInstance().isFileOfType(file, M68kFileType.INSTANCE);
  }

  @Override
  public void registerFileTypesUsedForIndexing(@NotNull Consumer<? super FileType> fileTypeSink) {
    fileTypeSink.consume(M68kFileType.INSTANCE);
  }

  @Override
  public @NotNull FileIncludeInfo @NotNull [] getIncludeInfos(FileContent content) {
    CharSequence contentAsText = content.getContentAsText();
    if (new StringSearcher("include", false, true).scan(contentAsText) == -1 &&
      new StringSearcher("incbin", false, true).scan(contentAsText) == -1) {
      return FileIncludeInfo.EMPTY;
    }

    // todo get from light tree (PsiDependentFileContent.getLighterAST)
    final List<FileIncludeInfo> result = new SmartList<>();
    content.getPsiFile().acceptChildren(new M68kVisitor() {

      @Override
      public void visitIncbinDirective(@NotNull M68kIncbinDirective o) {
        super.visitIncbinDirective(o);

        final String includePath = o.getIncludePath();
        if (includePath != null) {
          String filename = PathUtilRt.getFileName(includePath);
          final FileIncludeInfo includeInfo = new FileIncludeInfo(filename, includePath, -1, true);
          result.add(includeInfo);
        }
      }

      @Override
      public void visitIncludeDirective(@NotNull M68kIncludeDirective o) {
        super.visitIncludeDirective(o);

        final String includePath = o.getIncludePath();
        if (includePath != null) {
          result.add(new FileIncludeInfo(includePath));
        }
      }
    });
    return result.toArray(FileIncludeInfo.EMPTY);
  }
}
