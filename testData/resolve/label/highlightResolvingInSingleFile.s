* "plain" labels -------------------------------------------------------------------------------------------------------

_underscoreTopLevelLabel
label.with.dots

topLevelLabelWithColon:

topLevelLabel
topLevelLabel  ; redefined
  bra topLevelLabel
  bra anotherTopLevelLabel
  bra topLevelLabelWithColon
  bra .localLabel
  bra _underscoreTopLevelLabel
  bra label.with.dots

.localLabel
  bra .localLabel
  jmp .localLabel
.localLabelWithColon:
  jmp .localLabelWithColon

localLabelWithDollar$
  bra localLabelWithDollar$
localLabelWithDollarColon$:
  bra localLabelWithDollarColon$

anotherTopLevelLabel
  bra <error descr="Cannot resolve label 'INVALID_LABEL'">INVALID_LABEL</error>
  bra <error descr="Cannot resolve label '.INVALID_LABEL'">.INVALID_LABEL</error>

equrLabel equr d7
  bra <error descr="Cannot resolve label 'equrLabel'">equrLabel</error>

regLabel reg d0-d7
  bra <error descr="Cannot resolve label 'regLabel'">regLabel</error>

* M68kEquDirectiveBase -------------------------------------------------------------------------------------------------

DATE equ 233
MONTH = 42
  move.l #DATE,d0
  move.l #MONTH,d1
  move.l #YEAR,d6

  move.l #<error descr="Cannot resolve label 'INVALID_LABEL'">INVALID_LABEL</error>,d2

YEAR equ 2020
DECADE equ YEAR*10
INVALID_REFERENCE equ <error descr="Cannot resolve label 'INVALID_LABEL'">INVALID_LABEL</error>


* Macro labels ---------------------------------------------------------------------------------------------------------

myMacro macro

macroLabel\@
  bsr macroLabel\@
  jsr _LVO\1(a6)
  endm

  bra <error descr="Cannot resolve label 'myMacro'">myMacro</error>

* local macro label: inside current macro scope ------------------------------------------------------------------------

myLocalMacro macro

.localMacroLabel\@
  bsr .localMacroLabel\@

  bsr <error descr="Cannot resolve label '.anotherLocalMacroLabel\@'">.anotherLocalMacroLabel\@</error>
  endm

anotherLocalMacro macro
.anotherLocalMacroLabel\@
  bsr .anotherLocalMacroLabel\@

  bsr <error descr="Cannot resolve label '.localMacroLabel\@'">.localMacroLabel\@</error>
  endm


* conditional assembly directives referring potentially undefined label - weak highlighting ----------------------------

  ifd <weak_warning descr="Cannot resolve label 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifnd <weak_warning descr="Cannot resolve label 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifmacrod <weak_warning descr="Cannot resolve label 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifmacrond <weak_warning descr="Cannot resolve label 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif


* builtin symbols ------------------------------------------------------------------------------------------------------

MY_CPU equ __CPU

offsetLabel dc.l *-4