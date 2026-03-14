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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;
import static org.testframe.api.Asserters.assertZero;

import static playingcards.PlayingCardTest.RANDOM;

/**
 * Tests of the CardDeck class.
 * @author Alonso del Arte
 */
public class CardDeckTest {

    /**
     * The deck is expected to have thirteen cards for each of four suits, and
     * no Jokers.
     */
    public static final int EXPECTED_NUMBER_OF_CARDS_IN_DECK = 52;

    /**
     * The CardDeck class should have a constant declaring that the deck has 52 
     * cards at the time of instantiation.
     */
    @Test
    public void testNumberOfCards() {
        assertEquals(EXPECTED_NUMBER_OF_CARDS_IN_DECK, 
                CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
    }

    /**
     * Test of the hasNext function, of the CardDeck class.
     */
    @Test
    public void testNewDeckHasNext() {
        CardDeck deck = new CardDeck();
        assert deck.hasNext() : "A new deck should have a card to deal";
    }

    /**
     * Test of the hasNext function, of the CardDeck class. We need to ascertain 
     * the deck gives 52 cards and the function returns true before each of 
     * those cards is given out.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        CardDeck deck = new CardDeck();
        String msgPartA = "After giving out ";
        int counter = 0;
        String msgPartB = " card(s), deck should still have next";
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
            String msg = msgPartA + counter + msgPartB;
            assert deck.hasNext() : msg;
            deck.getNextCard();
            counter++;
        }
    }
    
    @Test
    public void testDoesNotHaveNext() {
        CardDeck deck = new CardDeck();
        int counter = 0;
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
            deck.getNextCard();
            counter++;
        }
        String msg = "Deck should be out of cards after dealing all 52";
        assert !deck.hasNext() : msg;
    }

    /**
     * Another test of the getNextCard function, of the CardDeck class.
     */
    @Test
    public void testGetNextCardDoesNotGiveNullCard() {
        CardDeck deck = new CardDeck();
        String msg = "Card should not be null";
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            assert card != null : msg;
        }
    }

