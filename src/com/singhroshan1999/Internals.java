package com.singhroshan1999;

public class Internals {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    static void parseInternals(String str){
        String[] strop = str.split(" ");
        switch (strop[0]){
            case "%EXECUTE": InstructionSet.executeIR();
            break;
            case "%SETMEMORY": MnemonicParser.setStart(Short.parseShort(strop[1]));
            break;
            case "%SETPC": InstructionSet.setPC(Short.parseShort(strop[1]));
            break;
            case "%INITMEMORY": Memory.initMemory();
            break;
            case "%GETREGISTER": printRegisters();
            break;
            case "%GETMEMORY": printMemory(Short.parseShort(strop[1]),Short.parseShort(strop[2]));
                break;
        }
    }
    private static void printRegisters(){
        System.out.print(ANSI_BLACK_BACKGROUND);
        System.out.println(ANSI_CYAN+"########################");
        System.out.printf ("# Register  #  values  #\n");
        System.out.println("########################");
        System.out.printf("#    A      #   %3d    #\n",GeneralPurposeRegisters.register((byte) 0b111)&0xff);
        System.out.printf("#    B      #   %3d    #\n",GeneralPurposeRegisters.register((byte) 0b000)&0xff);
        System.out.printf("#    C      #   %3d    #\n",GeneralPurposeRegisters.register((byte) 0b001)&0xff);
        System.out.printf("#    D      #   %3d    #\n",GeneralPurposeRegisters.register((byte) 0b010)&0xff);
        System.out.printf("#    E      #   %3d    #\n",GeneralPurposeRegisters.register((byte) 0b011)&0xff);
        System.out.printf("#    H      #   %3d    #\n",GeneralPurposeRegisters.register((byte) 0b100)&0xff);
        System.out.printf("#    L      #   %3d    #\n",GeneralPurposeRegisters.register((byte) 0b101)&0xff);
        System.out.printf("#    SP     # %5d    #\n",SpecialPurposeRegisters.SP()&0xffff);
        System.out.printf("#    IR     #   %3d    #\n",SpecialPurposeRegisters.IR()&0xff);
        System.out.printf("#    PC     #   %3d    #\n",SpecialPurposeRegisters.getPC()&0xffff);
        System.out.printf("#    S      #   %3d    #\n",SpecialPurposeRegisters.S()?1:0);
        System.out.printf("#    Z      #   %3d    #\n",SpecialPurposeRegisters.Z()?1:0);
        System.out.printf("#    AC     #   %3d    #\n",SpecialPurposeRegisters.AC()?1:0);
        System.out.printf("#    P      #   %3d    #\n",SpecialPurposeRegisters.P()?1:0);
        System.out.printf("#    CY     #   %3d    #\n",SpecialPurposeRegisters.CY()?1:0);
        System.out.printf("#    BC     # %5d    #\n",((GeneralPurposeRegisters.register((byte) 0b000)<<8)&0xffff | GeneralPurposeRegisters.register((byte) 0b001)&0xff));
        System.out.printf("#    DE     # %5d    #\n",((GeneralPurposeRegisters.register((byte) 0b010)<<8)&0xffff | GeneralPurposeRegisters.register((byte) 0b011)&0xff));
        System.out.printf("#    HL     # %5d    #\n",((GeneralPurposeRegisters.register((byte) 0b100)<<8)&0xffff | GeneralPurposeRegisters.register((byte) 0b101)&0xff));
        System.out.println("########################");
        System.out.print(ANSI_RESET);
    }
    private static void printMemory(short l,short r){
        System.out.print(ANSI_BLACK_BACKGROUND);
        System.out.println(ANSI_YELLOW+"######################");
        System.out.printf ("# Address  #  Value  #\n");
        System.out.println("######################");
        for(short i = l;i<=r;i++) {
            System.out.printf("#  %5d  #   %3d    #\n",i, Memory.read(i)&0xff);
        }
        System.out.println("######################");
        System.out.print(ANSI_RESET);
    }
}
