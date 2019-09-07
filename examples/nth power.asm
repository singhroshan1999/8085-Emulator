//program to find nth power of base
LXI H,2000
// here comes base
MVI M,5
lxi H,2001
here comes power
MVI M,3
lxi H,2000
MOV B,M
INX H
MOV C,M
MVI D,01
POWER_LOOP:
CALL MULTIPLY
DCR C
JNZ POWER_LOOP
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
%getmemory 2000 2003
q
