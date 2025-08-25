implicitLabelMacro macro
;\1 rs.l 1
    endm

anotherImplicitLabelMacro macro
;\1 rs.l 1
    endm

  implicitLabelMacro implicitLabel
  anotherImplicitLabelMacro implicitLabel

  lea implicitLabel(a0),a1