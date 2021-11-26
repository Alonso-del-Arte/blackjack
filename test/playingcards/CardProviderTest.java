/*
 * Copyright (C) 2021 Alonso del Arte
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
 * Tests of the CardProvider class.
 * @author Alonso del Arte
 */
public class CardProviderTest {
    
    @Test
    public void testGiveCard() {
        System.out.println("giveCard");
        CardProvider provider = new CardProvider();
        Rank[] ranks = Rank.values();
        Suit[] suits = Suit.values();
        for (Rank rank : ranks) {
            for (Suit suit: suits) {
                PlayingCard expected = new PlayingCard(rank, suit);
                PlayingCard actual = provider.giveCard(rank, suit);
                assertEquals(expected, actual);
            }
        }
    }
    
    @Test
    public void testGiveCardsByRank() {
        PlayingCard[] expected = new PlayingCard[4];
        PlayingCard[] actual;
        Rank[] ranks = Rank.values();
        Suit[] suits = Suit.values();
        CardProvider provider = new CardProvider();
        for (Rank rank : ranks) {
            for (Suit suit : suits) {
                expected[suit.ordinal()] = new PlayingCard(rank, suit);
            }
            actual = provider.giveCards(rank);
            assertArrayEquals(expected, actual);
        }
    }
    
    @Test
    public void testGiveCardsBySuit() {
        PlayingCard[] expected = new PlayingCard[13];
        PlayingCard[] actual;
        Rank[] ranks = Rank.values();
        Suit[] suits = Suit.values();
        CardProvider provider = new CardProvider();
        for (Suit suit : suits) {
            for (Rank rank : ranks) {
                expected[rank.ordinal()] = new PlayingCard(rank, suit);
            }
            actual = provider.giveCards(suit);
            assertArrayEquals(expected, actual);
        }
    }
    
}
