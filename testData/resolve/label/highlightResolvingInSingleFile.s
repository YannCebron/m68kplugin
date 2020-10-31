topLevelLabel
  bra topLevelLabel
  bra .localLabel

.localLabel
  bra .localLabel

  bra <error descr="Cannot resolve symbol 'INVALID_LABEL'">INVALID_LABEL</error>
  bra <error descr="Cannot resolve symbol '.INVALID_LABEL'">.INVALID_LABEL</error>

