/*
 * Copyright (C) 2020 Alonso del Arte
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class CardCounterTest {
    
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
    
    @Test
    public void testCountServerAfterDealOutSpades() {
        CardServer server = new CardServer(2);
        PlayingCard[] spades = server.giveCards(Suit.SPADES, 26);
        CardCounter counter = new CardCounter(server);
        for (PlayingCard spade : spades) {
            assertEquals(0, counter.count(spade));
        }
    }
    
}
