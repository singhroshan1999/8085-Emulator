package com.singhroshan1999;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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
//        FileReader f = ;
        String file;
//        System.out.println(Arrays.toString(args));
        if(args.length >= 1){
            file = args[0];
        }else{
            System.out.println("Enter file name to execute:");
            Scanner scan = new Scanner(System.in);
            file = scan.next();
        }
        BufferedReader s = new BufferedReader(new FileReader(file));
        short stptr = 0;
        String str;
        InstructionSet.setPC(stptr);
        // preprocess
        while(true){
            str = MnemonicParser.formatMnemonic(s.readLine());
            if(str.isBlank()) continue;
            if(str.equals("q")) break;
            if(str.endsWith(":")){
                MnemonicParser.setLabel(str);
                continue;
            } else if(str.startsWith("%") || str.startsWith("//")){
                continue;
            }
            MnemonicParser.preprocess(str);
        }
        s.close();
        MnemonicParser.setStart(stptr);
        s = new BufferedReader(new FileReader(file));
        str = "";
        while(true){
//            System.out.print("::");
            str = MnemonicParser.formatMnemonic(s.readLine());
            if(str.isBlank()) continue;
            if(str.equals("q")) break;
            if(str.startsWith("%")) {
                Internals.parseInternals(str.toUpperCase());
                continue;
            } else if(str.endsWith(":")){
                MnemonicParser.setLabel(str);
                continue;
            } else if(str.startsWith("//")){
                continue;
            }
            MnemonicParser.compile(str);
//            System.out.print("::");
//            str = s.nextLine();
        }

    }
}
