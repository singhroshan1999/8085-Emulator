package com.singhroshan1999;
import java.util.*;
// TODO : instruction set
class InstructionSet {
    private static Map<Byte,Runnable> _insSet= new HashMap<>();
    private static Map<String,Byte> _decode = new HashMap<>();
    private static final byte _B = 0b000,_C = 0B001,_D = 0B010,_E = 0B011,_H = 0B100,_L = 0B101,_M = 0B110, _A = 0B111;
//    private static byte _DDD_R(byte b){
//       return (byte) ((b & 0B00111000)>>3);
//    }
//    private static byte _DDD_I(byte b){
//        return (byte) ((b & 0B11000000)>>3);
//    }
    static {
        /* 1 byte instructions */
        _insSet.put((byte)1,()->System.out.println(""));

        /* 2 byte */

        /* 3 byte */
    }

    static {
        /* MOVE LOAD STORE */
        _decode.put("MOV",(byte)0B01000000);
        _decode.put("MVI",(byte)0B00000110);
        _decode.put("LXI",(byte)0B00000001);
        _decode.put("STAX",(byte)0B00000010);
        _decode.put("LDAX",(byte)0B00001010);
        _decode.put("STA",(byte)0B00110010);
        _decode.put("LDA",(byte)0B00111010);
        _decode.put("SHLD",(byte)0B00100010);
        _decode.put("LHLD",(byte)0B00101010);
        _decode.put("XCHG",(byte)0B11101011);
        /* STACK-OPS */
        _decode.put("PUSH",(byte)0B11000101);
        _decode.put("POP",(byte)0B11000001);
        _decode.put("XTHL",(byte)0B11100011);
        _decode.put("SPHL",(byte)0B11111001);
        _decode.put("LXI",(byte)0B01000000); // ***
        _decode.put("INX",(byte)0B00110011);
        _decode.put("DCX",(byte)0B00111011);
        /* JUMP */
        _decode.put("JMP",(byte)0B11000011);
        _decode.put("JC",(byte)0B11011010);
        _decode.put("JNC",(byte)0B11010010);
        _decode.put("JZ",(byte)0B11001010);
        _decode.put("JNZ",(byte)0B11000010);
        _decode.put("JP",(byte)0B11110010);
        _decode.put("JM",(byte)0B11111010);
        _decode.put("JPE",(byte)0B11101010);
        _decode.put("JPO",(byte)0B11100010);
        _decode.put("PCHL",(byte)0B11101001);
        /* CALL */
        _decode.put("CALL",(byte)0B11001100);
        _decode.put("CC",  (byte)0B11011100);
        _decode.put("CNC", (byte)0B11010100);
        _decode.put("CZ",  (byte)0B11001100);
        _decode.put("CNZ", (byte)0B11000100);
        _decode.put("CP",  (byte)0B11110100);
        _decode.put("CM",  (byte)0B11111100);
        _decode.put("CPE", (byte)0B11101100);
        _decode.put("CPO", (byte)0B11100100);
        /* RETURN */
        _decode.put("RET",(byte)0B11001001);
        _decode.put("RC", (byte)0B11011000);
        _decode.put("RNC",(byte)0B11010000);
        _decode.put("RZ", (byte)0B11001000);
        _decode.put("RNZ",(byte)0B11000000);
        _decode.put("RP", (byte)0B11110000);
        _decode.put("RM", (byte)0B11111000);
        _decode.put("RPE",(byte)0B11101000);
        _decode.put("RPO",(byte)0B11100000);
        /* RESTART */
        _decode.put("RST",(byte)0B11000111);
        /* I/O */
        _decode.put("IN", (byte)0B11011011);
        _decode.put("OUT",(byte)0B11010011);
        /* INCREMENT / DECREMENT */

    }

}
