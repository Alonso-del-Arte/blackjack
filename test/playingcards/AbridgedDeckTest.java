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
package playingcards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;
import static org.testframe.api.Asserters.assertZero;

/**
 * Tests of the AbridgedDeck class. The tests for decks with no omissions are 
 * presented first, but of course JUnit is by default free to run tests in 
 * whatever order is determined by the algorithm chosen by its creators.
 * @author Alonso del Arte
 */
public class AbridgedDeckTest {
    
    private static final Rank[] ALL_RANKS = Rank.values();
    
    private static final Suit[] ALL_SUITS = Suit.values();
    
    private static final Rank[] NO_RANKS = {};
    
    private static final Suit[] NO_SUITS = {};
    
    private static final CardServer SERVER = new CardServer(10);
    
    @Test
    public void testNewDeckNoRanksOmittedHasNext() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        assert deck.hasNext() : "A new deck should have cards to deal";
    }
    
    @Test
    public void testNewDeckNoSuitsOmittedHasNext() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        assert deck.hasNext() : "A new deck should have cards to deal";
    }
    
    @Test
    public void testNoRanksOmittedHasNext() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        String msgPartA = "After giving out ";
        int counter = 0;
        String msgPartB = " card(s), deck should still have next";
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            String msg = msgPartA + counter + msgPartB;
            assert deck.hasNext() : msg;
            deck.getNextCard();
            counter++;
        }
    }
    
    @Test
    public void testNoSuitsOmittedHasNext() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        String msgPartA = "After giving out ";
        int counter = 0;
        String msgPartB = " card(s), deck should still have next";
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            String msg = msgPartA + counter + msgPartB;
            assert deck.hasNext() : msg;
            deck.getNextCard();
            counter++;
        }
    }
    
    @Test
    public void testDepletedNoRanksOmittedDoesNotHaveNext() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            deck.getNextCard();
            counter++;
        }
        String msg = "Deck should be out of cards after dealing all 52";
        assert !deck.hasNext() : msg;
    }

    @Test
    public void testDepletedNoSuitsOmittedDoesNotHaveNext() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            deck.getNextCard();
            counter++;
        }
        String msg = "Deck should be out of cards after dealing all 52";
        assert !deck.hasNext() : msg;
    }

    @Test
    public void testNoRanksOmittedGetNextCardDoesNotGiveNullCard() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        String msg = "Card should not be null";
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            assert card != null : msg;
        }
    }

    @Test
    public void testNoSuitsOmittedGetNextCardDoesNotGiveNullCard() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        String msg = "Card should not be null";
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            assert card != null : msg;
        }
    }

    @Test
    public void testGetNextCardFromNoRanksOmittedDeck() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        Set<PlayingCard> cards = new HashSet<>();
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            PlayingCard card = deck.getNextCard();
            cards.add(card);
            counter++;
        }
        int actual = cards.size();
        assertEquals(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK, actual);
    }
    
    @Test
    public void testGetNextCardFromNoSuitsOmittedDeck() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        Set<PlayingCard> cards = new HashSet<>();
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            PlayingCard card = deck.getNextCard();
            cards.add(card);
            counter++;
        }
        int actual = cards.size();
        assertEquals(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK, actual);
    }
    
    @Test
    public void testNoDealAfterLastCardNoRanksOmitted() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            deck.getNextCard();
            counter++;
        }
        String msg = "Asking for card after last card should cause exception";
        RanOutOfCardsException roce = assertThrows(() -> {
            PlayingCard card = deck.getNextCard();
            System.out.println(msg + ", not given " + card.toString());
        }, RanOutOfCardsException.class, msg);
        Rank rank = roce.getRank();
        Suit suit = roce.getSuit();
        assert rank == null : "Rank after general running out should be null";
        assert suit == null : "Suit after general running out should be null";
        System.out.println("\"" + roce.getMessage() + "\"");
    }
    
    @Test
    public void testNoDealAfterLastCardNoSuitsOmitted() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            deck.getNextCard();
            counter++;
        }
        String msg = "Asking for card after last card should cause exception";
        RanOutOfCardsException roce = assertThrows(() -> {
            PlayingCard card = deck.getNextCard();
            System.out.println(msg + ", not given " + card.toString());
        }, RanOutOfCardsException.class, msg);
        Rank rank = roce.getRank();
        Suit suit = roce.getSuit();
        assert rank == null : "Rank after general running out should be null";
        assert suit == null : "Suit after general running out should be null";
        System.out.println("\"" + roce.getMessage() + "\"");
    }
    
    @Test
    public void testCountRemainingNoRanksOmitted() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        deck.shuffle();
        String msgPart = " cards left before giving out ";
        for (int expected = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK; 
                expected > 0; expected--) {
            int actual = deck.countRemaining();
            PlayingCard card = deck.getNextCard();
            String cardName = card.toString();
            String message = expected + msgPart + cardName;
            assertEquals(message, expected, actual);
        }
        String msg = "Deck should be depleted after giving out all cards";
        assertZero(deck.countRemaining(), msg);
    }
    
    @Test
    public void testCountRemainingNoSuitsOmitted() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        deck.shuffle();
        String msgPart = " cards left before giving out ";
        for (int expected = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK; 
                expected > 0; expected--) {
            int actual = deck.countRemaining();
            PlayingCard card = deck.getNextCard();
            String cardName = card.toString();
            String message = expected + msgPart + cardName;
            assertEquals(message, expected, actual);
        }
        String msg = "Deck should be depleted after giving out all cards";
        assertZero(deck.countRemaining(), msg);
    }
    
    @Test
    public void testShuffleNoRanksOmitted() {
        CardDeck unshuffled = new AbridgedDeck(NO_RANKS);
        CardDeck shuffled = new AbridgedDeck(NO_RANKS);
        shuffled.shuffle();
        PlayingCard fromShuffled, fromUnshuffled;
        boolean diffCardFound = false;
        while (shuffled.hasNext() && unshuffled.hasNext()) {
            fromShuffled = shuffled.getNextCard();
            fromUnshuffled = unshuffled.getNextCard();
            diffCardFound = diffCardFound 
                    || !fromShuffled.equals(fromUnshuffled);
        }
        String msg = "Shuffled deck should have cards in a different order";
        assert diffCardFound : msg;
    }
    
    @Test
    public void testShuffleNoSuitsOmitted() {
        CardDeck unshuffled = new AbridgedDeck(NO_SUITS);
        CardDeck shuffled = new AbridgedDeck(NO_SUITS);
        shuffled.shuffle();
        PlayingCard fromShuffled, fromUnshuffled;
        boolean diffCardFound = false;
        while (shuffled.hasNext() && unshuffled.hasNext()) {
            fromShuffled = shuffled.getNextCard();
            fromUnshuffled = unshuffled.getNextCard();
            diffCardFound = diffCardFound 
                    || !fromShuffled.equals(fromUnshuffled);
        }
        String msg = "Shuffled deck should have cards in a different order";
        assert diffCardFound : msg;
    }
    
    @Test
    public void testShuffleOnlyCardsInDeckNoRanksOmitted() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        List<PlayingCard> discardPile = new ArrayList<>();
        List<PlayingCard> stillInDeck = new ArrayList<>();
        int counter = 0;
        int halfCount = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK / 2;
        while (counter < halfCount) {
            discardPile.add(deck.getNextCard());
            counter++;
        }
        deck.shuffle();
        while (deck.hasNext()) {
            stillInDeck.add(deck.getNextCard());
        }
        List<PlayingCard> intersection = new ArrayList<>();
        discardPile.stream().filter((card) -> (stillInDeck.contains(card)))
                .forEachOrdered((card) -> {
            intersection.add(card);
        });
        String msg = "The following cards were dealt twice: " 
                + intersection.toString();
        assert intersection.isEmpty() : msg;
    }

    @Test
    public void testShuffleOnlyCardsInDeckNoSuitsOmitted() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        List<PlayingCard> discardPile = new ArrayList<>();
        List<PlayingCard> stillInDeck = new ArrayList<>();
        int counter = 0;
        int halfCount = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK / 2;
        while (counter < halfCount) {
            discardPile.add(deck.getNextCard());
            counter++;
        }
        deck.shuffle();
        while (deck.hasNext()) {
            stillInDeck.add(deck.getNextCard());
        }
        List<PlayingCard> intersection = new ArrayList<>();
        discardPile.stream().filter((card) -> (stillInDeck.contains(card)))
                .forEachOrdered((card) -> {
            intersection.add(card);
        });
        String msg = "The following cards were dealt twice: " 
                + intersection.toString();
        assert intersection.isEmpty() : msg;
    }

    @Test
    public void testNoShuffleForJustOneCardNoRanksOmitted() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        int counter = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - 1;
        while (counter < max) {
            deck.getNextCard();
            counter++;
        }
        String msg 
                = "Trying to shuffle deck with one card should cause exception";
        Throwable t = assertThrows(() -> {
            deck.shuffle();
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testNoShuffleForJustOneCardNoSuitsOmitted() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        int counter = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - 1;
        while (counter < max) {
            deck.getNextCard();
            counter++;
        }
        String msg 
                = "Trying to shuffle deck with one card should cause exception";
        Throwable t = assertThrows(() -> {
            deck.shuffle();
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testNoShuffleDepletedDeckNoRanksOmitted() {
        CardDeck deck = new AbridgedDeck(NO_RANKS);
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            deck.getNextCard();
            counter++;
        }
        String msg 
                = "Trying to shuffle deck with no cards should cause exception";
        Throwable t = assertThrows(() -> {
            deck.shuffle();
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testNoShuffleDepletedDeckNoSuitsOmitted() {
        CardDeck deck = new AbridgedDeck(NO_SUITS);
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            deck.getNextCard();
            counter++;
        }
        String msg 
                = "Trying to shuffle deck with no cards should cause exception";
        Throwable t = assertThrows(() -> {
            deck.shuffle();
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testOmitSingleRank() {
        int maxCount = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ALL_SUITS.length;
        for (Rank omittedRank : ALL_RANKS) {
            int currCount = 0;
            AbridgedDeck deck = new AbridgedDeck(omittedRank);
            deck.shuffle();
            String msgPart = " should not be " + omittedRank.getWord();
            while (currCount < maxCount) {
                PlayingCard card = deck.getNextCard();
                String msg = card.toString() + msgPart;
                assert card.getRank() != omittedRank : msg;
                currCount++;
            }
        }
    }
    
    @Test
    public void testOmitSingleSuit() {
        int maxCount = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ALL_RANKS.length;
        for (Suit omittedSuit : ALL_SUITS) {
            int currCount = 0;
            AbridgedDeck deck = new AbridgedDeck(omittedSuit);
            deck.shuffle();
            String msgPart = " should not be " + omittedSuit.getWord();
            while (currCount < maxCount) {
                PlayingCard card = deck.getNextCard();
                String msg = card.toString() + msgPart;
                assert card.getSuit()!= omittedSuit : msg;
                currCount++;
            }
        }
    }
    
    private static CardDeck getChoosingDeck() {
        CardDeck deck = new CardDeck();
        deck.shuffle();
        return deck;
    }
    
    private static Rank[] chooseRanksToOmit() {
        CardDeck choosingDeck = getChoosingDeck();
        int capacity = (choosingDeck.getNextCard().integerValue() % 5) + 3;
        Set<Rank> rankSet = new HashSet<>(capacity);
        while (rankSet.size() < capacity) {
            rankSet.add(choosingDeck.getNextCard().getRank());
        }
        Rank[] ranks = new Rank[capacity];
        Arrays.sort(rankSet.toArray(ranks));
        return ranks;
    }
    
    @Test
    public void testOmitMultipleRanks() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck abridgedDeck = new AbridgedDeck(ranks);
        int count = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ranks.length * ALL_SUITS.length;
        String msgPartA = "After omitting " + Arrays.toString(ranks) 
                + ", given card ";
        String msgPartB = " should not be of an omitted rank";
        while (count < max) {
            PlayingCard card = abridgedDeck.getNextCard();
            count++;
            String msg = msgPartA + card.toString() + msgPartB;
            assert Arrays.binarySearch(ranks, card.getRank()) < 0 : msg;
        }
    }
    
    private static Suit[] chooseTwoSuitsToOmit() {
        CardDeck choosingDeck = getChoosingDeck();
        Suit suitA = choosingDeck.getNextCard().getSuit();
        Suit suitB = suitA;
        while (suitA == suitB) {
            suitB = choosingDeck.getNextCard().getSuit();
        }
        Suit[] suits = {suitA, suitB};
        Arrays.sort(suits);
        return suits;
    }
    
    @Test
    public void testOmitTwoSuits() {
        Suit[] suitsToOmit = chooseTwoSuitsToOmit();
        CardDeck abridgedDeck = new AbridgedDeck(suitsToOmit);
        int count = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 2 * ALL_RANKS.length;
        String msgPartA = "After omitting " + Arrays.toString(suitsToOmit) 
                + ", given card ";
        String msgPartB = " should not be of an omitted suit";
        while (count < max) {
            PlayingCard card = abridgedDeck.getNextCard();
            count++;
            String msg = msgPartA + card.toString() + msgPartB;
            assert Arrays.binarySearch(suitsToOmit, card.getSuit()) < 0 : msg;
        }
    }
    
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instance = new AbridgedDeck(ranks);
        int count = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ranks.length * ALL_SUITS.length;
        String msgPartA = "Having dealt out ";
        String msgPartB = " card(s), deck should have next";
        while (count < max) {
            String msg = msgPartA + count + msgPartB;
            assert instance.hasNext() : msg;
            instance.getNextCard();
            count++;
        }
    }
    
    @Test
    public void testDoesNotHaveNext() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instance = new AbridgedDeck(ranks);
        int count = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ranks.length * ALL_SUITS.length;
        while (count < max) {
            instance.getNextCard();
            count++;
        }
        String msg = "Having dealt out " + count + " cards from deck with " 
                + Arrays.toString(ranks) 
                + " omitted, deck should not have next";
        assert !instance.hasNext() : msg;
    }
    
    @Test
    public void testHasNextSingleSuitOmitted() {
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - ALL_RANKS.length;
        String msgPartA = "Having dealt out ";
        String msgPartB = " card(s), deck should have next";
        for (Suit omittedSuit : ALL_SUITS) {
            int count = 0;
            AbridgedDeck deck = new AbridgedDeck(omittedSuit);
            deck.shuffle();
            while (count < max) {
                String msg = msgPartA + count + msgPartB;
                assert deck.hasNext() : msg;
                deck.getNextCard();
                count++;
            }
        }
    }
    
    @Test
    public void testHasNextTwoSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instance = new AbridgedDeck(suits);
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 2 * ALL_RANKS.length;
        String msgPartA = "Having dealt out ";
        String msgPartB = " card(s), deck should have next";
        int count = 0;
        instance.shuffle();
        while (count < max) {
            String msg = msgPartA + count + msgPartB;
            assert instance.hasNext() : msg;
            instance.getNextCard();
            count++;
        }
    }
    
    @Test
    public void testDoesNotHaveNextOneSuitOmitted() {
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - ALL_RANKS.length;
        for (Suit omittedSuit : ALL_SUITS) {
            int count = 0;
            AbridgedDeck deck = new AbridgedDeck(omittedSuit);
            deck.shuffle();
            while (count < max) {
                deck.getNextCard();
                count++;
            }
            String msg = "After dealing " + count + " cards, deck with " 
                    + omittedSuit.name() 
                    + " omitted should not have another card left to give";
            assert !deck.hasNext() : msg;
        }
    }
    
    @Test
    public void testDoesNotHaveNextTwoSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instance = new AbridgedDeck(suits);
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 2 * ALL_RANKS.length;
        int count = 0;
        instance.shuffle();
        while (count < max) {
            instance.getNextCard();
            count++;
        }
        String msg = "After dealing " + count + " cards, deck with " 
                + Arrays.toString(suits) 
                + " omitted should not have another card left to give";
        assert !instance.hasNext() : msg;
    }
    
    @Test
    public void testGetNextCard() {
        System.out.println("getNextCard");
        Rank[] ranks = chooseRanksToOmit();
        Arrays.sort(ranks);
        CardDeck instance = new AbridgedDeck(ranks);
        instance.shuffle();
        int count = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ranks.length * ALL_SUITS.length;
        String msgPart = " should not be of omitted rank " 
                + Arrays.toString(ranks);
        while (count < max) {
            PlayingCard card = instance.getNextCard();
            String msg = card.toString() + msgPart;
            Rank key = card.getRank();
            assert Arrays.binarySearch(ranks, key) < 0 : msg;
            count++;
        }
    }
    
    @Test
    public void testNoGetNextCardAfterShouldHaveRunOut() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instance = new AbridgedDeck(ranks);
        int count = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ranks.length * ALL_SUITS.length;
        while (count < max) {
            instance.getNextCard();
            count++;
        }
        String msg = "Having dealt out " + count + " cards from deck with " 
                + Arrays.toString(ranks) 
                + " omitted, deck should not have next";
        Throwable t = assertThrows(() -> {
            PlayingCard card = instance.getNextCard();
            System.out.println(msg + ", should not have given out " 
                    + card.toString());
        }, RanOutOfCardsException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testGetNextCardOneSuitOmitted() {
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - ALL_RANKS.length;
        for (Suit suit : ALL_SUITS) {
            Suit[] suits = {suit};
            CardDeck instance = new AbridgedDeck(suits);
            instance.shuffle();
            int count = 0;
            String msgPart = " should not be of omitted suit " + suit.getWord();
            while (count < max) {
                PlayingCard card = instance.getNextCard();
                Suit cardSuit = card.getSuit();
                String msg = card.toString() + msgPart;
                assert !suit.equals(cardSuit) : msg;
                count++;
            }
        }
    }
    
    @Test
    public void testNoGetNextCardAfterShouldHaveRunOutOneSuitOmitted() {
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - ALL_RANKS.length;
        for (Suit suit : ALL_SUITS) {
            Suit[] suits = {suit};
            CardDeck instance = new AbridgedDeck(suits);
            instance.shuffle();
            int count = 0;
            while (count < max) {
                instance.getNextCard();
                count++;
            }
            String msg = "Having dealt out " + count + " cards from deck with " 
                    + suit.getWord() + " omitted, deck should not have next";
            Throwable t = assertThrows(() -> {
                PlayingCard card = instance.getNextCard();
                System.out.println(msg + ", should not have given out " 
                        + card.toString());
            }, RanOutOfCardsException.class, msg);
            String excMsg = t.getMessage();
            assert excMsg != null : "Exception message should not be null";
            assert !excMsg.isBlank() : "Exception message should not be blank";
            System.out.println("\"" + excMsg + "\"");
        }
    }
    
    @Test
    public void testGetNextCardTwoSuitsOmitted() {
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 2 * ALL_RANKS.length;
        Suit[] suits = chooseTwoSuitsToOmit();
        Arrays.sort(suits);
        CardDeck instance = new AbridgedDeck(suits);
        instance.shuffle();
        int count = 0;
        String msgPart = " should not be one of omitted suits " 
                + Arrays.toString(suits);
        while (count < max) {
            PlayingCard card = instance.getNextCard();
            Suit key = card.getSuit();
            String msg = card.toString() + msgPart;
            assert Arrays.binarySearch(suits, key) < 0 : msg;
            count++;
        }
    }
    
    @Test
    public void testNoGetNextCardAfterShouldHaveRunOutTwoSuitsOmitted() {
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 2 * ALL_RANKS.length;
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instance = new AbridgedDeck(suits);
        instance.shuffle();
        int count = 0;
        while (count < max) {
            instance.getNextCard();
            count++;
        }
        String msg = "Having dealt out " + count + " cards from deck with " 
                + Arrays.toString(suits) 
                + " omitted, deck should not have next";
        Throwable t = assertThrows(() -> {
            PlayingCard card = instance.getNextCard();
            System.out.println(msg + ", should not have given out " 
                    + card.toString());
        }, RanOutOfCardsException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testCountRemaining() {
        System.out.println("countRemaining");
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instance = new AbridgedDeck(ranks);
        String msgPartA = "Having dealt out ";
        String msgPartB = " card(s) from deck with " + Arrays.toString(ranks) 
                + " omitted, deck should have ";
        String msgPartC = " card(s) left";
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ALL_SUITS.length * ranks.length;
        int dealCount = 0;
        while (dealCount < max) {
            int expected = max - dealCount;
            int actual = instance.countRemaining();
            String message = msgPartA + dealCount + msgPartB + expected 
                    + msgPartC;
            assertEquals(message, expected, actual);
            instance.getNextCard();
            dealCount++;
        }
        assertZero(instance.countRemaining(), "Deck should be out of cards");
    }
    
    @Test
    public void testCountRemainingOneSuitOmitted() {
        for (Suit suit : ALL_SUITS) {
            Suit[] suits = {suit};
            CardDeck instance = new AbridgedDeck(suits);
            String msgPartA = "Having dealt out ";
            String msgPartB = " card(s) from deck with " 
                    + Arrays.toString(suits) + " omitted, deck should have ";
            String msgPartC = " card(s) left";
            int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                    - ALL_RANKS.length;
            int dealCount = 0;
            while (dealCount < max) {
                int expected = max - dealCount;
                int actual = instance.countRemaining();
                String message = msgPartA + dealCount + msgPartB + expected 
                        + msgPartC;
                assertEquals(message, expected, actual);
                instance.getNextCard();
                dealCount++;
            }
            assertZero(instance.countRemaining(), 
                    "Deck should be out of cards");
        }
    }
    
    @Test
    public void testCountRemainingTwoSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instance = new AbridgedDeck(suits);
        String msgPartA = "Having dealt out ";
        String msgPartB = " card(s) from deck with " + Arrays.toString(suits) 
                + " omitted, deck should have ";
        String msgPartC = " card(s) left";
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 2 * ALL_RANKS.length;
        int dealCount = 0;
        while (dealCount < max) {
            int expected = max - dealCount;
            int actual = instance.countRemaining();
            String message = msgPartA + dealCount + msgPartB + expected 
                    + msgPartC;
            assertEquals(message, expected, actual);
            instance.getNextCard();
            dealCount++;
        }
        assertZero(instance.countRemaining(), "Deck should be out of cards");
    }
    
    @Test
    public void testProvenanceNoRanksOmitted() {
        CardDeck instance = new AbridgedDeck(NO_RANKS);
        instance.shuffle();
        int dealCount = 0;
        String msgPart = " dealt from " + instance.toString() 
                + " should return true for provenance";
        while (dealCount < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            PlayingCard card = instance.getNextCard();
            String msg = card.toString() + msgPart;
            assert instance.provenance(card) : msg;
            dealCount++;
        }
    }
    
    @Test
    public void testProvenanceNoSuitsOmitted() {
        CardDeck instance = new AbridgedDeck(NO_SUITS);
        instance.shuffle();
        int dealCount = 0;
        String msgPart = " dealt from " + instance.toString() 
                + " should return true for provenance";
        while (dealCount < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            PlayingCard card = instance.getNextCard();
            String msg = card.toString() + msgPart;
            assert instance.provenance(card) : msg;
            dealCount++;
        }
    }
    
    @Test
    public void testProvenance() {
        System.out.println("provenance");
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instance = new AbridgedDeck(ranks);
        instance.shuffle();
        int dealCount = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - ALL_SUITS.length * ranks.length;
        String msgPart = " dealt from " + instance.toString() 
                + " with " + Arrays.toString(ranks) 
                + " omitted should return true for provenance";
        while (dealCount < max) {
            PlayingCard card = instance.getNextCard();
            String msg = card.toString() + msgPart;
            assert instance.provenance(card) : msg;
            dealCount++;
        }
    }
    
    @Test
    public void testProvenanceOneSuitOmitted() {
        for (Suit suit : ALL_SUITS) {
            Suit[] suits = {suit};
            CardDeck instance = new AbridgedDeck(suits);
            instance.shuffle();
            int dealCount = 0;
            int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                    - ALL_RANKS.length;
            String msgPart = " dealt from " + instance.toString() + " with " 
                    + Arrays.toString(suits) 
                    + " omitted should return true for provenance";
            while (dealCount < max) {
                PlayingCard card = instance.getNextCard();
                String msg = card.toString() + msgPart;
                assert instance.provenance(card) : msg;
                dealCount++;
            }
        }
    }
    
    @Test
    public void testProvenanceTwoSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instance = new AbridgedDeck(suits);
        instance.shuffle();
        int dealCount = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 2 * ALL_RANKS.length;
        String msgPart = " dealt from " + instance.toString() + " with " 
                + Arrays.toString(suits) 
                + " omitted should return true for provenance";
        while (dealCount < max) {
            PlayingCard card = instance.getNextCard();
            String msg = card.toString() + msgPart;
            assert instance.provenance(card) : msg;
            dealCount++;
        }
    }
    
    @Test
    public void testNoProvenanceIfOmittedRank() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instance = new AbridgedDeck(ranks);
        String msgPart = " should not have provenance from deck with " 
                + Arrays.toString(ranks) + " omitted";
        for (Rank rank : ranks) {
            PlayingCard card = SERVER.giveCard(rank);
            String msg = card.toString() + msgPart;
            assert !instance.provenance(card) : msg;
        }
    }
    
    @Test
    public void testNoProvenanceIfOmittedSuit() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instance = new AbridgedDeck(suits);
        String msgPart = " should not have provenance from deck with " 
                + Arrays.toString(suits) + " omitted";
        for (Suit suit : suits) {
            PlayingCard card = SERVER.giveCard(suit);
            String msg = card.toString() + msgPart;
            assert !instance.provenance(card) : msg;
        }
    }
    
    @Test
    public void testNoProvenancePresentRankFromDiffDeck() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instance = new AbridgedDeck(ranks);
        instance.shuffle();
        PlayingCard provenanceCard = instance.getNextCard();
        Rank rank = provenanceCard.getRank();
        PlayingCard card = SERVER.giveCard(rank);
        String msg = card.toString() + " from " + SERVER.toString() 
                + " should not have provenance from " + instance.toString() 
                + " with " + Arrays.toString(ranks) + " omitted";
        assert !instance.provenance(card) : msg;
    }
    
    @Test
    public void testNoProvenancePresentSuitFromDiffDeck() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instance = new AbridgedDeck(suits);
        instance.shuffle();
        PlayingCard provenanceCard = instance.getNextCard();
        Suit suit = provenanceCard.getSuit();
        PlayingCard card = SERVER.giveCard(suit);
        String msg = card.toString() + " from " + SERVER.toString() 
                + " should not have provenance from " + instance.toString() 
                + " with " + Arrays.toString(suits) + " omitted";
        assert !instance.provenance(card) : msg;
    }
    
    @Test
    public void testShuffle() {
        System.out.println("shuffle");
        Rank[] ranks = chooseRanksToOmit();
        CardDeck unshuffled = new AbridgedDeck(ranks);
        CardDeck shuffled = new AbridgedDeck(ranks);
        shuffled.shuffle();
        PlayingCard fromShuffled, fromUnshuffled;
        boolean diffCardFound = false;
        while (shuffled.hasNext() && unshuffled.hasNext()) {
            fromShuffled = shuffled.getNextCard();
            fromUnshuffled = unshuffled.getNextCard();
            diffCardFound = diffCardFound 
                    || !fromShuffled.equals(fromUnshuffled);
        }
        String msg = "Shuffled deck with " + Arrays.toString(ranks)
                + " omitted should have cards in a different order";
        assert diffCardFound : msg;
    }
    
    @Test
    public void testShuffleSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck unshuffled = new AbridgedDeck(suits);
        CardDeck shuffled = new AbridgedDeck(suits);
        shuffled.shuffle();
        PlayingCard fromShuffled, fromUnshuffled;
        boolean diffCardFound = false;
        while (shuffled.hasNext() && unshuffled.hasNext()) {
            fromShuffled = shuffled.getNextCard();
            fromUnshuffled = unshuffled.getNextCard();
            diffCardFound = diffCardFound 
                    || !fromShuffled.equals(fromUnshuffled);
        }
        String msg = "Shuffled deck with " + Arrays.toString(suits)
                + " omitted should have cards in a different order";
        assert diffCardFound : msg;
    }
    
    @Test
    public void testShuffleOnlyCardsInDeck() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck deck = new AbridgedDeck(ranks);
        List<PlayingCard> discardPile = new ArrayList<>();
        List<PlayingCard> stillInDeck = new ArrayList<>();
        int counter = 0;
        int halfCount = (CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 4 * ranks.length) / 2;
        while (counter < halfCount) {
            discardPile.add(deck.getNextCard());
            counter++;
        }
        deck.shuffle();
        while (deck.hasNext()) {
            stillInDeck.add(deck.getNextCard());
        }
        List<PlayingCard> intersection = new ArrayList<>();
        discardPile.stream().filter((card) -> (stillInDeck.contains(card)))
                .forEachOrdered((card) -> {
            intersection.add(card);
        });
        String msg = "The following cards from deck with " 
                + Arrays.toString(ranks) + " omitted were dealt twice: " 
                + intersection.toString();
        assert intersection.isEmpty() : msg;
    }

    @Test
    public void testNoShuffleForJustOneCard() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck deck = new AbridgedDeck(ranks);
        int counter = 0;
        int max = (CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - 4 * ranks.length) 
                - 1;
        while (counter < max) {
            deck.getNextCard();
            counter++;
        }
        String msg 
                = "Trying to shuffle deck with one card should cause exception";
        Throwable t = assertThrows(() -> {
            deck.shuffle();
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testNoShuffleForDepletedDeck() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck deck = new AbridgedDeck(ranks);
        int counter = 0;
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - 4 * ranks.length;
        while (counter < max) {
            deck.getNextCard();
            counter++;
        }
        String msg 
                = "Trying to shuffle deck with no cards should cause exception";
        Throwable t = assertThrows(() -> {
            deck.shuffle();
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testShuffleOnlyCardsInDeckTwoSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck deck = new AbridgedDeck(suits);
        List<PlayingCard> discardPile = new ArrayList<>();
        List<PlayingCard> stillInDeck = new ArrayList<>();
        int counter = 0;
        int halfCount = 13;
        while (counter < halfCount) {
            discardPile.add(deck.getNextCard());
            counter++;
        }
        deck.shuffle();
        while (deck.hasNext()) {
            stillInDeck.add(deck.getNextCard());
        }
        List<PlayingCard> intersection = new ArrayList<>();
        discardPile.stream().filter((card) -> (stillInDeck.contains(card)))
                .forEachOrdered((card) -> {
            intersection.add(card);
        });
        String msg = "The following cards from deck with " 
                + Arrays.toString(suits) + " omitted were dealt twice: " 
                + intersection.toString();
        assert intersection.isEmpty() : msg;
    }

    @Test
    public void testNoShuffleForJustOneCardTwoSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck deck = new AbridgedDeck(suits);
        int counter = 0;
        int max = 25;
        while (counter < max) {
            deck.getNextCard();
            counter++;
        }
        String msg 
                = "Trying to shuffle deck with one card should cause exception";
        Throwable t = assertThrows(() -> {
            deck.shuffle();
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }

    @Test
    public void testNoShuffleForDepletedDeckTwoSuitsOmitted() {
        Suit[] ranks = chooseTwoSuitsToOmit();
        CardDeck deck = new AbridgedDeck(ranks);
        int counter = 0;
        int max = 26;
        while (counter < max) {
            deck.getNextCard();
            counter++;
        }
        String msg 
                = "Trying to shuffle deck with no cards should cause exception";
        Throwable t = assertThrows(() -> {
            deck.shuffle();
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testSameOrderAsInitiallyNoRanksOmittedSameAsBaseClass() {
        CardDeck instance = new AbridgedDeck(NO_RANKS);
        CardDeck other = new CardDeck();
        String msg = "Unshuffled deck no omissions should be in same order";
        assert instance.sameOrderAs(other) : msg;
    }

    @Test
    public void testSameOrderAsInitiallyNoSuitsOmittedSameAsBaseClass() {
        CardDeck instance = new AbridgedDeck(NO_SUITS);
        CardDeck other = new CardDeck();
        String msg = "Unshuffled deck no omissions should be in same order";
        assert instance.sameOrderAs(other) : msg;
    }
    
    @Test
    public void testSameOrderAsInitially() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instanceA = new AbridgedDeck(ranks);
        CardDeck instanceB = new AbridgedDeck(ranks);
        String msg = "Fresh unshuffled decks both with " 
                + Arrays.toString(ranks) 
                + " omitted should be in the same order";
        assert instanceA.sameOrderAs(instanceB) : msg;
    }

    @Test
    public void testSameOrderAsInitiallyTwoSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instanceA = new AbridgedDeck(suits);
        CardDeck instanceB = new AbridgedDeck(suits);
        String msg = "Fresh unshuffled decks both with " 
                + Arrays.toString(suits) 
                + " omitted should be in the same order";
        assert instanceA.sameOrderAs(instanceB) : msg;
    }

    @Test
    public void testNotSameOrderInitiallyAsDeckWithNoOmissions() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck abridgedDeck = new AbridgedDeck(ranks);
        CardDeck other = new CardDeck();
        String msg = "Fresh unshuffled deck with " + Arrays.toString(ranks) 
                + " omitted should not be in same order as unabridged deck";
        assert !abridgedDeck.sameOrderAs(other) : msg;
    }

    @Test
    public void testNotSameOrderInitiallyTwoSuitsOmitAsDeckWithNoOmissions() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck abridgedDeck = new AbridgedDeck(suits);
        CardDeck other = new CardDeck();
        String msg = "Fresh unshuffled deck with " + Arrays.toString(suits) 
                + " omitted should not be in same order as unabridged deck";
        assert !abridgedDeck.sameOrderAs(other) : msg;
    }
    
    private static boolean isOfAnOmittedRank(PlayingCard card, Rank[] ranks) {
        boolean match = false;
        for (Rank rank : ranks) {
            if (card.isOf(rank)) {
                match = true;
                break;
            }
        }
        return match;
    }
    
    @Test
    public void testNotSameOrderAbridgedShuffledAsUnshuffledUnabridged() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck instance = new AbridgedDeck(ranks);
        instance.shuffle();
        int target = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 4 * ranks.length;
        CardDeck other = new CardDeck();
        other.shuffle();
        Set<PlayingCard> selectedCards = new HashSet<>();
        while (other.countRemaining() > target) {
            PlayingCard card = other.getNextCard();
            if (isOfAnOmittedRank(card, ranks)) {
                selectedCards.add(card);
            }
        }
        String msg = "Deck with " + Arrays.toString(ranks) 
                + " omitted should not be in same order as deck that dealt " 
                + selectedCards.toString() + " even though both decks have " 
                + target + " cards remaining";
        assert !instance.sameOrderAs(other) : msg;
    }

    private static boolean isOfAnOmittedSuit(PlayingCard card, Suit[] suits) {
        boolean match = false;
        for (Suit suit : suits) {
            if (card.isOf(suit)) {
                match = true;
                break;
            }
        }
        return match;
    }
    
    @Test
    public void testNotSameOrderSuitsOmittedShuffledAsUnshuffledUnabridged() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck instance = new AbridgedDeck(suits);
        instance.shuffle();
        int target = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - 13 * suits.length;
        CardDeck other = new CardDeck();
        other.shuffle();
        Set<PlayingCard> selectedCards = new HashSet<>();
        while (other.countRemaining() > target) {
            PlayingCard card = other.getNextCard();
            if (isOfAnOmittedSuit(card, suits)) {
                selectedCards.add(card);
            }
        }
        String msg = "Deck with " + Arrays.toString(suits) 
                + " omitted should not be in same order as deck that dealt " 
                + selectedCards.toString() + " even though both decks have " 
                + target + " cards remaining";
        assert !instance.sameOrderAs(other) : msg;
    }
    
    @Test
    public void testNotSameOrderAsDiffDealCounts() {
        Rank[] ranks = chooseRanksToOmit();
        CardDeck deckA = new AbridgedDeck(ranks);
        CardDeck deckB = new AbridgedDeck(ranks);
        int max = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - 4 * ranks.length;
        deckA.getNextCard();
        int count = 1;
        while (count < max) {
            deckA.getNextCard();
            deckB.getNextCard();
            count++;
            String msg = "Deck that has dealt " + count 
                    + " card(s) should not be same order as deck dealt " 
                    + (count - 1) + " card(s)";
            assert !deckA.sameOrderAs(deckB) : msg;
        }
    }

    @Test
    public void testNotSameOrderAsDiffDealCountsSuitsOmitted() {
        Suit[] suits = chooseTwoSuitsToOmit();
        CardDeck deckA = new AbridgedDeck(suits);
        CardDeck deckB = new AbridgedDeck(suits);
        int max = 26;
        deckA.getNextCard();
        int count = 1;
        while (count < max) {
            deckA.getNextCard();
            deckB.getNextCard();
            count++;
            String msg = "Deck that has dealt " + count 
                    + " card(s) should not be same order as deck dealt " 
                    + (count - 1) + " card(s)";
            assert !deckA.sameOrderAs(deckB) : msg;
        }
    }

    @Test
    public void testConstructorRejectsNullRankArray() {
        Rank[] ranks = null;
        String msg = "Constructor should reject null array";
        Throwable t = assertThrows(() -> {
            CardDeck badInstance = new AbridgedDeck(ranks);
            System.out.println(msg + ", not created instance " 
                    + badInstance.toString());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsNullSuitArray() {
        Suit[] suits = null;
        String msg = "Constructor should reject null array";
        Throwable t = assertThrows(() -> {
            CardDeck badInstance = new AbridgedDeck(suits);
            System.out.println(msg + ", not created instance " 
                    + badInstance.toString());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
}
