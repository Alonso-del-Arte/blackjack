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
import currency.CurrencyChooser;
import playingcards.CardServer;
import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.matchers.RankPairSpec;

import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
    
    static final Wager DEFAULT_WAGER = new Wager(HUNDRED_BUCKS);
    
    private final CardServer SERVER = new CardServer(50);
    
    private static final Dealer DEALER = new Dealer();
    
    private static final Rank[] RANKS = Rank.values();
    
    private Set<RankPairSpec> makeRankPairSpecSet() {
        Set<RankPairSpec> pairSpecs 
                = new HashSet<>(BlackJack.DISTINCT_TEN_PAIRS);
        int stop = RANKS.length;
        for (int i = 0; i < stop; i++) {
            PlayingCard cardA = this.SERVER.getNextCard();
            PlayingCard cardB = this.SERVER.getNextCard();
            RankPairSpec pairSpec = new RankPairSpec(cardA.getRank(), 
                    cardB.getRank());
            pairSpecs.add(pairSpec);
        }
        return pairSpecs;
    }
    
    /**
     * Test of the toString function, of the Hand class.
     */
    @Test
    public void testToStringInitial() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard firstCard = this.SERVER.getNextCard();
        hand.add(firstCard);
        PlayingCard secondCard = this.SERVER.getNextCard();
        hand.add(secondCard);
        String expected = "(" + firstCard.toString() + "," 
                + secondCard.toString() + ")";
        String actual = hand.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard firstCard = this.SERVER.getNextCard();
        hand.add(firstCard);
        PlayingCard secondCard = this.SERVER.getNextCard();
        hand.add(secondCard);
        String expected = "(" + firstCard.toString() + "," 
                + secondCard.toString() + ")";
        int roughValueCount = firstCard.getRank().getIntVal() 
                + secondCard.getRank().getIntVal();
        do {
            PlayingCard card = this.SERVER.getNextCard();
            hand.add(card);
            roughValueCount += card.cardValue();
            expected = expected.replace("\u0029", "," + card.toString() 
                    + "\u0029");
            String actual = hand.toString().replace(" ", "");
            assertEquals(expected, actual);
        } while (roughValueCount < 22);
    }
    
    /**
     * Test of the getWager function, of the Hand class.
     */
    @Test
    public void testGetWager() {
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
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
        PlayingCard card = this.SERVER.giveCard(Rank.JACK);
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
        PlayingCard ace = this.SERVER.giveCard(Rank.ACE);
        hand.add(ace);
        ace = this.SERVER.giveCard(Rank.ACE);
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
        PlayingCard jack = this.SERVER.giveCard(Rank.JACK);
        PlayingCard queen = this.SERVER.giveCard(Rank.QUEEN);
        PlayingCard king = this.SERVER.giveCard(Rank.KING);
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
        PlayingCard[] expected = this.SERVER.giveCards(Rank.TWO, 4);
        hand.add(expected[0]);
        hand.add(expected[1]);
        hand.add(expected[2]);
        hand.add(expected[3]);
        PlayingCard[] actual = hand.inspectCards();
        assertArrayEquals(expected, actual);
        String msg;
        for (PlayingCard card : actual) {
            msg = card.toString() + " came from the test class's card server";
            assert this.SERVER.provenance(card) : msg;
        }
    }
    
    /**
     * Test of the isSplittableHand function, of the Hand class.
     */
    @Test
    public void testIsSplittableHand() {
        System.out.println("isSplittableHand");
        Set<RankPairSpec> pairSpecs = this.makeRankPairSpecSet();
        Dealer dealer = new Dealer(pairSpecs);
        int maxPairs = 39;
        for (int i = 0; i < maxPairs; i++) {
            PlayingCard cardA = this.SERVER.getNextCard();
            PlayingCard cardB = this.SERVER.getNextCard();
            Hand hand = new Hand(DEFAULT_WAGER);
            hand.add(cardA);
            hand.add(cardB);
            RankPairSpec pairSpec = new RankPairSpec(cardA.getRank(), 
                    cardB.getRank());
            boolean expected = pairSpecs.contains(pairSpec);
            boolean actual = hand.isSplittableHand(dealer);
            String msg = "Since test dealer is said to " 
                    + (expected ? "allow" : "not allow") + " splitting " 
                    + pairSpec.toString() + ", player should " 
                    + (expected ? "" : "not") + " be allowed to split " 
                    + hand.toString();
            if (expected) {
                assert actual : msg;
            } else {
                assert !actual : msg;
            }
        }
    }
    
    /**
     * Another test of the split function, of the Hand class.
     */
    @Test
    public void testCanNotSplitNewHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        try {
            Hand splitOff = hand.split(DEALER);
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
    
    private static void assertCanSplit(Hand hand, Dealer dealer) {
        PlayingCard[] cardsBeforeSplit = hand.inspectCards();
        assert cardsBeforeSplit.length == 2 : "Hand should only have two cards";
        Hand splitOffHand = hand.split(dealer);
        PlayingCard[] cardsA = hand.inspectCards();
        PlayingCard[] cardsB = splitOffHand.inspectCards();
        String msg = "Right after split, hand should only have one card";
        assert cardsA.length == 1 : msg;
        assert cardsB.length == 1 : msg;
        if (cardsA[0].equals(cardsBeforeSplit[0])) {
            assertEquals(cardsBeforeSplit[1], cardsB[0]);
        } else {
            assertEquals(cardsBeforeSplit[0], cardsB[0]);
        }
    }
    
    private static void assertCantSplit(Hand hand, Dealer dealer) {
        String prevHandStr = hand.toString();
        try {
            Hand splitOffHand = hand.split(dealer);
            String msg = "Should not have been able to split " + prevHandStr 
                    + " to " + hand.toString() + " and " 
                    + splitOffHand.toString();
            fail(msg);
        } catch (IllegalStateException ise) {
            String excMsg = ise.getMessage();
            assert excMsg != null : "Exception message should not be null";
        }
    }
    
    /**
     * Test of the split function, of the Hand class.
     */
    @Test
    public void testSplit() {
        System.out.println("split");
        Set<RankPairSpec> pairSpecs = this.makeRankPairSpecSet();
        Dealer dealer = new Dealer(pairSpecs);
        for (Rank firstCardRank : RANKS) {
            PlayingCard firstCard = this.SERVER.giveCard(firstCardRank);
            for (Rank secondCardRank : RANKS) {
                PlayingCard secondCard = this.SERVER.giveCard(secondCardRank);
                Hand hand = new Hand(DEFAULT_WAGER);
                hand.add(firstCard);
                hand.add(secondCard);
                RankPairSpec pairSpec = new RankPairSpec(firstCardRank, 
                        secondCardRank);
                boolean maySplit = pairSpecs.contains(pairSpec);
                if (maySplit) {
                    assertCanSplit(hand, dealer);
                } else {
                    assertCantSplit(hand, dealer);
                }
            }
        }
    }
    
    /**
     * Another test of the split function, of the Hand class. When a hand is 
     * split, the wager should also be split among the hands.
     */
    @Test
    public void testSplitOffHandHasSameWager() {
        long wagerInCents = 12800L;
        CurrencyAmount expected = new CurrencyAmount(wagerInCents, DOLLARS);
        Wager originalWager = new Wager(expected);
        Hand firstHand = new Hand(originalWager);
        PlayingCard firstTen = this.SERVER.giveCard(Rank.TEN);
        PlayingCard secondTen = this.SERVER.giveCard(Rank.TEN);
        firstHand.add(firstTen);
        firstHand.add(secondTen);
        Hand secondHand = firstHand.split(DEALER);
        Wager splitWager = firstHand.getWager();
        CurrencyAmount actual = splitWager.getAmount();
        assertEquals(expected, actual);
        splitWager = secondHand.getWager();
        actual = splitWager.getAmount();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of the isWinningHand function of the Hand class.
     */
    @Test
    public void testIsWinningHand() {
        System.out.println("isWinningHand");
        Hand winningHand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.SERVER.giveCard(Rank.ACE);
        PlayingCard jack = this.SERVER.giveCard(Rank.JACK);
        winningHand.add(ace);
        winningHand.add(jack);
        String msg = ace.toString() + " and " + jack.toString() 
                + " should be considered a winning hand";
        assert winningHand.isWinningHand() : msg;
    }
    
    @Test
    public void testOpenHandIsNotWinningHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.SERVER.getNextCard();
        hand.add(card);
        String msg = "Hand with just " + card.toString() 
                + " should not be considered a winning hand";
        assert !hand.isWinningHand() : msg;
    }
    
    @Test
    public void testBustedHandIsNotWinningHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = this.SERVER.giveCard(Rank.EIGHT);
        PlayingCard four = this.SERVER.giveCard(Rank.FOUR);
        PlayingCard ten = this.SERVER.giveCard(Rank.TEN);
        hand.add(eight);
        hand.add(four);
        hand.add(ten);
        String msg = "Hand with " + eight.toString() + ", " + four.toString() 
                + " and " + ten.toString() 
                + " should not be considered a winning hand";
        assert !hand.isWinningHand() : msg;
    }

    /**
     * Test of the isBustedHand function of the Hand class.
     */
    @Test
    public void testIsBustedHand() {
        System.out.println("isBustedHand");
        Hand bustedHand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = this.SERVER.giveCard(Rank.EIGHT);
        PlayingCard four = this.SERVER.giveCard(Rank.FOUR);
        PlayingCard ten = this.SERVER.giveCard(Rank.TEN);
        bustedHand.add(eight);
        bustedHand.add(four);
        bustedHand.add(ten);
        String msg = "Hand with " + eight.toString() + ", " + four.toString() 
                + " and " + ten.toString() 
                + " should be considered a busted hand";
        assert bustedHand.isBustedHand() : msg;
    }

    @Test
    public void testWinningHandIsNotBustedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.SERVER.giveCard(Rank.ACE);
        PlayingCard jack = this.SERVER.giveCard(Rank.JACK);
        hand.add(ace);
        hand.add(jack);
        String msg = ace.toString() + " and " + jack.toString() 
                + " should not be considered a busted hand";
        assert !hand.isBustedHand() : msg;
    }
    
    @Test
    public void testOpenHandIsNotBustedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.SERVER.getNextCard();
        hand.add(card);
        String msg = "Hand with just " + card.toString() 
                + " should not be considered a busted hand";
        assert !hand.isBustedHand() : msg;
    }
    
    @Test
    public void testWinningHandIsClosedHand() {
        System.out.println("isClosedHand");
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.SERVER.giveCard(Rank.ACE);
        PlayingCard jack = this.SERVER.giveCard(Rank.JACK);
        hand.add(ace);
        hand.add(jack);
        String msg = ace.toString() + " and " + jack.toString() 
                + " should be considered a closed hand";
        assert hand.isClosedHand() : msg;
    }
    
    @Test
    public void testOpenHandIsNotClosedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.SERVER.getNextCard();
        hand.add(card);
        String msg = "Hand with just " + card.toString()
                + " should not be considered a closed hand";
        assert !hand.isClosedHand() : msg;
    }
    
    @Test
    public void testBustedHandIsClosedHand() {
        Hand bustedHand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = this.SERVER.giveCard(Rank.EIGHT);
        PlayingCard four = this.SERVER.giveCard(Rank.FOUR);
        PlayingCard ten = this.SERVER.giveCard(Rank.TEN);
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
        PlayingCard ace = this.SERVER.giveCard(Rank.ACE);
        PlayingCard jack = this.SERVER.giveCard(Rank.JACK);
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
        PlayingCard card = this.SERVER.getNextCard();
        openHand.add(card);
        String msg = "Hand with just " + card.toString()
                + " should be considered an open hand";
        assert openHand.isOpenHand() : msg;
    }
    
    @Test
    public void testBustedHandIsNotOpenHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = this.SERVER.giveCard(Rank.EIGHT);
        PlayingCard four = this.SERVER.giveCard(Rank.FOUR);
        PlayingCard ten = this.SERVER.giveCard(Rank.TEN);
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
            card = this.SERVER.getNextCard();
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
        PlayingCard firstCard = SERVER.giveCard(Rank.JACK);
        hand.add(firstCard);
        PlayingCard secondCard = SERVER.giveCard(Rank.ACE);
        hand.add(secondCard);
        hand.markSettled();
        String msg = "Hand with " + firstCard.toString() + " and " 
                + secondCard.toString() 
                + " was marked settled, should be recognized as settled";
        assert hand.isSettledHand() : msg;
    }

    /**
     * Test of the add procedure, of the Hand class.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        PlayingCard card = this.SERVER.getNextCard();
        Hand hand = new Hand(DEFAULT_WAGER);
        hand.add(card);
        String msg = "Value should be greater than 0 after adding " 
                + card.toString();
        int value = hand.cardsValue();
        assert value > 0 : msg;
    }
    
    /**
     * Another test of the add procedure, of the Hand class.
     */
    @Test
    public void testCantAddSameCardTwice() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = this.SERVER.getNextCard();
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
    
    /**
     * Another test of the add procedure, of the Hand class.
     */
    @Test
    public void testCantAddCardAfterWinning() {
        Hand winningHand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.SERVER.giveCard(Rank.ACE);
        PlayingCard jack = this.SERVER.giveCard(Rank.JACK);
        winningHand.add(ace);
        winningHand.add(jack);
        PlayingCard someCard = this.SERVER.getNextCard();
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
        PlayingCard ten = this.SERVER.giveCard(Rank.TEN);
        PlayingCard jack = this.SERVER.giveCard(Rank.JACK);
        PlayingCard queen = this.SERVER.giveCard(Rank.QUEEN);
        winningHand.add(ten);
        winningHand.add(jack);
        winningHand.add(queen);
        PlayingCard someCard = this.SERVER.getNextCard();
        try {
            winningHand.add(someCard);
            String msg = "Should not have been able to add " 
                    + someCard.toString() + " after " + ten.toString() + ", " 
                    + jack.toString() + " and " + queen.toString();
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to add " + someCard.toASCIIString() 
                    + " after " + ten.toASCIIString() + ", " 
                    + jack.toASCIIString() + " and " + queen.toASCIIString()
                    + " correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add "
                    + someCard.toString() + " after " + ten.toString() + " and " 
                    + jack.toString() + " and " + queen.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testNoSettlementBeforeMarkingSettled() {
        Hand hand = new Hand(DEFAULT_WAGER);
        try {
            Wager.Settlement badSettlement = hand.getSettlement();
            String msg = "Settlement from new hand should not be " 
                    + badSettlement.toString() + ", should've caused exception";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Premature wager settlement caused exception");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for premature wager settlement";
            fail(msg);
        }
    }
    
    @Test
    public void testGetSettlement() {
        System.out.println("getSettlement");
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = this.SERVER.giveCard(Rank.ACE);
        PlayingCard ten = this.SERVER.giveCard(Rank.TEN);
        hand.add(ace);
        hand.add(ten);
        hand.markSettled();
        Wager.Outcome expected = Wager.Outcome.NATURAL_BLACKJACK;
//        Wager.Outcome actual = hand.getSettlement();
fail("Finish writing test");
    }
    
}
