package com.amazon.example.apdemo;

import com.amazon.example.ap.annotations.Stringify;
import com.amazon.example.apdemo.anotherpacakge.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Stringify(dateFormat = "EEE, d MMM yyyy HH:mm:ss Z")
public class ExampleClass2 {

    /*package*/ String aString;
    /*package*/ int anInt;
    /*package*/ long aLong;
    /*package*/ double aDouble;
    /*package*/ short aShort;
    /*package*/ byte aByte;

    public ExampleClass2(String aString, int anInt, long aLong, double aDouble, short aShort, byte aByte) {
        this.aString = aString;
        this.anInt = anInt;
        this.aLong = aLong;
        this.aDouble = aDouble;
        this.aShort = aShort;
        this.aByte = aByte;
    }

    public static ExampleClass2 random() {
        Random r = new Random();
        SecureRandom sRandom = new SecureRandom();
        byte[] bytes = new byte[1];
        r.nextBytes(bytes);
        return new ExampleClass2(
                        new BigInteger(r.nextInt(128), sRandom).toString(32),
                        r.nextInt(),
                        r.nextLong(),
                        r.nextDouble(),
                        (short) r.nextInt(),
                        bytes[0]
                );
    }

    @Override
    public String toString() {
        return Stringifier.stringify(this);
    }


}
