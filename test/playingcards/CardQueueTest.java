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
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CardQueue class.
 * @author Alonso del Arte
 */
public class CardQueueTest {
    
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
        String msg = "After giving out all cards, last of which was " + lastCard 
                + ", card queue should not have next";
        assert !queue.hasNext() : msg;
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
        for (PlayingCard card : cardCounts.keySet()) {
            int actual = cardCounts.get(card);
            assertEquals(expected, actual);
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
