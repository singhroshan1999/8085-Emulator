package com.singhroshan1999;
import com.singhroshan1999.*;
public class ControlUnit {
    /* assume all instructions are stored */
    static boolean XYZ;
    void executeAddress(byte high,byte low){
        short _16bit = (short) (high<<8 | low);
        SpecialPurposeRegisters.PC(_16bit);
        SpecialPurposeRegisters.IR(Memory.read(SpecialPurposeRegisters.PC()));
        while(XYZ){
            InstructionSet.executeIR();
        }
    }
}
