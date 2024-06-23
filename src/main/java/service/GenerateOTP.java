package service;

import java.util.Random;

public class GenerateOTP {
    public static String getOTP(){
        Random random = new Random();
        return String.format("%4d", random.nextInt(10000));
    }
}
