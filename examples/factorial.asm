// program to find factorial of a number
LXI H,2000
// change value of M to find factorial of other number
MVI M,4
MOV B,M
MVI D,1
FACTORIAL:
CALL MULTIPLY
DCR B
JNZ FACTORIAL
INX H
MOV M,D
HLT
MULTIPLY:
MOV E,B
MVI A,0
MULTIPLYLOOP:
ADD D
DCR E
JNZ MULTIPLYLOOP
MOV D,A
RET
%execute
%getregister
%getmemory 2000 2001
q
