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

package com.yanncebron.m68kplugin.lang.stubs.impl;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.stubs.M68kLabelStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kLabelStubImpl extends NamedStubBase<M68kLabel> implements M68kLabelStub {

  private final M68kLabelBase.LabelKind labelKind;

  public M68kLabelStubImpl(StubElement parent,
                           @NotNull IStubElementType elementType,
                           @Nullable String name,
                           M68kLabelBase.LabelKind labelKind) {
    super(parent, elementType, name);
    this.labelKind = labelKind;
  }

  @Override
  public M68kLabelBase.LabelKind getLabelKind() {
    return labelKind;
  }

  @Override
  public String toString() {
    return "M68kLabelStubImpl['" + getName() + "', " + getLabelKind() + "]";
  }
}