    /**
     * Test of the getNextCard function, of the CardDeck class. This test is to 
     * ascertain the deck gives 52 distinct cards.
     */
    @Test
    public void testGetNextCard() {
        System.out.println("getNextCard");
        CardDeck deck = new CardDeck();
        Set<PlayingCard> cards = new HashSet<>();
        int counter = 0;
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
            PlayingCard card = deck.getNextCard();
            cards.add(card);
            counter++;
        }
        int actual = cards.size();
        assertEquals(EXPECTED_NUMBER_OF_CARDS_IN_DECK, actual);
    }
    
    /**
     * Another test of the getNextCard function, of the CardDeck class. Trying 
     * to get another card after the last card has been dealt should cause 
     * {@link RanOutOfCardsException}. It should not cause {@code 
     * IndexOutOfBoundsException}, which would imply that the caller was 
     * expected to keep track of the index. The correct way to prevent {@code 
     * getNextCard()} from causing an exception is by checking {@code 
     * hasNext()}.
     */
    @Test
    public void testNoDealAfterLastCard() {
        CardDeck deck = new CardDeck();
        int counter = 0;
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
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
    public void testCountRemaining() {
        System.out.println("countRemaining");
        CardDeck deck = new CardDeck();
        deck.shuffle();
        String msgPart = " cards left before giving out ";
        for (int expected = EXPECTED_NUMBER_OF_CARDS_IN_DECK; expected > 0; 
                expected--) {
            int actual = deck.countRemaining();
            PlayingCard card = deck.getNextCard();
            String cardName = card.toString();
            System.out.print(cardName + ", ");
            if (expected % 13 == 1) System.out.println();
            String message = expected + msgPart + cardName;
            assertEquals(message, expected, actual);
        }
        String msg = "Deck should be depleted after giving out all cards";
        assertZero(deck.countRemaining(), msg);
    }

    /**
     * Test of the shuffle procedure, of the CardDeck class.
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
            diffCardFound = diffCardFound 
                    || !fromShuffled.equals(fromUnshuffled);
        }
        String msg = "Shuffled deck should have cards in a different order";
        assert diffCardFound : msg;
    }

    /**
     * Another test of the shuffle procedure, of the CardDeck class. I don't 
     * know if there are any card games that require the deck to be reshuffled 
     * after cards have been dealt, but it seems logical that if that is the 
     * case, the same card should not be dealt twice. Hence this test.
     */
    @Test
    public void testShuffleOnlyCardsInDeck() {
        CardDeck deck = new CardDeck();
        List<PlayingCard> discardPile = new ArrayList<>();
        List<PlayingCard> stillInDeck = new ArrayList<>();
        int counter = 0;
        int halfCount = EXPECTED_NUMBER_OF_CARDS_IN_DECK / 2;
        while (counter < halfCount) {
            discardPile.add(deck.getNextCard());
            counter++;
        }
        deck.shuffle();
        while (deck.hasNext()) {
            stillInDeck.add(deck.getNextCard());
        }
        ArrayList<PlayingCard> intersection = new ArrayList<>();
        discardPile.stream().filter((card) -> (stillInDeck.contains(card)))
                .forEachOrdered((card) -> {
            intersection.add(card);
        });
        if (!intersection.isEmpty()) {
            System.out.println("The following cards were dealt twice: ");
            intersection.forEach((card) -> {
                System.out.println(card.toASCIIString());
            });
            String message = "Card " + intersection.get(0).toString();
            if (intersection.size() > 1) {
                message = message + " and " + (intersection.size() - 1) 
                        + " other(s)";
            }
            message = message + " should not have been dealt twice";
            fail(message);
        }
    }

    /**
     * Another test of the shuffle procedure, of the CardDeck class. I don't 
     * know if there are any card games that require the deck to be reshuffled 
     * after cards have been dealt. However, if there is only one card left in 
     * the deck, there is no point in shuffling. It could be argued that there's 
     * no point shuffling two cards, but I'm going to let that one slide.
     */
    @Test
    public void testNoShuffleForJustOneCard() {
        CardDeck deck = new CardDeck();
        int counter = 0;
        int max = EXPECTED_NUMBER_OF_CARDS_IN_DECK - 1;
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

    /**
     * Another test of the shuffle procedure, of the CardDeck class. I don't 
     * know if there are any card games that require the deck to be reshuffled 
     * after cards have been dealt. However, if there are no cards left in the 
     * deck, trying to shuffle should cause an exception.
     */
    @Test
    public void testNoShuffleForZeroCards() {
        CardDeck deck = new CardDeck();
        int counter = 0;
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
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

    /**
     * Another test of the sameOrderAs function, of the CardDeck class.
     */
    @Test
    public void testSameOrderAsInitially() {
        CardDeck deck = new CardDeck();
        CardDeck other = new CardDeck();
        String msg = "Two new unshuffled decks should be in the same order";
        assert deck.sameOrderAs(other) : msg;
        assert other.sameOrderAs(deck) : msg;
    }
    
    /**
     * Test of the sameOrderAs function, of the CardDeck class.
     */
    @Test
    public void testSameOrderAs() {
        System.out.println("sameOrderAs");
        CardDeck deck = new CardDeck();
        CardDeck other = new CardDeck();
        String msgPartA = "After giving out ";
        int counter = 0;
        String msgPartB = " card(s) each, both decks should be in same order";
        while (counter < EXPECTED_NUMBER_OF_CARDS_IN_DECK) {
            deck.getNextCard();
            other.getNextCard();
            counter++;
            String msg = msgPartA + counter + msgPartB;
            assert deck.sameOrderAs(other) : msg;
            assert other.sameOrderAs(deck) : msg;
        }
    }
    
    // TODO: Write test with one deck shuffled but same deal count

    /**
     * Another test of the sameOrderAs function, of the CardDeck class.
     */
    @org.junit.Ignore @Test
    public void testNotSameOrderAsAfterDiffDealCounts() {
        fail("FINISH WRITING THIS TEST");
        CardDeck deck = new CardDeck();
        CardDeck other = new CardDeck();
        deck.getNextCard();
        int counterA = 1;
        int counterB = 0;
        while (deck.hasNext()) {
            //
        }
        CardDeck deck01 = new CardDeck();
        deck01.getNextCard();
        CardDeck deck02 = new CardDeck();
        String msg = "Decks should differ after different number of deals";
        assert !deck01.sameOrderAs(deck02) : msg;
        assert !deck02.sameOrderAs(deck01) : msg;
    }

    /**
     * Test of provenance method, of class CardDeck.
     */
    @org.junit.Ignore @Test
    public void testProvenance() {
        System.out.println("provenance");
        CardDeck deck = new CardDeck();
        PlayingCard card = deck.getNextCard();
        String msg = "The card " + card.toString() 
                + " dealt from this deck should be said to come from this deck";
        assert deck.provenance(card) : msg;
    }

    @org.junit.Ignore @Test
    public void testComesFromDiffDeck() {
        CardDeck deck01 = new CardDeck();
        CardDeck deck02 = new CardDeck();
        PlayingCard cardFromSecondDeck = deck02.getNextCard();
        String msg = "The card " + cardFromSecondDeck.toString() 
                + " from Deck 2 should not be said to come from Deck 1";
        assert !deck01.provenance(cardFromSecondDeck) : msg;
    }

}
