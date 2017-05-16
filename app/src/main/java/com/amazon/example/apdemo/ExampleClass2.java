package com.amazon.example.apdemo;

import com.amazon.example.ap.annotations.Stringify;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Stringify(dateFormat = "EEE, d MMM yyyy HH:mm:ss Z")
public class ExampleClass2 {

    /*package*/ Date aDate;
    /*package*/ String aString;
    /*package*/ int anInt;
    /*package*/ long aLong;
    /*package*/ double aDouble;
    /*package*/ short aShort;
    /*package*/ byte aByte;

    public ExampleClass2(Date aDate, String aString, int anInt, long aLong, double aDouble, short aShort, byte aByte) {
        this.aDate = aDate;
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
                        new Date(r.nextLong() % new Date().getTime()),
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
