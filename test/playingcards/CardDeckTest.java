/*
 * Copyright (C) 2020 Alonso del Arte
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
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class CardDeckTest {

    /**
     * Test of hasNext method, of class CardDeck.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        CardDeck deck = new CardDeck();
        assert deck.hasNext() : "A new deck should have a card to deal";
    }

    /**
     * Test of getNextCard method, of class CardDeck.
     */
    @Test
    public void testGetNextCard() {
        System.out.println("getNextCard");
        CardDeck deck = new CardDeck();
        String assertionMessage = "First card from new deck should not be null";
        assertNotNull(assertionMessage, deck.getNextCard());
    }

    @Test
    public void testDeckHas52Cards() {
        CardDeck deck = new CardDeck();
        int counter = 0;
        while (counter < 52) {
            deck.getNextCard();
            counter++;
        }
        assert !deck.hasNext() : "Deck should be out of cards after dealing all 52";
    }

    @Test
    public void testDeckHas52DistinctCards() {
        CardDeck deck = new CardDeck();
        PlayingCard card = new PlayingCard(Rank.TWO, Suit.CLUBS);
        HashSet<PlayingCard> cards = new HashSet<>();
        int counter = 0;
        while (counter < 52) {
            card = deck.getNextCard();
            cards.add(card);
            counter++;
        }
        System.out.println("Last card dealt was " + card.toASCIIString() + "?");
        int expected = CardDeck.NUMBER_OF_CARDS_PER_DECK;
        int actual = cards.size();
        assertEquals(expected, actual);
    }

    /**
     * Test of sameOrderAs method, of class CardDeck.
     */
    @Test
    public void testSameOrderAs() {
        System.out.println("sameOrderAs");
        CardDeck deck01 = new CardDeck();
        CardDeck deck02 = new CardDeck();
        String assertionMessage = "Two new unshuffled decks should be in the same order";
        assert deck01.sameOrderAs(deck02) : assertionMessage;
        assert deck02.sameOrderAs(deck01) : assertionMessage;
    }

    /**
     * Another test of sameOrderAs method, of class CardDeck.
     */
    @Test
    public void testNotSameOrderAsAfterDiffDealCounts() {
        System.out.println("sameOrderAs");
        CardDeck deck01 = new CardDeck();
        deck01.getNextCard();
        CardDeck deck02 = new CardDeck();
        String assertionMessage = "Two decks should not be considered to be in the same order after different number of deals";
        assert !deck01.sameOrderAs(deck02) : assertionMessage;
        assert !deck02.sameOrderAs(deck01) : assertionMessage;
    }

    /**
     * Test of shuffle method, of class CardDeck.
     */
    @Test
    public void testShuffle() {
        System.out.println("shuffle");
        CardDeck unshuffled = new CardDeck();
        CardDeck shuffled = new CardDeck();
        shuffled.shuffle();
        PlayingCard fromShuffled, fromUnshuffled;
        boolean diffCardFound = false;
        while (shuffled.hasNext() && unshuffled.hasNext()) {
            fromShuffled = shuffled.getNextCard();
            fromUnshuffled = unshuffled.getNextCard();
            diffCardFound = diffCardFound || !fromShuffled.equals(fromUnshuffled);
        }
        assert diffCardFound : "Shuffled deck should have at least two cards in different order";
    }

    /**
     * Simultaneous test of shuffle and sameOrderAs. The result of this test is
     * irrelevant if any of the tests for shuffle or sameOrderAs fail.
     */
    @Test
    public void testDiffOrderAfterShuffle() {
        CardDeck unshuffled = new CardDeck();
        CardDeck shuffled = new CardDeck();
        shuffled.shuffle();
        String assertionMessage = "Shuffled deck should not be in same order as unshuffled deck";
        assert !shuffled.sameOrderAs(unshuffled) : assertionMessage;
        assert !unshuffled.sameOrderAs(shuffled) : assertionMessage;
    }

    @Test
    public void testShuffleOnlyCardsInDeck() {
        CardDeck deck = new CardDeck();
        ArrayList<PlayingCard> discardPile = new ArrayList<>();
        ArrayList<PlayingCard> stillInDeck = new ArrayList<>();
        int counter = 0;
        while (counter < 26) {
            discardPile.add(deck.getNextCard());
            counter++;
        }
        deck.shuffle();
        while (deck.hasNext()) {
            stillInDeck.add(deck.getNextCard());
        }
        ArrayList<PlayingCard> intersection = new ArrayList<>();
        discardPile.stream().filter((card) -> (stillInDeck.contains(card))).forEachOrdered((card) -> {
            intersection.add(card);
        });
        if (intersection.size() > 0) {
            System.out.println("Somehow the following cards were dealt twice: ");
            intersection.forEach((card) -> {
                System.out.println(card.toASCIIString());
            });
            String failMsg = "Card " + intersection.get(0).toString();
            if (intersection.size() > 1) {
                failMsg = failMsg + " and " + (intersection.size() - 1) 
                        + " other(s) ";
            }
            failMsg = failMsg + " should not have been dealt twice";
            fail(failMsg);
        }
    }

    /**
     * Test of provenance method, of class CardDeck.
     */
    @Test
    public void testProvenance() {
        System.out.println("provenance");
        CardDeck deck = new CardDeck();
        PlayingCard card = deck.getNextCard();
        String assertionMessage = "The card " + card.toString()
                + " dealt from this deck should be said to come from this deck";
        assert deck.provenance(card) : assertionMessage;
    }

    @Test
    public void testComesFromDiffDeck() {
        CardDeck deck01 = new CardDeck();
        CardDeck deck02 = new CardDeck();
        PlayingCard cardFromSecondDeck = deck02.getNextCard();
        String assertionMessage = "The card " + cardFromSecondDeck.toString()
                + " from Deck 2 should not be said to come from Deck 1";
        assert !deck01.provenance(cardFromSecondDeck) : assertionMessage;
    }

}
