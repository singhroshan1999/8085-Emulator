//program to find nth fibonnaci fibonnaci
LXI H,3050
// contend to C in n
MVI C,12
MVI B,0
MVI D,1
MOV M,B
INX H
MOV M,D
XXXX:
MOV A,B
ADD D
MOV B,D
MOV D,A
INX H
MOV M,A
DCR C
JNZ XXXX
HLT
%execute
%getregister
%getmemory 3050 3064
q
