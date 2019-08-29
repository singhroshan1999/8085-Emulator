package com.singhroshan1999;
import java.util.*;
import com.singhroshan1999.*;
// TODO : instruction set
class InstructionSet {
    // MNEM vs OPCODE(k)
    // OPCODE vs INST
    // MNEM vs INST
    private static Map<Integer,Runnable> _insSet= new HashMap<>();
    private static Map<String,Integer> _decode = new HashMap<>();
    static Map<String,Integer> _mnemonic = new HashMap<>(); // MNEM - opcode
    private static Map<Integer,Integer> _op_to_inst = new HashMap<>(); // OP - INST
    static Set<String> _nBIT_INST = new HashSet<>();
    static Set<String> _3BIT_INST = new HashSet<>();
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
        _insSet.put(0,()->{ // MOV
            byte d = (byte) ((SpecialPurposeRegisters.IR() & 0B00111000) >>3);
            byte s = (byte) ((SpecialPurposeRegisters.IR() & 0B00000111));
            if(d == InstructionSet._M){
                Memory.writeHL(GeneralPurposeRegisters.register(s));
            }else if(s == InstructionSet._M){
                GeneralPurposeRegisters.register(d,Memory.readHL());
            }else{
                GeneralPurposeRegisters.register(d,GeneralPurposeRegisters.register(s));
            }

        });
        _insSet.put(1,()->{ // MVI
//            System.out.println("88888888888");
            byte d = (byte) ((SpecialPurposeRegisters.IR() & 0B00111000) >>3);
            if(d == InstructionSet._M){
            Memory.write((short) ((short) ((GeneralPurposeRegisters.register(_H)<<8)&0xffff|GeneralPurposeRegisters.register(_L)&0xff)),Memory.readPC());
            } else{
                GeneralPurposeRegisters.register(d,Memory.readPC());
            }
        });
        _insSet.put(2,()->{ // LXI
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
        _insSet.put(3,()->{ // STAX
            byte r = (byte) ((SpecialPurposeRegisters.IR() & 0B00110000) >>4);
            if(r == 0b00){
                Memory.write((short) (GeneralPurposeRegisters.register(_B)<<8 | GeneralPurposeRegisters.register(_C)),GeneralPurposeRegisters.register((byte)0b111));
            }else if(r == 0b01){
                Memory.write((short) (GeneralPurposeRegisters.register(_D)<<8 | GeneralPurposeRegisters.register(_E)),GeneralPurposeRegisters.register((byte)0b111));
            }

        });
    _insSet.put(4,()->{  // LDAX
        byte r = (byte) ((SpecialPurposeRegisters.IR() & 0B00110000) >>4);
        if(r == 0b00){
            GeneralPurposeRegisters.register((byte)0b111,Memory.read((short) (GeneralPurposeRegisters.register(_B)<<8 | GeneralPurposeRegisters.register(_C))));
        }else if(r == 0b01){
            GeneralPurposeRegisters.register((byte)0b111,Memory.read((short) (GeneralPurposeRegisters.register(_D)<<8 | GeneralPurposeRegisters.register(_E))));
        }

    });
    _insSet.put(5,()->{ // STA
        Memory.write(Memory.readPC(),GeneralPurposeRegisters.register((byte)0b111));
    });
    _insSet.put(6,()->{  // LDA
        GeneralPurposeRegisters.register((byte)0b111,Memory.read(Memory.readPC()));
    });
    _insSet.put(7,()->{  //SHLD
        short add = Memory.readPC();
        Memory.write(add,GeneralPurposeRegisters.register(_H));
        Memory.write((short) (add+1),GeneralPurposeRegisters.register(_L));
    });
    _insSet.put(8,()->{  //LHLD
        short add = (short) ((Memory.readPC()<<8)&0xffff | Memory.readPC()&0xff);
        GeneralPurposeRegisters.register(_H,Memory.read(add));
        GeneralPurposeRegisters.register(_L,Memory.read((short) (add+1)));
    });
    _insSet.put(9,()->{ //XCHG
        byte t;
        t = GeneralPurposeRegisters.register(_H);
        GeneralPurposeRegisters.register(_H,GeneralPurposeRegisters.register(_D));
        GeneralPurposeRegisters.register(_D,t);
        t = GeneralPurposeRegisters.register(_L);
        GeneralPurposeRegisters.register(_L,GeneralPurposeRegisters.register(_E));
        GeneralPurposeRegisters.register(_E,t);
    });

    _insSet.put(10,()->{ //JMP
        SpecialPurposeRegisters.PC(Memory.readPC());
    });
    _insSet.put(11,()->{ // JC
        if(SpecialPurposeRegisters.CY()){
            SpecialPurposeRegisters.PC(Memory.readPC());
        }else{
            Memory.readPC();
        }
    });
    _insSet.put(12,()->{ // JNC
        if(!SpecialPurposeRegisters.CY()){
            SpecialPurposeRegisters.PC(Memory.readPC());
        }else{
            Memory.readPC();
        }
    });
    _insSet.put(13,()->{ // JZ
        if(SpecialPurposeRegisters.Z()){
            SpecialPurposeRegisters.PC(Memory.readPC());
        }else{
            Memory.readPC();
        }
    });
    _insSet.put(14,()->{ // JNZ
        if(!SpecialPurposeRegisters.Z()){
            SpecialPurposeRegisters.PC(Memory.readPC());
        }else{
            Memory.readPC();
        }
    });
    _insSet.put(15,()->{ // JP
        if(SpecialPurposeRegisters.S()){
            SpecialPurposeRegisters.PC(Memory.readPC());
        }else{
            Memory.readPC();
        }
    });
    _insSet.put(16,()->{ //JM
        if(!SpecialPurposeRegisters.S()){
            SpecialPurposeRegisters.PC(Memory.readPC());
        }else{
            Memory.readPC();
        }
    });
    _insSet.put(17,()->{ // JPE
        if(SpecialPurposeRegisters.P()){
            SpecialPurposeRegisters.PC(Memory.readPC());
        }else{
            Memory.readPC();
        }
    });
    _insSet.put(18,()->{ // JPO
        if(!SpecialPurposeRegisters.P()){
            SpecialPurposeRegisters.PC(Memory.readPC());
        }else{
            Memory.readPC();
        }
    });
    _insSet.put(19,()->{ // PCHL
            SpecialPurposeRegisters.PC((short)(GeneralPurposeRegisters.register(_H)<<8|GeneralPurposeRegisters.register(_L)));
    });

    // TODO : RST IN/OUT
    // TODO : SPECIALS/CONTROL/NEW

    _insSet.put(20,()->{ // INR
        byte r = (byte) ((SpecialPurposeRegisters.IR() & 0B00111000) >>3);
        if(r == 0b110){
            short add = Memory.readPC();
            //88888888888888
            Memory.write(add, (byte) (Memory.read(add)+1));
        }else{
            int sum = (GeneralPurposeRegisters.register(r)+1);
            SpecialPurposeRegisters.CY(sum>>8 > 0b0);
            GeneralPurposeRegisters.register(r, (byte) sum);
        }
    });
    _insSet.put(21,()->{ // DCR
        byte r = (byte) ((SpecialPurposeRegisters.IR() & 0B00111000) >>3);
        if(r == 0b110){
            short add = Memory.readPC();
            Memory.write(add, (byte) (Memory.read(add)-1));
        }else{
            GeneralPurposeRegisters.register(r, (byte) (GeneralPurposeRegisters.register(r)-1));
        }
    });
    _insSet.put(22,()->{ // INX  TODO : INX DCX
        byte r = (byte) ((SpecialPurposeRegisters.IR() &  0B00110000) >> 4 );
        if(r == 0b00){
            short x =(short) (GeneralPurposeRegisters.BC()+1);
            GeneralPurposeRegisters.BC((byte) (x>>8),(byte) (x&0b0000000011111111));
        } else if(r == 0b01){
            short x =(short) (GeneralPurposeRegisters.DE()+1);
            GeneralPurposeRegisters.DE((byte) (x>>8),(byte) (x&0b0000000011111111));
        }else if(r == 0b10){
            short x =(short) (GeneralPurposeRegisters.HL()+1);
            GeneralPurposeRegisters.HL((byte) (x>>8),(byte) (x&0b0000000011111111));
        }

    });
    _insSet.put(23,()->{ // DCX
        byte r = (byte) ((SpecialPurposeRegisters.IR() &  0B00110000) >> 4 );
        if(r == 0b00){
            short x =(short) (GeneralPurposeRegisters.BC()-1);
            GeneralPurposeRegisters.BC((byte) (x>>8),(byte) (x&0b0000000011111111));
        } else if(r == 0b01){
            short x =(short) (GeneralPurposeRegisters.DE()-1);
            GeneralPurposeRegisters.DE((byte) (x>>8),(byte) (x&0b0000000011111111));
        }else if(r == 0b10){
            short x =(short) (GeneralPurposeRegisters.HL()-1);
            GeneralPurposeRegisters.HL((byte) (x>>8),(byte) (x&0b0000000011111111));
        }
    });
    _insSet.put(24,()->{ // ADD ADC ADI ACI
        byte r = (byte) (SpecialPurposeRegisters.IR() & 0b00000111);
        byte c = (byte) ((SpecialPurposeRegisters.IR() &0b1000)>>3);
        byte i = (byte) ((SpecialPurposeRegisters.IR() &0b01000000)>>6);
        byte carry = 0;
        if(c == 0b1 && SpecialPurposeRegisters.CY()) carry = 0b1;
        if(i == 0b0) {
            if (r != 0b110) {
//                System.out.println(GeneralPurposeRegisters.register(_A)+"^^^^"+GeneralPurposeRegisters.register(r));
                GeneralPurposeRegisters.register(_A, (byte) (GeneralPurposeRegisters.register(_A) + GeneralPurposeRegisters.register(r) + carry));
            } else {
                GeneralPurposeRegisters.register(_A, (byte) (GeneralPurposeRegisters.register(_A) + Memory.readHL() + carry));
            }
        } else{
            GeneralPurposeRegisters.register(_A,(byte) (GeneralPurposeRegisters.register(_A)+Memory.readPC()+carry));
        }
    });
    // TODO : DAD (addition)
    // TODO : subtraction, logical, rotate, special, control
//    _insSet.put((byte)0B11000000,()->{ //
//
//    });
//    _insSet.put((byte)0B11000000,()->{
//
//    });
//    _insSet.put((byte)0B11000000,()->{
//
//    });
    // TODO : STACK OPS
        _insSet.put(25,()->{ // PUSH
            byte r = (byte) ((SpecialPurposeRegisters.IR() & 0b00110000)>>4);
            switch (r){
                case 0b00:
                    Memory.writeSP((short) (GeneralPurposeRegisters.register(_B)<<8 | GeneralPurposeRegisters.register(_C)));
                    break;
                case 0b01:
                    Memory.writeSP((short) (GeneralPurposeRegisters.register(_D)<<8 | GeneralPurposeRegisters.register(_E)));
                    break;
                case 0b10:
                    Memory.writeSP((short) (GeneralPurposeRegisters.register(_H)<<8 | GeneralPurposeRegisters.register(_L)));
                    break;
                case 0b11:
                    byte flag;
                    flag = (byte) (SpecialPurposeRegisters.S()? 1 : 0);
                    flag = (byte) (flag<<1 | (SpecialPurposeRegisters.Z()? 1 : 0));
                    flag = (byte) (flag<<1);
                    flag = (byte) (flag<<1 | (SpecialPurposeRegisters.AC()? 1 : 0));
                    flag = (byte) (flag<<1);
                    flag = (byte) (flag<<1 | (SpecialPurposeRegisters.P()? 1 : 0));
                    flag = (byte) (flag<<1);
                    flag = (byte) (flag<<1 | (SpecialPurposeRegisters.CY()? 1 : 0));
                    Memory.writeSP((short) (GeneralPurposeRegisters.register(_A)<<8 | flag));
                    break;
            }
    });
    _insSet.put(26,()->{ // POP
        byte r = (byte) ((SpecialPurposeRegisters.IR() & 0b00110000)>>4);
        short data = Memory.readSP();
        switch (r){
            case 0b00:
                GeneralPurposeRegisters.register(_B, (byte) (data>>8));
                GeneralPurposeRegisters.register(_C, (byte) (data & 0b0000000011111111));
                break;
            case 0b01:
                GeneralPurposeRegisters.register(_D, (byte) (data>>8));
                GeneralPurposeRegisters.register(_E, (byte) (data & 0b0000000011111111));
                break;
            case 0b10:
                GeneralPurposeRegisters.register(_H, (byte) (data>>8));
                GeneralPurposeRegisters.register(_L, (byte) (data & 0b0000000011111111));
                break;
            case 0b11:
                GeneralPurposeRegisters.register(_A, (byte) (data>>8));
                byte flag = (byte) (data & 0b0000000011111111);
                SpecialPurposeRegisters.CY((flag & 0b00000001) == 0b00000001);
                SpecialPurposeRegisters.P((flag & 0b00000100) == 0b00000100);
                SpecialPurposeRegisters.AC((flag & 0b00010000) == 0b00010000);
                SpecialPurposeRegisters.Z((flag & 0b01000000) == 0b01000000);
                SpecialPurposeRegisters.S((flag & 0b10000000) == 0b10000000);
                break;
        }

    });
    _insSet.put(27,()->{ // XTHL
        short temp = (short) (GeneralPurposeRegisters.register(_H)<<8 | GeneralPurposeRegisters.register(_L));
        short temp2 = Memory.readSP();
        Memory.writeSP(temp);
        GeneralPurposeRegisters.register(_H, (byte) (temp>>8));
        GeneralPurposeRegisters.register(_L, (byte) (temp & 0b0000000011111111));
    });
    _insSet.put(28,()->{ // SPHL
        short temp = (short) (GeneralPurposeRegisters.register(_H)<<8 | GeneralPurposeRegisters.register(_L));
        Memory.writeSP(temp);
    });
    // ~ LXI INX DCX
    // TODO : CALL
    // TODO : RETURN
    _insSet.put(29,()->{ // CALL CC CNC CZ CNZ CP CM CPE CPO
        byte r = (byte) ((SpecialPurposeRegisters.IR()&0b00111000)>>3);
        byte c = (byte) (SpecialPurposeRegisters.IR()&0b00000001);
        short temp = SpecialPurposeRegisters.getPC();
        if(c == 0b0){
            if(r == 0b011 && SpecialPurposeRegisters.CY()){
                SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
                Memory.writeSP(temp);
            } else if(r == 0b010 && !SpecialPurposeRegisters.CY()){
                SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
                Memory.writeSP(temp);
            }  else if(r == 0b001 && SpecialPurposeRegisters.Z()){
                SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
                Memory.writeSP(temp);
            } else if(r == 0b000 && !SpecialPurposeRegisters.Z()){
                SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
                Memory.writeSP(temp);
            } else if(r == 0b110 && SpecialPurposeRegisters.S()){
                SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
                Memory.writeSP(temp);
            } else if(r == 0b111 && !SpecialPurposeRegisters.S()){
                SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
                Memory.writeSP(temp);
            } else if(r == 0b101 && SpecialPurposeRegisters.P()){
                SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
                Memory.writeSP(temp);
            } else if(r == 0b100 && !SpecialPurposeRegisters.P()){
                SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
                Memory.writeSP(temp);
            }
        } else{
            SpecialPurposeRegisters.PC((short) (Memory.readPC()<<8 | Memory.readPC()));
            Memory.writeSP(temp);
        }
    });
    _insSet.put(30,()->{  //RET RC RNC RZ RNZ RP RM RPE RPO
        byte r = (byte) ((SpecialPurposeRegisters.IR()&0b00111000)>>3);
        byte c = (byte) (SpecialPurposeRegisters.IR()&0b00000001);
//        short temp = SpecialPurposeRegisters.getPC();
        if(c == 0b0){
            if(r == 0b011 && SpecialPurposeRegisters.CY()){
                SpecialPurposeRegisters.PC(Memory.readSP());
            } else if(r == 0b010 && !SpecialPurposeRegisters.CY()){
                SpecialPurposeRegisters.PC(Memory.readSP());
            }  else if(r == 0b001 && SpecialPurposeRegisters.Z()){
                SpecialPurposeRegisters.PC(Memory.readSP());
            } else if(r == 0b000 && !SpecialPurposeRegisters.Z()){
                SpecialPurposeRegisters.PC(Memory.readSP());
            } else if(r == 0b110 && SpecialPurposeRegisters.S()){
                SpecialPurposeRegisters.PC(Memory.readSP());
            } else if(r == 0b111 && !SpecialPurposeRegisters.S()){
                SpecialPurposeRegisters.PC(Memory.readSP());
            } else if(r == 0b101 && SpecialPurposeRegisters.P()){
                SpecialPurposeRegisters.PC(Memory.readSP());
            } else if(r == 0b100 && !SpecialPurposeRegisters.P()){
                SpecialPurposeRegisters.PC(Memory.readSP());
            }
        } else{
            SpecialPurposeRegisters.PC(Memory.readSP());
        }
    });
    // TODO : subtraction, logical, rotate, special, control
    _insSet.put(31,()->{  // SUB SBB SUI SBI
        byte r = (byte) (SpecialPurposeRegisters.IR() & 0b00000111);
        byte c = (byte) ((SpecialPurposeRegisters.IR() &0b1000)>>3);
        byte i = (byte) ((SpecialPurposeRegisters.IR() &0b01000000)>>6);
        byte carry = 0;
        if(c == 0b1 && SpecialPurposeRegisters.CY()) carry = 0b1;
        if(i == 0b0) {
            if (r != 0b110) {
//                System.out.println(GeneralPurposeRegisters.register(_A)+"^^^^"+GeneralPurposeRegisters.register(r));
                GeneralPurposeRegisters.register(_A, (byte) (GeneralPurposeRegisters.register(_A) - GeneralPurposeRegisters.register(r) - carry));
            } else {
                GeneralPurposeRegisters.register(_A, (byte) (GeneralPurposeRegisters.register(_A) - Memory.readHL() - carry));
            }
        } else{
            GeneralPurposeRegisters.register(_A,(byte) (GeneralPurposeRegisters.register(_A)-Memory.readPC()-carry));
        }

    });
    _insSet.put(32,()->{ // ANA XRA ORA CMP ANI XRI ORI CPI
        byte r = (byte)(SpecialPurposeRegisters.IR()&0b00000111);
        byte i = (byte) ((SpecialPurposeRegisters.IR()&0b01000000)>>6);
        byte op = (byte) ((SpecialPurposeRegisters.IR()&0b00111000)>>3);
        if(i == 0b0){
            if(r == _M){
                switch(op){
                    case 0b100: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) & Memory.readHL()));
                        break;
                    case 0b101: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) ^ Memory.readHL()));
                        break;
                    case 0b110: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) | Memory.readHL()));
                        break;
                    case 0b111: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) & Memory.readHL()));  // TODO : CMP
                        break;
                }
            } else {
                switch(op){
                    case 0b100: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) & GeneralPurposeRegisters.register(r)));
                        break;
                    case 0b101: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) ^ GeneralPurposeRegisters.register(r)));
                        break;
                    case 0b110: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) | GeneralPurposeRegisters.register(r)));
                        break;
                    case 0b111: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) & GeneralPurposeRegisters.register(r)));  // TODO : CMP
                        break;
                }
            }
        } else {
            switch(op){
                case 0b100: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) & Memory.readPC()));
                    break;
                case 0b101: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) ^ Memory.readPC()));
                    break;
                case 0b110: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) | Memory.readPC()));
                    break;
                case 0b111: GeneralPurposeRegisters.register(_A,(byte)(GeneralPurposeRegisters.register(_A) & Memory.readPC()));  // TODO : CMP
                    break;
            }
        }

    });
    _insSet.put(33,()->{ // CMA STC CMC DAA
        byte op = (byte) ((SpecialPurposeRegisters.IR()&0b00011000)>>3);
        switch (op){
            case 0b00: break;  // TODO : DAA
            case 0b01: GeneralPurposeRegisters.register(_A, (byte) ~GeneralPurposeRegisters.register(_A));
            break;
            case 0b10: SpecialPurposeRegisters.CY(true);
            break;
            case 0b11: SpecialPurposeRegisters.CY(!SpecialPurposeRegisters.CY());
            break;
        }
    });
    _insSet.put(34,()->{ // NOP

    });
