/*
 * Copyright 2022 The Authors
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

package com.yanncebron.m68kplugin.lang.stubs;

import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.text.StringUtilRt;
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.psi.stubs.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.yanncebron.m68kplugin.lang.M68kFileElementType;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.impl.M68kLabelImpl;
import com.yanncebron.m68kplugin.lang.stubs.impl.M68kLabelStubImpl;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kLabelStubIndex;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kMacroStubIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public interface M68kStubElementTypesHolder {

  IStubElementType<M68kLabelStub, M68kLabel> LABEL =
    new M68kStubElementType<>("LABEL") {

      @NotNull
      @Override
      public M68kLabelStub createStub(@NotNull LighterAST tree, @NotNull LighterASTNode node, @NotNull StubElement<?> parentStub) {
        final LighterASTNode idNode = LightTreeUtil.requiredChildOfType(tree, node, M68kTokenTypes.ID);
        final LighterASTNode parent = tree.getParent(node);
        assert parent != null;

        M68kLabelBase.LabelKind kind;
        String value = null;

        final IElementType tokenType = parent.getTokenType();
        if (tokenType == M68kFileElementType.INSTANCE) {
          kind = M68kLabelBase.LabelKind.GLOBAL;
        } else if (tokenType == M68kTypes.EQU_DIRECTIVE) {
          kind = M68kLabelBase.LabelKind.EQU;
          value = parseValue(tree, parent, M68kTokenTypes.EQU);
        } else if (tokenType == M68kTypes.EQUALS_DIRECTIVE) {
          kind = M68kLabelBase.LabelKind.EQUALS;
          value = parseValue(tree, parent, M68kTokenTypes.EQ_DIRECTIVE);
        } else if (tokenType == M68kTypes.MACRO_DIRECTIVE) {
          kind = M68kLabelBase.LabelKind.MACRO;
        } else if (tokenType == M68kTypes.SET_DIRECTIVE) {
          kind = M68kLabelBase.LabelKind.SET;
          value = parseValue(tree, parent, M68kTokenTypes.SET);
        } else if (tokenType == M68kTypes.EQUR_DIRECTIVE) {
          kind = M68kLabelBase.LabelKind.EQUR;
          value = parseValue(tree, parent, M68kTokenTypes.EQUR);
        } else if (tokenType == M68kTypes.REG_DIRECTIVE) {
          kind = M68kLabelBase.LabelKind.REG;
          value = parseValue(tree, parent, M68kTokenTypes.REG);
        } else if (tokenType == M68kTypes.FO_DIRECTIVE) {
          kind = M68kLabelBase.LabelKind.FO;
          value = parseValue(tree, parent, M68kTokenTypes.FO);
        } else if (tokenType == M68kTypes.SO_DIRECTIVE) {
          kind = M68kLabelBase.LabelKind.SO;
          value = parseValue(tree, parent, M68kTokenTypes.SO);
        } else {
          throw new IllegalArgumentException("unknown parent token type: " + tokenType);
        }

        return new M68kLabelStubImpl(parentStub, this,
          LightTreeUtil.toFilteredString(tree, idNode, null),
          kind, value);
      }

      @Override
      public void serialize(@NotNull M68kLabelStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        final M68kLabelBase.LabelKind labelKind = stub.getLabelKind();
        dataStream.writeByte(labelKind.ordinal());
        if (labelKind.hasValue()) {
          dataStream.writeUTFFast(StringUtilRt.notNullize(stub.getValue()));
        }
      }

      @NotNull
      @Override
      public M68kLabelStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final String name = dataStream.readNameString();

        final short ordinal = dataStream.readByte();
        M68kLabelBase.LabelKind kind = null;
        for (M68kLabelBase.LabelKind value : M68kLabelBase.LabelKind.values()) {
          if (value.ordinal() == ordinal) {
            kind = value;
            break;
          }
        }
        assert kind != null : ordinal;

        String value = kind.hasValue() ? dataStream.readUTFFast() : null;
        return new M68kLabelStubImpl(parentStub, this, name, kind, value);
      }

      @Override
      public void indexStub(@NotNull M68kLabelStub stub, @NotNull IndexSink sink) {
        final String name = stub.getName();
        if (name != null) {
          // todo do not put broken parsing results into index
          if (name.startsWith(".")) {
            if (!ApplicationManager.getApplication().isUnitTestMode()) {
              System.out.println("skipping wrong label '" + name + "'");
            }
            return;
          }
          sink.occurrence(M68kLabelStubIndex.KEY, name);

          if (stub.getLabelKind() == M68kLabelBase.LabelKind.MACRO) {
            sink.occurrence(M68kMacroStubIndex.KEY, name);
          }
        }
      }

      @Override
      public M68kLabel createPsi(@NotNull M68kLabelStub stub) {
        return new M68kLabelImpl(stub, this);
      }

      @Nullable
      private String parseValue(@NotNull LighterAST tree,
                                @NotNull LighterASTNode node,
                                IElementType valueDelimiterTokenType) {
        boolean expectingInit = false;
        for (LighterASTNode child : tree.getChildren(node)) {
          final IElementType type = child.getTokenType();
          if (type == valueDelimiterTokenType) {
            expectingInit = true;
          } else if (expectingInit &&
            !TokenSet.WHITE_SPACE.contains(type) &&
            !M68kTokenGroups.DATA_SIZES.contains(type)) {
            return LightTreeUtil.toFilteredString(tree, child, null);
          }
        }
        return null;
      }

    };
}
