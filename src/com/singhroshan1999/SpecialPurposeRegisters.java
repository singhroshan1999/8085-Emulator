package com.singhroshan1999;

class SpecialPurposeRegisters {
    private static short _SP,_PC,_INC,ADDBUF,DATABUFF;
    private static byte _IR;
    // TODO : special purpose register
    static void  PC(short tbyte){
        SpecialPurposeRegisters._PC = tbyte;
    }
    static short PC(){
        return SpecialPurposeRegisters._PC++;
    }

    static void  IR(byte tbyte){
        SpecialPurposeRegisters._IR = tbyte;
    }
    static byte IR(){
        return SpecialPurposeRegisters._IR;
    }
}
