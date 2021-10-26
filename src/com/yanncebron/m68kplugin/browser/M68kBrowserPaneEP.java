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

package com.yanncebron.m68kplugin.browser;

import com.intellij.DynamicBundle;
import com.intellij.serviceContainer.BaseKeyedLazyInstance;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public final class M68kBrowserPaneEP extends BaseKeyedLazyInstance<M68kBrowserPaneBase<?>> {

  @Attribute("displayName")
  public String displayName;

  @Attribute("bundle")
  public String bundle;

  @Attribute("implementation")
  public String implementation;

  public String getDisplayName() {
    String baseName = bundle != null ? bundle : getPluginDescriptor().getResourceBundleBaseName();
    assert baseName != null;

    ResourceBundle bundle = DynamicBundle.INSTANCE.getResourceBundle(baseName, getLoaderForClass());
    return bundle.getString(displayName);
  }

  @Override
  protected @Nullable String getImplementationClassName() {
    return implementation;
  }
}
