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
 * Tests of the Insurance class.
 * @author Alonso del Arte
 */
public class InsuranceTest {
    
    @Test
    public void testNewInsuranceIsNotAlreadySettled() {
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(amount, true);
        Insurance instance = new Insurance(wager);
        String msg = "New insurance with " + wager.toString() 
                + " should not be already settled";
        assert !instance.isSettled() : msg;
    }
    
    /**
     * Test of the isSettled function, of the Hand class. For this test it 
     * doesn't actually matter if the insurance bet is won or lost, only that it 
     * has been settled.
     */
    @Test
    public void testIsSettled() {
        System.out.println("isSettled");
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(amount, true);
        Insurance instance = new Insurance(wager);
        boolean won = RANDOM.nextBoolean();
        instance.markSettled(won);
        String wonStr = won ? "won" : "lost";
        String msg = "Insurance with " + wager.toString() + " settled as " 
                + wonStr + " should be settled";
        assert instance.isSettled() : msg;
    }
    
    /**
     * Test of the getWager function, of the Hand class.
     */
    @Test
    public void testGetWager() {
        System.out.println("getWager");
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager expected = new Wager(amount, true);
        Insurance instance = new Insurance(expected);
        Wager actual = instance.getWager();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testTryingToGetSettlementBeforeMarkingSettledCausesException() {
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(amount, true);
        Insurance instance = new Insurance(wager);
        String msg = "Trying to get settlement for " + wager.toString() 
                + " before marking settled should cause exception";
        Throwable t = assertThrows(() -> {
            Wager.Settlement settlement = instance.getSettlement();
            System.out.println(msg + ", not give settlement of " 
                    + settlement.getAmount().toString() + " for " 
                    + settlement.getOutcome().toString());
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    /**
     * Test the getSettlement function, of the Insurance class. This test is for 
     * {@link Wager.Outcome#INSURANCE_WON}.
     */
    @Test
    public void testGetSettlement() {
        System.out.println("getSettlement");
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(amount, true);
        Insurance instance = new Insurance(wager);
        instance.markSettled(true);
        Wager.Settlement settlement = instance.getSettlement();
        CurrencyAmount expected = amount.times(2);
        CurrencyAmount actual = settlement.getAmount();
        String message = "Settling " + wager.toString() + " won";
        assertEquals(message, Wager.Outcome.INSURANCE_WON, 
                settlement.getOutcome());
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testGetSettlementInsuranceLost() {
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(amount, true);
        Insurance instance = new Insurance(wager);
        instance.markSettled(false);
        Wager.Settlement settlement = instance.getSettlement();
        CurrencyAmount expected = amount.negate();
        CurrencyAmount actual = settlement.getAmount();
        String message = "Settling " + wager.toString() + " lost";
        assertEquals(message, Wager.Outcome.INSURANCE_LOST, 
                settlement.getOutcome());
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testNoMarkSettledTwice() {
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(amount, true);
        Insurance instance = new Insurance(wager);
        boolean won = RANDOM.nextBoolean();
        instance.markSettled(won);
        Wager.Outcome firstOutcome = won ? Wager.Outcome.INSURANCE_WON 
                : Wager.Outcome.INSURANCE_LOST;
        String msg = wager.toString() + " already marked settled with outcome " 
                + firstOutcome.toString() + " can't be settled again";
        Throwable t = assertThrows(() -> {
            instance.markSettled(RANDOM.nextBoolean());
            System.out.println(msg + " for outcome " 
                    + instance.getSettlement().getOutcome().toString());
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNullWager() {
        String msg = "Constructor should reject null wager";
        Throwable t = assertThrows(() -> {
            Insurance badInstance = new Insurance(null);
            System.out.println(msg + ", not created instance " 
                    + badInstance.toString());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
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
