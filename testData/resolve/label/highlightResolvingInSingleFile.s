* "plain" labels

_underscoreTopLevelLabel

topLevelLabel
  bra topLevelLabel
  bra anotherTopLevelLabel
  bra .localLabel
  bra _underscoreTopLevelLabel

.localLabel
  bra .localLabel

anotherTopLevelLabel
  bra <error descr="Cannot resolve symbol 'INVALID_LABEL'">INVALID_LABEL</error>
  bra <error descr="Cannot resolve symbol '.INVALID_LABEL'">.INVALID_LABEL</error>


* M68kEquDirectiveBase

DATE equ 233
MONTH = 42
  move.l #DATE,d0
  move.l #MONTH,d1
  move.l #YEAR,d6

  move.l #<error descr="Cannot resolve symbol 'INVALID_LABEL'">INVALID_LABEL</error>,d2

YEAR equ 2020


* conditional assembly directives referring potentially undefined label - weak highlighting

  ifd <weak_warning descr="Cannot resolve symbol 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifnd <weak_warning descr="Cannot resolve symbol 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifmacrod <weak_warning descr="Cannot resolve symbol 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifmacrond <weak_warning descr="Cannot resolve symbol 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif
