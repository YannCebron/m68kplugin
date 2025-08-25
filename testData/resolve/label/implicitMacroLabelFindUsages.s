implicitLabelMacro macro
;\1 rs.l 1
    endm

  implicitLabelMacro implicit<caret>Label
  lea implicitLabel(a0),a1

MY_EQU equ implicitLabel