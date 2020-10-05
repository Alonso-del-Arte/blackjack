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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CardServer class.
 * @author Alonso del Arte
 */
public class CardServerTest {
    
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
        String msg = "Single-deck server should have run out after giving 52 cards";
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
        String msg = "Two-deck server should not have run out after giving 52 cards";
        assert server.hasNext() : msg;
    }
    
    /**
     * Test of provenance method, of class CardServer.
     */
    @Test
    public void testProvenance() {
        System.out.println("provenance");
        CardServer server = new CardServer(2);
        PlayingCard[] aces = server.giveCards(Rank.ACE, 8);
        for (PlayingCard ace : aces) {
            assert server.provenance(ace) : "The ace came from this card server";
        }
    }
    
    /**
     * Another test of provenance method, of class CardServer.
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
     * Test of getNextCard method, of class CardServer.
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
     * Another test of getNextCard method, of class CardServer.
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
            String msg = "Trying to deal from second deck should not have caused RanOutOfCardsException";
            System.out.println(msg);
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(msg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for inability to deal from second deck";
            fail(msg);
        }
    }
    
    /**
     * Test of giveCard(Rank) method, of class CardServer.
     */
    @Test
    public void testGiveCardRankThree() {
        System.out.println("giveCard(Rank)");
        CardServer server = new CardServer();
        Rank expected = Rank.THREE;
        PlayingCard card = server.giveCard(expected);
        assert card != null : "Served card should not be null";
        System.out.println("Server served " + card.toASCIIString());
        String assertionMessage = "Served card " + card.toString() 
                + " should be a " + expected.getRankWord();
        assert card.isOf(expected) : assertionMessage;
    }
    
    /**
     * Another test of giveCard(Rank) method, of class CardServer.
     */
    @Test
    public void testGiveCardMultOfRankInvokeDistinct() {
        CardServer server = new CardServer();
        PlayingCard[] cards = new PlayingCard[4];
        Rank expected = Rank.FIVE;
        String assertionMessage = "Served card should be of rank " 
                + expected.getRankWord();
        for (int n = 0; n < 4; n++) {
            cards[n] = server.giveCard(expected);
            assert cards[n].isOf(expected) : assertionMessage;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                assertNotEquals(cards[i], cards[j]);
            }
        }
    }
    
    /**
     * Test of giveCard(Suit) method, of class CardServer.
     */
    @Test
    public void testGiveCardSuitHearts() {
        System.out.println("giveCard(Suit)");
        CardServer server = new CardServer();
        Suit expected = Suit.HEARTS;
        PlayingCard card = server.giveCard(expected);
        assert card != null : "Served card should not be null";
        System.out.println("Server served " + card.toASCIIString());
        String assertionMessage = "Served card " + card.toString() 
                + " should be of suit " + expected.getSuitWord();
        assert card.isOf(expected) : assertionMessage;
    }
    
    /**
     * Another test of giveCard(Suit) method, of class CardServer.
     */
    @Test
    public void testGiveCardMultOfSuitInvokeDistinct() {
        CardServer server = new CardServer();
        PlayingCard[] cards = new PlayingCard[13];
        Suit expected = Suit.HEARTS;
        String assertionMessage = "Served card should be of suit " 
                + expected.getSuitWord();
        for (int n = 0; n < 13; n++) {
            cards[n] = server.giveCard(expected);
            assert cards[n].isOf(expected) : assertionMessage;
        }
        for (int i = 0; i < 12; i++) {
            for (int j = i + 1; j < 13; j++) {
                assertNotEquals(cards[i], cards[j]);
            }
        }
    }
    
    /**
     * Test of giveCards method, of class CardServer.
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
    @Test
    public void testGiveCardsRankFour() {
        System.out.println("giveCards(Rank)");
        CardServer server = new CardServer();
        Rank expected = Rank.FOUR;
        PlayingCard[] cards = server.giveCards(expected, 4);
        String assertionMessage = "Served card should be of rank " 
                + expected.getRankWord();
        for (PlayingCard card : cards) {
            assert card.isOf(expected) : assertionMessage;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                assertNotEquals(cards[i], cards[j]);
            }
        }
    }
    
    /**
     * Test of giveCards(Suit) method, of class CardServer.
     */
    @Test
    public void testGiveCardsSuitClubs() {
        System.out.println("giveCards(Suit)");
        CardServer server = new CardServer();
        Suit expected = Suit.CLUBS;
        PlayingCard[] cards = server.giveCards(expected, 13);
        String assertionMessage = "Served card should be of suit " 
                + expected.getSuitWord();
        for (PlayingCard card : cards) {
            assert card.isOf(expected) : assertionMessage;
        }
        for (int i = 0; i < 12; i++) {
            for (int j = i + 1; j < 13; j++) {
                assertNotEquals(cards[i], cards[j]);
            }
        }
    }
    
    /**
     * Another test of giveCards method, of class CardServer.
     */
    @Test
    public void testGiveCardsCanDealFromTwoDecks() {
        CardServer server = new CardServer(2);
        try {
            PlayingCard[] cards = server.giveCards(53);
            System.out.println("Server gave " + cards[52].toASCIIString() 
                    + " from second deck");
        } catch (RanOutOfCardsException roce) {
            String msg = "Trying to deal from second deck should not have caused RanOutOfCardsException";
            System.out.println(msg);
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(msg);
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for inability to deal from second deck";
            fail(msg);
        }
    }
    
    /**
     * Another test of giveCards method, of class CardServer.
     */
    @Test
    public void testGiveCardsRankCanServeFromTwoDecks() {
        CardServer server = new CardServer(2);
        Rank expected = Rank.SEVEN;
        try {
            PlayingCard[] cards = server.giveCards(expected, 7);
            String assertionMessage = "Served card should be of rank " 
                    + expected.getRankWord();
            for (PlayingCard card : cards) {
                assert card.isOf(expected) : assertionMessage;
            }
        } catch (RuntimeException re) {
            String msg = "Server should have been able to deal cards of rank " 
                    + expected.getRankWord() + " from two decks without causing " 
                    + re.getClass().getName();
            fail(msg);
        }
    }
    
    /**
     * Another test of giveCards method, of class CardServer.
     */
    @Test
    public void testGiveCardsSuitCanServeFromTwoDecks() {
        CardServer server = new CardServer(2);
        Suit expected = Suit.DIAMONDS;
        try {
            PlayingCard[] cards = server.giveCards(expected, 25);
            String assertionMessage = "Served card should be of suit " 
                    + expected.getSuitWord();
            for (PlayingCard card : cards) {
                assert card.isOf(expected) : assertionMessage;
            }
        } catch (RuntimeException re) {
            String msg = "Server should have been able to deal cards of rank " 
                    + expected.getSuitWord() + " from two decks without causing " 
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
        try {
            CardServer server = new CardServer(-1);
            String msg = "Should not have been able to create " 
                    + server.toString() + " with negative deck quantity";
            fail(msg);
        } catch (NegativeArraySizeException nase) {
            System.out.println("Trying to create CardServer with negative deck quantity correctly caused exception");
            System.out.println("\"" + nase.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to create CardServer with negative deck quantity";
            fail(msg);
        }
    }
    
    /**
     * Another test of the CardServer constructor. Zero should be an invalid 
     * number of decks constructor parameter.
     */
    @Test
    public void testConstructorRejectZeroDeckQuantity() {
        try {
            CardServer server = new CardServer(0);
            String msg = "Should not have been able to create " 
                    + server.toString() + " with deck quantity zero";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create CardServer with deck quantity zero correctly caused exception");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to create CardServer with deck quantity zero";
            fail(msg);
        }
    }
    
}
