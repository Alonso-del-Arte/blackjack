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

import currency.CurrencyAmount;

import java.util.ArrayList;
import java.util.List;

import playingcards.CardServer;
import playingcards.PlayingCard;
import playingcards.Rank;

import static org.junit.Assert.*;
import org.junit.Test;

import static org.testframe.api.Asserters.assertThrows;
import static org.testframe.api.Asserters.assertZero;

/**
 * Tests of the Player class.
 * @author Alonso del Arte
 */
public class PlayerTest {
    
    private static final String DEFAULT_PLAYER_NAME = "Test Player";
    
    private static final CurrencyAmount DEFAULT_INITIAL_BANKROLL 
            = HandTest.DEFAULT_WAGER.getAmount().times(10);
    
    private final CardServer SERVER = new CardServer(50);
    
    static Player getPlayer() {
        return new Player(DEFAULT_PLAYER_NAME, DEFAULT_INITIAL_BANKROLL);
    }
    
    @Test
    public void testAddToBankroll() {
        Player player = getPlayer();
        int moreCents = DealerTest.RANDOM.nextInt(10000);
        CurrencyAmount additional = new CurrencyAmount(moreCents, 
                WagerTest.DOLLARS);
        player.add(additional);
        CurrencyAmount expected = DEFAULT_INITIAL_BANKROLL.plus(additional);
        CurrencyAmount actual = player.getBalance();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNoAddNegativeToBankroll() {
        Player player = getPlayer();
        int cents = -DealerTest.RANDOM.nextInt(10000) - 1;
        CurrencyAmount amount = new CurrencyAmount(cents, WagerTest.DOLLARS);
        String negAmtStr = amount.toString();
        String msg = "Trying to add " + negAmtStr + " should cause exception";
        Throwable t = assertThrows(() -> {
            player.add(amount);
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        String containsMsg = "Exception message should include \"" + negAmtStr 
                + "\"";
        assert excMsg.contains(negAmtStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expected = "John Q. Player";
        Player player = new Player(expected, DEFAULT_INITIAL_BANKROLL);
        String actual = player.getName();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetActiveHandsCountZeroAtFirst() {
        Player player = new Player(DEFAULT_PLAYER_NAME, 
                DEFAULT_INITIAL_BANKROLL);
        String msg = "New player at table shouldn't have active hands";
        int actual = player.getActiveHandsCount();
        assertZero(actual, msg);
    }
    
    @Test
    public void testGetActiveHandsCount() {
        System.out.println("getActiveHandsCount");
        Player player = getPlayer();
        int initialCapacity = 4;
        List<Hand> hands = new ArrayList<>(initialCapacity);
        String msgPart = "Getting active hand count for player with ";
        for (int expected = 1; initialCapacity >= expected; expected++) {
            PlayingCard firstCard = SERVER.getNextCard();
            Hand hand = new Hand(HandTest.DEFAULT_WAGER, firstCard);
            hand.add(SERVER.getNextCard());
            player.add(hand);
            int actual = player.getActiveHandsCount();
            hands.add(hand);
            String message = msgPart + hands.toString();
            assertEquals(message, expected, actual);
        }
    }
    
    @Test
    public void testSettledHandDoesNotCountAsActive() {
        Hand hand = new Hand(HandTest.DEFAULT_WAGER);
        hand.add(SERVER.giveCard(Rank.TEN));
        hand.add(SERVER.giveCard(Rank.TEN));
        Player player = getPlayer();
        player.add(hand);
        assertEquals(1, player.getActiveHandsCount());
        hand.markSettled();
        String message = "After player stands on hand " + hand.toString() 
                + ", that hand should not count as active";
        assertEquals(message, 0, player.getActiveHandsCount());
    }
    
    @Test
    public void testGetHands() {
        System.out.println("getHands");
        Hand firstHand = new Hand(HandTest.DEFAULT_WAGER);
        firstHand.add(SERVER.giveCard(Rank.EIGHT));
        firstHand.add(SERVER.getNextCard());
        Hand secondHand = new Hand(HandTest.DEFAULT_WAGER);
        secondHand.add(SERVER.giveCard(Rank.EIGHT));
        secondHand.add(SERVER.getNextCard());
        Player player = getPlayer();
        player.add(firstHand);
        player.add(secondHand);
        List<Hand> expected = new ArrayList<>(2);
        expected.add(firstHand);
        expected.add(secondHand);
        List<Hand> actual = player.getHands();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetHandsDoesNotLeakList() {
        Hand hand = new Hand(HandTest.DEFAULT_WAGER);
        hand.add(SERVER.getNextCard());
        hand.add(SERVER.getNextCard());
        Player player = getPlayer();
        player.add(hand);
        List<Hand> firstOutput = player.getHands();
        List<Hand> expected = new ArrayList<>(firstOutput);
        Hand secondHand = new Hand(HandTest.DEFAULT_WAGER);
        secondHand.add(SERVER.getNextCard());
        secondHand.add(SERVER.getNextCard());
        firstOutput.add(secondHand);
        List<Hand> actual = player.getHands();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testConstructorRejectsNullName() {
        String msg = "Null name should have been caused an exception";
        Throwable t = assertThrows(() -> {
            Player badPlayer = new Player(null, DEFAULT_INITIAL_BANKROLL);
            System.out.println(msg + ", not created " + badPlayer.toString() 
                    + " with empty name");
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsEmptyName() {
        String msg = "Empty name should have been caused an exception";
        Throwable t = assertThrows(() -> {
            Player badPlayer = new Player("", DEFAULT_INITIAL_BANKROLL);
            System.out.println(msg + ", not created " + badPlayer.toString() 
                    + " with empty name");
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testPrimaryConstructorReflectsInitialBankroll() {
        int usualWagerCents = (int) DEFAULT_INITIAL_BANKROLL.getAmountInCents();
        int cents = usualWagerCents 
                + DealerTest.RANDOM.nextInt(usualWagerCents);
        CurrencyAmount expected = new CurrencyAmount(cents, WagerTest.DOLLARS);
        Player player = new Player(DEFAULT_PLAYER_NAME, expected);
        CurrencyAmount actual = player.getBalance();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testConstructorRejectsNullBankroll() {
        String msg = "Null bankroll should cause NPE";
        Throwable t = assertThrows(() -> {
            Player badPlayer = new Player(DEFAULT_PLAYER_NAME, null);
            System.out.println(msg + ", not created instance " 
                    + badPlayer.getName());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsBankrollZero() {
        CurrencyAmount badBankroll = new CurrencyAmount(0, WagerTest.DOLLARS);
        String moneyStr = badBankroll.toString();
        String msg = "Constructor should have rejected initial bankroll of " 
                + moneyStr;
        Throwable t = assertThrows(() -> {
            Player badPlayer = new Player(DEFAULT_PLAYER_NAME, badBankroll);
            System.out.println(msg + ", not created instance " 
                    + badPlayer.getName());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should contain \"" + moneyStr 
                + "\"";
        assert excMsg.contains(moneyStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNegativeBankroll() {
        int badCents = -DealerTest.RANDOM.nextInt(25600) - 1;
        CurrencyAmount badBankroll = new CurrencyAmount(badCents, 
                WagerTest.DOLLARS);
        String moneyStr = badBankroll.toString();
        String msg = "Constructor should have rejected initial bankroll of " 
                + moneyStr;
        Throwable t = assertThrows(() -> {
            Player badPlayer = new Player(DEFAULT_PLAYER_NAME, badBankroll);
            System.out.println(msg + ", not created instance " 
                    + badPlayer.getName());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should contain \"" + moneyStr 
                + "\"";
        assert excMsg.contains(moneyStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
}
