package com.singhroshan1999;

class SpecialPurposeRegisters {
    private static short _SP,_PC,_INC,ADDBUF,DATABUFF;
    private static byte _IR;
    private static boolean[] _flag= new boolean[8];
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

    static void CY(boolean b){
        SpecialPurposeRegisters._flag[0] = b;
    }
    static boolean CY(){
        return SpecialPurposeRegisters._flag[0];
    }

    static void P(boolean b){
        SpecialPurposeRegisters._flag[2] = b;
    }
    static boolean P(){
        return SpecialPurposeRegisters._flag[2];
    }

    static void AC(boolean b){
        SpecialPurposeRegisters._flag[4] = b;
    }
    static boolean AC(){
        return SpecialPurposeRegisters._flag[4];
    }

    static void Z(boolean b){
        SpecialPurposeRegisters._flag[6] = b;
    }
    static boolean Z(){
        return SpecialPurposeRegisters._flag[6];
    }

    static void S(boolean b){
        SpecialPurposeRegisters._flag[7] = b;
    }
    static boolean S(){
        return SpecialPurposeRegisters._flag[7];
    }
}
