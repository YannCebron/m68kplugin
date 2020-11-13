_underscoreTopLevelLabel

topLevelLabel
  bra topLevelLabel
  bra .localLabel
  bra _underscoreTopLevelLabel

.localLabel
  bra .localLabel

  bra <error descr="Cannot resolve symbol 'INVALID_LABEL'">INVALID_LABEL</error>
  bra <error descr="Cannot resolve symbol '.INVALID_LABEL'">.INVALID_LABEL</error>


* directives referring potentially undefined label - weak highlighting

  ifd <weak_warning descr="Cannot resolve symbol 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifnd <weak_warning descr="Cannot resolve symbol 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifmacrod <weak_warning descr="Cannot resolve symbol 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif

  ifmacrond <weak_warning descr="Cannot resolve symbol 'INVALID_VALUE'">INVALID_VALUE</weak_warning>
  endif
