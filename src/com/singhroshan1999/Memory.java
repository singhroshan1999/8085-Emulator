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
    static void writeInt(int address,int data){ Memory.write((short) address,(byte) data);}
    static byte readPC(){
        return memory[(int)SpecialPurposeRegisters.PC()];
    }
    static byte readHL(){
        return memory[(int)(GeneralPurposeRegisters.register((byte) 0b100)<<8 | GeneralPurposeRegisters.register((byte) 0b101))];
    }
    static void writeHL(byte data){
        Memory.write((short) (GeneralPurposeRegisters.register((byte) 0b100)<<8 | GeneralPurposeRegisters.register((byte) 0b101)),data);
    }

}
