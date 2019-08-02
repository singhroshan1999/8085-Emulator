package com.singhroshan1999;
import java.util.*;
import com.singhroshan1999.*;
// TODO : instruction set
class InstructionSet {
    private static Map<Byte,Runnable> _insSet= new HashMap<>();
    private static Map<String,Byte> _decode = new HashMap<>();
    private static final byte _B = 0b000,_C = 0B001,_D = 0B010,_E = 0B011,_H = 0B100,_L = 0B101,_M = 0B110, _A = 0B111;
    private static Map<Byte,Runnable> _registers = new HashMap<>();
//    private static byte _DDD_R(byte b){
//       return (byte) ((b & 0B00111000)>>3);
//    }
//    private static byte _DDD_I(byte b){
//        return (byte) ((b & 0B11000000)>>3);
//    }
//    static {
//        _registers.put(_B,GeneralPurposeRegisters.);
//
//}
    static {

        /* 1 byte instructions */
        _insSet.put((byte)0B01000000,()->{ // MOV
            byte d = (byte) ((SpecialPurposeRegisters.IR() & 0B00111000) >>3);
            byte s = (byte) ((SpecialPurposeRegisters.IR() & 0B00000111));
            if(d == InstructionSet._M){
                Memory.write(Memory.readPC(),GeneralPurposeRegisters.register(s));
            }else if(s == InstructionSet._M){
                GeneralPurposeRegisters.register(d,Memory.readPC());
            }else{
                GeneralPurposeRegisters.register(d,GeneralPurposeRegisters.register(s));
            }

        });
        _insSet.put((byte)0B01000000,()->{ // MVI
            byte d = (byte) ((SpecialPurposeRegisters.IR() & 0B00111000) >>3);
            if(d == InstructionSet._M){
                Memory.write(Memory.readPC(),Memory.readPC());
            } else{
                GeneralPurposeRegisters.register(d,Memory.readPC());
            }
        });
        _insSet.put((byte)0B10000000,()->{ // LXI
            byte r = (byte) ((SpecialPurposeRegisters.IR() & 0B00110000) >>4);
            if(r == 0b00){
                GeneralPurposeRegisters.register(_B,Memory.readPC());
                GeneralPurposeRegisters.register(_C,Memory.readPC());
            }else if(r == 0b01){
                GeneralPurposeRegisters.register(_D,Memory.readPC());
                GeneralPurposeRegisters.register(_E,Memory.readPC());
            } else if(r == 0b10){
                GeneralPurposeRegisters.register(_H,Memory.readPC());
                GeneralPurposeRegisters.register(_L,Memory.readPC());
            }
        });
        _insSet.put((byte)0B11000000,()->{ // STAX
            byte r = (byte) ((SpecialPurposeRegisters.IR() & 0B00110000) >>4);
            if(r == 0b00){
                Memory.write((short) (GeneralPurposeRegisters.register(_B)<<8 | GeneralPurposeRegisters.register(_C)),GeneralPurposeRegisters.register((byte)0b111));
            }else if(r == 0b01){
                Memory.write((short) (GeneralPurposeRegisters.register(_D)<<8 | GeneralPurposeRegisters.register(_E)),GeneralPurposeRegisters.register((byte)0b111));
            }

        });
    _insSet.put((byte)0B11000000,()->{  // LDAX
        byte r = (byte) ((SpecialPurposeRegisters.IR() & 0B00110000) >>4);
        if(r == 0b00){
            GeneralPurposeRegisters.register((byte)0b111,Memory.read((short) (GeneralPurposeRegisters.register(_B)<<8 | GeneralPurposeRegisters.register(_C))));
        }else if(r == 0b01){
            GeneralPurposeRegisters.register((byte)0b111,Memory.read((short) (GeneralPurposeRegisters.register(_D)<<8 | GeneralPurposeRegisters.register(_E))));
        }

    });
    _insSet.put((byte)0B11000000,()->{ // STA
        Memory.write(Memory.readPC(),GeneralPurposeRegisters.register((byte)0b111));
    });
    _insSet.put((byte)0B11000000,()->{  // LDA
        GeneralPurposeRegisters.register((byte)0b111,Memory.read(Memory.readPC()));
    });
    _insSet.put((byte)0B11000000,()->{  //SHLD
        short add = Memory.readPC();
        Memory.write(add,GeneralPurposeRegisters.register((byte)_H));
        Memory.write((short) (add+1),GeneralPurposeRegisters.register((byte)_L));
    });
    _insSet.put((byte)0B11000000,()->{  //LHLD
        short add = Memory.readPC();
        GeneralPurposeRegisters.register((byte)_H,Memory.read(add));
        GeneralPurposeRegisters.register((byte)_L,Memory.read((short) (add+1)));
    });
    _insSet.put((byte)0B11000000,()->{
        byte t;
        t = GeneralPurposeRegisters.register(_H);
        GeneralPurposeRegisters.register(_H,GeneralPurposeRegisters.register(_D));
        GeneralPurposeRegisters.register(_D,t);
        t = GeneralPurposeRegisters.register(_L);
        GeneralPurposeRegisters.register(_L,GeneralPurposeRegisters.register(_E));
        GeneralPurposeRegisters.register(_E,t);
    });
    _insSet.put((byte)0B11000000,()->{

    });
    _insSet.put((byte)0B11000000,()->{

    });
    _insSet.put((byte)0B11000000,()->{

    });
    _insSet.put((byte)0B11000000,()->{

    });
    _insSet.put((byte)0B11000000,()->{

    });
    _insSet.put((byte)0B11000000,()->{

    });
    _insSet.put((byte)0B11000000,()->{

    });
    _insSet.put((byte)0B11000000,()->{

    });
    _insSet.put((byte)0B11000000,()->{

    });
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
        _decode.put("LXI",(byte)0B0); // ***
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
        _decode.put("INR",(byte)0B00000100);
        _decode.put("DCR",(byte)0B00000101);
        _decode.put("INX",(byte)0B0); // ****
        _decode.put("DCX",(byte)0B0); // ****
        /* ADD */
        _decode.put("ADD",(byte)0B10000000);
        _decode.put("ADC",(byte)0B10001000);
        _decode.put("ADI",(byte)0B11000110);
        _decode.put("ACI",(byte)0B11001110);
        _decode.put("DAD",(byte)0B00000001);
        /* SUB */
        _decode.put("SUB",(byte)0B10010000);
        _decode.put("SBB",(byte)0B10011000);
        _decode.put("SUI",(byte)0B11010110);
        _decode.put("SBI",(byte)0B11011110);
        /* LOGICAL */
        _decode.put("ANA",(byte)0B10100000);
        _decode.put("XRA",(byte)0B10101000);
        _decode.put("ORA",(byte)0B10110000);
        _decode.put("CMP",(byte)0B10111000);
        _decode.put("ANI",(byte)0B11100110);
        _decode.put("XRI",(byte)0B11101110);
        _decode.put("ORI",(byte)0B11110110);
        _decode.put("CPI",(byte)0B11111110);
        _decode.put("RLC",(byte)0B00000111);
        _decode.put("RRC",(byte)0B00001111);
        _decode.put("RAL",(byte)0B00010111);
        _decode.put("RAR",(byte)0B00011111);
        /* SPECIALS */
        _decode.put("CMA",(byte)0B00100111);
        _decode.put("STC",(byte)0B00101111);
        _decode.put("CMC",(byte)0B00110111);
        _decode.put("DAA",(byte)0B00111111);
        /* CONTROL */
        _decode.put("EI", (byte)0B11111011);
        _decode.put("DI", (byte)0B11110011);
        _decode.put("NOP",(byte)0B00000000);
        _decode.put("HLT",(byte)0B01110110);
        /* NEW 8085AH INSTRUCTIONS */
        _decode.put("RIM",(byte)0B00100000);
        _decode.put("SIM",(byte)0B00110000);
    }

    static void executeIR(){}

}
