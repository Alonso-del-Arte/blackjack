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

import com.sun.javafx.scene.control.skin.VirtualFlow;
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