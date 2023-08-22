/*
 * Copyright (C) 2023 Alonso del Arte
 *
 * This program is free software; you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation; either version 2 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package currency;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CurrencyAmount class.
 * @author Alonso del Arte
 */
public class CurrencyAmountTest {

    static final Currency DOLLARS = Currency.getInstance(Locale.US);
    static final Currency EUROS = Currency.getInstance("EUR");
    static final Currency DINARS
            = Currency.getInstance(Locale.forLanguageTag("ar-ly"));
    static final Currency YEN = Currency.getInstance(Locale.JAPAN);
    
    private static final Random RANDOM = new Random();

    @Test
    public void testToString() {
        System.out.println("toString");
        CurrencyAmount amount = new CurrencyAmount(49989, DOLLARS);
        String expected = "$499.89";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringOtherAmount() {
        CurrencyAmount amount = new CurrencyAmount(104250, DOLLARS);
        String expected = "$1042.50";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringCentAmount() {
        CurrencyAmount amount = new CurrencyAmount(5, DOLLARS);
        String expected = "$0.05";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringNegativeCentAmount() {
        CurrencyAmount amount = new CurrencyAmount(-8, DOLLARS);
        String expected = "$-0.08";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringEuroAmount() {
        CurrencyAmount amount = new CurrencyAmount(7320, EUROS);
        String expected = "EUR73.20";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringDinarAmount() {
        CurrencyAmount amount = new CurrencyAmount(29505, DINARS);
        String expected = "LYD29.505";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringDirhamAmount() {
        Locale arJordan = Locale.forLanguageTag("ar-JO");
        Currency dinars = Currency.getInstance(arJordan);
        CurrencyAmount amount = new CurrencyAmount(709, dinars);
        String expected = "JOD0.709";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringYenAmount() {
        CurrencyAmount amount = new CurrencyAmount(20167, YEN);
        String expected = "JPY20167";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        System.out.println("equals()");
        int cents = RANDOM.nextInt();
        CurrencyAmount amount = new CurrencyAmount(cents, EUROS);
        assertEquals(amount, amount);
    }
    
    @Test
    public void testNotEqualsNull() {
        int cents = RANDOM.nextInt();
        CurrencyAmount amount = new CurrencyAmount(cents, EUROS);
        assertNotEquals(amount, null);
    }
    
    @Test
    public void testNotEqualsOtherClass() {
        int cents = RANDOM.nextInt();
        CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Test executed at " + now.toString());
        assertNotEquals(amount, now);
    }
    
    @Test
    public void testDiffCentsSameCurrency() {
        int cents = RANDOM.nextInt();
        CurrencyAmount amountA = new CurrencyAmount(cents, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(cents + 1, DOLLARS);
        assertNotEquals(amountA, amountB);
    }

    @Test
    public void testSameCentsSameCurrency() {
        CurrencyAmount amountA = new CurrencyAmount(2989, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(2989, DOLLARS);
        assertEquals(amountA, amountB);
    }
    
    @Test
    public void testSameCentsDiffCurrency() {
        int cents = RANDOM.nextInt();
        CurrencyAmount amountA = new CurrencyAmount(cents, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(cents, YEN);
        assertNotEquals(amountA, amountB);
    }
    
    @Test
    public void testNotEqualsSubClass() {
        int cents = RANDOM.nextInt();
        CurrencyAmount amountA = new CurrencyAmount(cents, DOLLARS);
        DollarAmount amountB = new DollarAmount(cents);
        assertNotEquals(amountA, amountB);
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int capacity = RANDOM.nextInt(64) + 16;
        Set<CurrencyAmount> amounts = new HashSet<>(capacity);
        Set<Integer> hashes = new HashSet<>(capacity);
        while (amounts.size() < capacity) {
            int cents = RANDOM.nextInt();
            CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
            amounts.add(amount);
            hashes.add(amount.hashCode());
        }
        int expected = amounts.size();
        int actual = hashes.size();
        String msg = "Collection of " + expected 
                + " distinct amounts should have as many hashes";
        assertEquals(msg, expected, actual);
    }
    
    @Test
    public void testDiffHashCodeForDiffAmount() {
        int centsA = RANDOM.nextInt(262144);
        int centsB = centsA + RANDOM.nextInt(centsA) + 1;
        CurrencyAmount amountA = new CurrencyAmount(centsA, EUROS);
        CurrencyAmount amountB = new CurrencyAmount(centsB, EUROS);
        int hashA = amountA.hashCode();
        int hashB = amountB.hashCode();
        String msg = amountA.toString() + " hashes to " + hashA 
                + ", should not be the same as " + amountB.toString() 
                + " which hashes to " + hashB;
        assert hashA != hashB : msg;
    }

    @Test
    public void testDiffHashCodeForDiffCurrency() {
        int cents = RANDOM.nextInt();
        CurrencyAmount amountA = new CurrencyAmount(cents, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(cents, EUROS);
        int hashA = amountA.hashCode();
        int hashB = amountB.hashCode();
        String msg = amountA.toString() + " hashes to " + hashA 
                + ", should not be the same as " + amountB.toString() 
                + " which hashes to " + hashB;
        assert hashA != hashB : msg;
    }

    @Test
    public void testGetAmountInCents() {
        System.out.println("getAmountInCents");
        CurrencyAmount amount = new CurrencyAmount(15347, EUROS);
        long expected = 15347;
        long actual = amount.getAmountInCents();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrency() {
        System.out.println("getCurrency");
        CurrencyAmount amount = new CurrencyAmount(15347, EUROS);
        Currency actual = amount.getCurrency();
        assertEquals(EUROS, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testPlusNull() {
        int cents = RANDOM.nextInt();
        CurrencyAmount addend = new CurrencyAmount(cents, EUROS);
        CurrencyAmount result = addend.plus(null);
        System.out.println(addend.toString() + " plus null equals "
                + result.toString() + "???");
    }

    @Test
    public void testPlus() {
        System.out.println("plus");
        int centsA = RANDOM.nextInt(262144) + 16;
        int centsB = RANDOM.nextInt(262144) + 16;
        CurrencyAmount addendA = new CurrencyAmount(centsA, DOLLARS);
        CurrencyAmount addendB = new CurrencyAmount(centsB, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(centsA + centsB, DOLLARS);
        CurrencyAmount actual = addendA.plus(addendB);
        String msg = addendA.toString() + " + " + addendB + " expected to be " 
                + expected.toString();
        assertEquals(msg, expected, actual);
    }

    @Test
    public void testPlusEuros() {
        int centsA = RANDOM.nextInt(262144) + 16;
        int centsB = RANDOM.nextInt(262144) + 16;
        CurrencyAmount addendA = new CurrencyAmount(centsA, EUROS);
        CurrencyAmount addendB = new CurrencyAmount(centsB, EUROS);
        CurrencyAmount expected = new CurrencyAmount(centsA + centsB, EUROS);
        CurrencyAmount actual = addendA.plus(addendB);
        String msg = addendA.toString() + " + " + addendB + " expected to be " 
                + expected.toString();
        assertEquals(msg, expected, actual);
    }

    @Test(expected = CurrencyConversionNeededException.class)
    public void testPlusDifferentCurrencies() {
        int cents = RANDOM.nextInt(131072) + 8;
        CurrencyAmount dollars = new CurrencyAmount(cents, DOLLARS);
        CurrencyAmount euros = new CurrencyAmount(cents, EUROS);
        CurrencyAmount result = dollars.plus(euros);
        System.out.println("Trying to add " + dollars.toString() + " to "
                + euros.toString()
                + " should have caused an exception, not given result "
                + result.toString());
    }

    @Test(expected = ArithmeticException.class)
    public void testPlusTooMuch() {
        long centsA = ((long) Integer.MAX_VALUE) 
                + RANDOM.nextInt(Integer.MAX_VALUE);
        long centsB = Long.MAX_VALUE - centsA + 2;
        CurrencyAmount amountA = new CurrencyAmount(centsA, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(centsB, DOLLARS);
        CurrencyAmount result = amountA.plus(amountB);
        System.out.println("Trying to add " + amountA.toString() + " to "
                + amountB.toString()
                + " should have caused an exception, not given result "
                + result.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testMinusNull() {
        int cents = RANDOM.nextInt(Short.MAX_VALUE);
        CurrencyAmount minuend = new CurrencyAmount(cents, EUROS);
        CurrencyAmount result = minuend.minus(null);
        System.out.println(minuend.toString() + " minus null equals "
                + result.toString() + " ???");
    }

    @Test
    public void testMinus() {
        System.out.println("minus");
        int centsA = RANDOM.nextInt(262144) + 16;
        int centsB = RANDOM.nextInt(262144) + 16;
        CurrencyAmount minuend = new CurrencyAmount(centsA, DOLLARS);
        CurrencyAmount subtrahend = new CurrencyAmount(centsB, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(centsA - centsB, DOLLARS);
        CurrencyAmount actual = minuend.minus(subtrahend);
        String msg = minuend.toString() + " - " + subtrahend.toString() 
                + " expected to be " + expected.toString();
        assertEquals(msg, expected, actual);
    }

    @Test
    public void testMinusEuros() {
        int centsA = RANDOM.nextInt(262144) + 16;
        int centsB = RANDOM.nextInt(262144) + 16;
        CurrencyAmount minuend = new CurrencyAmount(centsA, EUROS);
        CurrencyAmount subtrahend = new CurrencyAmount(centsB, EUROS);
        CurrencyAmount expected = new CurrencyAmount(centsA - centsB, EUROS);
        CurrencyAmount actual = minuend.minus(subtrahend);
        String msg = minuend.toString() + " - " + subtrahend.toString() 
                + " expected to be " + expected.toString();
        assertEquals(msg, expected, actual);
    }

    @Test(expected = CurrencyConversionNeededException.class)
    public void testMinusDifferentCurrencies() {
        int centsA = RANDOM.nextInt(262144) + 16;
        int centsB = RANDOM.nextInt(262144) + 16;
        CurrencyAmount dollars = new CurrencyAmount(centsA, DOLLARS);
        CurrencyAmount euros = new CurrencyAmount(centsB, EUROS);
        CurrencyAmount result = dollars.minus(euros);
        System.out.println("Trying to subtract " + euros.toString() + " from "
                + dollars.toString()
                + " should have caused an exception, not given result "
                + result.toString());
    }

    @Test(expected = ArithmeticException.class)
    public void testMinusTooMuch() {
        long centsA = -((long) Integer.MAX_VALUE) 
                - RANDOM.nextInt(Integer.MAX_VALUE);
        long centsB = Long.MAX_VALUE - Math.abs(centsA) + 3;
        CurrencyAmount amountA = new CurrencyAmount(centsA, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(centsB, DOLLARS);
        CurrencyAmount result = amountA.minus(amountB);
        System.out.println("Trying to subtract " + amountB.toString() + " from "
                + amountA.toString()
                + " should have caused an exception, not given result "
                + result.toString());
    }

    @Test
    public void testNegate() {
        System.out.println("negate");
        int cents = RANDOM.nextInt(1048576) + 4;
        CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(-cents, DOLLARS);
        CurrencyAmount actual = amount.negate();
        assertEquals(expected, actual);
    }

    @Test
    public void testNegateAlreadyNegative() {
        int cents = -RANDOM.nextInt(1048576) - 4;
        CurrencyAmount amount = new CurrencyAmount(-cents, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(cents, DOLLARS);
        CurrencyAmount actual = amount.negate();
        assertEquals(expected, actual);
    }

    @Test
    public void testNegateEuros() {
        int cents = RANDOM.nextInt(1048576) + 4;
        CurrencyAmount amount = new CurrencyAmount(cents, EUROS);
        CurrencyAmount expected = new CurrencyAmount(-cents, EUROS);
        CurrencyAmount actual = amount.negate();
        assertEquals(expected, actual);
    }

    @Test
    public void testTimes() {
        System.out.println("times");
        int singleCents = RANDOM.nextInt(131072) + 8;
        CurrencyAmount amount = new CurrencyAmount(singleCents, DOLLARS);
        int multiplier = RANDOM.nextInt(128) - 64;
        int multCents = multiplier * singleCents;
        CurrencyAmount expected = new CurrencyAmount(multCents, DOLLARS);
        CurrencyAmount actual = amount.times(multiplier);
        String msg = amount.toString() + " times " + multiplier 
                + " expected to be " + expected.toString();
        assertEquals(msg, expected, actual);
    }

    @Test
    public void testEurosTimesInt() {
        int singleCents = RANDOM.nextInt(131072) + 8;
        CurrencyAmount amount = new CurrencyAmount(singleCents, EUROS);
        int multiplier = RANDOM.nextInt(128) - 64;
        int multCents = multiplier * singleCents;
        CurrencyAmount expected = new CurrencyAmount(multCents, EUROS);
        CurrencyAmount actual = amount.times(multiplier);
        String msg = amount.toString() + " times " + multiplier 
                + " expected to be " + expected.toString();
        assertEquals(msg, expected, actual);
    }

//    @Test
    public void testDollarsTimesDouble() {
        CurrencyAmount subTotal = new CurrencyAmount(19975, DOLLARS);
        double salesTax = 0.06;
        CurrencyAmount expected = new CurrencyAmount(1199, DOLLARS);
        CurrencyAmount actual = subTotal.times(salesTax);
        assertEquals(expected, actual);
    }

//    @Test
    public void testOtherDollarsTimesDouble() {
        CurrencyAmount amount = new CurrencyAmount(108250, DOLLARS);
        double multiplier = 1.5;
        CurrencyAmount expected = new CurrencyAmount(162375, DOLLARS);
        CurrencyAmount actual = amount.times(multiplier);
        assertEquals(expected, actual);
    }

//    @Test
    public void testEurosTimesDouble() {
        CurrencyAmount amount = new CurrencyAmount(5989, EUROS);
        double multiplier = 1.09;
        CurrencyAmount expected = new CurrencyAmount(6528, EUROS);
        CurrencyAmount actual = amount.times(multiplier);
        assertEquals(expected, actual);
    }

//    @Test(expected = ArithmeticException.class)
    public void testOneDollarTimesPositiveInfinity() {
        CurrencyAmount oneBuck = new CurrencyAmount(100, DOLLARS);
        CurrencyAmount result = oneBuck.times(Double.POSITIVE_INFINITY);
        System.out.println(oneBuck.toString()
                + " times +Infinity is said to be "
                + result.toString() + "???");
    }

//    @Test(expected = ArithmeticException.class)
    public void testOneDollarTimesNegativeInfinity() {
        CurrencyAmount oneBuck = new CurrencyAmount(100, DOLLARS);
        CurrencyAmount result = oneBuck.times(Double.NEGATIVE_INFINITY);
        System.out.println(oneBuck.toString()
                + " times -Infinity is said to be "
                + result.toString() + "???");
    }

//    @Test(expected = ArithmeticException.class)
    public void testOneDollarTimesNaN() {
        CurrencyAmount oneBuck = new CurrencyAmount(100, DOLLARS);
        CurrencyAmount result = oneBuck.times(Double.NaN);
        System.out.println(oneBuck.toString() + " times NaN is said to be "
                + result.toString() + "???");
    }

//    @Test
    public void testDivideByZero() {
        CurrencyAmount amount = new CurrencyAmount(7320, EUROS);
        try {
            CurrencyAmount result = amount.divides(0);
            System.out.println(amount.toString() 
                    + " divided by zero is said to be " + result.toString() 
                    + "???");
            fail("Trying to divide by zero should have caused an exception");
        } catch (IllegalArgumentException | ArithmeticException iae) {
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() 
                    + " is the wrong exception to throw for division by zero";
            fail(failMsg);
        }
    }

//    @Test
    public void testDivides() {
        System.out.println("divides");
        CurrencyAmount amount = new CurrencyAmount(30000000, DOLLARS);
        int divisor = 7;
        CurrencyAmount expected = new CurrencyAmount(4285714, DOLLARS);
        CurrencyAmount actual = amount.divides(divisor);
        assertEquals(expected, actual);
    }

//    @Test
    public void testDivideOtherCurrency() {
        CurrencyAmount amount = new CurrencyAmount(7320, EUROS);
        int divisor = 3;
        CurrencyAmount expected = new CurrencyAmount(2440, EUROS);
        CurrencyAmount actual = amount.divides(divisor);
        assertEquals(expected, actual);
    }
    
//    @Test
    public void testCompareToLesser() {
        CurrencyAmount amountA = new CurrencyAmount(49899, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(104250, DOLLARS);
        String assertionMessage = amountA.toString() + " is less than " 
                + amountB.toString();
        assertTrue(assertionMessage, amountA.compareTo(amountB) < 0);
    }
    
//    @Test
    public void testCompareToEqual() {
        CurrencyAmount someAmount = new CurrencyAmount(49899, DOLLARS);
        CurrencyAmount sameAmount = new CurrencyAmount(49899, DOLLARS);
        String assertionMessage = someAmount.toString() + " is equal to " 
                + sameAmount.toString();
        assertEquals(assertionMessage, 0, someAmount.compareTo(sameAmount));
    }

//    @Test
    public void testCompareToGreater() {
        CurrencyAmount amountA = new CurrencyAmount(49899, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(104250, DOLLARS);
        String assertionMessage = amountB.toString() + " is greater than " 
                + amountA.toString();
        assertTrue(assertionMessage, amountB.compareTo(amountA) > 0);
    }

//    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        CurrencyAmount negBal = new CurrencyAmount(-372, DOLLARS);
        CurrencyAmount zero = new CurrencyAmount(0, DOLLARS);
        CurrencyAmount amountA = new CurrencyAmount(49989, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(104250, DOLLARS);
        CurrencyAmount amountC = new CurrencyAmount(583047758, DOLLARS);
        ArrayList<CurrencyAmount> expected = new ArrayList<>();
        expected.add(negBal);
        expected.add(zero);
        expected.add(amountA);
        expected.add(amountB);
        expected.add(amountC);
        ArrayList<CurrencyAmount> actual = new ArrayList<>();
        actual.add(amountB);
        actual.add(amountC);
        actual.add(zero);
        actual.add(negBal);
        actual.add(amountA);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }
    
//    @Test
    public void testCompareDifferentCurrencies() {
        CurrencyAmount dollarsAmount = new CurrencyAmount(57380, DOLLARS);
        CurrencyAmount eurosAmount = new CurrencyAmount(57380, EUROS);
        try {
            int result = dollarsAmount.compareTo(eurosAmount);
            String failMsg = "Trying to compare " + dollarsAmount.toString() 
                    + " to " + eurosAmount.toString()
                    + " should have caused an exception, not given result " 
                    + result;
            fail(failMsg);
        } catch (CurrencyConversionNeededException curConvNeedExc) {
            System.out.println("Trying to compare " + dollarsAmount.toString() 
                    + " to " + eurosAmount.toString()
                    + " correctly caused CurrencyConversionNeededException");
            System.out.println("\"" + curConvNeedExc.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to compare " 
                    + dollarsAmount.toString() + " to " 
                    + eurosAmount.toString();
            fail(failMsg);
        }
    }
    
    @Test
    public void testIsNegativeButItIsNot() {
        int cents = RANDOM.nextInt() & Integer.MAX_VALUE;
        CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
        String msg = "Amount " + amount.toString() 
                + " should not be deemed to be negative";
        assert !amount.isNegative() : msg;
    }
    
    @Test
    public void testIsNegative() {
        System.out.println("isNegative");
        int cents = RANDOM.nextInt() | Integer.MIN_VALUE;
        CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
        String msg = "Amount " + amount.toString() 
                + " should be deemed to be negative";
        assert amount.isNegative() : msg;
    }
    
    @Test
    public void testZeroIsNotNegative() {
        CurrencyAmount amount = new CurrencyAmount(0, DOLLARS);
        String msg = "Amount " + amount.toString() 
                + " should not be deemed to be negative";
        assert !amount.isNegative() : msg;
    }
    
    @Test
    public void testIsNotPositive() {
        System.out.println("isNotPositive");
        int cents = RANDOM.nextInt() | Integer.MIN_VALUE;
        CurrencyAmount amount = new CurrencyAmount(cents, EUROS);
        String msg = "Amount " + amount.toString() 
                + " should be deemed not positive";
        assert amount.isNotPositive() : msg;
    }
    
    @Test
    public void testIsNotPositiveButItIs() {
        int cents = RANDOM.nextInt() & Integer.MAX_VALUE;
        CurrencyAmount amount = new CurrencyAmount(cents, EUROS);
        String msg = "Amount " + amount.toString() 
                + " should not be deemed not positive";
        assert !amount.isNotPositive() : msg;
    }
    
    @Test
    public void testZeroIsNotPositive() {
        CurrencyAmount amount = new CurrencyAmount(0, EUROS);
        String msg = "Amount " + amount.toString() 
                + " should be deemed not positive";
        assert amount.isNotPositive() : msg;
    }
    
    @Test
    public void testParseAmount() {
        System.out.println("parseAmount");
        String s = "$198.97";
        CurrencyAmount expected = new CurrencyAmount(19897, DOLLARS);
        CurrencyAmount actual = CurrencyAmount.parseAmount(s);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseOtherDollarAmount() {
        String s = "$20";
        CurrencyAmount expected = new CurrencyAmount(2000, DOLLARS);
        CurrencyAmount actual = CurrencyAmount.parseAmount(s);
        assertEquals(expected, actual);
    }

    @Ignore
    @Test
    public void testParseDinarAmount() {
        System.out.println("parseAmount");
        String s = "LYD7063.255";
        CurrencyAmount expected = new CurrencyAmount(7063255, DINARS);
        CurrencyAmount actual = CurrencyAmount.parseAmount(s);
        assertEquals(expected, actual);
    }
    
    @Test(expected = NullPointerException.class)
    public void testConstructorRefuseNullCurrency() {
        CurrencyAmount badAmount = new CurrencyAmount(0, null);
        System.out.println("Should not have been able to create CurrencyAmount@"
                + badAmount.hashCode() + " with null currency");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRefuseMetals() {
        Currency platinum = Currency.getInstance("XPT");
        CurrencyAmount ptAmount = new CurrencyAmount(102153, platinum);
        System.out.println("Should not have been able to create "
                + ptAmount.toString());
    }
    
    private static class DollarAmount extends CurrencyAmount {
        
        public DollarAmount(long cents) {
            super(cents, DOLLARS);
        }
        
    }

}
