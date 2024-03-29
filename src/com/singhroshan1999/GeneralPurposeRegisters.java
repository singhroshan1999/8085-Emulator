package com.singhroshan1999;

import java.util.HashMap;
import java.util.Map;

class GeneralPurposeRegisters {
    private static Map<Byte,Byte> _register= new HashMap<>();
    private static byte _B,_C,_D,_E,_H,_L;
    private static byte _W,_Z;
    static {
        _register.put((byte)0B000,(byte)0B000); //B
        _register.put((byte)0B001,(byte)0B000); //C
        _register.put((byte)0B010,(byte)0B000); //D
        _register.put((byte)0B011,(byte)0B000); //E
        _register.put((byte)0B100,(byte)0B000); //H
        _register.put((byte)0B101,(byte)0B000); //L
        _register.put((byte)0B111,(byte)0B000); //A
    }
    /** setter */
    static void B(byte b){
        GeneralPurposeRegisters._B = b;
    }
    static void C(byte b){
        GeneralPurposeRegisters._C = b;
    }
    static void D(byte b){
        GeneralPurposeRegisters._D = b;
    }
    static void E(byte b){
        GeneralPurposeRegisters._E = b;
    }
    static void H(byte b){
        GeneralPurposeRegisters._H = b;
    }
    static void L(byte b){
        GeneralPurposeRegisters._L = b;
    }
    static void W(byte b){
        GeneralPurposeRegisters._W = b;
    }
    static void Z(byte b){
        GeneralPurposeRegisters._Z = b;
    }
    /** getter */
    static byte B(){
        return GeneralPurposeRegisters._B;
    }
    static byte C(){
        return GeneralPurposeRegisters._C;
    }
    static byte D(){
        return GeneralPurposeRegisters._D;
    }
    static byte E(){
        return GeneralPurposeRegisters._E;
    }
    static byte H(){
        return GeneralPurposeRegisters._H;
    }
    static byte L(){
        return GeneralPurposeRegisters._L;
    }
    static byte W(){
        return GeneralPurposeRegisters._W;
    }
    static byte Z(){
        return GeneralPurposeRegisters._Z;
    }

    /* COUPLE */
    /** setter */

    static void BC(byte b1,byte b2){
        GeneralPurposeRegisters._B = b1;
        GeneralPurposeRegisters._C = b2;
    }
    static void DE(byte b1,byte b2){
        GeneralPurposeRegisters._D = b1;
        GeneralPurposeRegisters._E = b2;
    }
    static void HL(byte b1,byte b2){
        GeneralPurposeRegisters._H = b1;
        GeneralPurposeRegisters._L = b2;
    }

    /** getter */

    static short BC(){
        return (short) (GeneralPurposeRegisters._B<<8 | GeneralPurposeRegisters._C);
    }
    static short DE(){
        return (short) (GeneralPurposeRegisters._D<<8 | GeneralPurposeRegisters._E);
    }
    static short HL(){
        return (short) (GeneralPurposeRegisters._H<<8 | GeneralPurposeRegisters._L);
    }

    static void register(byte address,byte value){
        GeneralPurposeRegisters._register.put(address,value);
    }
    static byte register(byte address){
        return GeneralPurposeRegisters._register.get(address);
    }


}
