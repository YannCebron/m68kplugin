This register contains the Modulo for Blitter source (x=A,B,C)
or Dest (x=D). A Modulo is a number that is automatically
added to the address at the end of each line, in order that
the address then points to the start of the next line. Each
source or destination has its own Modulo, allowing each
to be a different size, while an identical area of each is
used in the Blitter operation.