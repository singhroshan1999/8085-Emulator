package com.singhroshan1999;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class MnemonicParser {
    private static short _nextInstr = 0;
    private static Map<String,Short> _label= new HashMap<>();

    static void setStart(short start){
        _nextInstr = start;
    }
    static void compile(String instructions){
        String[] i = instructions.toUpperCase().split("[ ,]"); // 1 opcode 2 operands
        if(InstructionSet._nBIT_INST.contains(i[0]) || InstructionSet._3BIT_INST.contains(i[0])){
            if(i.length == 2){
                Memory.writeInt(_nextInstr++, InstructionSet._mnemonic.get(i[0]));
                int op;
                try {
                    op = Integer.parseInt(i[1]);
                }
                catch (NumberFormatException e1){
//                    System.out.println(_label.toString());
                    op = _label.get(i[1]);
                }
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
    static void preprocess(String instructions){

        String[] i = instructions.toUpperCase().split("[ ,]"); // 1 opcode 2 operands
        if(InstructionSet._nBIT_INST.contains(i[0]) || InstructionSet._3BIT_INST.contains(i[0])){
            if(i.length == 2){
                _nextInstr++;
//                Memory.writeInt(_nextInstr++, InstructionSet._mnemonic.get(i[0]));
//                int op;
//                try {
//                    op = Integer.parseInt(i[1]);
//                }
//                catch (NumberFormatException e1){
////                    System.out.println(_label.toString());
//                    op = _label.get(i[1]);
//                }
                if(InstructionSet._3BIT_INST.contains(i[0])){
                    _nextInstr+=2;
//                    Memory.writeInt(_nextInstr++,op>>8);
//                    Memory.writeInt(_nextInstr++,op&0b0000000011111111);
                }else{
//                    Memory.writeInt(_nextInstr++,op);
                    _nextInstr++;
                }
            } else if(i.length == 3){
//                Memory.writeInt(_nextInstr++, InstructionSet._mnemonic.get(i[0]+" "+i[1]));
                _nextInstr++;
                int op = Integer.parseInt(i[2]);
                if(InstructionSet._3BIT_INST.contains(i[0])){
//                    Memory.writeInt(_nextInstr++,op>>8);
//                    Memory.writeInt(_nextInstr++,op&0b0000000011111111);
                    _nextInstr+=2;
                }else{
//                    Memory.writeInt(_nextInstr++,op);
                    _nextInstr++;
                }
            }
        }else{
//            System.out.println(instructions);
//            Memory.writeInt(_nextInstr++, InstructionSet._mnemonic.get(instructions.toUpperCase()));
            _nextInstr++;
        }
//        System.out.println(_label.toString());
    }

    public static void setLabel(String str) {
        _label.putIfAbsent(str.substring(0,str.length()-1).toUpperCase(), _nextInstr);
    }

    static String formatMnemonic(String mnemonic){
        // remove inline comment
        int indx = mnemonic.indexOf("#");
        if(indx>=0) mnemonic = mnemonic.substring(0,indx);
        // remove white space
        mnemonic = mnemonic.trim();
        // remove double space
        mnemonic = mnemonic.replaceAll("\\s+"," ");
        mnemonic = mnemonic.replaceAll(" ,",",");
        // remove ,space
        mnemonic = mnemonic.replaceAll(", ",",");
        mnemonic = mnemonic.replaceAll(" :",":");
        mnemonic = mnemonic.replaceAll("% ","%");
//        System.out.println("-"+mnemonic+"-");
        return mnemonic;

    }
}
