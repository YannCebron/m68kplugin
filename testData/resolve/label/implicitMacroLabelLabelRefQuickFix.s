implicitLabelMacro macro
;\1 rs.l 1
    endm

  implicitLabelMacro ; no parameters, skip

  implicitLabelMacro <weak_warning descr="Cannot resolve label 'implicitLabel'">implicitLabel</weak_warning>
  lea <error descr="Cannot resolve label 'implicitLabel', use quick fix to configure macro defining it">implicit<caret>Label</error>(a0),a1