package com.amazon.example.apdemo;

import com.amazon.example.ap.annotations.Stringify;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Stringify(dateFormat = "yyyy.MM.dd G 'at' HH:mm:ss z")
public class ExampleClass {

    /*package*/ String aString;
    /*package*/ int anInt;
    /*package*/ long aLong;
    /*package*/ double aDouble;
    /*package*/ short aShort;

    public ExampleClass(String aString, int anInt, long aLong, double aDouble, short aShort) {
        this.aString = aString;
        this.anInt = anInt;
        this.aLong = aLong;
        this.aDouble = aDouble;
        this.aShort = aShort;
    }

    public static ExampleClass random() {
        Random r = new Random();
        SecureRandom sRandom = new SecureRandom();
        return new ExampleClass(
                        new BigInteger(r.nextInt(128), sRandom).toString(32),
                        r.nextInt(),
                        r.nextLong(),
                        r.nextDouble(),
                        (short) r.nextInt()
                );
    }

    @Override
    public String toString() {
        return Stringifier.stringify(this);
    }

}
