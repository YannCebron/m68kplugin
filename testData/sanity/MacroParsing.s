macroName MACRO
 ENDM
macroName2 MACRO
 \1
 \a
 ENDM

 macroName
 macroName2 12,"test"
 macroName2 42,<"test",0>