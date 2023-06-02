/*
 * Copyright 2023 The Authors
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

import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.impl.include.FileIncludeInfo;
import com.intellij.psi.impl.include.FileIncludeProvider;
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.Consumer;
import com.intellij.util.PathUtilRt;
import com.intellij.util.SmartList;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.PsiDependentFileContent;
import com.intellij.util.text.StringSearcher;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.M68kTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

final class M68kFileIncludeProvider extends FileIncludeProvider {

  @Override
  public @NotNull String getId() {
    return "m68k_include";
  }

  @Override
  public int getVersion() {
    return 3;
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
    CharSequence text = content.getContentAsText();
    int[] offsets = ArrayUtil.mergeArrays(
      new StringSearcher("include", false, true).findAllOccurrences(text),
      new StringSearcher("incbin", false, true).findAllOccurrences(text));
    if (offsets.length == 0) return FileIncludeInfo.EMPTY;

    final List<FileIncludeInfo> result = new SmartList<>();

    LighterAST tree = ((PsiDependentFileContent) content).getLighterAST();
    LightTreeUtil.processLeavesAtOffsets(offsets, tree, (leaf, offset) -> {
      LighterASTNode element = tree.getParent(leaf);
      if (element == null) return;

      final LighterASTNode pathNode = LightTreeUtil.firstChildOfType(tree, element, M68kTokenTypes.STRING);
      if (pathNode == null) return;

      final String path = StringUtil.unquoteString(LightTreeUtil.toFilteredString(tree, pathNode, null));

      if (element.getTokenType() == M68kTypes.INCLUDE_DIRECTIVE) {
        result.add(new FileIncludeInfo(path, offset));
      } else if (element.getTokenType() == M68kTypes.INCBIN_DIRECTIVE) {
        String filename = PathUtilRt.getFileName(path);
        result.add(new FileIncludeInfo(filename, path, offset, true));
      }
    });

    return result.toArray(FileIncludeInfo.EMPTY);
  }
}
