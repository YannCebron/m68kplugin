myLabel
  bra my<caret>Label

  bra <error descr="Cannot resolve label 'INVALID_LABEL'">INVALID_LABEL</error>

* highlightResolvingInMultipleFiles_other.s
  bra otherLabel
  bra otherLabel2