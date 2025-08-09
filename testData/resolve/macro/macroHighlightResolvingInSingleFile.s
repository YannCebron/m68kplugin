macro1 macro
  endm

macro1: macro      ; redefine
  macro2 ; forward ref is valid
  endm

macro2 macro
  endm

  macro macro3:
  macro2
  endm

  macro1
  macro2.l

  macro3

  <error descr="Cannot resolve macro 'INVALID_MACRO_NAME'">INVALID_MACRO_NAME</error>