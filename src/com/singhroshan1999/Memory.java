package com.singhroshan1999;
import java.util.*;
public class Memory {
    private static final int size = 65536;
    private static byte[] memory = new byte[size];
    static byte read(short address){
        return memory[(int)address&0xffff];
    }
    static void write(short address,byte data){
//        System.out.println("addr:"+address);
        memory[(int)address&0xffff] = data;
    }
    static void writeInt(int address,int data){ Memory.write((short) address,(byte) data);}
    static byte readPC(){
        return memory[(int)SpecialPurposeRegisters.PC()&0xffff];
    }
    static byte readHL(){
        return memory[(int)((GeneralPurposeRegisters.register((byte) 0b100)<<8)&0xffff | GeneralPurposeRegisters.register((byte) 0b101)&0xff)];
    }
    static void writeHL(byte data){
        Memory.write((short) ((short) ((GeneralPurposeRegisters.register((byte) 0b100)<<8)&0xffff | GeneralPurposeRegisters.register((byte) 0b101)&0xff)),data);
    }
    static void writeSP(short data){
        SpecialPurposeRegisters.decSP();
//        System.out.println(SpecialPurposeRegisters.SP());
//        System.out.println("SP:"+(SpecialPurposeRegisters.SP()));
        Memory.write((short) (SpecialPurposeRegisters.SP()),(byte) (data & 0b0000000011111111));
        SpecialPurposeRegisters.decSP();
//        System.out.println(SpecialPurposeRegisters.SP());
        Memory.write(SpecialPurposeRegisters.SP(),(byte) (data >> 8));
    }
    static short readSP(){
        short i;
//        System.out.println(SpecialPurposeRegisters.SP() +"r");
        i = (short) ((Memory.read(SpecialPurposeRegisters.SP())<<8)&0xffff);
        SpecialPurposeRegisters.incSP();
//        System.out.println(SpecialPurposeRegisters.SP());
        i = (short) (i | Memory.read(SpecialPurposeRegisters.SP())&0xff);
        SpecialPurposeRegisters.incSP();
//        System.out.println(i);
        return i;
    }
    static void initMemory(){ Arrays.fill(memory, (byte) 0);}

}
