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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kConstantExpressionInspectionTest extends BasePlatformTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myFixture.enableInspections(new M68kConstantExpressionInspection());
  }

  public void testExpressions() {
    doTest("""
      ; division by zero
        dc.b <error descr="Error evaluating expression: 1/0">1/0</error>
      ; overflow
        dc.l <error descr="Error evaluating expression: 88888888*99999999">88888888*99999999</error>
      """);
  }

  public void testNumberExpression() {
    doTest("""
        dc.l <error descr="Number value out of range">999988887777666655554444</error>
      """);
  }

  public void testPlainInstructions() {
    doTest("""
        moveq #-128,d0
        moveq #<error descr="Operand value out of range: -6666 (valid: -128..127)">-6666</error>,d0
        moveq #127,d0
        moveq #<error descr="Operand value out of range: 6666 (valid: -128..127)">6666</error>,d0
      
        trap #0
        trap #<error descr="Operand value out of range: -6666 (valid: 0..15)">-6666</error>
        trap #15
        trap #<error descr="Operand value out of range: 6666 (valid: 0..15)">6666</error>
      
        bkpt #0
        bkpt #<error descr="Operand value out of range: -6666 (valid: 0..7)">-6666</error>
        bkpt #7
        bkpt #<error descr="Operand value out of range: 6666 (valid: 0..7)">6666</error>
      
        stop #0
        stop #<error descr="Operand value out of range: -6666 (valid: 0..65535)">-6666</error>
        stop #65535
        stop #<error descr="Operand value out of range: 66666 (valid: 0..65535)">66666</error>
      
        rtd #-32768
        rtd #<error descr="Operand value out of range: -66666 (valid: -32768..32767)">-66666</error>
        rtd #32767
        rtd #<error descr="Operand value out of range: 66666 (valid: -32768..32767)">66666</error>
      
        addq #1,d0
        addq #<error descr="Operand value out of range: -6666 (valid: 1..8)">-6666</error>,d0
        addq #8,d0
        addq #<error descr="Operand value out of range: 6666 (valid: 1..8)">6666</error>,d0
      
        subq #1,d0
        subq #<error descr="Operand value out of range: -6666 (valid: 1..8)">-6666</error>,d0
        subq #8,d0
        subq #<error descr="Operand value out of range: 6666 (valid: 1..8)">6666</error>,d0
      
        bchg #1,d0
        bchg #<error descr="Operand value out of range: -6666 (valid: 0..255)">-6666</error>,d0
        bchg #8,d0
        bchg #<error descr="Operand value out of range: 6666 (valid: 0..255)">6666</error>,d0
      
        btst #1,d0
        btst #<error descr="Operand value out of range: -6666 (valid: 0..255)">-6666</error>,d0
        btst #8,d0
        btst #<error descr="Operand value out of range: 6666 (valid: 0..255)">6666</error>,d0
      
        bclr #1,d0
        bclr #<error descr="Operand value out of range: -6666 (valid: 0..255)">-6666</error>,d0
        bclr #8,d0
        bclr #<error descr="Operand value out of range: 6666 (valid: 0..255)">6666</error>,d0
      
        asl #1,d0
        asl #<error descr="Operand value out of range: -6666 (valid: 1..8)">-6666</error>,d0
        asl #8,d0
        asl #<error descr="Operand value out of range: 6666 (valid: 1..8)">6666</error>,d0
      
        asr #1,d0
        asr #<error descr="Operand value out of range: -6666 (valid: 1..8)">-6666</error>,d0
        asr #8,d0
        asr #<error descr="Operand value out of range: 6666 (valid: 1..8)">6666</error>,d0
      
        lsl #1,d0
        lsl #<error descr="Operand value out of range: -6666 (valid: 1..8)">-6666</error>,d0
        lsl #8,d0
        lsl #<error descr="Operand value out of range: 6666 (valid: 1..8)">6666</error>,d0
      
        lsr #1,d0
        lsr #<error descr="Operand value out of range: -6666 (valid: 1..8)">-6666</error>,d0
        lsr #8,d0
        lsr #<error descr="Operand value out of range: 6666 (valid: 1..8)">6666</error>,d0
      
        roxl #1,d0
        roxl #<error descr="Operand value out of range: -6666 (valid: 1..8)">-6666</error>,d0
        roxl #8,d0
        roxl #<error descr="Operand value out of range: 6666 (valid: 1..8)">6666</error>,d0
      
        roxr #1,d0
        roxr #<error descr="Operand value out of range: -6666 (valid: 1..8)">-6666</error>,d0
        roxr #8,d0
        roxr #<error descr="Operand value out of range: 6666 (valid: 1..8)">6666</error>,d0
      """);
  }

  public void testDirectives() {
    doTest("""
        rept 1
        rept <error descr="Operand value out of range: -6666 (valid: 1..2147483647)">-6666</error>
      
        dc.b -128
        dc.b <error descr="Operand value out of range: -6666 (valid: -128..255)">-6666</error>
        dc.b 127
        dc.b <error descr="Operand value out of range: 6666 (valid: -128..255)">6666</error>
      
        dc.w -32768
        dc.w <error descr="Operand value out of range: -66666 (valid: -32768..65535)">-66666</error>
        dc.w 65535
        dc.w <error descr="Operand value out of range: 66666 (valid: -32768..65535)">66666</error>
      
        dc.l -666666
        dc.l 666666
      """);
  }

  private void doTest(String text) {
    myFixture.configureByText("a.s", text);
    myFixture.testHighlighting();
  }
}
