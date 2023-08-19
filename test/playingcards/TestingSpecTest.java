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
 * Tests of the TestingSpec enumeration.
 * @author Alonso del Arte
 */
public class TestingSpecTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Suit[] SUITS = Suit.values();
    
    private static final int NUMBER_OF_RANKS = RANKS.length;
    
    private static final int NUMBER_OF_SUITS = SUITS.length;
    
    private static final Rank[] ODDS = {Rank.ACE, Rank.THREE, Rank.FIVE, 
        Rank.SEVEN, Rank.NINE};
    
    private static final Rank[] EVENS = {Rank.TWO, Rank.FOUR, Rank.SIX, 
        Rank.EIGHT, Rank.TEN};
    
    private static final Rank[] COURTS = {Rank.JACK, Rank.QUEEN, Rank.KING};
    
    @Test
    public void testGetChar() {
        System.out.println("getChar");
        assertEquals('o', TestingSpec.ODD_PIP.getChar());
        assertEquals('e', TestingSpec.EVEN_PIP.getChar());
        assertEquals('c', TestingSpec.COURT.getChar());
    }
    
    @Test
    public void testGetCharASCII() {
        System.out.println("getCharASCII");
        assertEquals('o', TestingSpec.ODD_PIP.getCharASCII());
        assertEquals('e', TestingSpec.EVEN_PIP.getCharASCII());
        assertEquals('c', TestingSpec.COURT.getCharASCII());
    }
    
    @Test
    public void testGetWord() {
        System.out.println("getWord");
        assertEquals("odd", TestingSpec.ODD_PIP.getWord());
        assertEquals("even", TestingSpec.EVEN_PIP.getWord());
        assertEquals("court", TestingSpec.COURT.getWord());
    }
    
    @Test
    public void testGetPluralWord() {
        System.out.println("getPluralWord");
        assertEquals("odds", TestingSpec.ODD_PIP.getPluralWord());
        assertEquals("evens", TestingSpec.EVEN_PIP.getPluralWord());
        assertEquals("court cards", TestingSpec.COURT.getPluralWord());
    }
    
    private static Suit chooseSuit() {
        int index = (int) Math.floor(Math.random() * NUMBER_OF_SUITS);
        return SUITS[index];
    }
    
    @Test
    public void testMatches() {
        System.out.println("matches");
        for (Rank odd : ODDS) {
            PlayingCard card = new PlayingCard(odd, chooseSuit());
            String msg = card.toString() + " should match testing spec " 
                    + TestingSpec.ODD_PIP.getWord();
            assert TestingSpec.ODD_PIP.matches(card) : msg;
        }
        for (Rank even : EVENS) {
            PlayingCard card = new PlayingCard(even, chooseSuit());
            String msg = card.toString() + " should match testing spec " 
                    + TestingSpec.EVEN_PIP.getWord();
            assert TestingSpec.EVEN_PIP.matches(card) : msg;
        }
        for (Rank court : COURTS) {
            PlayingCard card = new PlayingCard(court, chooseSuit());
            String msg = card.toString() + " should match testing spec " 
                    + TestingSpec.COURT.getWord();
            assert TestingSpec.COURT.matches(card) : msg;
        }
    }
    
    @Test
    public void testDoesNotMatch() {
        for (Rank odd : ODDS) {
            PlayingCard card = new PlayingCard(odd, chooseSuit());
            String msg = card.toString() + " should not match testing spec " 
                    + TestingSpec.EVEN_PIP.getWord() + " nor " 
                    + TestingSpec.COURT.getWord();
            assert !TestingSpec.EVEN_PIP.matches(card) : msg;
            assert !TestingSpec.COURT.matches(card) : msg;
        }
        for (Rank even : EVENS) {
            PlayingCard card = new PlayingCard(even, chooseSuit());
            String msg = card.toString() + " should not match testing spec " 
                    + TestingSpec.ODD_PIP.getWord() + " nor " 
                    + TestingSpec.COURT.getWord();
            assert !TestingSpec.ODD_PIP.matches(card) : msg;
            assert !TestingSpec.COURT.matches(card) : msg;
        }
        for (Rank court : COURTS) {
            PlayingCard card = new PlayingCard(court, chooseSuit());
            String msg = card.toString() + " should not match testing spec " 
                    + TestingSpec.ODD_PIP.getWord() + " nor " 
                    + TestingSpec.COURT.getWord();
            assert !TestingSpec.ODD_PIP.matches(card) : msg;
            assert !TestingSpec.EVEN_PIP.matches(card) : msg;
        }
    }
    
}
