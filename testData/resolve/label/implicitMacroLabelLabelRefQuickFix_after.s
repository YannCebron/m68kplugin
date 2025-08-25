implicitLabelMacro macro
;\1 rs.l 1
    endm

  implicitLabelMacro ; no parameters, skip

  implicitLabelMacro implicitLabel
  lea implicitLabel(a0),a1