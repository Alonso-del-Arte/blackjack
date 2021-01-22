/*
 * Copyright (C) 2021 Alonso del Arte
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
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package currency;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CurrencyConversionNeededException class.
 * @author Alonso del Arte
 */
public class CurrencyConversionNeededExceptionTest {
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    private static final Currency POUNDS_STERLING 
            = Currency.getInstance(Locale.UK);
    
    private static final CurrencyAmount DOLLAR_AMOUNT 
            = new CurrencyAmount(58243, DOLLARS);
    private static final CurrencyAmount POUND_STERLING_AMOUNT 
            = new CurrencyAmount(40820, POUNDS_STERLING);
    
    private static final String EXCEPTION_MESSAGE = "For testing purposes";
    
    private static final CurrencyConversionNeededException CURRENCY_EXCEPTION 
                = new CurrencyConversionNeededException(EXCEPTION_MESSAGE, 
                        DOLLAR_AMOUNT, POUND_STERLING_AMOUNT);

        /**
     * Test of getMessage method, of class CurrencyConversionNeededException.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        String result = CURRENCY_EXCEPTION.getMessage();
        assertEquals(EXCEPTION_MESSAGE, result);
    }

    /**
     * Test of getAmountA method, of class CurrencyConversionNeededException.
     */
    @Test
    public void testGetAmountA() {
        System.out.println("getAmountA");
        CurrencyAmount result = CURRENCY_EXCEPTION.getAmountA();
        assertEquals(DOLLAR_AMOUNT, result);
    }

    /**
     * Test of getAmountB method, of class CurrencyConversionNeededException.
     */
    @Test
    public void testGetAmountB() {
        System.out.println("getAmountB");
        CurrencyAmount result = CURRENCY_EXCEPTION.getAmountB();
        assertEquals(POUND_STERLING_AMOUNT, result);
    }
    
}
