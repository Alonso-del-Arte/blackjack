/*
 * Copyright (C) 2026 Alonso del Arte
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

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

import playingcards.AbridgedDeck;
import playingcards.CardDeck;
import playingcards.CardServer;
import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.matchers.RankPairSpec;

/**
 * Tests of the Hand class.
 * @author Alonso del Arte
 */
public class HandTest {
    
    private static final CardServer SERVER = new CardServer(25);
    
    private static final CardServer EXTRA_SERVER = new CardServer(10);
    
    private static final Dealer DEALER = new Dealer();
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Rank[] ROYAL_RANKS 
            = {Rank.JACK, Rank.QUEEN, Rank.KING};
    
    private static final Rank[] ACES_AND_ROYAL_RANKS 
            = {Rank.ACE, Rank.JACK, Rank.QUEEN, Rank.KING};
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final CurrencyAmount HUNDRED_BUCKS 
            = new CurrencyAmount(10000, DOLLARS);
    
    static final Wager DEFAULT_WAGER = new Wager(HUNDRED_BUCKS);
    
    private static final Predicate<PlayingCard> NOT_ACE_PREDICATE 
            = ((card) -> card.getRank().ordinal() > 0);
    
    private static final Predicate<PlayingCard> TEN_CARD_PREDICATE 
            = ((card) -> card.getRank().ordinal() > 8);
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Card server has " + SERVER.countRemaining() 
                + " cards remaining");
        System.out.println("Extra server has " + EXTRA_SERVER.countRemaining() 
                + " cards remaining");
    }
    
    private Set<RankPairSpec> makeRankPairSpecSet() {
        Set<RankPairSpec> pairSpecs 
                = new HashSet<>(BlackJack.DISTINCT_TEN_PAIRS);
        int stop = RANKS.length;
        for (int i = 0; i < stop; i++) {
            PlayingCard cardA = SERVER.getNextCard();
            PlayingCard cardB = SERVER.getNextCard();
            RankPairSpec pairSpec = new RankPairSpec(cardA.getRank(), 
                    cardB.getRank());
            pairSpecs.add(pairSpec);
        }
        return pairSpecs;
    }
    
    private static int assessValue(PlayingCard card) {
        return switch (card.getRank()) {
            case ACE -> 11;
            case JACK, QUEEN, KING -> 10;
            default -> card.integerValue();
        };
    }
    
    private static Hand makeOpenHand() {
        boolean suitable = false;
        Hand hand = null;
        while (!suitable) {
            hand = new Hand(DEFAULT_WAGER);
            int value = 0;
            boolean firstAceEncounteredAlready = false;
            while (value < 16) {
                PlayingCard card = EXTRA_SERVER.getNextCard();
                hand.add(card);
                int addend = assessValue(card);
                if (firstAceEncounteredAlready) {
                    addend -= 10;
                } else {
                    firstAceEncounteredAlready = card.isOf(Rank.ACE);
                }
                value += addend;
            }
            suitable = value < 21;
        }
        return hand;
    }
    
    private static Hand makeOpenHandAuxConstructor() {
        boolean suitable = false;
        Hand hand = null;
        while (!suitable) {
            PlayingCard firstCard = EXTRA_SERVER.getNextCard();
            hand = new Hand(DEFAULT_WAGER, firstCard);
            int value = assessValue(firstCard);
            boolean firstAceEncounteredAlready = firstCard.isOf(Rank.ACE);
            while (value < 16) {
                PlayingCard card = EXTRA_SERVER.getNextCard();
                hand.add(card);
                int addend = assessValue(card);
                if (firstAceEncounteredAlready) {
                    addend -= 10;
                } else {
                    firstAceEncounteredAlready = card.isOf(Rank.ACE);
                }
                value += addend;
            }
            suitable = value < 21;
        }
        return hand;
    }
    
    private static Predicate<PlayingCard> predicateForBust(int value) {
        int target = 21 - value;
        return ((card) -> card.integerValue() > target);
    }
    
    @Test
    public void testToStringInitial() {
        Hand hand = new Hand(DEFAULT_WAGER);
        String expected = "()";
        String actual = hand.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringInitialAuxConstructor() {
        PlayingCard firstCard = SERVER.getNextCard();
        Hand hand = new Hand(DEFAULT_WAGER, firstCard);
        String expected = "(" + firstCard.toString() + ")";
        String actual = hand.toString().replace(" ", "");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        Hand hand = new Hand(DEFAULT_WAGER);
        List<PlayingCard> cards = new ArrayList<>(11);
        int roughValueCount = 0;
        do {
            PlayingCard card = SERVER.getNextCard();
            hand.add(card);
            cards.add(card);
            roughValueCount += card.integerValue();
            String intermediate = cards.toString().replace(" ", "");
            String expected = "(" + intermediate
                    .substring(1, intermediate.length() - 1) + ")";
            String actual = hand.toString().replace(" ", "");
            assertEquals(expected, actual);
        } while (roughValueCount < 21);
    }
    
    @Test
    public void testToStringFromAuxConstructor() {
        PlayingCard firstCard = SERVER.getNextCard();
        Hand hand = new Hand(DEFAULT_WAGER, firstCard);
        List<PlayingCard> cards = new ArrayList<>(11);
        cards.add(firstCard);
        int roughValueCount = firstCard.integerValue();
        do {
            PlayingCard card = SERVER.getNextCard();
            hand.add(card);
            cards.add(card);
            roughValueCount += card.integerValue();
            String intermediate = cards.toString().replace(" ", "");
            String expected = "(" + intermediate
                    .substring(1, intermediate.length() - 1) + ")";
            String actual = hand.toString().replace(" ", "");
            assertEquals(expected, actual);
        } while (roughValueCount < 21);
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
        Wager expected = new Wager(amount);
        Hand hand = new Hand(expected);
        Wager actual = hand.getWager();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetWagerFromAuxConstructorInstance() {
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager expected = new Wager(amount);
        PlayingCard firstCard = SERVER.getNextCard();
        Hand hand = new Hand(expected, firstCard);
        Wager actual = hand.getWager();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testCardsValueNewEmptyHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        int expected = 0;
        int actual = hand.cardsValue();
        String message = "Empty hand should have value 0";
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the cardsValue function, of the Hand class.
     */
    @Test
    public void testCardsValue() {
        System.out.println("cardsValue");
        CardDeck deck = new AbridgedDeck(ACES_AND_ROYAL_RANKS);
        deck.shuffle();
        Hand instance = new Hand(DEFAULT_WAGER);
        int expected = 0;
        do {
            PlayingCard card = deck.getNextCard();
            instance.add(card);
            expected += card.integerValue();
            int actual = instance.cardsValue();
            String message = "Reckoning value of " + instance.toString();
            assertEquals(message, expected, actual);
        } while (expected < 21);
    }
    
    /**
     * Another test of the cardsValue function, of the Hand class. Two Aces 
     * valued at 11 each would mean the hand has gone bust. However, when a hand 
     * has two Aces, one or both of them should be revalued at 1, so that the 
     * hand's value is then less than 22.
     */
    @Test
    public void testTwoAcesDoNotBust() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = SERVER.giveCard(Rank.ACE);
        hand.add(ace);
        ace = SERVER.giveCard(Rank.ACE);
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
        final int expected = 10;
        for (Rank rank : ROYAL_RANKS) {
            Hand hand = new Hand(DEFAULT_WAGER);
            PlayingCard card = SERVER.giveCard(rank);
            hand.add(card);
            int actual = hand.cardsValue();
            String message = "Getting value of " + card.toString() 
                    + " in a blackjack hand";
            assertEquals(message, expected, actual);
        }
    }
    
    /**
     * Test of the inspectCards function, of the Hand class.
     */
    @org.junit.Ignore
    @Test
    public void testInspectCards() {
        System.out.println("inspectCards");
        fail("REASSESS THE FUNCTION BEING TESTED AND REWRITE TEST");
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard[] expected = SERVER.giveCards(Rank.TWO, 4);
        hand.add(expected[0]);
        hand.add(expected[1]);
        hand.add(expected[2]);
        hand.add(expected[3]);
        PlayingCard[] actual = hand.inspectCards();
        assertArrayEquals(expected, actual);
        String msg;
        for (PlayingCard card : actual) {
            msg = card.toString() + " came from the test class's card server";
            assert SERVER.provenance(card) : msg;
        }
    }
    
    /**
     * Test of the isSplittable function, of the Hand class.
     */
    @Test
    public void testIsSplittable() {
        System.out.println("isSplittableHand");
        Set<RankPairSpec> pairSpecs = this.makeRankPairSpecSet();
        Dealer dealer = new Dealer(pairSpecs);
        int maxPairs = 39;
        for (int i = 0; i < maxPairs; i++) {
            PlayingCard cardA = SERVER.getNextCard();
            PlayingCard cardB = SERVER.getNextCard();
            Hand hand = new Hand(DEFAULT_WAGER);
            hand.add(cardA);
            hand.add(cardB);
            RankPairSpec pairSpec = new RankPairSpec(cardA.getRank(), 
                    cardB.getRank());
            boolean expected = pairSpecs.contains(pairSpec);
            boolean actual = hand.isSplittable(dealer);
            String msg = "Since test dealer is said to " 
                    + (expected ? "allow" : "not allow") + " splitting " 
                    + pairSpec.toString() + ", player should " 
                    + (expected ? "" : "not") + " be allowed to split " 
                    + hand.toString();
            assert expected == actual : msg;
        }
    }
    
    @Test
    public void testAuxConstructorInstanceIsSplittable() {
        Set<RankPairSpec> pairSpecs = this.makeRankPairSpecSet();
        Dealer dealer = new Dealer(pairSpecs);
        int maxPairs = 39;
        for (int i = 0; i < maxPairs; i++) {
            PlayingCard firstCard = SERVER.getNextCard();
            Hand hand = new Hand(DEFAULT_WAGER, firstCard);
            PlayingCard card = SERVER.getNextCard();
            hand.add(card);
            RankPairSpec pairSpec = new RankPairSpec(firstCard.getRank(), 
                    card.getRank());
            boolean expected = pairSpecs.contains(pairSpec);
            boolean actual = hand.isSplittable(dealer);
            String msg = "Since test dealer is said to " 
                    + (expected ? "allow" : "not allow") + " splitting " 
                    + pairSpec.toString() + ", player should " 
                    + (expected ? "" : "not") + " be allowed to split " 
                    + hand.toString();
            assert expected == actual : msg;
        }
    }
    
    /**
     * Another test of the split function, of the Hand class.
     */
    @Test
    public void testCanNotSplitNewHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        String msg = "Shouldn't be able to split " + hand.toString() 
                + " with wager " + DEFAULT_WAGER.toString();
        Throwable t = assertThrows(() -> {
            Hand splitOff = hand.split(DEALER);
            System.out.println("Somehow created " + splitOff.toString() 
                    + " valued at " + splitOff.cardsValue() 
                    + " from new hand with no cards");
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testNoSplitNewHandAuxConstructorInstance() {
        PlayingCard firstCard = SERVER.getNextCard();
        Hand hand = new Hand(DEFAULT_WAGER, firstCard);
        String msg = "Shouldn't be able to split " + hand.toString() 
                + " with wager " + DEFAULT_WAGER.toString();
        Throwable t = assertThrows(() -> {
            Hand splitOff = hand.split(DEALER);
            System.out.println("Somehow created " + splitOff.toString() 
                    + " valued at " + splitOff.cardsValue() 
                    + " from hand with just one card");
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testNoSplitHandWithJustOneCard() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = SERVER.getNextCard();
        hand.add(card);
        String msg = "Shouldn't be able to split " + hand.toString() 
                + " with wager " + DEFAULT_WAGER.toString();
        Throwable t = assertThrows(() -> {
            Hand splitOff = hand.split(DEALER);
            System.out.println("Somehow created " + splitOff.toString() 
                    + " valued at " + splitOff.cardsValue() 
                    + " from hand with just one card");
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
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
    
    private static void assertCanNotSplit(Hand hand, Dealer dealer) {
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
            PlayingCard firstCard = SERVER.giveCard(firstCardRank);
            for (Rank secondCardRank : RANKS) {
                PlayingCard secondCard = SERVER.giveCard(secondCardRank);
                Hand hand = new Hand(DEFAULT_WAGER);
                hand.add(firstCard);
                hand.add(secondCard);
                RankPairSpec pairSpec = new RankPairSpec(firstCardRank, 
                        secondCardRank);
                boolean maySplit = pairSpecs.contains(pairSpec);
                if (maySplit) {
                    assertCanSplit(hand, dealer);
                } else {
                    assertCanNotSplit(hand, dealer);
                }
            }
        }
    }
    
    @Test
    public void testSplitAuxConstructorInstance() {
        Set<RankPairSpec> pairSpecs = this.makeRankPairSpecSet();
        Dealer dealer = new Dealer(pairSpecs);
        for (Rank firstCardRank : RANKS) {
            PlayingCard firstCard = SERVER.giveCard(firstCardRank);
            for (Rank secondCardRank : RANKS) {
                Hand hand = new Hand(DEFAULT_WAGER, firstCard);
                PlayingCard secondCard = SERVER.giveCard(secondCardRank);
                hand.add(secondCard);
                RankPairSpec pairSpec = new RankPairSpec(firstCardRank, 
                        secondCardRank);
                boolean maySplit = pairSpecs.contains(pairSpec);
                if (maySplit) {
                    assertCanSplit(hand, dealer);
                } else {
                    assertCanNotSplit(hand, dealer);
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
        int cents = RANDOM.nextInt(1000, 100000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount expected = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(expected);
        Hand firstHand = new Hand(wager);
        PlayingCard firstCard = SERVER.getNextCard();
        Rank rank = firstCard.getRank();
        PlayingCard secondCard = SERVER.giveCard(rank);
        firstHand.add(firstCard);
        firstHand.add(secondCard);
        String originalHandLabel = firstHand.toString();
        Hand secondHand = firstHand.split(DEALER);
        String msgPart = " split off from " + originalHandLabel 
                + " should have " + expected.toString() + " wager";
        Wager splitWager = firstHand.getWager();
        String message = "Hand " + firstHand.toString() + msgPart;
        CurrencyAmount actual = splitWager.getAmount();
        assertEquals(message, expected, actual);
        message = "Hand " + secondHand.toString() + msgPart;
        splitWager = secondHand.getWager();
        actual = splitWager.getAmount();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testSplitOffHandHasSameWagerAuxConstructor() {
        int cents = RANDOM.nextInt(1000, 100000);
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount expected = new CurrencyAmount(cents, currency);
        Wager originalWager = new Wager(expected);
        PlayingCard firstCard = SERVER.getNextCard();
        Hand firstHand = new Hand(originalWager, firstCard);
        Rank rank = firstCard.getRank();
        PlayingCard secondCard = SERVER.giveCard(rank);
        firstHand.add(secondCard);
        String originalHandLabel = firstHand.toString();
        Hand secondHand = firstHand.split(DEALER);
        String msgPart = " split off from " + originalHandLabel 
                + " should have " + expected.toString() + " wager";
        Wager splitWager = firstHand.getWager();
        String message = "Hand " + firstHand.toString() + msgPart;
        CurrencyAmount actual = splitWager.getAmount();
        assertEquals(message, expected, actual);
        message = "Hand " + secondHand.toString() + msgPart;
        splitWager = secondHand.getWager();
        actual = splitWager.getAmount();
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the isWinning function of the Hand class.
     */
    @Test
    public void testIsWinning() {
        System.out.println("isWinning");
        Hand winningHand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = SERVER.giveCard(Rank.ACE);
        PlayingCard tenCard = SERVER.giveCard(TEN_CARD_PREDICATE);
        winningHand.add(ace);
        winningHand.add(tenCard);
        String msg = ace.toString() + " and " + tenCard.toString() 
                + " should be considered a winning hand";
        assert winningHand.isWinning() : msg;
    }
    
    @Test
    public void testIsWinningAuxConstructorInstanceStartWithAce() {
        PlayingCard firstCard = SERVER.giveCard(Rank.ACE);
        Hand hand = new Hand(DEFAULT_WAGER, firstCard);
        PlayingCard card = SERVER.giveCard(TEN_CARD_PREDICATE);
        hand.add(card);
        String msg = hand.toString() + " should be considered a winning hand";
        assert hand.isWinning() : msg;
    }
    
    @Test
    public void testIsWinningAuxConstructorInstanceStartWithTenCard() {
        PlayingCard firstCard = SERVER.giveCard(TEN_CARD_PREDICATE);
        Hand hand = new Hand(DEFAULT_WAGER, firstCard);
        PlayingCard card = SERVER.giveCard(Rank.ACE);
        hand.add(card);
        String msg = hand.toString() + " should be considered a winning hand";
        assert hand.isWinning() : msg;
    }
    
    @Test
    public void testOpenHandIsNotWinningHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        int value = 0;
        while (value < 21) {
            String msg = "Hand " + hand.toString() 
                    + " should not be a winning hand";
            assert !hand.isWinning() : msg;
            PlayingCard card = SERVER.giveCard(NOT_ACE_PREDICATE);
            hand.add(card);
            value += assessValue(card);
        }
    }
    
    @Test
    public void testOpenHandAuxConstructorIsNotWinningHand() {
        PlayingCard firstCard = SERVER.getNextCard();
        Hand hand = new Hand(DEFAULT_WAGER, firstCard);
        int value = assessValue(firstCard);
        while (value < 21) {
            String msg = "Hand " + hand.toString() 
                    + " should not be a winning hand";
            assert !hand.isWinning() : msg;
            PlayingCard card = SERVER.giveCard(NOT_ACE_PREDICATE);
            hand.add(card);
            value += assessValue(card);
        }
    }
    
    @Test
    public void testBustedHandIsNotWinningHand() {
        Hand hand = makeOpenHand();
        Predicate<PlayingCard> predicate = predicateForBust(hand.cardsValue());
        PlayingCard card = SERVER.giveCard(predicate);
        hand.add(card);
        String msg = "Hand " + hand.toString() 
                + " should not be considered a winning hand";
        assert !hand.isWinning() : msg;
    }

    @Test
    public void testBustedHandAuxConstructorInstanceIsNotWinningHand() {
        Hand hand = makeOpenHandAuxConstructor();
        Predicate<PlayingCard> predicate = predicateForBust(hand.cardsValue());
        PlayingCard card = SERVER.giveCard(predicate);
        hand.add(card);
        String msg = "Hand " + hand.toString() 
                + " should not be considered a winning hand";
        assert !hand.isWinning() : msg;
    }

    /**
     * Test of the isBusted function of the Hand class.
     */
    @Test
    public void testIsBustedHand() {
        System.out.println("isBustedHand");
        Hand bustedHand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = SERVER.giveCard(Rank.EIGHT);
        PlayingCard four = SERVER.giveCard(Rank.FOUR);
        PlayingCard ten = SERVER.giveCard(Rank.TEN);
        bustedHand.add(eight);
        bustedHand.add(four);
        bustedHand.add(ten);
        String msg = "Hand with " + eight.toString() + ", " + four.toString() 
                + " and " + ten.toString() 
                + " should be considered a busted hand";
        assert bustedHand.isBusted() : msg;
    }

    @Test
    public void testWinningHandIsNotBustedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = SERVER.giveCard(Rank.ACE);
        PlayingCard jack = SERVER.giveCard(Rank.JACK);
        hand.add(ace);
        hand.add(jack);
        String msg = ace.toString() + " and " + jack.toString() 
                + " should not be considered a busted hand";
        assert !hand.isBusted() : msg;
    }
    
    @Test
    public void testOpenHandIsNotBustedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = SERVER.getNextCard();
        hand.add(card);
        String msg = "Hand with just " + card.toString() 
                + " should not be considered a busted hand";
        assert !hand.isBusted() : msg;
    }
    
    @Test
    public void testWinningHandIsClosedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = SERVER.giveCard(Rank.ACE);
        PlayingCard jack = SERVER.giveCard(Rank.JACK);
        hand.add(ace);
        hand.add(jack);
        String msg = ace.toString() + " and " + jack.toString() 
                + " should be considered a closed hand";
        assert hand.isClosed() : msg;
    }
    
    @Test
    public void testOpenHandIsNotClosedHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card = SERVER.getNextCard();
        hand.add(card);
        String msg = "Hand with just " + card.toString()
                + " should not be considered a closed hand";
        assert !hand.isClosed() : msg;
    }
    
    @Test
    public void testBustedHandIsClosedHand() {
        Hand bustedHand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = SERVER.giveCard(Rank.EIGHT);
        PlayingCard four = SERVER.giveCard(Rank.FOUR);
        PlayingCard ten = SERVER.giveCard(Rank.TEN);
        bustedHand.add(eight);
        bustedHand.add(four);
        bustedHand.add(ten);
        String msg = "Hand with " + eight.toString() + ", " + four.toString() 
                + " and " + ten.toString() 
                + " should be considered a closed hand";
        assert bustedHand.isClosed() : msg;
    }

    @Test
    public void testWinningHandIsNotOpenHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = SERVER.giveCard(Rank.ACE);
        PlayingCard jack = SERVER.giveCard(Rank.JACK);
        hand.add(ace);
        hand.add(jack);
        String msg = ace.toString() + " and " + jack.toString() 
                + " should not be considered an open hand";
        assert !hand.isOpen() : msg;
    }
    
    /**
     * Test of isOpen method of class Hand.
     */
    @Test
    public void testIsOpen() {
        System.out.println("isOpen");
        Hand openHand = new Hand(DEFAULT_WAGER);
        PlayingCard card = SERVER.getNextCard();
        openHand.add(card);
        String msg = "Hand with just " + card.toString()
                + " should be considered an open hand";
        assert openHand.isOpen() : msg;
    }
    
    @Test
    public void testBustedHandIsNotOpenHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard eight = SERVER.giveCard(Rank.EIGHT);
        PlayingCard four = SERVER.giveCard(Rank.FOUR);
        PlayingCard ten = SERVER.giveCard(Rank.TEN);
        hand.add(eight);
        hand.add(four);
        hand.add(ten);
        String msg = "Hand with " + eight.toString() + ", " + four.toString() 
                + " and " + ten.toString() 
                + " should not be considered an open hand";
        assert !hand.isOpen() : msg;
    }
    
@org.junit.Ignore
    @Test
    public void testOpenClosedCorrespondence() {
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard card;
        String msg = "Open flag should be opposite of closed flag";
        while (hand.cardsValue() < 21) {
            card = SERVER.getNextCard();
            hand.add(card);
            assert hand.isOpen() == !hand.isClosed() : msg;
        }
    }
    
    @Test
    public void testNewHandShouldNotBeSettledHand() {
        Hand hand = new Hand(DEFAULT_WAGER);
        String msg = "New hand should not be a settled hand";
        assert !hand.isSettled() : msg;
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
        assert hand.isSettled() : msg;
    }

    /**
     * Test of the add procedure, of the Hand class.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        PlayingCard card = SERVER.getNextCard();
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
        PlayingCard card = SERVER.getNextCard();
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
    @org.junit.Ignore
    @Test
    public void testNoAddCardAfterWinning() {
        fail("REWRITE THIS TEST USING assertThrows()");
        Hand winningHand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = SERVER.giveCard(Rank.ACE);
        PlayingCard jack = SERVER.giveCard(Rank.JACK);
        winningHand.add(ace);
        winningHand.add(jack);
        PlayingCard someCard = SERVER.getNextCard();
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
    
    @org.junit.Ignore
    @Test
    public void testNoAddCardAfterBusting() {
        fail("REWRITE THIS TEST USING assertThrows()");
        Hand winningHand = new Hand(DEFAULT_WAGER);
        PlayingCard ten = SERVER.giveCard(Rank.TEN);
        PlayingCard jack = SERVER.giveCard(Rank.JACK);
        PlayingCard queen = SERVER.giveCard(Rank.QUEEN);
        winningHand.add(ten);
        winningHand.add(jack);
        winningHand.add(queen);
        PlayingCard someCard = SERVER.getNextCard();
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
    
    @org.junit.Ignore
    @Test
    public void testGetSettlement() {
        System.out.println("getSettlement");
        Hand hand = new Hand(DEFAULT_WAGER);
        PlayingCard ace = SERVER.giveCard(Rank.ACE);
        PlayingCard ten = SERVER.giveCard(Rank.TEN);
        hand.add(ace);
        hand.add(ten);
        hand.markSettled();
        Wager.Outcome expected = Wager.Outcome.NATURAL_BLACKJACK;
//        Wager.Outcome actual = hand.getSettlement();
fail("Finish writing test");
    }
    
    @Test
    public void testPrimaryConstructorRejectsNullWager() {
        String msg = "Constructor should reject null wager";
        Throwable t = assertThrows(() -> {
            Hand badInstance = new Hand(null);
            System.out.println(msg + ", not create instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(System
                            .identityHashCode(badInstance)));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testAuxConstructorRejectsNullWager() {
        PlayingCard firstCard = SERVER.getNextCard();
        String msg = "Constructor should reject " + firstCard.toString() 
                + " with null wager";
        Throwable t = assertThrows(() -> {
            Hand badInstance = new Hand(null, firstCard);
            System.out.println(msg + ", not create instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(System
                            .identityHashCode(badInstance)));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testAuxConstructorRejectsNullCard() {
        int cents = RANDOM.nextInt(65536) + 64;
        Currency currency = CurrencyChooser.chooseCurrency();
        CurrencyAmount amount = new CurrencyAmount(cents, currency);
        Wager wager = new Wager(amount);
        String msg = "Constructor should reject " + wager.toString() 
                + " with null card";
        Throwable t = assertThrows(() -> {
            Hand badInstance = new Hand(wager, null);
            System.out.println(msg + ", not create instance " 
                    + badInstance.getClass().getName() + "@" 
                    + Integer.toHexString(System
                            .identityHashCode(badInstance)));
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Card server has " + SERVER.countRemaining() 
                + " cards remaining");
        System.out.println("Extra server has " + EXTRA_SERVER.countRemaining() 
                + " cards remaining");
    }
    
}
