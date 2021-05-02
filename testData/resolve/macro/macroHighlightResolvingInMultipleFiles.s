localMacro macro
  endm
macro1 macro
  endm

  otherMacro
  yet<caret>AnotherMacro

  <error descr="Cannot resolve macro 'INVALID_MACRO_NAME'">INVALID_MACRO_NAME</error>