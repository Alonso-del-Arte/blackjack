/*
 * Copyright (C) 2025 Alonso del Arte
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

import static blackjack.DealerTest.RANDOM;
import currency.CurrencyAmount;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Wager class. The play money wagers are drawn in U.&nbsp;S. 
 * dollars (USD), euros (EUR), Swiss francs (CHF) and Japanese yen (JPY).
 * @author Alonso del Arte
 */
public class WagerTest {
    
    private static final int DEFAULT_CENTS = 10000;
    
    static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final Currency EUROS = Currency.getInstance("EUR");
    
    private static final Currency FRANCS = Currency.getInstance("CHF");
    
    private static final Currency YEN = Currency.getInstance(Locale.JAPAN);
    
    private static final Currency[] TEST_CURRENCIES = {DOLLARS, EUROS, FRANCS, 
        YEN};
    
    private static final int NUMBER_OF_TEST_CURRENCIES = TEST_CURRENCIES.length;
    
    private static final CurrencyAmount DEFAULT_WAGER_AMOUNT 
            = new CurrencyAmount(DEFAULT_CENTS, DOLLARS);
    
    private static Currency chooseCurrency() {
        int index = RANDOM.nextInt(NUMBER_OF_TEST_CURRENCIES);
        return TEST_CURRENCIES[index];
    }
    
    private static CurrencyAmount chooseAmount() {
        int bound = 100000;
        int cents = RANDOM.nextInt(bound) + 1;
        Currency currency = chooseCurrency();
        return new CurrencyAmount(cents, currency);
    }
    
    private static Wager.Outcome pickOutcome() {
        Wager.Outcome[] outcomes = Wager.Outcome.values();
        int index = DealerTest.RANDOM.nextInt(outcomes.length);
        return outcomes[index];
    }
    
//    @Test
    public void testToString() {
        //
        fail();
    }
    
    /**
     * Test of the getAmount function, of the Wager class.
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
    public void testIsNotInsuranceWager() {
        Wager wager = new Wager(DEFAULT_WAGER_AMOUNT, false);
        String msg = "Wager not specified as insurance shouldn't be marked so";
        assert !wager.isInsuranceWager() : msg;
    }
    
    @Test
    public void testIsInsuranceWager() {
        System.out.println("isInsuranceWager");
        Wager wager = new Wager(DEFAULT_WAGER_AMOUNT, true);
        String msg = "Wager specified as insurance should be so marked";
        assert wager.isInsuranceWager() : msg;
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
    
    /**
     * Test of the isSettled function, of the Wager class.
     */
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
    
    /**
     * Test of the getSettlement function, of the Wager class.
     */
    @Test
    public void testGetSettlement() {
        System.out.println("getSettlement");
        Wager.Outcome[] outcomes = Wager.Outcome.values();
        for (Wager.Outcome outcome : outcomes) {
            int cents = DealerTest.RANDOM.nextInt(DEFAULT_CENTS) 
                    + DEFAULT_CENTS;
            CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
            boolean isInsurance = outcome.name().startsWith("INSURANCE_");
            Wager wager = new Wager(amount, isInsurance);
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
                case REPLACED:
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
    
    /**
     * Another test of the doubleDown function, of the Wager class. The casino 
     * may or may not allow the player to double down twice, but internally, a 
     * new Wager object should be created for each increased wager. This means 
     * that calling doubleDown() twice on the same Wager object should cause an 
     * IllegalStateException.
     */
    @Test
    public void testNoDoubleDownTwice() {
        int cents = DealerTest.RANDOM.nextInt(DEFAULT_CENTS) + DEFAULT_CENTS;
        CurrencyAmount originalAmount = new CurrencyAmount(cents, DOLLARS);
        Wager wager = new Wager(originalAmount);
        wager.doubleDown();
        try {
            wager.doubleDown();
            fail("Should not have been able to double down twice");
        } catch (IllegalStateException ise) {
            System.out.println("Double down twice same wager caused exception");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is wrong exception for double down twice same wager";
            fail(msg);
        }
    }
    
    /**
     * Test of the doubleDown function, of the Wager class.
     */
    @Test
    public void testDoubleDown() {
        System.out.println("doubleDown");
        int cents = DealerTest.RANDOM.nextInt(DEFAULT_CENTS) + DEFAULT_CENTS;
        CurrencyAmount originalAmount = new CurrencyAmount(cents, DOLLARS);
        Wager originalWager = new Wager(originalAmount);
        Wager doubleDownWager = originalWager.doubleDown();
        assert originalWager.isSettled() : "Original wager should be settled";
        Wager.Settlement settlement = originalWager.getSettlement();
        assertEquals("Original wager should be settled as replaced", 
                Wager.Outcome.REPLACED, settlement.getOutcome());
        CurrencyAmount expected = originalAmount.times(2);
        CurrencyAmount actual = doubleDownWager.getAmount();
        assertEquals(expected, actual);
        assert !doubleDownWager.isSettled() 
                : "Double down wager should not be settled yet";
    }
    
    @Test
    public void testAuxiliaryConstructorMarksAsNotInsurance() {
        int cents = DealerTest.RANDOM.nextInt((int) DEFAULT_WAGER_AMOUNT
                .getAmountInCents()) + 1;
        CurrencyAmount amount = new CurrencyAmount(cents, DOLLARS);
        Wager wager = new Wager(amount);
        String msg = "Wager initialized through aux constructor not insurance";
        assert !wager.isInsuranceWager() : msg;
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
