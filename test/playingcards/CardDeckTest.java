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
     * The deck is expected to have thirteen cards for each of four suits, and
     * no Jokers.
     */
    public static final int EXPECTED_NUMBER_OF_CARDS_IN_DECK = 52;

    @Test
    public void testNumberOfCards() {
        assertEquals(EXPECTED_NUMBER_OF_CARDS_IN_DECK, CardDeck.NUMBER_OF_CARDS_PER_DECK);
    }

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
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
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
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
            card = deck.getNextCard();
            cards.add(card);
            counter++;
        }
        System.out.println("Last card dealt was " + card.toASCIIString() + "?");
        int actual = cards.size();
        assertEquals(EXPECTED_NUMBER_OF_CARDS_IN_DECK, actual);
    }
    
    /**
     * Another test of getNextCard method, of class CardDeck. Trying to get 
     * another card after the last card has been dealt should cause 
     * <code>IllegalStateException</code>. It should not cause 
     * <code>IndexOutOfBoundsException</code>, which would imply that the caller 
     * was expected to keep track of the index. The correct way to prevent 
     * <code>getNextCard()</code> from causing an exception is by checking 
     * <code>hasNext()</code>.
     */
    @Test
    public void testNoDealAfterLastCard() {
        CardDeck deck = new CardDeck();
        int counter = 0;
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
            deck.getNextCard();
            counter++;
        }
        try {
            PlayingCard card = deck.getNextCard();
            String failMsg = 
                    "Asking for another card after last should not have given " 
                    + card.toString();
            fail(failMsg);
        } catch (RanOutOfCardsException roce) {
            System.out.println("Asking for another card after last correctly caused RanOutOfCardsException");
            System.out.println("\"" + roce.getMessage() + "\"");
            Rank rank = roce.getRank();
            Suit suit = roce.getSuit();
            assertNull(rank);
            assertNull(suit);
        } catch (IndexOutOfBoundsException ioobe) {
            String failMsg = "Asking for another card after last should not disclose IndexOutOfBoundsException";
            System.out.println(failMsg);
            System.out.println("\"" + ioobe.getMessage() + "\"");
            fail(failMsg);
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() + " is the wrong exception to throw after asking for another card after last";
            fail(failMsg);
        }
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

    /**
     * Another test of shuffle method, of class CardDeck. I don't know if there
     * are any card games that require the deck to be reshuffled after cards
     * have been dealt, but it seems logical that if that is the case, the same
     * card should not be dealt twice. Hence this test.
     */
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
     * Another test of shuffle method, of class CardDeck. I don't know if there
     * are any card games that require the deck to be reshuffled after cards
     * have been dealt. However, if there is only one card left in the deck, 
     * there is no point in shuffling. It could be argued that there's no point 
     * shuffling two cards, but I'm going to let that one slide.
     */
    @Test
    public void testNoShuffleForJustOneCard() {
        CardDeck deck = new CardDeck();
        int counter = 0;
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK - 1) {
            deck.getNextCard();
            counter++;
        }
        try {
            deck.shuffle();
            fail("Should not have been able to shuffle deck with just one card");
        } catch (IllegalStateException ise) {
            System.out.println("Trying to shuffle deck with only one card left correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() + " is the wrong exception to throw for trying to shuffle a deck with just one card";
            fail(failMsg);
        }
    }

    /**
     * Another test of shuffle method, of class CardDeck. I don't know if there
     * are any card games that require the deck to be reshuffled after cards 
     * have been dealt. However, if there are no cards left in the deck, trying 
     * to shuffle should cause an exception.
     */
    @Test
    public void testNoShuffleForZeroCards() {
        CardDeck deck = new CardDeck();
        int counter = 0;
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
            deck.getNextCard();
            counter++;
        }
        try {
            deck.shuffle();
            fail("Should not have been able to shuffle deck with no cards left");
        } catch (IllegalStateException ise) {
            System.out.println("Trying to shuffle deck with no cards left correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() + " is the wrong exception to throw for trying to shuffle a deck with no cards left";
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
