topLevelLabel
topLevelLabel   ; 2nd variant

 implicitLabelMacro implicitLabel
 implicitLabelMacro implicitLabel ; 2nd variant

  bra <caret>

.localLabel
.localLabel2
.localLabelWithColon:
localLabelDollar$
localLabelDollarWithColon$:

anotherTopLevel.Label:
.localLabelNotVisible

setLabel set 2
equLabel equ 42
equalsLabel = 33

implicitLabelMacro macro
;\1 rs.l 1
    endm

; not showing --------------
equrLabel equr d7
regLabel reg d0-d7

myMacro macro
  endm