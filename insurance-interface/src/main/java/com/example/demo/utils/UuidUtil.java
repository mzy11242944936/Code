package com.example.demo.utils;

import java.util.UUID;

public class UuidUtil {
    public static String getUuId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
