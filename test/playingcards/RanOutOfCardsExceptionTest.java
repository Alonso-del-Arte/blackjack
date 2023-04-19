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
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Suit[] SUITS = Suit.values();
    
    private static final String EXCEPTION_MESSAGE_PART = "Ran out of ";
    
    private static final String EXCEPTION_MESSAGE_ALL_OUT 
            = "Ran out of all cards";
    
    /**
     * Test of the rankDeficient function, of the RanOutOfCardsException class.
     */
    @Test
    public void testRankDeficient() {
        System.out.println("rankDeficient");
        String msgPart = " should be considered rank-deficient";
        for (Rank rank : RANKS) {
            String excMsg = EXCEPTION_MESSAGE_PART + rank.getPluralWord();
            RanOutOfCardsException instance 
                    = new RanOutOfCardsException(excMsg, rank);
            String msg = excMsg + msgPart;
            assert instance.rankDeficient() : msg;
        }
    }

    /**
     * Another test of the rankDeficient function, of the RanOutOfCardsException 
     * class.
     */
    @Test
    public void testNotRankDeficient() {
        RanOutOfCardsException instance 
                = new RanOutOfCardsException(EXCEPTION_MESSAGE_ALL_OUT);
        String msg = "Ran out of all cards not considered rank-deficient";
        assert !instance.rankDeficient() : msg;
    }

    /**
     * Test of the suitDeficient function, of the RanOutOfCardsException class.
     */
    @Test
    public void testSuitDeficient() {
        System.out.println("suitDeficient");
        String msgPart = " should be considered suit-deficient";
        for (Suit suit : SUITS) {
            String excMsg = EXCEPTION_MESSAGE_PART + suit.getPluralWord();
            RanOutOfCardsException instance 
                    = new RanOutOfCardsException(excMsg, suit);
            String msg = excMsg + msgPart;
            assert instance.suitDeficient() : msg;
        }
    }

    /**
     * Another test of the suitDeficient function, of the RanOutOfCardsException 
     * class.
     */
    @Test
    public void testNotSuitDeficient() {
        RanOutOfCardsException instance 
                = new RanOutOfCardsException(EXCEPTION_MESSAGE_ALL_OUT);
        String msg = "Ran out of all cards not considered suit-deficient";
        assert !instance.suitDeficient() : msg;
    }

    /**
     * Test of the getRank function, of the RanOutOfCardsException class.
     */
    @Test
    public void testGetRank() {
        System.out.println("getRank");
        for (Rank expected : RANKS) {
            String excMsg = EXCEPTION_MESSAGE_PART + expected.getPluralWord();
            RanOutOfCardsException instance 
                    = new RanOutOfCardsException(excMsg, expected);
            Rank actual = instance.getRank();
            assertEquals(expected, actual);
        }
    }

    /**
     * Another test of the getRank function, of the RanOutOfCardsException 
     * class. If the exception was constructed without specifying a rank 
     * deficiency, the getter should return a null pointer.
     */
    @Test
    public void testGetRankShouldNotInventRank() {
        for (Suit suit : SUITS) {
            String excMsg = EXCEPTION_MESSAGE_PART + suit.getPluralWord();
            RanOutOfCardsException instance 
                    = new RanOutOfCardsException(excMsg, suit);
            Rank rank = instance.getRank();
            assertNull(rank);
        }
    }

    /**
     * Test of the getSuit function, of the RanOutOfCardsException class.
     */
    @Test
    public void testGetSuit() {
        System.out.println("getSuit");
        for (Suit expected : SUITS) {
            String excMsg = EXCEPTION_MESSAGE_PART + expected.getPluralWord();
            RanOutOfCardsException instance 
                    = new RanOutOfCardsException(excMsg, expected);
            Suit actual = instance.getSuit();
            assertEquals(expected, actual);
        }
    }
    
    /**
     * Another test of the getSuit function, of the RanOutOfCardsException 
     * class. If the exception was constructed without specifying a suit 
     * deficiency, the getter should return a null pointer.
     */
    @Test
    public void testGetSuitShouldNotInventSuit() {
        System.out.println("getSuit");
        for (Rank rank : RANKS) {
            String excMsg = EXCEPTION_MESSAGE_PART + rank.getPluralWord();
            RanOutOfCardsException instance 
                    = new RanOutOfCardsException(excMsg, rank);
            Suit suit = instance.getSuit();
            assertNull(suit);
        }
    }
    
    @Test
    public void testZeroParamConstructor() {
        RanOutOfCardsException instance = new RanOutOfCardsException();
        String expected = "No cards of any rank or suit left";
        String actual = instance.getMessage();
        assertEquals(expected, actual);
        String msg = "With no cards whatsoever, no rank, suit should be listed";
        assertNull(msg, instance.getRank());
        assertNull(msg, instance.getSuit());
    }
    
    @Test
    public void testRankParamConstructor() {
        for (Rank rank : RANKS) {
            RanOutOfCardsException instance = new RanOutOfCardsException(rank);
            String expected = "Ran out of " + rank.getPluralWord();
            String actual = instance.getMessage();
            assertEquals(expected, actual);
            assertEquals(rank, instance.getRank());
            String msg = expected 
                    + " exception is rank-deficient, not suit-deficient";
            assert instance.rankDeficient() : msg;
            assert !instance.suitDeficient() : msg;
        }
    }
    
}
