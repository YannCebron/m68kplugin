/*
 * Copyright 2025 The Authors
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
import com.intellij.openapi.extensions.RequiredElement;
import com.intellij.serviceContainer.BaseKeyedLazyInstance;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public final class M68kBrowserPaneFactoryEP extends BaseKeyedLazyInstance<M68kBrowserPaneFactory<?>> {

  @RequiredElement
  @Attribute("displayNameKey")
  public String displayNameKey;

  @Attribute("bundle")
  public String bundle;

  @RequiredElement
  @Attribute("factoryClass")
  public String factoryClass;

  public String getDisplayName() {
    String baseName = bundle != null ? bundle : getPluginDescriptor().getResourceBundleBaseName();
    assert baseName != null;

    ResourceBundle bundle = DynamicBundle.getResourceBundle(getLoaderForClass(), baseName);
    return bundle.getString(displayNameKey);
  }

  @Override
  protected @Nullable String getImplementationClassName() {
    return factoryClass;
  }
}
