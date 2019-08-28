package com.singhroshan1999;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.singhroshan1999.*;

public class Main {

    public static void main(String[] args)  {
//        System.out.println(0XC6);
//        Memory.writeInt(2000,0X3E);
//        Memory.writeInt(2001,195);
//        Memory.writeInt(2002,0XC6);
//        Memory.writeInt(2003,13);
//        Memory.writeInt(2004,0X77);
//        Memory.writeInt(2005,0X76);

//        InstructionSet.setPC((short) 2000);
//        InstructionSet.executeIR();
//        System.out.println((Memory.read((short) 0))&0xff);
        MnemonicParser.compile("MVI A,5");
        MnemonicParser.compile("MVI B,15");
        MnemonicParser.compile("ADD B");
        MnemonicParser.compile("STA 100");
        MnemonicParser.compile("HLT");
        InstructionSet.setPC((short)0);
        InstructionSet.executeIR();
        for (int i = 10; i < 9; i++) {
            System.out.println(Memory.read((short)i));
        }
        System.out.println(Memory.read((short) 100));

    }
}
