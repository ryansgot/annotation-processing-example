package com.amazon.example.apdemo;

import com.amazon.example.ap.annotations.Stringify;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Stringify(dateFormat = "yyyy.MM.dd G 'at' HH:mm:ss z")
public class ExampleClass {

    /*package*/ Date aDate;
    /*package*/ String aString;
    /*package*/ int anInt;
    /*package*/ long aLong;
    /*package*/ double aDouble;
    /*package*/ short aShort;
    /*package*/ Date anotherDate;
    /*package*/ String anotherString;
    /*package*/ int anotherInt;
    /*package*/ long anotherLong;
    /*package*/ double anotherDouble;
    /*package*/ short anotherShort;
    /*package*/ Date anotherDate2;
    /*package*/ String anotherString2;
    /*package*/ int anotherInt2;
    /*package*/ long anotherLong2;
    /*package*/ double anotherDouble2;
    /*package*/ short anotherShort2;
    /*package*/ Date anotherDate3;
    /*package*/ String anotherString3;
    /*package*/ int anotherInt3;
    /*package*/ long anotherLong3;
    /*package*/ double anotherDouble3;
    /*package*/ short anotherShort3;
    /*package*/ Date anotherDate4;
    /*package*/ String anotherString4;
    /*package*/ int anotherInt4;
    /*package*/ long anotherLong4;
    /*package*/ double anotherDouble4;
    /*package*/ short anotherShort4;

    public ExampleClass(Date aDate, String aString, int anInt, long aLong, double aDouble, short aShort, Date anotherDate, String anotherString, int anotherInt, long anotherLong, double anotherDouble, short anotherShort, Date anotherDate2, String anotherString2, int anotherInt2, long anotherLong2, double anotherDouble2, short anotherShort2, Date anotherDate3, String anotherString3, int anotherInt3, long anotherLong3, double anotherDouble3, short anotherShort3, Date anotherDate4, String anotherString4, int anotherInt4, long anotherLong4, double anotherDouble4, short anotherShort4) {
        this.aDate = aDate;
        this.aString = aString;
        this.anInt = anInt;
        this.aLong = aLong;
        this.aDouble = aDouble;
        this.aShort = aShort;
        this.anotherDate = anotherDate;
        this.anotherString = anotherString;
        this.anotherInt = anotherInt;
        this.anotherLong = anotherLong;
        this.anotherDouble = anotherDouble;
        this.anotherShort = anotherShort;
        this.anotherDate2 = anotherDate2;
        this.anotherString2 = anotherString2;
        this.anotherInt2 = anotherInt2;
        this.anotherLong2 = anotherLong2;
        this.anotherDouble2 = anotherDouble2;
        this.anotherShort2 = anotherShort2;
        this.anotherDate3 = anotherDate3;
        this.anotherString3 = anotherString3;
        this.anotherInt3 = anotherInt3;
        this.anotherLong3 = anotherLong3;
        this.anotherDouble3 = anotherDouble3;
        this.anotherShort3 = anotherShort3;
        this.anotherDate4 = anotherDate4;
        this.anotherString4 = anotherString4;
        this.anotherInt4 = anotherInt4;
        this.anotherLong4 = anotherLong4;
        this.anotherDouble4 = anotherDouble4;
        this.anotherShort4 = anotherShort4;
    }

    public static ExampleClass random() {
        Random r = new Random();
        SecureRandom sRandom = new SecureRandom();
        return new ExampleClass(
                new Date(r.nextLong() % new Date().getTime()),
                new BigInteger(r.nextInt(128), sRandom).toString(32),
                r.nextInt(),
                r.nextLong(),
                r.nextDouble(),
                (short) r.nextInt(),
                new Date(r.nextLong() % new Date().getTime()),
                new BigInteger(r.nextInt(128), sRandom).toString(32),
                r.nextInt(),
                r.nextLong(),
                r.nextDouble(),
                (short) r.nextInt(),
                new Date(r.nextLong() % new Date().getTime()),
                new BigInteger(r.nextInt(128), sRandom).toString(32),
                r.nextInt(),
                r.nextLong(),
                r.nextDouble(),
                (short) r.nextInt(),
                new Date(r.nextLong() % new Date().getTime()),
                new BigInteger(r.nextInt(128), sRandom).toString(32),
                r.nextInt(),
                r.nextLong(),
                r.nextDouble(),
                (short) r.nextInt(),
                new Date(r.nextLong() % new Date().getTime()),
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
