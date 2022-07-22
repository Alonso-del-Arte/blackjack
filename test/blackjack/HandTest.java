/*
 * Copyright (C) 2022 Alonso del Arte
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
import playingcards.CardServer;
import playingcards.PlayingCard;
import playingcards.Rank;

import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Hand class.
 * @author Alonso del Arte
 */
public class HandTest {
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final CurrencyAmount HUNDRED_BUCKS 
            = new CurrencyAmount(10000, DOLLARS);
    
    private static final Wager DEFAULT_WAGER = new Wager(HUNDRED_BUCKS);
    
    private CardServer server;
    
    private static Dealer dealer;
    
    /**
     * Sets up a new card server with six decks.
     */
    @Before
    public void setUp() {
        this.server = new CardServer(6);
    }
    
    /**
     * Test of the getWager function, of the Hand class.
     */
    @Test
    public void testGetWager() {
        Currency yen = Currency.getInstance(Locale.JAPAN);
        CurrencyAmount amount = new CurrencyAmount(50000, yen);
        Wager expected = new Wager(amount);
        Hand hand = new Hand(expected);
        Wager actual = hand.getWager();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the cardsValue function, of the Hand class.
     */
    @Test
    public void testCardsValue() {
        System.out.println("cardsValue");
        Hand hand = new Hand(DEFAULT_WAGER);
        assertEquals("Empty hand should have value 0", 0, hand.cardsValue());
    }
    
    /**
     * Another test of the cardsValue function, of the Hand class. A Jack of any 
     * suit should be valued at 10.
     */
    @Test
    public void testCardsValueAfterAddingJack() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.server.giveCard(Rank.JACK);
        hand.add(card);
        assertEquals("Hand with Jack should have value 10", 10, 
                hand.cardsValue());
    }
    
