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

import static org.junit.Assert.*;
import org.junit.Test;

import static playingcards.PlayingCardTest.RANDOM;

/**
 * Tests of the CardStream class.
 * @author Alonso del Arte
 */
public class CardStreamTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final int NUMBER_OF_RANKS = RANKS.length;
    
    @Test
    public void testGiveCard() {
        System.out.println("giveCard");
        int numberOfDecks = RANDOM.nextInt(2, 10);
        int numberOfCalls = numberOfDecks * NUMBER_OF_RANKS 
                + RANDOM.nextInt(NUMBER_OF_RANKS);
        String msgPartA = "Card ";
        String msgPartB1 = " should be of rank ";
        for (Rank rank : RANKS) {
            String msgPartB = msgPartB1 + rank.getWord();
            for (int i = 0; i < numberOfCalls; i++) {
                PlayingCard card = CardStream.giveCard(rank);
                String msg = msgPartA + card.toString() + msgPartB;
                assert card.isOf(rank) : msg;
            }
        }
    }
    
}
