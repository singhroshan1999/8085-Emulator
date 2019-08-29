package com.singhroshan1999;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

//        MnemonicParser.compile("MVI A,5");
//        MnemonicParser.compile("MVI B,15");
//        MnemonicParser.compile("ADD B");
//        MnemonicParser.compile("STA 100");
//        MnemonicParser.compile("HLT");
//        InstructionSet.setPC((short)0);
//        InstructionSet.executeIR();
//        for (int i = 10; i < 9; i++) {
//            System.out.println(Memory.read((short)i));
//        }
//        System.out.println(Memory.read((short) 100));
//        Scanner s = new Scanner(System.in);
        FileReader f = new FileReader("in.asm");
        BufferedReader s = new BufferedReader(f);
        short stptr = 0;
        String str = new String();
        InstructionSet.setPC(stptr);
        while(!str.equals("q")){
//            System.out.print("::");
            str = s.readLine();
            if(str.equals("q")) break;
            if(str.startsWith("%")) {
                Internals.parseInternals(str.toUpperCase());
                continue;
            }
            MnemonicParser.compile(str);
//            System.out.print("::");
//            str = s.nextLine();
        }

    }
}