    /**
     * Another test of the cardsValue function, of the Hand class. Two Aces 
     * valued at 11 each would mean the hand has gone bust. However, when a hand 
     * has two Aces, one or both of them should be revalued at 1, so that the 
     * hand's value is then less than 22.
     */
    @Test
    public void testTwoAcesCantBust() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.server.giveCard(Rank.ACE);
        hand.add(ace);
        ace = this.server.giveCard(Rank.ACE);
        hand.add(ace);
        int value = hand.cardsValue();
        assert value < 22 : "Two Aces shouldn't bust";
    }
    
    /**
     * Another test of the cardsValue function, of the Hand class. The court 
     * cards should each be valued at 10.
     */
    @Test
    public void testCourtCardsAreTenEach() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard jack = this.server.giveCard(Rank.JACK);
        PlayingCard queen = this.server.giveCard(Rank.QUEEN);
        PlayingCard king = this.server.giveCard(Rank.KING);
        hand.add(jack);
        hand.add(queen);
        hand.add(king);
        assertEquals(30, hand.cardsValue());
    }
    
    /**
     * Test of the inspectCards function, of the Hand class.
     */
    @Test
    public void testInspectCards() {
        System.out.println("inspectCards");
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard[] expected = this.server.giveCards(Rank.TWO, 4);
        hand.add(expected[0]);
        hand.add(expected[1]);
        hand.add(expected[2]);
        hand.add(expected[3]);
        PlayingCard[] actual = hand.inspectCards();
        assertArrayEquals(expected, actual);
        String msg;
        for (PlayingCard card : actual) {
            msg = card.toString() + " came from the test class's card server";
            assert this.server.provenance(card) : msg;
        }
    }
    
    /**
     * Test of the isSplittableHand function, of the Hand class.
     */
    @Test
    public void testIsSplittableHand() {
        System.out.println("isSplittableHand");
        Hand splittableHand = new Hand(DEFAULT_WAGER);
        PlayingCard firstSix = this.server.giveCard(Rank.SIX);
        PlayingCard secondSix = this.server.giveCard(Rank.SIX);
        splittableHand.add(firstSix);
        splittableHand.add(secondSix);
        String msg = "Hand with " + firstSix.toString() + " and " 
                + secondSix.toString() + " should be considered splittable";
        assert splittableHand.isSplittableHand(dealer) : msg;
    }
    
    /**
     * Another test of the isSplittableHand function, of the Hand class.
     */
    @Test
    public void testNotSplittableHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard firstSix = this.server.giveCard(Rank.SIX);
        PlayingCard secondSix = this.server.giveCard(Rank.SIX);
        PlayingCard seven = this.server.giveCard(Rank.SEVEN);
        hand.add(firstSix);
        hand.add(secondSix);
        hand.add(seven);
        String msg = "Hand with " + firstSix.toString() + ", " 
                + secondSix.toString() + " and " + seven.toString() 
                + " should not be considered splittable";
        assert !hand.isSplittableHand(dealer) : msg;
    }
    
    /**
     * Another test of the split function, of the Hand class.
     */
    @Test
    public void testCanNotSplitNewHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        try {
            Hand splitOff = hand.split(dealer);
            System.out.println("Somehow created " + splitOff.toString() 
                    + " valued at " + splitOff.cardsValue() 
                    + " from new hand with no cards");
            String msg = "Shouldn't've been able to split off from 0-card hand";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Split from empty caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for split from empty";
            fail(msg);
        }
    }
    
    /**
     * Test of the split function, of the Hand class.
     */
    @Test
    public void testSplit() {
        System.out.println("split");
        Hand firstHand = new Hand(DEFAULT_WAGER);
        PlayingCard firstEight = this.server.giveCard(Rank.EIGHT);
        PlayingCard secondEight = this.server.giveCard(Rank.EIGHT);
        firstHand.add(firstEight);
        firstHand.add(secondEight);
        Hand splitOffHand = firstHand.split(dealer);
        assertEquals(8, firstHand.cardsValue());
        assertEquals(8, splitOffHand.cardsValue());
        PlayingCard[] cards = splitOffHand.inspectCards();
        String msg = cards[0].toString() 
                + " should have came from the same source as " 
                + firstEight.toString();
        assert this.server.provenance(cards[0]) : msg;
    }
    
    /**
     * Another test of the split function, of the Hand class. When a hand is 
     * split, the wager should also be split among the hands.
     */
    @Test
    public void testSplitAlsoSplitsWager() {
        long originalWagerCents = 128000L;
        CurrencyAmount originalWagerAmount 
                = new CurrencyAmount(originalWagerCents, DOLLARS);
        Wager originalWager = new Wager(originalWagerAmount);
        Hand firstHand = new Hand(originalWager);
        long splitWagerCents = originalWagerCents / 2;
        CurrencyAmount expected = new CurrencyAmount(splitWagerCents, DOLLARS);
        PlayingCard firstTen = this.server.giveCard(Rank.TEN);
        PlayingCard secondTen = this.server.giveCard(Rank.TEN);
        firstHand.add(firstTen);
        firstHand.add(secondTen);
        Hand secondHand = firstHand.split(dealer);
        Wager splitWager = firstHand.getWager();
        CurrencyAmount actual = splitWager.getAmount();
        assertEquals(expected, actual);
        splitWager = secondHand.getWager();
        actual = splitWager.getAmount();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the split function, of the Hand class. Some casinos allow 
     * a player to split any hand that consists of two cards valued at 10 each, 
     * even if they are not the same rank, such as, for example, 10&#9824; and 
     * Q&#9830;. However, in this blackjack implementation, that is not allowed.
     */
    @Test
    public void testMayNotSplitSameValuedCardsIfDiffRanks() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ten = this.server.giveCard(Rank.TEN);
        PlayingCard queen = this.server.giveCard(Rank.QUEEN);
        hand.add(ten);
        hand.add(queen);
        try {
            Hand splitOffHand = hand.split(dealer);
            String msg = "Trying to split off hand consisting of " 
                    + ten.toString() + " and " + queen.toString() 
                    + " should not have created " + splitOffHand.toString();
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to split off hand consisting of " 
                    + ten.toASCIIString() + " and " + queen.toASCIIString() 
                    + " correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        }
    }
    
    /**
     * Another test of split method, of class Hand. A few casinos do not allow a 
     * player to split a pair of Aces, such as, for example, A&#9827; and 
     * A&#9829;. In line with most casinos, splitting Aces is allowed in this  
     * blackjack implementation.
     */
    @Test
    public void testMaySplitPairOfAces() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard firstAce = this.server.giveCard(Rank.ACE);
        PlayingCard secondAce = this.server.giveCard(Rank.ACE);
        hand.add(firstAce);
        hand.add(secondAce);
        try {
            Hand splitOffHand = hand.split(dealer);
            System.out.println("Trying to split off hand consisting of " 
                    + firstAce.toASCIIString() + " and " 
                    + secondAce.toASCIIString() 
                    + " correctly created " + splitOffHand.toString());
        } catch (IllegalStateException ise) {
            String msg = "Trying to split off hand consisting of " 
                    + firstAce.toString() + " and " + secondAce.toString() 
                    + " should not have caused IllegalStateException";
            System.out.println("\"" + ise.getMessage() + "\"");
            fail(msg);
        }
    }
    
    /**
     * Test of isWinningHand method of class Hand.
     */
    @Test
    public void testIsWinningHand() {
        System.out.println("isWinningHand");
        Hand winningHand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.server.giveCard(Rank.ACE);
        PlayingCard jack = this.server.giveCard(Rank.JACK);
        winningHand.add(ace);
        winningHand.add(jack);
        String msg = ace.toString() + " and " + jack.toString() 
                + " should be considered a winning hand";
        assert winningHand.isWinningHand() : msg;
    }
    
    @Test
    public void testOpenHandIsNotWinningHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.server.getNextCard();
        hand.add(card);
        String assertionMessage = "Hand with just " + card.toString()
                + " should not be considered a winning hand";
        assert !hand.isWinningHand() : assertionMessage;
    }
    
    @Test
    public void testBustedHandIsNotWinningHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = this.server.giveCard(Rank.EIGHT);
        PlayingCard four = this.server.giveCard(Rank.FOUR);
        PlayingCard ten = this.server.giveCard(Rank.TEN);
        hand.add(eight);
        hand.add(four);
        hand.add(ten);
        String assertionMessage = "Hand with " + eight.toString() + ", " 
                + four.toString() + " and " + ten.toString() 
                + " should not be considered a winning hand";
        assert !hand.isWinningHand() : assertionMessage;
    }

    /**
     * Test of isBustedHand method of class Hand.
     */
    @Test
    public void testIsBustedHand() {
        System.out.println("isBustedHand");
        Hand bustedHand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = this.server.giveCard(Rank.EIGHT);
        PlayingCard four = this.server.giveCard(Rank.FOUR);
        PlayingCard ten = this.server.giveCard(Rank.TEN);
        bustedHand.add(eight);
        bustedHand.add(four);
        bustedHand.add(ten);
        String assertionMessage = "Hand with " + eight.toString() + ", " 
                + four.toString() + " and " + ten.toString() 
                + " should be considered a busted hand";
        assert bustedHand.isBustedHand() : assertionMessage;
    }

    @Test
    public void testWinningHandIsNotBustedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.server.giveCard(Rank.ACE);
        PlayingCard jack = this.server.giveCard(Rank.JACK);
        hand.add(ace);
        hand.add(jack);
        String assertionMessage = ace.toString() + " and " + jack.toString() 
                + " should not be considered a busted hand";
        assert !hand.isBustedHand() : assertionMessage;
    }
    
    @Test
    public void testOpenHandIsNotBustedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.server.getNextCard();
        hand.add(card);
        String assertionMessage = "Hand with just " + card.toString()
                + " should not be considered a busted hand";
        assert !hand.isBustedHand() : assertionMessage;
    }
    
    @Test
    public void testWinningHandIsClosedHand() {
        System.out.println("isClosedHand");
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.server.giveCard(Rank.ACE);
        PlayingCard jack = this.server.giveCard(Rank.JACK);
        hand.add(ace);
        hand.add(jack);
        String assertionMessage = ace.toString() + " and " + jack.toString() 
                + " should be considered a closed hand";
        assert hand.isClosedHand() : assertionMessage;
    }
    
    @Test
    public void testOpenHandIsNotClosedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.server.getNextCard();
        hand.add(card);
        String msg = "Hand with just " + card.toString()
                + " should not be considered a closed hand";
        assert !hand.isClosedHand() : msg;
    }
    
    @Test
    public void testBustedHandIsClosedHand() {
        Hand bustedHand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = this.server.giveCard(Rank.EIGHT);
        PlayingCard four = this.server.giveCard(Rank.FOUR);
        PlayingCard ten = this.server.giveCard(Rank.TEN);
        bustedHand.add(eight);
        bustedHand.add(four);
        bustedHand.add(ten);
        String msg = "Hand with " + eight.toString() + ", " + four.toString() 
                + " and " + ten.toString() 
                + " should be considered a closed hand";
        assert bustedHand.isClosedHand() : msg;
    }

    @Test
    public void testWinningHandIsNotOpenHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.server.giveCard(Rank.ACE);
        PlayingCard jack = this.server.giveCard(Rank.JACK);
        hand.add(ace);
        hand.add(jack);
        String msg = ace.toString() + " and " + jack.toString() 
                + " should not be considered an open hand";
        assert !hand.isOpenHand() : msg;
    }
    
    /**
     * Test of isOpenHand method of class Hand.
     */
    @Test
    public void testIsOpenHand() {
        System.out.println("isOpenHand");
        Hand openHand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.server.getNextCard();
        openHand.add(card);
        String msg = "Hand with just " + card.toString()
                + " should be considered an open hand";
        assert openHand.isOpenHand() : msg;
    }
    
    @Test
    public void testBustedHandIsNotOpenHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = this.server.giveCard(Rank.EIGHT);
        PlayingCard four = this.server.giveCard(Rank.FOUR);
        PlayingCard ten = this.server.giveCard(Rank.TEN);
        hand.add(eight);
        hand.add(four);
        hand.add(ten);
        String msg = "Hand with " + eight.toString() + ", " + four.toString() 
                + " and " + ten.toString() 
                + " should not be considered an open hand";
        assert !hand.isOpenHand() : msg;
    }
    
    @Test
    public void testOpenClosedCorrespondence() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card;
        String msg = "Open flag should be opposite of closed flag";
        while (hand.cardsValue() < 21) {
            card = this.server.getNextCard();
            hand.add(card);
            assert hand.isOpenHand() == !hand.isClosedHand() : msg;
        }
    }
    
    @Test
    public void testNewHandShouldNotBeSettledHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        String msg = "New hand should not be a settled hand";
        assert !hand.isSettledHand() : msg;
    }
    
    @Test
    public void testMarkSettled() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard firstCard = server.giveCard(Rank.JACK);
        hand.add(firstCard);
        PlayingCard secondCard = server.giveCard(Rank.ACE);
        hand.add(secondCard);
        hand.markSettled();
        String msg = "Hand with " + firstCard.toString() + " and " 
                + secondCard.toString() 
                + " was marked settled, should be recognized as settled";
        assert hand.isSettledHand() : msg;
    }

    /**
     * Test of add method, of class Hand.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        PlayingCard card = this.server.getNextCard();
        Hand hand = new Hand(DEFAULT_WAGER);
        hand.add(card);
        String msg = "Value should be greater than 0 after adding " 
                + card.toString();
        int value = hand.cardsValue();
        assert value > 0 : msg;
    }
    
    /**
     * Another test of add method, of class Hand.
     */
    @Test
    public void testCantAddSameCardTwice() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.server.getNextCard();
        hand.add(card);
        try {
            hand.add(card);
            String msg = "Should not have been able to add " + card.toString() 
                    + " (" + System.identityHashCode(card) + ") twice";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Add same card twice correcly caused IAE");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add "
                    + card.toString() + " (" + System.identityHashCode(card) 
                    + ") twice";
            fail(msg);
        }
    }
    
    @Test
    public void testCantAddCardAfterWinning() {
        Hand winningHand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.server.giveCard(Rank.ACE);
        PlayingCard jack = this.server.giveCard(Rank.JACK);
        winningHand.add(ace);
        winningHand.add(jack);
        PlayingCard someCard = this.server.getNextCard();
        try {
            winningHand.add(someCard);
            String msg = "Should not have been able to add " 
                    + someCard.toString() + " after " + ace.toString() + " and " 
                    + jack.toString();
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to add " + someCard.toASCIIString() 
                    + " after " + ace.toASCIIString() + " and " 
                    + jack.toASCIIString() 
                    + " correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add "
                    + someCard.toString() + " after " + ace.toString() + " and " 
                    + jack.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testCantAddCardAfterBusting() {
        Hand winningHand = new Hand(DEFAULT_WAGER);
        PlayingCard ten = this.server.giveCard(Rank.TEN);
        PlayingCard jack = this.server.giveCard(Rank.JACK);
        PlayingCard queen = this.server.giveCard(Rank.QUEEN);
        winningHand.add(ten);
        winningHand.add(jack);
        winningHand.add(queen);
        PlayingCard someCard = this.server.getNextCard();
        try {
            winningHand.add(someCard);
            String failMsg = "Should not have been able to add " 
                    + someCard.toString() + " after " + ten.toString() + ", " 
                    + jack.toString() + " and " + queen.toString();
            fail(failMsg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to add " + someCard.toASCIIString() 
                    + " after " + ten.toASCIIString() + ", " 
                    + jack.toASCIIString() + " and " + queen.toASCIIString()
                    + " correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add "
                    + someCard.toString() + " after " + ten.toString() + " and " 
                    + jack.toString() + " and " + queen.toString();
            fail(failMsg);
        }
    }
    
}
