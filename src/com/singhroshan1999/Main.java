package com.singhroshan1999;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.singhroshan1999.*;

public class Main {

    public static void main(String[] args)  {
        System.out.println(0XC6);
        Memory.writeInt(2000,0X3E);
        Memory.writeInt(2001,12);
        Memory.writeInt(2002,0XC6);
        Memory.writeInt(2003,13);
        Memory.writeInt(2004,0X76);
        InstructionSet.setPC((short) 2000);
        InstructionSet.executeIR();
        System.out.println(GeneralPurposeRegisters.register((byte) 0b111));
    }
}
