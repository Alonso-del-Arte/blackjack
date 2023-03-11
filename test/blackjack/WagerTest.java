/*
 * Copyright (C) 2023 Alonso del Arte
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
    
    private static final int DEFAULT_CENTS = 10000;
    
    static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final CurrencyAmount DEFAULT_WAGER_AMOUNT 
            = new CurrencyAmount(DEFAULT_CENTS, DOLLARS);
    
    private static Wager.Outcome pickOutcome() {
        Wager.Outcome[] outcomes = Wager.Outcome.values();
        int index = DealerTest.RANDOM.nextInt(outcomes.length);
        return outcomes[index];
    }
    
    /**
     * Test of getAmount method, of class Wager.
     */
    @Test
    public void testGetAmount() {
        System.out.println("getAmount");
        CurrencyAmount expected = new CurrencyAmount(12500, DOLLARS);
        Wager wager = new Wager(expected);
        CurrencyAmount actual = wager.getAmount();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNewWagerShouldNotBeSettledAlready() {
        Wager wager = new Wager(DEFAULT_WAGER_AMOUNT);
        String msg = "New wager should not be settled already";
        assert !wager.isSettled() : msg;
    }
    
    @Test
    public void testTryingToGetSettlementBeforeSettleCausesException() {
        Wager wager = new Wager(DEFAULT_WAGER_AMOUNT);
        try {
            Wager.Settlement invalidSettlement = wager.getSettlement();
            String msg = "Should not have gotten invalid settlement " 
                    + invalidSettlement.toString() 
                    + " because wager is not settled yet";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Premature settlement fetch caused exception");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for premature settlement fetch";
            fail(msg);
        }
    }
    
    @Test
    public void testIsSettled() {
        System.out.println("isSettled");
        Wager wager = new Wager(DEFAULT_WAGER_AMOUNT);
        Wager.Outcome outcome = pickOutcome();
        wager.settle(outcome);
        String msg = "After settling wager with outcome " + outcome.toString() 
                + ", wager should be settled";
        assert wager.isSettled() : msg;
    }
    
    @Test
    public void testCanNotSettleTwice() {
        Wager wager = new Wager(DEFAULT_WAGER_AMOUNT);
        Wager.Outcome firstOutcome = pickOutcome();
        wager.settle(firstOutcome);
        Wager.Outcome secondOutcome = pickOutcome();
        String msgPart = "After settling with outcome " 
                + firstOutcome.toString() 
                + " trying to settle a second time with outcome " 
                + secondOutcome.toString() + " ";
        try {
            wager.settle(secondOutcome);
            String msg = msgPart + "should not have been allowed";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println(msgPart 
                    + "correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = msgPart + "should not have caused " 
                    + re.getClass().getName();
            fail(msg);
        }
    }
    
    @Test
    public void testGetSettlement() {
        System.out.println("getSettlement");
        Wager.Outcome[] outcomes = Wager.Outcome.values();
        for (Wager.Outcome outcome : outcomes) {
            int cents = DealerTest.RANDOM.nextInt(DEFAULT_CENTS) 
                    + DEFAULT_CENTS;
            CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
            Wager wager = new Wager(amount);
            wager.settle(outcome);
            Wager.Settlement settlement = wager.getSettlement();
            Wager.Outcome actualOutcome = settlement.getOutcome();
            assertEquals(outcome, actualOutcome);
            CurrencyAmount expected;
            CurrencyAmount actual = settlement.getAmount();
            switch (outcome) {
                case NATURAL_BLACKJACK:
                    expected = amount.times(3).divides(2);
                    break;
                case BLACKJACK:
                case BETTER_SCORE:
                case INSURANCE_WON:
                    expected = amount;
                    break;
                case STANDOFF:
                    expected = new CurrencyAmount(0, DOLLARS);
                    break;
                case INSURANCE_LOST:
                case BUST:
                case LOWER_SCORE:
                    expected = amount.negate();
                    break;
                default:
                    expected = amount;
                    String msg = "Unexpected outcome " + outcome.toString() 
                            + "; either add test or remove unexpected outcome";
                    fail(msg);
            }
            assertEquals(expected, actual);
        }
    }
    
//    TODO: Write @Test
    public void testDoubleDown() {
        System.out.println("doubleDown");
        fail("Haven't written test yet");
    }
    
    @Test
    public void testConstructorRejectsNegativeAmount() {
        CurrencyAmount badAmount = new CurrencyAmount(-10000, DOLLARS);
        try {
            Wager badWager = new Wager(badAmount);
            String msg = "Should not have been able to create wager " 
                    + badWager.toString() + " with amount " 
                    + badAmount.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create wager with " 
                    + badAmount.toString() 
                    + " correctly caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            System.out.println("\"" + excMsg + "\"");
            String currencySymbol = badAmount.getCurrency().getSymbol();
            String msg = "Exception message should contain currency symbol " 
                    + currencySymbol;
            assert excMsg.contains(currencySymbol) : msg;
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for wager amount " 
                    + badAmount.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsAmountZero() {
        CurrencyAmount badAmount = new CurrencyAmount(0, DOLLARS);
        try {
            Wager badWager = new Wager(badAmount);
            String msg = "Should not have been able to create wager " 
                    + badWager.toString() + " with amount " 
                    + badAmount.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create wager with " 
                    + badAmount.toString() 
                    + " correctly caused IllegalArgumentException");
            String excMsg = iae.getMessage();
            System.out.println("\"" + excMsg + "\"");
            String currencySymbol = badAmount.getCurrency().getSymbol();
            String msg = "Exception message should contain currency symbol " 
                    + currencySymbol;
            assert excMsg.contains(currencySymbol) : msg;
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for wager amount " 
                    + badAmount.toString();
            fail(msg);
        }
    }
    
}
