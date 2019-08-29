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
        return memory[(int)SpecialPurposeRegisters.PC()&0xffff];
    }
    static byte readHL(){
        return memory[(int)((GeneralPurposeRegisters.register((byte) 0b100)<<8)&0xffff | GeneralPurposeRegisters.register((byte) 0b101))&0xff];
    }
    static void writeHL(byte data){
        Memory.write((short) ((short) ((GeneralPurposeRegisters.register((byte) 0b100)<<8)&0xffff | GeneralPurposeRegisters.register((byte) 0b101))&0xff),data);
    }
    static void writeSP(short data){
        SpecialPurposeRegisters.decSP();
        Memory.write(SpecialPurposeRegisters.SP(),(byte) (data & 0b0000000011111111));
        SpecialPurposeRegisters.decSP();
        Memory.write(SpecialPurposeRegisters.SP(),(byte) (data >> 8));
    }
    static short readSP(){
        short i;
        i = (short) (Memory.read(SpecialPurposeRegisters.SP())<<8);
        SpecialPurposeRegisters.incSP();
        i = (short) (i | Memory.read(SpecialPurposeRegisters.SP()));
        SpecialPurposeRegisters.incSP();
        return i;
    }
    static void initMemory(){ Arrays.fill(memory, (byte) 0);}

}
