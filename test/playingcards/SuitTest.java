/*
 * Copyright (C) 2020 Alonso del Arte
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
 *
 * @author Alonso del Arte
 */
public class SuitTest {
    
    // TODO: Weigh the different Unicode symbols for playing card suits.
    // https://www.unicode.org/charts/PDF/U2600.pdf page 5 of 8, and
    // https://emojipedia.org/spade-suit/ and crossrefs thereof

    @Test
    public void testGetSpadesChar() {
        System.out.println("getSuitChar");
        assertEquals('\u2664', Suit.SPADES.getSuitChar());
    }
    
    @Test
    public void testGetDiamondsChar() {
        assertEquals('\u2666', Suit.DIAMONDS.getSuitChar());
    }
    
    @Test
    public void testGetHeartsChar() {
        assertEquals('\u2665', Suit.HEARTS.getSuitChar());
    }
    
    @Test
    public void testGetClubsChar() {
        assertEquals('\u2667', Suit.CLUBS.getSuitChar());
    }
    
    @Test
    public void testGetSpadesASCIIChar() {
        System.out.println("getSuitCharASCII");
        assertEquals('S', Suit.SPADES.getSuitCharASCII());
    }
    
    @Test
    public void testGetDiamondsASCIIChar() {
        assertEquals('D', Suit.DIAMONDS.getSuitCharASCII());
    }
    
    @Test
    public void testGetHeartsASCIIChar() {
        assertEquals('H', Suit.HEARTS.getSuitCharASCII());
    }
    
    @Test
    public void testGetClubsASCIIChar() {
        assertEquals('C', Suit.CLUBS.getSuitCharASCII());
    }
    
    @Test
    public void testGetSpadesWord() {
        System.out.println("getSuitWord");
        assertEquals("Spades", Suit.SPADES.getSuitWord());
    }
    
    @Test
    public void testGetDiamondsWord() {
        assertEquals("Diamonds", Suit.DIAMONDS.getSuitWord());
    }
    
    @Test
    public void testGetHeartsWord() {
        assertEquals("Hearts", Suit.HEARTS.getSuitWord());
    }
    
    @Test
    public void testGetClubsWord() {
        assertEquals("Clubs", Suit.CLUBS.getSuitWord());
    }
    
}
