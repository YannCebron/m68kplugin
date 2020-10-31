_underscoreTopLevelLabel

topLevelLabel
  bra topLevelLabel
  bra .localLabel
  bra _underscoreTopLevelLabel

.localLabel
  bra .localLabel

  bra <error descr="Cannot resolve symbol 'INVALID_LABEL'">INVALID_LABEL</error>
  bra <error descr="Cannot resolve symbol '.INVALID_LABEL'">.INVALID_LABEL</error>

