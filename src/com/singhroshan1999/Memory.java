package com.singhroshan1999;
import java.util.*;
public class Memory {
    private static final int size = 65536;
    private static byte[] memory = new byte[size];
    static byte read(byte address){
        return memory[(int)address];
    }
    static void write(byte address,byte data){
        memory[(int)address] = data;
    }
}
