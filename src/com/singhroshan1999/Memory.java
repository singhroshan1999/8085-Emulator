package com.singhroshan1999;
import java.util.*;
public class Memory {
    private static final int size = 65536;
    private static byte[] memory = new byte[size];
    static byte read(short address){
        return memory[(int)address];
    }
    static void write(short address,byte data){
        memory[(int)address] = data;
    }
    static byte readPC(){
        return memory[(int)SpecialPurposeRegisters.PC()];
    }

}
