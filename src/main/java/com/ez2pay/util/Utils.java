package com.ez2pay.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {

    public static boolean isNumber(String str){
        try{
            Double.parseDouble(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static String parseObjectToJson (Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }

}
