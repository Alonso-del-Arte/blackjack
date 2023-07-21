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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Round class.
 * @author Alonso del Arte
 */
public class RoundTest {

    private static final CurrencyAmount DEFAULT_INITIAL_BANKROLL_AMOUNT 
            = new CurrencyAmount(100000, WagerTest.DOLLARS);
    
    @Test
    public void testNotBegunBeforeCallingBegin() {
        Dealer dealer = new Dealer();
        Player player = PlayerTest.getPlayer();
        Round round = new Round(dealer, player);
        String msg = "Round should not begin before calling begin()";
        assert !round.begun() : msg;
    }
    
    @Test
    public void testConstructorRequiresAtLeastOnePlayer() {
        Dealer dealer = new Dealer();
        try {
            Round badRound = new Round(dealer);
            String msg = "Should not have been able to create " 
                    + badRound.toString() + " with zero players";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Zero players for round caused exception");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for zero players";
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsNullDealer() {
        Player player = new Player("Johnny Q. Test", 
                DEFAULT_INITIAL_BANKROLL_AMOUNT);
        try {
            Round badRound = new Round(null, player);
            String msg = "Should not have been able to create " 
                    + badRound.toString() + " with null dealer";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null dealer correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null dealer";
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsNullPlayer() {
        Dealer dealer = new Dealer();
        Player player1 = new Player("Johnny Q. Test", 
                DEFAULT_INITIAL_BANKROLL_AMOUNT);
        Player player3 = new Player("Johnny Q. Test, Jr.", 
                DEFAULT_INITIAL_BANKROLL_AMOUNT);
        try {
            Round badRound = new Round(dealer, player1, null, player3);
            String msg = "Should not have been able to create " 
                    + badRound.toString() + " with any null players";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null dealer correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null player";
            fail(msg);
        }
    }
    
}
