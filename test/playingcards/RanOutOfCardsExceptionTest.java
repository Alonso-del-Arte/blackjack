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
public class RanOutOfCardsExceptionTest {
    
    public RanOutOfCardsExceptionTest() {
    }

    /**
     * Test of rankDeficient method, of class RanOutOfCardsException.
     */
    @Test
    public void testRankDeficient() {
        System.out.println("rankDeficient");
        RanOutOfCardsException roce = new RanOutOfCardsException("Ran out of Jacks", Rank.JACK);
        assert roce.rankDeficient() 
                : "Exception for ran out of Jacks should be considered rank-deficient";
    }

    /**
     * Another test of rankDeficient method, of class RanOutOfCardsException.
     */
    @Test
    public void testNotRankDeficient() {
        RanOutOfCardsException roce = new RanOutOfCardsException("Ran out of all cards");
        assert !roce.rankDeficient() 
                : "Exception for ran out of all cards should not be considered rank-deficient";
    }

    /**
     * Test of suitDeficient method, of class RanOutOfCardsException.
     */
    @Test
    public void testSuitDeficient() {
        System.out.println("suitDeficient");
        RanOutOfCardsException roce = new RanOutOfCardsException("Ran out of Clubs", Suit.CLUBS);
        assert roce.suitDeficient() 
                : "Exception for ran out of Clubs should be considered suit-deficient";
    }

    /**
     * Another test of suitDeficient method, of class RanOutOfCardsException.
     */
    @Test
    public void testNotSuitDeficient() {
        RanOutOfCardsException roce = new RanOutOfCardsException("Ran out of all cards");
        assert !roce.suitDeficient()
                : "Exception for ran out of all cards should not be considered suit-deficient";
    }

    /**
     * Test of getRank method, of class RanOutOfCardsException.
     */
    @Test
    public void testGetRank() {
        System.out.println("getRank");
        Rank expected = Rank.NINE;
        RanOutOfCardsException roce = new RanOutOfCardsException("Ran out of Nines", expected);
        Rank actual = roce.getRank();
        assertEquals(expected, actual);
    }

    /**
     * Another test of getRank method, of class RanOutOfCardsException.
     */
    @Test
    public void testGetRankShouldNotInventRank() {
        RanOutOfCardsException roce = new RanOutOfCardsException("Ran out of Hearts", Suit.HEARTS);
        Rank rank = roce.getRank();
        assertNull(rank);
    }

    /**
     * Test of getSuit method, of class RanOutOfCardsException.
     */
    @Test
    public void testGetSuit() {
        System.out.println("getSuit");
        Suit expected = Suit.HEARTS;
        RanOutOfCardsException roce = new RanOutOfCardsException("Ran out of Hearts", expected);
        Suit actual = roce.getSuit();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of getSuit method, of class RanOutOfCardsException.
     */
    @Test
    public void testGetSuitShouldNotInventSuit() {
        System.out.println("getSuit");
        RanOutOfCardsException roce = new RanOutOfCardsException("Ran out of Hearts", Rank.NINE);
        Suit suit = roce.getSuit();
        assertNull(suit);
    }
    
}
