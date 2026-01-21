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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

import static playingcards.PlayingCardTest.RANDOM;

/**
 * Tests of the CardCounter class.
 * @author Alonso del Arte
 */
public class CardCounterTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Suit[] SUITS = Suit.values();
    
    /**
     * Test of the count function, of the CardCounter class. A new deck should 
     * have one of each card, no more, no less.
     */
    @Test
    public void testCountDeck() {
        System.out.println("count");
        CardDeck deck = new CardDeck();
        CardCounter counter = new CardCounter(deck);
        PlayingCard card;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                card = new PlayingCard(rank, suit);
                assertEquals(1, counter.count(card));
            }
        }
    }
    
    /**
     * Another test of the count function, of the CardCounter class.
     */
    @Test
    public void testCountServer() {
        int deckQty = RANDOM.nextInt(2, 11);
        CardServer supplier = new CardServer(deckQty);
        Map<PlayingCard, Integer> counts 
                = new HashMap<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        for (Rank rank : RANKS) {
            for (Suit suit : SUITS) {
                PlayingCard key = new PlayingCard(rank, suit);
                counts.put(key, deckQty);
            }
        }
        int cardQty = RANDOM.nextInt(40, 80);
        PlayingCard[] cards = supplier.giveCards(cardQty);
        for (PlayingCard key : cards) {
            int value = counts.get(key) - 1;
            counts.put(key, value);
        }
        CardCounter counter = new CardCounter(supplier);
        for (Rank rank : RANKS) {
            for (Suit suit : SUITS) {
                PlayingCard card = new PlayingCard(rank, suit);
                int expected = counts.get(card);
                int actual = counter.count(card);
                String message = "Given supplier with " + deckQty 
                        + " decks with some given out already, expected " 
                        + expected + " of " + card.toString();
                assertEquals(message, expected, actual);
            }
        }
    }
    
}
