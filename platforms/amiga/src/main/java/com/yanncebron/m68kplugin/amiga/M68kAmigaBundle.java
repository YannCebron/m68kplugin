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

package com.yanncebron.m68kplugin.amiga;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.function.Supplier;

public class M68kAmigaBundle extends AbstractBundle {

  @NonNls
  private static final String PATH_TO_BUNDLE = "messages.M68kAmigaBundle";

  private static final M68kAmigaBundle INSTANCE = new M68kAmigaBundle();

  private M68kAmigaBundle() {
    super(PATH_TO_BUNDLE);
  }

  public static String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, Object @NotNull ... params) {
    return INSTANCE.getMessage(key, params);
  }

  public static Supplier<String> messagePointer(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, Object @NotNull ... params) {
    return INSTANCE.getLazyMessage(key, params);
  }

}
