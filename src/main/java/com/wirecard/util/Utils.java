package com.wirecard.util;

public class Utils {
    public static boolean isNumber(String str){
        try{
            Double.parseDouble(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
