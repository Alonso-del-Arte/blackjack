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
package playingcards;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertDoesNotThrow;
import static org.testframe.api.Asserters.assertThrows;

import static playingcards.CardJSONServerTest.RANDOM;

/**
 * Tests of the CardServer class.
 * @author Alonso del Arte
 */
public class CardServerTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Suit[] SUITS = Suit.values();
    
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        CardServer server = new CardServer(2);
        String msg = "Newly initialized server should have cards to give";
        assert server.hasNext() : msg;
    }
    
    @Test
    public void testDoesNotHaveNext() {
        CardServer server = new CardServer(1);
        PlayingCard[] cards = new PlayingCard[52];
        for (int i = 0; i < 52; i++) {
            cards[i] = server.getNextCard();
        }
        System.out.println("First card server gave was " 
                + cards[0].toASCIIString() + ", last card was " 
                + cards[51].toASCIIString());
        String msg = "Single-deck server should've run out after 52 cards";
        assert !server.hasNext() : msg;
    }
    
    @Test
    public void testStillHasNextAfterFirstDeck() {
        CardServer server = new CardServer(2);
        PlayingCard[] cards = new PlayingCard[52];
        for (int i = 0; i < 52; i++) {
            cards[i] = server.getNextCard();
        }
        System.out.println("First card server gave was " 
                + cards[0].toASCIIString() + ", last card was " 
                + cards[51].toASCIIString());
        String msg = "Two-deck server should not have run out after 52 cards";
        assert server.hasNext() : msg;
    }
    
    @Test
    public void testServerHasAsManyDecksAsSpecifiedByConstructor() {
        int deckQty = RANDOM.nextInt(3, 11);
        CardServer server = new CardServer(deckQty);
        int numberOfCards = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        PlayingCard[] cards = new PlayingCard[numberOfCards];
        String msg = "Server with " + deckQty + " should've run out after " 
                + numberOfCards + " cards, not before";
        assertDoesNotThrow(() -> {
            for (int i = 0; i < numberOfCards; i++) {
                cards[i] = server.getNextCard();
            }
        }, msg);
        System.out.println("First card server gave was " + cards[0].toString() 
                + ", last card was " + cards[numberOfCards - 1].toString());
        assert !server.hasNext() : msg;
    }
    
    /**
     * Test of the provenance function, of the CardServer class.
     */
    @Test
    public void testProvenance() {
        System.out.println("provenance");
        CardServer server = new CardServer(2);
        PlayingCard[] aces = server.giveCards(Rank.ACE, 8);
        String msg = "Ace should've come from this card server";
        for (PlayingCard ace : aces) {
            assert server.provenance(ace) : msg;
        }
    }
    
    /**
     * Another test of the provenance function, of the CardServer class.
     */
    @Test
    public void testNotProvenance() {
        CardServer server = new CardServer();
        PlayingCard servedCard = server.getNextCard();
        PlayingCard copiedCard = new PlayingCard(servedCard.getRank(), 
                servedCard.getSuit());
        String assertionMessage = "Copied card " + copiedCard.toString() 
                + " should not be said to have come from this server";
        assert !server.provenance(copiedCard) : assertionMessage;
    }
    
    /**
     * Test of the getNextCard function, of the CardServer class.
     */
    @Test
    public void testGetNextCard() {
        System.out.println("getNextCard");
        CardServer server = new CardServer();
        PlayingCard card = server.getNextCard();
        assert card != null : "Server should not serve null card";
        System.out.println("For no specific rank/suit, server served " 
                + card.toASCIIString());
    }
    
    /**
     * Another test of the getNextCard function, of the CardServer class.
     */
    @Test
    public void testGetNextCardCanDealFromTwoDecks() {
        CardServer server = new CardServer(2);
        int counter = 0;
        while (counter < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            server.getNextCard();
            counter++;
        }
        try {
            PlayingCard card = server.getNextCard();
            System.out.println("Server gave " + card.toASCIIString() 
                    + " from second deck");
        } catch (RanOutOfCardsException roce) {
            String message = "Second deck should've had cards";
            System.out.println(message);
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception second deck not having cards";
            fail(message);
        }
    }
    
    /**
     * Test of the giveCard(Rank) function, of the CardServer class.
     */
    @Test
    public void testGiveCardRank() {
        System.out.println("giveCard(Rank)");
        CardServer server = new CardServer();
        for (Rank expected : RANKS) {
            PlayingCard card = server.giveCard(expected);
            assert card != null : "Served card should not be null";
            String msg = "Served card " + card.toString() + " should be a " 
                    + expected.getWord();
            assert card.isOf(expected) : msg;
        }
    }
    
    /**
     * Another test of the giveCard(Rank) function, of the CardServer class.
     */
    @Test
    public void testGiveCardMultOfRankInvokeDistinct() {
        CardServer server = new CardServer();
        for (Rank expected : RANKS) {
            PlayingCard[] cards = new PlayingCard[4];
            String msgPart = " should be of rank " + expected.getWord();
            for (int n = 0; n < 4; n++) {
                cards[n] = server.giveCard(expected);
                String msg = cards[n].toString() + msgPart;
                assert cards[n].isOf(expected) : msg;
            }
            for (int i = 0; i < 3; i++) {
                for (int j = i + 1; j < 4; j++) {
                    assertNotEquals(cards[i], cards[j]);
                }
            }
        }
    }
    
    /**
     * Test of the giveCard(Suit) function, of the CardServer class.
     */
    @Test
    public void testGiveCardSuit() {
        System.out.println("giveCard(Suit)");
        CardServer server = new CardServer();
        for (Suit expected : SUITS) {
            PlayingCard card = server.giveCard(expected);
            assert card != null : "Served card should not be null";
            String msg = "Served card " + card.toString() 
                    + " should be of suit " + expected.getWord();
            assert card.isOf(expected) : msg;
        }
    }
    
    /**
     * Another test of the giveCard(Suit) function, of the CardServer class.
     */
    @Test
    public void testGiveCardMultOfSuitInvokeDistinct() {
        CardServer server = new CardServer();
        PlayingCard[] cards = new PlayingCard[13];
        for (Suit expected : SUITS) {
            String msg = "Served card should be of suit " + expected.getWord();
            for (int n = 0; n < 13; n++) {
                cards[n] = server.giveCard(expected);
                assert cards[n].isOf(expected) : msg;
            }
            for (int i = 0; i < 12; i++) {
                for (int j = i + 1; j < 13; j++) {
                    assertNotEquals(cards[i], cards[j]);
                }
            }
        }
    }
    
    /**
     * Test of the giveCards function, of the CardServer class.
     */
    @Test
    public void testGiveCards() {
        System.out.println("giveCards");
        CardServer server = new CardServer();
        PlayingCard[] cards = server.giveCards(20);
        for (PlayingCard card : cards) {
            assert card != null : "Served card should not be null";
        }
    }
    
    /**
     * Test of giveCards(Rank) method, of class CardServer.
     */
    @org.junit.Ignore
    @Test
    public void testGiveCardsRankFour() {
        System.out.println("giveCards(Rank)");
        fail("REWRITE THIS TEST THROUGH ALL RANKS");
        CardServer server = new CardServer();
        Rank expected = Rank.FOUR;
        PlayingCard[] cards = server.giveCards(expected, 4);
        String msg = "Served card should be of rank " + expected.getWord();
        for (PlayingCard card : cards) {
            assert card.isOf(expected) : msg;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                assertNotEquals(cards[i], cards[j]);
            }
        }
    }
    
    /**
     * Test of the giveCards(Suit) function, of the CardServer class.
     */
    @org.junit.Ignore
    @Test
    public void testGiveCardsSuitClubs() {
        System.out.println("giveCards(Suit)");
        fail("REWRITE THIS TEST THROUGH ALL SUITS");
        CardServer server = new CardServer();
        Suit expected = Suit.CLUBS;
        PlayingCard[] cards = server.giveCards(expected, 13);
        String msg = "Served card should be of suit " 
                + expected.getWord();
        for (PlayingCard card : cards) {
            assert card.isOf(expected) : msg;
        }
        for (int i = 0; i < 12; i++) {
            for (int j = i + 1; j < 13; j++) {
                assertNotEquals(cards[i], cards[j]);
            }
        }
    }
    
    /**
     * Another test of the giveCards function, of class CardServer. I'm not 
     * using assertThrows() for this test because I want a different message if 
     * {@link RanOutOfCardsException} occurs.
     */
    @Test
    public void testGiveCardsCanDealFromTwoDecks() {
        CardServer server = new CardServer(2);
        try {
            PlayingCard[] cards = server.giveCards(53);
            System.out.println("Server gave " + cards[52].toASCIIString() 
                    + " from second deck");
        } catch (RanOutOfCardsException roce) {
            String message = "Second deck should've had cards";
            System.out.println(message);
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(message);
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is wrong exception for second deck not having cards";
            fail(message);
        }
    }
    
    /**
     * Another test of the giveCards function, of class CardServer. I'm not 
     * using assertThrows() for this test because I want a different message if 
     * {@link RanOutOfCardsException} occurs.
     */
    @org.junit.Ignore
    @Test
    public void testGiveCardsRankCanServeFromTwoDecks() {
        fail("REWRITE THIS TEST THROUGH ALL RANKS OR ONE RANDOM RANK");
        fail("REWRITE WITH assertDoesNotThrow()");
        CardServer server = new CardServer(2);
        Rank expected = Rank.SEVEN;
        try {
            PlayingCard[] cards = server.giveCards(expected, 7);
            String assertionMessage = "Served card should be of rank " 
                    + expected.getWord();
            for (PlayingCard card : cards) {
                assert card.isOf(expected) : assertionMessage;
            }
        } catch (RuntimeException re) {
            String message = "Server should have been able to deal cards of rank " 
                    + expected.getWord() + " from two decks without causing " 
                    + re.getClass().getName();
            fail(message);
        }
    }
    
    /**
     * Another test of the giveCards function, of class CardServer. I'm not 
     * using assertThrows() for this test because I want a different message if 
     * {@link RanOutOfCardsException} occurs.
     */
    @org.junit.Ignore
    @Test
    public void testGiveCardsSuitCanServeFromTwoDecks() {
        fail("REWRITE THIS TEST THROUGH ALL SUITS OR ONE RANDOM SUIT");
        CardServer server = new CardServer(2);
        Suit expected = Suit.DIAMONDS;
        try {
            PlayingCard[] cards = server.giveCards(expected, 25);
            String assertionMessage = "Served card should be of suit " 
                    + expected.getWord();
            for (PlayingCard card : cards) {
                assert card.isOf(expected) : assertionMessage;
            }
        } catch (RuntimeException re) {
            String msg = "Server should have been able to deal cards of rank " 
                    + expected.getWord() + " from two decks without causing " 
                    + re.getClass().getName();
            fail(msg);
        }
    }
    
    /**
     * Test of the CardServer constructor. A negative number should be an 
     * invalid number of decks constructor parameter.
     */
    @Test
    public void testConstructorRejectNegativeDeckQuantity() {
        int deckQty = -RANDOM.nextInt(32) - 1;
        String msg = "Trying to create server with deck quantity " + deckQty 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            CardServer badServer = new CardServer(deckQty);
            System.out.println(msg + ", not created instance " 
                    + badServer.toString());
        }, NegativeArraySizeException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should contain \"" + deckQty 
                + "\"";
        assert excMsg.contains(Integer.toString(deckQty)) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    /**
     * Another test of the CardServer constructor. Zero should be an invalid 
     * number of decks constructor parameter.
     */
    @Test
    public void testConstructorRejectZeroDeckQuantity() {
        int deckQty = 0;
        String msg = "Trying to create server with deck quantity " + deckQty 
                + " should have caused an exception";
        Throwable t = assertThrows(() -> {
            CardServer badServer = new CardServer(deckQty);
            System.out.println(msg + ", not created instance " 
                    + badServer.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should contain \"0\"";
        assert excMsg.contains("0") : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
}
