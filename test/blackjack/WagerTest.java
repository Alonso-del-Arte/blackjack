/*
 * Copyright (C) 2021 Alonso del Arte
 *
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package blackjack;

import currency.CurrencyAmount;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Wager class.
 * @author Alonso del Arte
 */
public class WagerTest {
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    /**
     * Test of getAmount method, of class Wager.
     */
    @Test
    public void testGetAmount() {
        System.out.println("getAmount");
        CurrencyAmount expected = new CurrencyAmount(10000, DOLLARS);
        Wager wager = new Wager(expected);
        CurrencyAmount actual = wager.getAmount();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testConstructorRejectsNegativeAmount() {
        CurrencyAmount badAmount = new CurrencyAmount(-10000, DOLLARS);
        try {
            Wager badWager = new Wager(badAmount);
            String msg = "Should not have been able to create wager " 
                    + badWager.toString() + " with " + badAmount.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create wager with " 
                    + badAmount.toString() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for wager amount " 
                    + badAmount.toString();
            fail(msg);
        }
    }
    
}
