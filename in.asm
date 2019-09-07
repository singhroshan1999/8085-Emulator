    lxi h,2050
  mvi m,4
 MVI D,1
        MVI E,1
LDA  2050
lab2  :
SUB    D
JZ      lab1
INR D
INR D
INR E
 # dfg dsfgs dfgsbdfgsdfcg
JMP lab2
lab1 :
MOV A  ,  E
STA 2051
HLT
     %execute # dfgsdgsdgsdfgsdf
%    getregister
%getmemory          2050          2051
q