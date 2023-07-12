/*
 * Copyright (C) 2023 Alonso del Arte
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CardQueue class.
 * @author Alonso del Arte
 */
public class CardQueueTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Suit[] SUITS = Suit.values();
    
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        int deckQty = CardJSONServerTest.RANDOM.nextInt(10) + 1;
        CardQueue queue = new CardQueue(deckQty);
        int cardQty = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        PlayingCard lastCard = null;
        String msgPart = "Card queue should have next while there are ";
        while (cardQty > 0) {
            String msg = msgPart + cardQty + " cards left";
            assert queue.hasNext() : msg;
            lastCard = queue.getNextCard();
            cardQty--;
        }
        assert lastCard != null : "Last card should not be null";
        String msg = "After giving out all cards, last of which was " 
                + lastCard.toASCIIString() 
                + ", card queue should not have next";
        assert !queue.hasNext() : msg;
    }
    
    @Test
    public void testGetNextCardThrowsExceptionAfterRunningOut() {
        int deckQty = CardJSONServerTest.RANDOM.nextInt(10) + 1;
        CardQueue queue = new CardQueue(deckQty);
        int cardQty = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        PlayingCard lastCard = new PlayingCard(Rank.JACK, Suit.CLUBS);
        while (cardQty > 0) {
            assert queue.hasNext() : "Queue should have next";
            lastCard = queue.getNextCard();
            cardQty--;
        }
        try {
            PlayingCard nonExistentCard = queue.getNextCard();
            String msg = "After giving out last card " + lastCard.toString() 
                    + ", queue should not have given " 
                    + nonExistentCard.toString();
            fail(msg);
        } catch (RanOutOfCardsException roce) {
            System.out.println("After giving out last card " 
                    + lastCard.toASCIIString() 
                    + " queue correctly threw RanOutOfCardsException");
            System.out.println("\"" + roce.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for next card after last card " 
                    + lastCard.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testGetNextCard() {
        System.out.println("getNextCard");
        Map<PlayingCard, Integer> cardCounts 
                = new HashMap<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        int expected = CardJSONServerTest.RANDOM.nextInt(8) + 2;
        CardSupplier queue = new CardQueue(expected);
        while (queue.hasNext()) {
            PlayingCard card 
                    = ProvenanceInscribedPlayingCardTest
                            .removeProvenanceInfo(queue.getNextCard());
            if (cardCounts.containsKey(card)) {
                cardCounts.put(card, cardCounts.get(card) + 1);
            } else {
                cardCounts.put(card, 1);
            }
        }
        Iterator<PlayingCard> iterator = cardCounts.keySet().iterator();
        while (iterator.hasNext()) {
            int actual = cardCounts.get(iterator.next());
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testConstructorShufflesCards() {
        int deckQty = 2;
        CardSupplier queue = new CardQueue(deckQty);
        Map<PlayingCard, Integer> cardCounts 
                = new HashMap<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        for (int dealCount = 0; 
                dealCount < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK; 
                dealCount++) {
            PlayingCard card 
                    = ProvenanceInscribedPlayingCardTest
                            .removeProvenanceInfo(queue.getNextCard());
            if (cardCounts.containsKey(card)) {
                cardCounts.put(card, cardCounts.get(card) + 1);
            } else {
                cardCounts.put(card, 1);
            }
        }
        int dupCount = 0;
        PlayingCard lastDupFound = null;
        for (PlayingCard card : cardCounts.keySet()) {
            if (cardCounts.get(card) > 1) {
                dupCount++;
                lastDupFound = card;
            }
        }
        String msg = "Expected to find at least one duplicate, found " 
                + dupCount;
        assert dupCount > 0 : msg;
        assert lastDupFound != null;
        System.out.println("Last duplicate found was " 
                + lastDupFound.toASCIIString());
    }
    
    @Test
    public void testProvenance() {
        System.out.println("provenance");
        int deckQty = 2;
        CardSupplier queue = new CardQueue(deckQty);
        String msgPart = " that was given by " + queue.toString() 
                + " should not be disavowed";
        while (queue.hasNext()) {
            PlayingCard card = queue.getNextCard();
            String msg = "Card " + card.toString() + msgPart;
            assert queue.provenance(card) : msg;
        }
    }
    
    @Test
    public void testProvenanceDiffQueue() {
        int deckQty = CardJSONServerTest.RANDOM.nextInt(8) + 2;
        CardSupplier queueA = new CardQueue(deckQty);
        CardSupplier queueB = new CardQueue(deckQty);
        String nameA = queueA.toString();
        String nameB = queueB.toString();
        PlayingCard cardA = queueA.getNextCard();
        PlayingCard cardB = queueB.getNextCard();
        String msgA = cardA.toString() + " from " + nameA 
                + " should not be said to be from " + nameB;
        String msgB = cardB.toString() + " from " + nameB
                + " should not be said to be from " + nameA;
        assert !queueA.provenance(cardB) : msgA;
        assert !queueB.provenance(cardA) : msgB;
    }
    
    @Test
    public void testCueUpRank() {
        PlayingCard[] cuedUpCards = new PlayingCard[RANKS.length];
        CardQueue queue = new CardQueue(6);
        for (Rank expected : RANKS) {
            queue.cueUp(expected);
            PlayingCard card = queue.getNextCard();
            Rank actual = card.getRank();
            assertEquals(expected, actual);
            cuedUpCards[expected.ordinal()] = card;
        }
        String cardListStr = "";
        for (PlayingCard card : cuedUpCards) {
            cardListStr += card.toASCIIString() + ", ";
        }
        cardListStr = cardListStr.substring(0, cardListStr.length() - 2);
        System.out.println("Cue up rank gave " + cardListStr);
    }
    
    @Test
    public void testCueUpRankThrowsExceptionAfterRunningOut() {
        CardQueue queue = new CardQueue(1);
        int numberOfSuits = SUITS.length;
        for (Rank expected : RANKS) {
            int suitIndex = 0;
            while (suitIndex < numberOfSuits) {
                queue.cueUp(expected);
                queue.getNextCard();
                suitIndex++;
            }
            String msgPart = "Trying to get one more " + expected.getWord() 
                    + " after depleting available " + expected.getPluralWord() 
                    + " should've caused RanOutOfCardsException";
            try {
                queue.cueUp(expected);
                PlayingCard card = queue.getNextCard();
                String msg = msgPart + ", not given " + card.toString(); 
                fail(msg);
            } catch (RanOutOfCardsException roce) {
                String msg = msgPart.replace(" should've", "");
                Rank actual = roce.getRank();
                assertEquals(msg, expected, actual);
            } catch (RuntimeException re) {
                String msg = msgPart + ", not " + re.getClass().getName();
                fail(msg);
            }
        }
    }
    
    @Test
    public void testCueUpSuit() {
        PlayingCard[] cuedUpCards = new PlayingCard[SUITS.length];
        CardQueue queue = new CardQueue(6);
        for (Suit expected : SUITS) {
            queue.cueUp(expected);
            PlayingCard card = queue.getNextCard();
            Suit actual = card.getSuit();
            assertEquals(expected, actual);
            cuedUpCards[expected.ordinal()] = card;
        }
        String cardListStr = "";
        for (PlayingCard card : cuedUpCards) {
            cardListStr += card.toASCIIString() + ", ";
        }
        cardListStr = cardListStr.substring(0, cardListStr.length() - 2);
        System.out.println("Cue up rank gave " + cardListStr);
    }
    
    @Test
    public void testCueUpSuitThrowsExceptionAfterRunningOut() {
        CardQueue queue = new CardQueue(1);
        int numberOfRanks = RANKS.length;
        for (Suit expected : SUITS) {
            int rankIndex = 0;
            while (rankIndex < numberOfRanks) {
                queue.cueUp(expected);
                queue.getNextCard();
                rankIndex++;
            }
            String msgPart = "Trying to get one more " + expected.getWord() 
                    + " card after depleting available " 
                    + expected.getPluralWord() 
                    + " should've caused RanOutOfCardsException";
            try {
                queue.cueUp(expected);
                PlayingCard card = queue.getNextCard();
                String msg = msgPart + ", not given " + card.toString(); 
                fail(msg);
            } catch (RanOutOfCardsException roce) {
                String msg = msgPart.replace(" should've", "");
                Suit actual = roce.getSuit();
                assertEquals(msg, expected, actual);
            } catch (RuntimeException re) {
                String msg = msgPart + ", not " + re.getClass().getName();
                fail(msg);
            }
        }
    }
    
    @Test
    public void testConstructorRejectsDeckQuantityZero() {
        int badQty = 0;
        try {
            CardQueue badQueue = new CardQueue(badQty);
            String msg = "Should not have been able to create " + badQueue 
                    + " with deck quantity " + badQty;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use deck quantity " + badQty 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for deck quantity " + badQty;
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsNegativeDeckQuantity() {
        int badQty = -CardJSONServerTest.RANDOM.nextInt(1024) - 1;
        try {
            CardQueue badQueue = new CardQueue(badQty);
            String msg = "Should not have been able to create " + badQueue 
                    + " with deck quantity " + badQty;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use deck quantity " + badQty 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for deck quantity " + badQty;
            fail(msg);
        }
    }
    
}
