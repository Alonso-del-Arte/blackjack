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
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the Insurance class.
 * @author Alonso del Arte
 */
public class InsuranceTest {
    
    /**
     * Test of getWager method, of class Insurance.
     */
    @org.junit.Ignore
    @Test
    public void testGetWager() {
        System.out.println("getWager");
        Insurance instance = null;
        Wager expResult = null;
        Wager result = instance.getWager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSettlement method, of class Insurance.
     */
    @org.junit.Ignore
    @Test
    public void testGetSettlement() {
        System.out.println("getSettlement");
        Insurance instance = null;
        Wager.Settlement expResult = null;
        Wager.Settlement result = instance.getSettlement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testConstructorRequiresInsuranceWager() {
        int cents = RANDOM.nextInt(10000, 100000);
        Currency currency = CurrencyChooser.chooseCurrency(
                cur -> !cur.getSymbol().equals(cur.getCurrencyCode())
        );
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(amount, false);
        String msg = "Constructor should reject " + wager.toString() 
                + ", which is not marked as an insurance wager";
        Throwable t = assertThrows(() -> {
            Insurance badInstance = new Insurance(wager);
            System.out.println(msg + ", not created instance " 
                    + badInstance.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
}
