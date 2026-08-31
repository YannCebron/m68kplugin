BG_COLOR EQU $DFF180/*<# COLOR00 #>*/

  move.b	#0,$bfe001/*<# CIAA_PRA #>*/  ; clear
  btst 		#14,$dff002/*<# DMACONR #>*/ ; blitter busy?