/*
 * Copyright (C) 2026 Alonso del Arte
 *
 * This program is free software; you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation; either version 2 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package blackjack;

import static blackjack.DealerTest.RANDOM;
import currency.CurrencyAmount;
import currency.CurrencyChooser;

import java.util.Currency;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the Bettable class.
 * @author Alonso del Arte
 */
public class BettableTest {
    
    /**
     * Test of the getWager function, of the Bettable class.
     */
    @Test
    public void testGetWager() {
        System.out.println("getWager");
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager expected = new Wager(amount, true);
        Bettable instance = new BettableImpl(expected);
        Wager actual = instance.getWager();
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorRejectsNullWager() {
        String msg = "Constructor should reject null wager";
        Throwable t = assertThrows(() -> {
            Bettable badInstance = new BettableImpl(null);
            System.out.println(msg + ", not created instance " 
                    + badInstance.toString());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    public class BettableImpl extends Bettable {
        
        @Override
        public Wager.Settlement getSettlement() {
            return null;
        }
        
        public BettableImpl(Wager wager) {
            super(wager);
        }

    }
    
}
