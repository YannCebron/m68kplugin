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

package icons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public final class M68kIcons {

  public static final Icon FILE = AllIcons.FileTypes.Custom;

  public static final Icon TOOL_WINDOW = IconLoader.findIcon("/icons/browserToolWindow.svg", M68kIcons.class);

  public static final Icon LABEL_GLOBAL = AllIcons.Nodes.Method;
  public static final Icon LABEL_LOCAL = AllIcons.Nodes.AbstractMethod;
  public static final Icon LABEL_EQU = AllIcons.Nodes.ClassInitializer;
  public static final Icon LABEL_MACRO = AllIcons.Nodes.Function;
  public static final Icon LABEL_SET = AllIcons.Nodes.Variable;

  public static final Icon LABEL_EQUR = AllIcons.Nodes.Artifact;
  public static final Icon LABEL_REG = AllIcons.Nodes.Target;

  public static final Icon LABEL_FO = AllIcons.Nodes.Field;
  public static final Icon LABEL_SO = AllIcons.Nodes.Static;

  public static final Icon INCLUDE = AllIcons.Graph.Layout;
  public static final Icon INCBIN = AllIcons.FileTypes.Archive;
}
