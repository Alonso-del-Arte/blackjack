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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Utilities class.
 * @author Alonso del Arte
 */
public class UtilitiesTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Suit[] SUITS = Suit.values();
    
    @Test
    public void testSortingValueRank() {
        System.out.println("sortingValue(rank)");
        for (Rank rank : RANKS) {
            int expected = rank.getRank() % 13;
            if (expected == 0) {
                expected = 13;
            }
            int actual = Utilities.sortingValue(rank);
            String msg = "Expecting " + expected + " for " + rank.getWord();
            assertEquals(msg, expected, actual);
        }
    }
    
    @Test
    public void testSortingValueSuit() {
        System.out.println("sortingValue(suit)");
        Suit[] suits = {Suit.SPADES, Suit.DIAMONDS, Suit.CLUBS, Suit.HEARTS};
        for (int expected = 0; expected < suits.length; expected++) {
            int actual = Utilities.sortingValue(suits[expected]);
            String msg = "Expecting " + expected + " for " 
                    + suits[expected].getWord();
            assertEquals(msg, expected, actual);
        }
    }
    
    @Test
    public void testBrandNewDeckOrderComparator() {
        List<PlayingCard> expected 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        Rank[] ranksForward = Rank.values();
        for (Rank spadeRank : ranksForward) {
            expected.add(new PlayingCard(spadeRank, Suit.SPADES));
        }
        for (Rank diamondRank : ranksForward) {
            expected.add(new PlayingCard(diamondRank, Suit.DIAMONDS));
        }
        int numberOfRanks = ranksForward.length;
        Rank[] ranksBackward = new Rank[numberOfRanks];
        for (int i = 0; i < numberOfRanks; i++) {
            ranksBackward[numberOfRanks - i - 1] = ranksForward[i];
        }
        for (Rank clubRank : ranksBackward) {
            expected.add(new PlayingCard(clubRank, Suit.CLUBS));
        }
        for (Rank heartRank : ranksBackward) {
            expected.add(new PlayingCard(heartRank, Suit.HEARTS));
        }
        CardDeck deck = new CardDeck();
        deck.shuffle();
        List<PlayingCard> actual 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (deck.hasNext()) {
            actual.add(deck.getNextCard());
        }
        Collections.sort(actual, Utilities.BRAND_NEW_DECK_ORDER);
        assertEquals(expected, actual);
    }
    
}
