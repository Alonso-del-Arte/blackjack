/*
 * Copyright (C) 2022 Alonso del Arte
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
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CardJSONServer class. And also of the static classes nested 
 * within it.
 * @author Alonso del Arte
 */
public class CardJSONServerTest {
    
    private static final Random RANDOM = new Random();
    
    public CardJSONServerTest() {
    }

    @Test
    public void getDeckHash() {
        System.out.println("ProvenanceInscribedPlayingCard.getDeckHash");
        int expected = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                CardJSONServer.ProvenanceInscribedPlayingCard card 
                        = new CardJSONServer
                                .ProvenanceInscribedPlayingCard(rank, suit, 
                                        expected, 0);
                int actual = card.getDeckHash();
                assertEquals(expected, actual);
            }
        }
    }
    
    @Test
    public void getShoeHash() {
        System.out.println("ProvenanceInscribedPlayingCard.getShoeHash");
        int expected = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                CardJSONServer.ProvenanceInscribedPlayingCard card 
                        = new CardJSONServer
                                .ProvenanceInscribedPlayingCard(rank, suit, 
                                        0, expected);
                int actual = card.getShoeHash();
                assertEquals(expected, actual);
            }
        }
    }
    
}
