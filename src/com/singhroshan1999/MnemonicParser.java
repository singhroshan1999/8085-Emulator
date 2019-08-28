package com.singhroshan1999;

import java.lang.reflect.Array;

public class MnemonicParser {
    private static short _nextInstr = 0;

    static void setStart(short start){
        _nextInstr = start;
    }
    static void compile(String instructions){
//        _parse_and_compile(new StringBuffer(instructions));
        String[] i = instructions.toUpperCase().split("[ ,]"); // 1 opcode 2 operands
//        for( String x : i) System.out.println(x);
        if(InstructionSet._nBIT_INST.contains(i[0]) || InstructionSet._3BIT_INST.contains(i[0])){
            if(i.length == 2){
                Memory.writeInt(_nextInstr++, InstructionSet._mnemonic.get(i[0]));
                int op = Integer.parseInt(i[1]);
                if(InstructionSet._3BIT_INST.contains(i[0])){
                    Memory.writeInt(_nextInstr++,op>>8);
                    Memory.writeInt(_nextInstr++,op&0b0000000011111111);
                }else{
                    Memory.writeInt(_nextInstr++,op);
                }
            } else if(i.length == 3){
                Memory.writeInt(_nextInstr++, InstructionSet._mnemonic.get(i[0]+" "+i[1]));
                int op = Integer.parseInt(i[2]);
                if(InstructionSet._3BIT_INST.contains(i[0])){
                    Memory.writeInt(_nextInstr++,op>>8);
                    Memory.writeInt(_nextInstr++,op&0b0000000011111111);
                }else{
                    Memory.writeInt(_nextInstr++,op);
                }
            }
        }else{
//            System.out.println(instructions);
            Memory.writeInt(_nextInstr++, InstructionSet._mnemonic.get(instructions.toUpperCase()));
        }

    }
}
