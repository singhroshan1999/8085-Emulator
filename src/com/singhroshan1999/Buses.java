package com.singhroshan1999;

class Buses {
    private static byte _ADDBUSH,_ADDBUSL; // 8bit high + 8bit low // address buffer + address/data buffer
    private static byte __DATABUS;  // __ internal
    // TODO : internal bus
    private static boolean ALE,MUX;

    static void ADDBUS(byte high,byte low){
        Buses._ADDBUSH = high;
        Buses._ADDBUSL = low;
    }
    static void D0_7(byte low){
        Buses._ADDBUSL = low;
    }

    static byte A8_15(){
        return _ADDBUSH;
    }

    static byte A0_7(){
        return (ALE)?_ADDBUSL:0b00000000;
    }

    static void DATABUS(byte b){
        Buses.__DATABUS = b;
    }
    static byte DATABUS(){
        return Buses.__DATABUS;
    }
    static void transDtoAB(){
        Buses._ADDBUSL = Buses.__DATABUS;
    }






}