//    _insSet.put(0,()->{
//
//    });
//    _insSet.put(0,()->{
//
//    });
//    _insSet.put(0,()->{
//
//    });


    }

    static {
        /* MOVE LOAD STORE */
        _decode.put("MOV",0);
        _decode.put("MVI",1);
        _decode.put("LXI",2);
        _decode.put("STAX",3);
        _decode.put("LDAX",4);
        _decode.put("STA",5);
        _decode.put("LDA",6);
        _decode.put("SHLD",7);
        _decode.put("LHLD",8);
        _decode.put("XCHG",9);
        /* STACK-OPS */
//        _decode.put("PUSH",(byte)0B11000101);
//        _decode.put("POP",(byte)0B11000001);
//        _decode.put("XTHL",(byte)0B11100011);
//        _decode.put("SPHL",(byte)0B11111001);
//        _decode.put("LXI",(byte)0B0); // ***
//        _decode.put("INX",(byte)0B00110011);
//        _decode.put("DCX",(byte)0B00111011);
        /* JUMP */
        _decode.put("JMP",10);
        _decode.put("JC",11);
        _decode.put("JNC",12);
        _decode.put("JZ",13);
        _decode.put("JNZ",14);
        _decode.put("JP",15);
        _decode.put("JM",16);
        _decode.put("JPE",17);
        _decode.put("JPO",18);
        _decode.put("PCHL",19);
        /* CALL */
//        _decode.put("CALL",(byte)0B11001100);
//        _decode.put("CC",  (byte)0B11011100);
//        _decode.put("CNC", (byte)0B11010100);
//        _decode.put("CZ",  (byte)0B11001100);
//        _decode.put("CNZ", (byte)0B11000100);
//        _decode.put("CP",  (byte)0B11110100);
//        _decode.put("CM",  (byte)0B11111100);
//        _decode.put("CPE", (byte)0B11101100);
//        _decode.put("CPO", (byte)0B11100100);
        /* RETURN */
//        _decode.put("RET",(byte)0B11001001);
//        _decode.put("RC", (byte)0B11011000);
//        _decode.put("RNC",(byte)0B11010000);
//        _decode.put("RZ", (byte)0B11001000);
//        _decode.put("RNZ",(byte)0B11000000);
//        _decode.put("RP", (byte)0B11110000);
//        _decode.put("RM", (byte)0B11111000);
//        _decode.put("RPE",(byte)0B11101000);
//        _decode.put("RPO",(byte)0B11100000);
        /* RESTART */
//        _decode.put("RST",(byte)0B11000111);
        /* I/O */
//        _decode.put("IN", (byte)0B11011011);
//        _decode.put("OUT",(byte)0B11010011);
        /* INCREMENT / DECREMENT */
        _decode.put("INR",20);
        _decode.put("DCR",21);
        _decode.put("INX",22); // ****
        _decode.put("DCX",23); // ****
        /* ADD */
        _decode.put("ADD",24);
        _decode.put("ADC",24);
        _decode.put("ADI",24);
        _decode.put("ACI",24);
//        _decode.put("DAD",);
        /* SUB */
//        _decode.put("SUB",(byte)0B10010000);
//        _decode.put("SBB",(byte)0B10011000);
//        _decode.put("SUI",(byte)0B11010110);
//        _decode.put("SBI",(byte)0B11011110);
        /* LOGICAL */
//        _decode.put("ANA",(byte)0B10100000);
//        _decode.put("XRA",(byte)0B10101000);
//        _decode.put("ORA",(byte)0B10110000);
//        _decode.put("CMP",(byte)0B10111000);
//        _decode.put("ANI",(byte)0B11100110);
//        _decode.put("XRI",(byte)0B11101110);
//        _decode.put("ORI",(byte)0B11110110);
//        _decode.put("CPI",(byte)0B11111110);
//        _decode.put("RLC",(byte)0B00000111);
//        _decode.put("RRC",(byte)0B00001111);
//        _decode.put("RAL",(byte)0B00010111);
//        _decode.put("RAR",(byte)0B00011111);
        /* SPECIALS */
//        _decode.put("CMA",(byte)0B00100111);
//        _decode.put("STC",(byte)0B00101111);
//        _decode.put("CMC",(byte)0B00110111);
//        _decode.put("DAA",(byte)0B00111111);
        /* CONTROL */
//        _decode.put("EI", (byte)0B11111011);
//        _decode.put("DI", (byte)0B11110011);
//        _decode.put("NOP",(byte)0B00000000);
//        _decode.put("HLT",(byte)0B01110110);
        /* NEW 8085AH INSTRUCTIONS */
//        _decode.put("RIM",(byte)0B00100000);
//        _decode.put("SIM",(byte)0B00110000);
    }

    static {
        _mnemonic.put("ACI",0XCE);
        _mnemonic.put("ADC A",0X8F);
        _mnemonic.put("ADC B",0X88);
        _mnemonic.put("ADC C",0X89);
        _mnemonic.put("ADC D",0X8A);
        _mnemonic.put("ADC E",0X8B);
        _mnemonic.put("ADC H",0X8C);
        _mnemonic.put("ADC L",0X8D);
        _mnemonic.put("ADC M",0X8E);
        _mnemonic.put("ADD A",0X87);
        _mnemonic.put("ADD B",0X80);
        _mnemonic.put("ADD C",0X81);
        _mnemonic.put("ADD D",0X82);
        _mnemonic.put("ADD E",0X83);
        _mnemonic.put("ADD H",0X84);
        _mnemonic.put("ADD L",0X85);
        _mnemonic.put("ADD M",0X86);
        _mnemonic.put("ADI",0XC6);
        _mnemonic.put("ANA A",0XA7);
        _mnemonic.put("ANA B",0XA0);
        _mnemonic.put("ANA C",0XA1);
        _mnemonic.put("ANA D",0XA2);
        _mnemonic.put("ANA E",0XA3);
        _mnemonic.put("ANA H",0XA4);
        _mnemonic.put("ANA L",0XA5);
        _mnemonic.put("ANA M",0XA6);
        _mnemonic.put("ANI",0XE6);
        _mnemonic.put("CALL",0XCD);
        _mnemonic.put("CC",0XDC);
        _mnemonic.put("CM",0XFC);
        _mnemonic.put("CMA",0X2F);
        _mnemonic.put("CMC",0X3F);
        _mnemonic.put("CMP A",0XBF);
        _mnemonic.put("CMP B",0XB8);
        _mnemonic.put("CMP C",0XB9);
        _mnemonic.put("CMP D",0XBA);
        _mnemonic.put("CMP E",0XBB);
        _mnemonic.put("CMP H",0XBC);
        _mnemonic.put("CMP L",0XBD);
        _mnemonic.put("CMP M",0XBD);
        _mnemonic.put("CNC",0XD4);
        _mnemonic.put("CNZ",0XC4);
        _mnemonic.put("CP",0XF4);
        _mnemonic.put("CPE",0XEC);
        _mnemonic.put("CPI",0XFE);
        _mnemonic.put("CPO",0XE4);
        _mnemonic.put("CZ",0XCC);
        _mnemonic.put("DAA",0X27);
        _mnemonic.put("DAD B",0X09);
        _mnemonic.put("DAD D",0X19);
        _mnemonic.put("DAD H",0X29);
        _mnemonic.put("DAD SP",0X39);
        _mnemonic.put("DCR A",0X3D);
        _mnemonic.put("DCR B",0X05);
        _mnemonic.put("DCR C",0X0D);
        _mnemonic.put("DCR D",0X15);
        _mnemonic.put("DCR E",0X1D);
        _mnemonic.put("DCR H",0X25);
        _mnemonic.put("DCR L",0X2D);
        _mnemonic.put("DCR M",0X35);
        _mnemonic.put("DCX B",0X0B);
        _mnemonic.put("DCX D",0X1B);
        _mnemonic.put("DCX H",0X2B);
        _mnemonic.put("DCX SP",0X3B);
        _mnemonic.put("DI",0XF3);
        _mnemonic.put("EI",0XFB);
        _mnemonic.put("HLT",0X76);
        _mnemonic.put("IN",0XDB);
        _mnemonic.put("INR A",0X3C);
        _mnemonic.put("INR B",0X04);
        _mnemonic.put("INR C",0X0C);
        _mnemonic.put("INR D",0X14);
        _mnemonic.put("INR E",0X1C);
        _mnemonic.put("INR H",0X24);
        _mnemonic.put("INR L",0X2C);
        _mnemonic.put("INR M",0X34);
        _mnemonic.put("INX B",0X03);
        _mnemonic.put("INX D",0X13);
        _mnemonic.put("INX H",0X23);
        _mnemonic.put("INX SP",0X33);
        _mnemonic.put("JC",0XDA);
        _mnemonic.put("JM",0XFA);
        _mnemonic.put("JMP",0XC3);
        _mnemonic.put("JNC",0XD2);
        _mnemonic.put("JNZ",0XC2);
        _mnemonic.put("JP",0XF2);
        _mnemonic.put("JPE",0XEA);
        _mnemonic.put("JPO",0XE2);
        _mnemonic.put("JZ",0XCA);
        _mnemonic.put("LDA",0X3A);
        _mnemonic.put("LDAX B",0X0A);
        _mnemonic.put("LDAX D",0X1A);
        _mnemonic.put("LHLD",0X2A);
        _mnemonic.put("LXI B",0X01);
        _mnemonic.put("LXI D",0X11);
        _mnemonic.put("LXI H",0X21);
        _mnemonic.put("LXI SP",0X31);
        _mnemonic.put("MOV A,A",0X7F);
        _mnemonic.put("MOV A,B",0X78);
        _mnemonic.put("MOV A,C",0X79);
        _mnemonic.put("MOV A,D",0X7A);
        _mnemonic.put("MOV A,E",0X7B);
        _mnemonic.put("MOV A,H",0X7C);
        _mnemonic.put("MOV A,L",0X7D);
        _mnemonic.put("MOV A,M",0X7E);
        _mnemonic.put("MOV B,A",0X47);
        _mnemonic.put("MOV B,B",0X40);
        _mnemonic.put("MOV B,C",0X41);
        _mnemonic.put("MOV B,D",0X42);
        _mnemonic.put("MOV B,E",0X43);
        _mnemonic.put("MOV B,H",0X44);
        _mnemonic.put("MOV B,L",0X45);
        _mnemonic.put("MOV B,M",0X46);
        _mnemonic.put("MOV C,A",0X4F);
        _mnemonic.put("MOV C,B",0X48);
        _mnemonic.put("MOV C,C",0X49);
        _mnemonic.put("MOV C,D",0X4A);
        _mnemonic.put("MOV C,E",0X4B);
        _mnemonic.put("MOV C,H",0X4C);
        _mnemonic.put("MOV C,L",0X4D);
        _mnemonic.put("MOV C,M",0X4E);
        _mnemonic.put("MOV D,A",0X57);
        _mnemonic.put("MOV D,B",0X50);
        _mnemonic.put("MOV D,C",0X51);
        _mnemonic.put("MOV D,D",0X52);
        _mnemonic.put("MOV D,E",0X53);
        _mnemonic.put("MOV D,H",0X54);
        _mnemonic.put("MOV D,L",0X55);
        _mnemonic.put("MOV D,M",0X56);
        _mnemonic.put("MOV E,A",0X5F);
        _mnemonic.put("MOV E,B",0X58);
        _mnemonic.put("MOV E,C",0X59);
        _mnemonic.put("MOV E,D",0X5A);
        _mnemonic.put("MOV E,E",0X5B);
        _mnemonic.put("MOV E,H",0X5C);
        _mnemonic.put("MOV E,L",0X5D);
        _mnemonic.put("MOV E,M",0X5E);
        _mnemonic.put("MOV H,A",0X67);
        _mnemonic.put("MOV H,B",0X60);
        _mnemonic.put("MOV H,C",0X61);
        _mnemonic.put("MOV H,D",0X62);
        _mnemonic.put("MOV H,E",0X63);
        _mnemonic.put("MOV H,H",0X64);
        _mnemonic.put("MOV H,L",0X65);
        _mnemonic.put("MOV H,M",0X66);
        _mnemonic.put("MOV L,A",0X6F);
        _mnemonic.put("MOV L,B",0X68);
        _mnemonic.put("MOV L,C",0X69);
        _mnemonic.put("MOV L,D",0X6A);
        _mnemonic.put("MOV L,E",0X6B);
        _mnemonic.put("MOV L,H",0X6C);
        _mnemonic.put("MOV L,L",0X6D);
        _mnemonic.put("MOV L,M",0X6E);
        _mnemonic.put("MOV M,A",0X77);
        _mnemonic.put("MOV M,B",0X70);
        _mnemonic.put("MOV M,C",0X71);
        _mnemonic.put("MOV M,D",0X72);
        _mnemonic.put("MOV M,E",0X73);
        _mnemonic.put("MOV M,H",0X74);
        _mnemonic.put("MOV M,L",0X75);
        _mnemonic.put("MVI A",0X3E);
        _mnemonic.put("MVI B",0X06);
        _mnemonic.put("MVI C",0X0E);
        _mnemonic.put("MVI D",0X16);
        _mnemonic.put("MVI E",0X1E);
        _mnemonic.put("MVI H",0X26);
        _mnemonic.put("MVI L",0X2E);
        _mnemonic.put("MVI M",0X36);
        _mnemonic.put("NOP",0X00);
        _mnemonic.put("ORA A",0XB7);
        _mnemonic.put("ORA B",0XB0);
        _mnemonic.put("ORA C",0XB1);
        _mnemonic.put("ORA D",0XB2);
        _mnemonic.put("ORA E",0XB3);
        _mnemonic.put("ORA H",0XB4);
        _mnemonic.put("ORA L",0XB5);
        _mnemonic.put("ORA M",0XB6);
        _mnemonic.put("ORI",0XF6);
        _mnemonic.put("OUT",0XD3);
        _mnemonic.put("PCHL",0XE9);
        _mnemonic.put("POP B",0XC1);
        _mnemonic.put("POP D",0XD1);
        _mnemonic.put("POP H",0XE1);
        _mnemonic.put("POP PSW",0XF1);
        _mnemonic.put("PUSH B",0XC5);
        _mnemonic.put("PUSH D",0XD5);
        _mnemonic.put("PUSH H",0XE5);
        _mnemonic.put("PUSH PSW",0XF5);
        _mnemonic.put("RAL",0X17);
        _mnemonic.put("RAR",0X1F);
        _mnemonic.put("RC",0XD8);
        _mnemonic.put("RET",0XC9);
        _mnemonic.put("RIM",0X20);
        _mnemonic.put("RLC",0X07);
        _mnemonic.put("RM",0XF8);
        _mnemonic.put("RNC",0XD0);
        _mnemonic.put("RNZ",0XC0);
        _mnemonic.put("RP",0XF0);
        _mnemonic.put("RPE",0XE8);
        _mnemonic.put("RPO",0XE0);
        _mnemonic.put("RRC",0X0F);
        _mnemonic.put("RST 0",0XC7);
        _mnemonic.put("RST 1",0XCF);
        _mnemonic.put("RST 2",0XD7);
        _mnemonic.put("RST 3",0XDF);
        _mnemonic.put("RST 4",0XE7);
        _mnemonic.put("RST 5",0XEF);
        _mnemonic.put("RST 6",0XF7);
        _mnemonic.put("RST 7",0XFF);
        _mnemonic.put("RZ",0XC8);
        _mnemonic.put("SBB A",0X9F);
        _mnemonic.put("SBB B",0X98);
        _mnemonic.put("SBB C",0X99);
        _mnemonic.put("SBB D",0X9A);
        _mnemonic.put("SBB E",0X9B);
        _mnemonic.put("SBB H",0X9C);
        _mnemonic.put("SBB L",0X9D);
        _mnemonic.put("SBB M",0X9E);
        _mnemonic.put("SBI",0XDE);
        _mnemonic.put("SHLD",0X22);
        _mnemonic.put("SIM",0X30);
        _mnemonic.put("SPHL",0XF9);
        _mnemonic.put("STA",0X32);
        _mnemonic.put("STAX B",0X02);
        _mnemonic.put("STAX D",0X12);
        _mnemonic.put("STC",0X37);
        _mnemonic.put("SUB A",0X97);
        _mnemonic.put("SUB B",0X90);
        _mnemonic.put("SUB C",0X91);
        _mnemonic.put("SUB D",0X92);
        _mnemonic.put("SUB E",0X93);
        _mnemonic.put("SUB H",0X94);
        _mnemonic.put("SUB L",0X95);
        _mnemonic.put("SUB M",0X96);
        _mnemonic.put("SUI",0XD6);
        _mnemonic.put("XCHG",0XEB);
        _mnemonic.put("XRA A",0XAF);
        _mnemonic.put("XRA B",0XA8);
        _mnemonic.put("XRA C",0XA9);
        _mnemonic.put("XRA D",0XAA);
        _mnemonic.put("XRA E",0XAB);
        _mnemonic.put("XRA H",0XAC);
        _mnemonic.put("XRA L",0XAD);
        _mnemonic.put("XRA M",0XAE);
        _mnemonic.put("XRI",0XEE);
        _mnemonic.put("XTHL",0XE3);


    }

    static {
        _op_to_inst.put(0XCE,24); // ACI Data
        _op_to_inst.put(0X8F,24); // ADC A
        _op_to_inst.put(0X88,24); // ADC B
        _op_to_inst.put(0X89,24); // ADC C
        _op_to_inst.put(0X8A,24); // ADC D
        _op_to_inst.put(0X8B,24); // ADC E
        _op_to_inst.put(0X8C,24); // ADC H
        _op_to_inst.put(0X8D,24); // ADC L
        _op_to_inst.put(0X8E,24); // ADC M
        _op_to_inst.put(0X87,24); // ADD A
        _op_to_inst.put(0X80,24); // ADD B
        _op_to_inst.put(0X81,24); // ADD C
        _op_to_inst.put(0X82,24); // ADD D
        _op_to_inst.put(0X83,24); // ADD E
        _op_to_inst.put(0X84,24); // ADD H
        _op_to_inst.put(0X85,24); // ADD L
        _op_to_inst.put(0X86,24); // ADD M
        _op_to_inst.put(0XC6,24); // ADI Data
        _op_to_inst.put(0XA7,32); // ANA A
        _op_to_inst.put(0XA0,32); // ANA B
        _op_to_inst.put(0XA1,32); // ANA C
        _op_to_inst.put(0XA2,32); // ANA D
        _op_to_inst.put(0XA3,32); // ANA E
        _op_to_inst.put(0XA4,32); // ANA H
        _op_to_inst.put(0XA5,32); // ANA L
        _op_to_inst.put(0XA6,32); // ANA M
        _op_to_inst.put(0XE6,32); // ANI Data
        _op_to_inst.put(0XCD,29); // CALL Label
        _op_to_inst.put(0XDC,29); // CC Label
        _op_to_inst.put(0XFC,29); // CM Label
        _op_to_inst.put(0X2F,33); // CMA
        _op_to_inst.put(0X3F,33); // CMC
        _op_to_inst.put(0XBF,32); // CMP A
        _op_to_inst.put(0XB8,32); // CMP B
        _op_to_inst.put(0XB9,32); // CMP C
        _op_to_inst.put(0XBA,32); // CMP D
        _op_to_inst.put(0XBB,32); // CMP E
        _op_to_inst.put(0XBC,32); // CMP H
        _op_to_inst.put(0XBD,32); // CMP L
        _op_to_inst.put(0XBE,32); // CMP M
        _op_to_inst.put(0XD4,29); // CNC Label
        _op_to_inst.put(0XC4,29); // CNZ Label
        _op_to_inst.put(0XF4,29); // CP Label
        _op_to_inst.put(0XEC,29); // CPE Label
        _op_to_inst.put(0XFE,32); // CPI Data
        _op_to_inst.put(0XE4,29); // CPO Label
        _op_to_inst.put(0XCC,29); // CZ Label
        _op_to_inst.put(0X27,33); // DAA
        _op_to_inst.put(0X09,255); // DAD B
        _op_to_inst.put(0X19,255); // DAD D
        _op_to_inst.put(0X29,255); // DAD H
        _op_to_inst.put(0X39,255); // DAD SP
        _op_to_inst.put(0X3D,21); // DCR A
        _op_to_inst.put(0X05,21); // DCR B
        _op_to_inst.put(0X0D,21); // DCR C
        _op_to_inst.put(0X15,21); // DCR D
        _op_to_inst.put(0X1D,21); // DCR E
        _op_to_inst.put(0X25,21); // DCR H
        _op_to_inst.put(0X2D,21); // DCR L
        _op_to_inst.put(0X35,21); // DCR M
        _op_to_inst.put(0X0B,23); // DCX B
        _op_to_inst.put(0X1B,23); // DCX D
        _op_to_inst.put(0X2B,23); // DCX H
        _op_to_inst.put(0X3B,23); // DCX SP
        _op_to_inst.put(0XF3,255); // DI
        _op_to_inst.put(0XFB,255); // EI
        _op_to_inst.put(0X76,255); // HLT
        _op_to_inst.put(0XDB,255); // IN Port-address
        _op_to_inst.put(0X3C,20); // INR A
        _op_to_inst.put(0X04,20); // INR B
        _op_to_inst.put(0X0C,20); // INR C
        _op_to_inst.put(0X14,20); // INR D
        _op_to_inst.put(0X1C,20); // INR E
        _op_to_inst.put(0X24,20); // INR H
        _op_to_inst.put(0X2C,20); // INR L
        _op_to_inst.put(0X34,20); // INR M
        _op_to_inst.put(0X03,22); // INX B
        _op_to_inst.put(0X13,22); // INX D
        _op_to_inst.put(0X23,22); // INX H
        _op_to_inst.put(0X33,22); // INX SP
        _op_to_inst.put(0XDA,11); // JC Label
        _op_to_inst.put(0XFA,16); // JM Label
        _op_to_inst.put(0XC3,10); // JMP Label
        _op_to_inst.put(0XD2,12); // JNC Label
        _op_to_inst.put(0XC2,14); // JNZ Label
        _op_to_inst.put(0XF2,15); // JP Label
        _op_to_inst.put(0XEA,17); // JPE Label
        _op_to_inst.put(0XE2,18); // JPO Label
        _op_to_inst.put(0XCA,13); // JZ Label
        _op_to_inst.put(0X3A,6); // LDA Address
        _op_to_inst.put(0X0A,4); // LDAX B
        _op_to_inst.put(0X1A,4); // LDAX D
        _op_to_inst.put(0X2A,8); // LHLD Address
        _op_to_inst.put(0X01,2); // LXI B
        _op_to_inst.put(0X11,2); // LXI D
        _op_to_inst.put(0X21,2); // LXI H
        _op_to_inst.put(0X31,2); // LXI SP
        _op_to_inst.put(0X7F,0); // MOV A,A
        _op_to_inst.put(0X78,0); // MOV A,B
        _op_to_inst.put(0X79,0); // MOV A,C
        _op_to_inst.put(0X7A,0); // MOV A,D
        _op_to_inst.put(0X7B,0); // MOV A,E
        _op_to_inst.put(0X7C,0); // MOV A,H
        _op_to_inst.put(0X7D,0); // MOV A,L
        _op_to_inst.put(0X7E,0); // MOV A,M
        _op_to_inst.put(0X47,0); // MOV B,A
        _op_to_inst.put(0X40,0); // MOV B,B
        _op_to_inst.put(0X41,0); // MOV B,C
        _op_to_inst.put(0X42,0); // MOV B,D
        _op_to_inst.put(0X43,0); // MOV B,E
        _op_to_inst.put(0X44,0); // MOV B,H
        _op_to_inst.put(0X45,0); // MOV B,L
        _op_to_inst.put(0X46,0); // MOV B,M
        _op_to_inst.put(0X4F,0); // MOV C,A
        _op_to_inst.put(0X48,0); // MOV C,B
        _op_to_inst.put(0X49,0); // MOV C,C
        _op_to_inst.put(0X4A,0); // MOV C,D
        _op_to_inst.put(0X4B,0); // MOV C,E
        _op_to_inst.put(0X4C,0); // MOV C,H
        _op_to_inst.put(0X4D,0); // MOV C,L
        _op_to_inst.put(0X4E,0); // MOV C,M
        _op_to_inst.put(0X57,0); // MOV D,A
        _op_to_inst.put(0X50,0); // MOV D,B
        _op_to_inst.put(0X51,0); // MOV D,C
        _op_to_inst.put(0X52,0); // MOV D,D
        _op_to_inst.put(0X53,0); // MOV D,E
        _op_to_inst.put(0X54,0); // MOV D,H
        _op_to_inst.put(0X55,0); // MOV D,L
        _op_to_inst.put(0X56,0); // MOV D,M
        _op_to_inst.put(0X5F,0); // MOV E,A
        _op_to_inst.put(0X58,0); // MOV E,B
        _op_to_inst.put(0X59,0); // MOV E,C
        _op_to_inst.put(0X5A,0); // MOV E,D
        _op_to_inst.put(0X5B,0); // MOV E,E
        _op_to_inst.put(0X5C,0); // MOV E,H
        _op_to_inst.put(0X5D,0); // MOV E,L
        _op_to_inst.put(0X5E,0); // MOV E,M
        _op_to_inst.put(0X67,0); // MOV H,A
        _op_to_inst.put(0X60,0); // MOV H,B
        _op_to_inst.put(0X61,0); // MOV H,C
        _op_to_inst.put(0X62,0); // MOV H,D
        _op_to_inst.put(0X63,0); // MOV H,E
        _op_to_inst.put(0X64,0); // MOV H,H
        _op_to_inst.put(0X65,0); // MOV H,L
        _op_to_inst.put(0X66,0); // MOV H,M
        _op_to_inst.put(0X6F,0); // MOV L,A
        _op_to_inst.put(0X68,0); // MOV L,B
        _op_to_inst.put(0X69,0); // MOV L,C
        _op_to_inst.put(0X6A,0); // MOV L,D
        _op_to_inst.put(0X6B,0); // MOV L,E
        _op_to_inst.put(0X6C,0); // MOV L,H
        _op_to_inst.put(0X6D,0); // MOV L,L
        _op_to_inst.put(0X6E,0); // MOV L,M
        _op_to_inst.put(0X77,0); // MOV M,A
        _op_to_inst.put(0X70,0); // MOV M,B
        _op_to_inst.put(0X71,0); // MOV M,C
        _op_to_inst.put(0X72,0); // MOV M,D
        _op_to_inst.put(0X73,0); // MOV M,E
        _op_to_inst.put(0X74,0); // MOV M,H
        _op_to_inst.put(0X75,0); // MOV M,L
        _op_to_inst.put(0X3E,1); // MVI A,Data
        _op_to_inst.put(0X06,1); // MVI B,Data
        _op_to_inst.put(0X0E,1); // MVI C,Data
        _op_to_inst.put(0X16,1); // MVI D,Data
        _op_to_inst.put(0X1E,1); // MVI E,Data
        _op_to_inst.put(0X26,1); // MVI H,Data
        _op_to_inst.put(0X2E,1); // MVI L,Data
        _op_to_inst.put(0X36,1); // MVI M,Data
        _op_to_inst.put(0X00,34); // NOP
        _op_to_inst.put(0XB7,32); // ORA A
        _op_to_inst.put(0XB0,32); // ORA B
        _op_to_inst.put(0XB1,32); // ORA C
        _op_to_inst.put(0XB2,32); // ORA D
        _op_to_inst.put(0XB3,32); // ORA E
        _op_to_inst.put(0XB4,32); // ORA H
        _op_to_inst.put(0XB5,32); // ORA L
        _op_to_inst.put(0XB6,32); // ORA M
        _op_to_inst.put(0XF6,32); // ORI Data
        _op_to_inst.put(0XD3,255); // OUT Port-Address
        _op_to_inst.put(0XE9,255); // PCHL
        _op_to_inst.put(0XC1,26); // POP B
        _op_to_inst.put(0XD1,26); // POP D
        _op_to_inst.put(0XE1,26); // POP H
        _op_to_inst.put(0XF1,26); // POP PSW
        _op_to_inst.put(0XC5,25); // PUSH B
        _op_to_inst.put(0XD5,25); // PUSH D
        _op_to_inst.put(0XE5,25); // PUSH H
        _op_to_inst.put(0XF5,25); // PUSH PSW
        _op_to_inst.put(0X17,255); // RAL
        _op_to_inst.put(0X1F,255); // RAR
        _op_to_inst.put(0XD8,30); // RC
        _op_to_inst.put(0XC9,30); // RET
        _op_to_inst.put(0X20,255); // RIM
        _op_to_inst.put(0X07,255); // RLC
        _op_to_inst.put(0XF8,30); // RM
        _op_to_inst.put(0XD0,30); // RNC
        _op_to_inst.put(0XC0,30); // RNZ
        _op_to_inst.put(0XF0,30); // RP
        _op_to_inst.put(0XE8,30); // RPE
        _op_to_inst.put(0XE0,30); // RPO
        _op_to_inst.put(0X0F,255); // RRC
        _op_to_inst.put(0XC7,255); // RST 0
        _op_to_inst.put(0XCF,255); // RST 1
        _op_to_inst.put(0XD7,255); // RST 2
        _op_to_inst.put(0XDF,255); // RST 3
        _op_to_inst.put(0XE7,255); // RST 4
        _op_to_inst.put(0XEF,255); // RST 5
        _op_to_inst.put(0XF7,255); // RST 6
        _op_to_inst.put(0XFF,255); // RST 7
        _op_to_inst.put(0XC8,30); // RZ
        _op_to_inst.put(0X9F,31); // SBB A
        _op_to_inst.put(0X98,31); // SBB B
        _op_to_inst.put(0X99,31); // SBB C
        _op_to_inst.put(0X9A,31); // SBB D
        _op_to_inst.put(0X9B,31); // SBB E
        _op_to_inst.put(0X9C,31); // SBB H
        _op_to_inst.put(0X9D,31); // SBB L
        _op_to_inst.put(0X9E,31); // SBB M
        _op_to_inst.put(0XDE,31); // SBI Data
        _op_to_inst.put(0X22,7); // SHLD Address
        _op_to_inst.put(0X30,255); // SIM
        _op_to_inst.put(0XF9,28); // SPHL
        _op_to_inst.put(0X32,5); // STA Address
        _op_to_inst.put(0X02,3); // STAX B
        _op_to_inst.put(0X12,3); // STAX D
        _op_to_inst.put(0X37,33); // STC
        _op_to_inst.put(0X97,31); // SUB A
        _op_to_inst.put(0X90,31); // SUB B
        _op_to_inst.put(0X91,31); // SUB C
        _op_to_inst.put(0X92,31); // SUB D
        _op_to_inst.put(0X93,31); // SUB E
        _op_to_inst.put(0X94,31); // SUB H
        _op_to_inst.put(0X95,31); // SUB L
        _op_to_inst.put(0X96,31); // SUB M
        _op_to_inst.put(0XD6,31); // SUI Data
        _op_to_inst.put(0XEB,9); // XCHG
        _op_to_inst.put(0XAF,32); // XRA A
        _op_to_inst.put(0XA8,32); // XRA B
        _op_to_inst.put(0XA9,32); // XRA C
        _op_to_inst.put(0XAA,32); // XRA D
        _op_to_inst.put(0XAB,32); // XRA E
        _op_to_inst.put(0XAC,32); // XRA H
        _op_to_inst.put(0XAD,32); // XRA L
        _op_to_inst.put(0XAE,32); // XRA M
        _op_to_inst.put(0XEE,32); // XRI Data
        _op_to_inst.put(0XE3,27); // XTHL

    }

    static {
        _nBIT_INST.add("ACI");
        _nBIT_INST.add("ADI");
        _nBIT_INST.add("ANI");
        _3BIT_INST.add("CALL");
        _3BIT_INST.add("CC");
        _3BIT_INST.add("CM");
        _3BIT_INST.add("CNC");
        _3BIT_INST.add("CNZ");
        _3BIT_INST.add("CP");
        _3BIT_INST.add("CPE");
        _nBIT_INST.add("CPI");
        _3BIT_INST.add("CPO");
        _3BIT_INST.add("CZ");
        _nBIT_INST.add("IN");
        _3BIT_INST.add("JC");
        _3BIT_INST.add("JNC");
        _3BIT_INST.add("JM");
        _3BIT_INST.add("JMP");
        _3BIT_INST.add("JNZ");
        _3BIT_INST.add("JZ");
        _3BIT_INST.add("JPO");
        _3BIT_INST.add("JPE");
        _3BIT_INST.add("LDA");
        _3BIT_INST.add("LHLD");
        _3BIT_INST.add("LXI");
        _nBIT_INST.add("MVI");
        _nBIT_INST.add("ORI");
        _nBIT_INST.add("OUT");
        _nBIT_INST.add("SBI");
        _3BIT_INST.add("SHLD");
        _3BIT_INST.add("STA");
        _nBIT_INST.add("SUI");
        _nBIT_INST.add("XRI");

    }

    static void setPC(short startptr){
        SpecialPurposeRegisters.PC(startptr);
    }
    static void executeIR(){ // 0X76
        SpecialPurposeRegisters.IR(Memory.readPC());
        while((SpecialPurposeRegisters.IR()&0xff) != 0X76){
            _insSet.get(_op_to_inst.get(((int) SpecialPurposeRegisters.IR()) & 0xff)).run();
            SpecialPurposeRegisters.IR(Memory.readPC());
        }

    }

}
