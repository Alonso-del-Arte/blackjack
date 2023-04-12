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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the RanOutOfCardsException class.
 * @author Alonso del Arte
 */
public class RanOutOfCardsExceptionTest {
    
    /**
     * Test of the rankDeficient function, of the RanOutOfCardsException class.
     */
    @Test
    public void testRankDeficient() {
        System.out.println("rankDeficient");
        RanOutOfCardsException roce 
                = new RanOutOfCardsException("Ran out of Jacks", Rank.JACK);
        String msg = "Ran out of Jacks should be considered rank-deficient";
        assert roce.rankDeficient() : msg;
    }

    /**
     * Another test of the rankDeficient function, of the RanOutOfCardsException 
     * class.
     */
    @Test
    public void testNotRankDeficient() {
        RanOutOfCardsException roce 
                = new RanOutOfCardsException("Ran out of all cards");
        String msg = "Ran out of all cards not considered rank-deficient";
        assert !roce.rankDeficient() : msg;
    }

    /**
     * Test of the suitDeficient function, of the RanOutOfCardsException class.
     */
    @Test
    public void testSuitDeficient() {
        System.out.println("suitDeficient");
        RanOutOfCardsException roce 
                = new RanOutOfCardsException("Ran out of Clubs", Suit.CLUBS);
        String msg = "Ran out of Clubs should be considered suit-deficient";
        assert roce.suitDeficient() : msg;
    }

    /**
     * Another test of the suitDeficient function, of the RanOutOfCardsException 
     * class.
     */
    @Test
    public void testNotSuitDeficient() {
        RanOutOfCardsException roce 
                = new RanOutOfCardsException("Ran out of all cards");
        String msg = "Ran out of all cards not considered suit-deficient";
        assert !roce.suitDeficient() : msg;
    }

    /**
     * Test of the getRank function, of the RanOutOfCardsException class.
     */
    @Test
    public void testGetRank() {
        System.out.println("getRank");
        Rank expected = Rank.NINE;
        RanOutOfCardsException roce 
                = new RanOutOfCardsException("Ran out of Nines", expected);
        Rank actual = roce.getRank();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the getRank function, of the RanOutOfCardsException 
     * class. If the exception was constructed without specifying a rank 
     * deficiency, the getter should return a null pointer.
     */
    @Test
    public void testGetRankShouldNotInventRank() {
        RanOutOfCardsException roce 
                = new RanOutOfCardsException("Ran out of Hearts", Suit.HEARTS);
        Rank rank = roce.getRank();
        assertNull(rank);
    }

    /**
     * Test of the getSuit function, of the RanOutOfCardsException class.
     */
    @Test
    public void testGetSuit() {
        System.out.println("getSuit");
        Suit expected = Suit.HEARTS;
        RanOutOfCardsException roce 
                = new RanOutOfCardsException("Ran out of Hearts", expected);
        Suit actual = roce.getSuit();
        assertEquals(expected, actual);
    }
    
    /**
     * Another test of the getSuit function, of the RanOutOfCardsException 
     * class. If the exception was constructed without specifying a suit 
     * deficiency, the getter should return a null pointer.
     */
    @Test
    public void testGetSuitShouldNotInventSuit() {
        System.out.println("getSuit");
        RanOutOfCardsException roce 
                = new RanOutOfCardsException("Ran out of Hearts", Rank.NINE);
        Suit suit = roce.getSuit();
        assertNull(suit);
    }
    
    @Test
    public void testZeroParamConstructor() {
        RanOutOfCardsException exc = new RanOutOfCardsException();
        String expected = "No cards of any rank or suit left";
        String actual = exc.getMessage();
        assertEquals(expected, actual);
        String msg = "With no cards whatsoever, no rank, suit should be listed";
        assertNull(msg, exc.getRank());
        assertNull(msg, exc.getSuit());
    }
    
}
