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

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertDoesNotThrow;
import static org.testframe.api.Asserters.assertThrows;
import static org.testframe.api.Asserters.assertZero;

import static playingcards.PlayingCardTest.RANDOM;

/**
 * Tests of the CardServer class.
 * @author Alonso del Arte
 */
public class CardServerTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Suit[] SUITS = Suit.values();
    
    private static final int DEFAULT_DECK_QUANTITY = 10;
    
    private static class PredicateWithDescription {
        
        final Predicate<PlayingCard> predicate;
        
        final String description;

        PredicateWithDescription(Predicate<PlayingCard> pred, String descr) {
            this.predicate = pred;
            this.description = descr;
        }    
        
    }
    
    private static PredicateWithDescription inventRankPredicate() {
        int remainder = RANDOM.nextInt(4);
        Predicate<PlayingCard> pred 
                = (PlayingCard card) 
                        -> (card.getRank().ordinal() + 1) % 4 == remainder;
        String descr = ", rank ordinal should be " + remainder + " mod 4";
        return new PredicateWithDescription(pred, descr);
    }
    
    private static Color chooseColor() {
        int r = 0;
        if (RANDOM.nextBoolean()) {
            r = 255;
        }
        return new Color(r, 0, 0);
    }
    
    private static PredicateWithDescription inventSuitPredicate() {
        Color suitColor = chooseColor();
        Predicate<PlayingCard> pred 
                = (PlayingCard card) 
                        -> card.getSuit().getTextColor().equals(suitColor);
        String descr = ", suit color should be " + suitColor.toString();
        return new PredicateWithDescription(pred, descr);
    }
    
    private static PredicateWithDescription inventPredicate() {
        if (RANDOM.nextDouble() < 0.75) {
            return inventRankPredicate();
        } else {
            return inventSuitPredicate();
        }
    }
    
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
    
    @Test
    public void testCountRemainingInitial() {
        int deckQty = RANDOM.nextInt(3, 2 * DEFAULT_DECK_QUANTITY);
        CardSupplier instance = new CardServer(deckQty);
        String message = "Server initialized with " + deckQty 
                + " decks has not dealt any cards yet";
        int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        int actual = instance.countRemaining();
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testCountRemaining() {
        System.out.println("countRemaining");
        int deckQty = RANDOM.nextInt(3, 2 * DEFAULT_DECK_QUANTITY);
        CardSupplier instance = new CardServer(deckQty);
        int initial = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        PlayingCard lastDealt = instance.getNextCard();
        int count = 1;
        String msgPartA = "Server initialized with " + deckQty 
                + " decks has so far dealt ";
        String msgPartB = " card(s), last of which was ";
        for (int expected = initial - 1; expected > 0; expected--) {
            String message = msgPartA + count + msgPartB + lastDealt.toString();
            int actual = instance.countRemaining();
            assertEquals(message, expected, actual);
            lastDealt = instance.getNextCard();
            count++;
        }
    }
    
    @Test
    public void testCountRemainingGiveCardByRank() {
        int deckQty = RANDOM.nextInt(3, 2 * DEFAULT_DECK_QUANTITY);
        CardServer instance = new CardServer(deckQty);
        int count = 0;
        int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        int cardsPerRank = deckQty * SUITS.length;
        String msgPartA = "Server initialized with " + deckQty 
                + " decks has so far dealt ";
        for (Rank rank : RANKS) {
            String msgPartB = " of rank " + rank.getWord();
            for (int i = 0; i < cardsPerRank; i++) {
                instance.giveCard(rank);
                expected--;
                count++;
                int actual = instance.countRemaining();
                String message = msgPartA + count + " cards, including " 
                        + (i + 1) + msgPartB;
                assertEquals(message, expected, actual);
            }
        }
    }
    
    @Test
    public void testCountRemainingDepleted() {
        int deckQty = RANDOM.nextInt(3, 2 * DEFAULT_DECK_QUANTITY);
        CardSupplier instance = new CardServer(deckQty);
        int count = 0;
        for (int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK; 
                expected > 0; expected--) {
            instance.getNextCard();
            count++;
        }
        int actual = instance.countRemaining();
        String msg = "Server initialized with " + deckQty 
                + " decks should be depleted after dealing " + count + " cards";
        assertZero(actual, msg);
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
    
    @Test
    public void testGiveCard() {
        System.out.println("giveCard");
        CardServer instance = new CardServer(DEFAULT_DECK_QUANTITY);
        PredicateWithDescription pwd = inventPredicate();
        Predicate<PlayingCard> predicate = pwd.predicate;
        int numberOfCalls = 26;
        int counter = 0;
        do {
            PlayingCard card = instance.giveCard(predicate);
            String msg = "Got " + card.toString() + pwd.description;
            assert predicate.test(card) : msg;
            counter++;
        } while (counter < numberOfCalls);
    }
    
    @Test
    public void testGiveCardNotOfRank() {
        System.out.println("giveCardNotOf(Rank)");
        CardServer server = new CardServer(DEFAULT_DECK_QUANTITY);
        for (Rank rank : RANKS) {
            PlayingCard card = server.giveCardNotOf(rank);
            String msg = "Card " + card.toString() + " should not be " 
                    + rank.getWord();
            assert !card.isOf(rank) : msg;
        }
    }
    
    @Test
    public void testGiveCardsRejectsNegativeCardQuantity() {
        CardServer instance = new CardServer();
        int cardQty = -RANDOM.nextInt(520) - 1;
        String msg = "Negative card quantity " + cardQty 
                + " should cause exception";
        Throwable t = assertThrows(() -> {
            PlayingCard[] cards = instance.giveCards(cardQty);
            System.out.println(msg + ", not given " + Arrays.toString(cards));
        }, NegativeArraySizeException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String numStr = Integer.toString(cardQty);
        String containsMsg = "Exception message should contain \"" + numStr 
                + "\""; 
        assert excMsg.contains(numStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    /**
     * Test of the giveCards function, of the CardServer class.
     */
    @Test
    public void testGiveCards() {
        System.out.println("giveCards");
        int expected 
                = RANDOM.nextInt(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        Set<PlayingCard> examinedCards = new HashSet<>(expected);
        CardServer server = new CardServer();
        PlayingCard[] cards = server.giveCards(expected);
        for (PlayingCard card : cards) {
            assert card != null : "Served card should not be null";
            examinedCards.add(card);
        }
        int actual = examinedCards.size();
        String message = "Request for " + expected 
                + " cards should've been fulfilled with as many cards";
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the giveCards(Rank) function, of the CardServer class.
     */
    @Test
    public void testGiveCardsByRank() {
        System.out.println("giveCards(Rank)");
        int deckQty = RANDOM.nextInt(2, DEFAULT_DECK_QUANTITY + 1);
        CardServer server = new CardServer(deckQty);
        for (Rank rank : RANKS) {
            int cardQty = RANDOM.nextInt(4, 4 * deckQty);
            PlayingCard[] cards = server.giveCards(rank, cardQty);
            String msg = "Served card should be of rank " + rank.getWord();
            Set<PlayingCard> expected = Arrays.stream(SUITS)
                    .map(suit -> new PlayingCard(rank, suit))
                    .collect(Collectors.toSet());
            Set<PlayingCard> actual = new HashSet<>(4);
            for (PlayingCard card : cards) {
                assert card.isOf(rank) : msg;
                actual.add(card);
            }
            assertEquals(expected, actual);
        }
    }
    
    /**
     * Test of the giveCards(Suit) function, of the CardServer class.
     */
    @Test
    public void testGiveCardsBySuit() {
        System.out.println("giveCards(Suit)");
        int deckQty = RANDOM.nextInt(2, DEFAULT_DECK_QUANTITY + 1);
        CardServer server = new CardServer(deckQty);
        for (Suit suit : SUITS) {
            int cardQty = RANDOM.nextInt(13, 13 * deckQty);
            PlayingCard[] cards = server.giveCards(suit, cardQty);
            String msg = "Served card should be of suit " + suit.getWord();
            Set<PlayingCard> expected = Arrays.stream(RANKS)
                    .map(rank -> new PlayingCard(rank, suit))
                    .collect(Collectors.toSet());
            Set<PlayingCard> actual = new HashSet<>(13);
            for (PlayingCard card : cards) {
                assert card.isOf(suit) : msg;
                actual.add(card);
            }
            assertEquals(expected, actual);
        }
    }
    
    /**
     * Another test of the giveCards function, of the CardServer class.
     */
    @Test
    public void testGiveCardsCanDealFromMultipleDecks() {
        int deckQty = RANDOM.nextInt(2, DEFAULT_DECK_QUANTITY + 1);
        CardServer server = new CardServer(deckQty);
        int cardQty = CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK * (deckQty - 1) 
                + 1;
        String msg = "Server with " + deckQty + " decks should be able to give " 
                + cardQty + " cards";
        assertDoesNotThrow(() -> {
            PlayingCard[] cards = server.giveCards(cardQty);
            System.out.println("Server gave " + cards[cardQty - 1].toString() 
                    + " from last deck");
        }, msg);
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
