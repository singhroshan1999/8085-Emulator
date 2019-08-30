// this is 8085 assembly program to sort numbers
LXI H,3000
MVI M,6
LXI H,3001
MVI M,1
LXI H,3002
MVI M,5
LXI H,3003
MVI M,2
LXI H,3004
MVI M,4
LXI H,3005
MVI M,3
hlt
// this is comment
%execute
%getmemory 3000 3010
MVI B,5
START:
LXI H,3000
MVI C,5
BACK:
MOV A,M
INX H
CMP M
JC SKIP
JZ SKIP
MOV D,M
MOV M,A
DCX H
MOV M,D
INX H
SKIP:
DCR C
JNZ BACK
DCR B
JNZ START
HLT
%execute
%getregister
%getmemory 3000 3010
q