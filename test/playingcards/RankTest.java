/*
 * Copyright (C) 2025 Alonso del Arte
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

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Rank enumerated type.
 * @author Alonso del Arte
 */
public class RankTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Locale[] LOCALES = Locale.getAvailableLocales();
    
    @Test
    public void testGetIntValAce() {
        System.out.println("getRank");
        assertEquals(14, Rank.ACE.getIntVal());
    }
    
    @Test
    public void testGetIntVal2() {
        assertEquals(2, Rank.TWO.getIntVal());
    }
    
    @Test
    public void testGetIntVal3() {
        assertEquals(3, Rank.THREE.getIntVal());
    }
    
    @Test
    public void testGetIntVal4() {
        assertEquals(4, Rank.FOUR.getIntVal());
    }
    
    @Test
    public void testGetIntVal5() {
        assertEquals(5, Rank.FIVE.getIntVal());
    }
    
    @Test
    public void testGetIntVal6() {
        assertEquals(6, Rank.SIX.getIntVal());
    }
    
    @Test
    public void testGetIntVal7() {
        assertEquals(7, Rank.SEVEN.getIntVal());
    }
    
    @Test
    public void testGetIntVal8() {
        assertEquals(8, Rank.EIGHT.getIntVal());
    }
    
    @Test
    public void testGetIntVal9() {
        assertEquals(9, Rank.NINE.getIntVal());
    }
    
    @Test
    public void testGetIntVal10() {
        assertEquals(10, Rank.TEN.getIntVal());
    }
    
    @Test
    public void testGetIntValJack() {
        assertEquals(11, Rank.JACK.getIntVal());
    }
    
    @Test
    public void testGetIntValQueen() {
        assertEquals(12, Rank.QUEEN.getIntVal());
    }
    
    @Test
    public void testGetIntValKing() {
        assertEquals(13, Rank.KING.getIntVal());
    }
    
    @Test
    public void testGetRankCharAce() {
        System.out.println("getChar");
        assertEquals('A', Rank.ACE.getChar());
    }
    
    @Test
    public void testGetRankCharASCIIAce() {
        System.out.println("getRankCharASCII");
        assertEquals('A', Rank.ACE.getCharASCII());
    }
    
    @Test
    public void testGetRankCharsAce() {
        System.out.println("getRankChars");
        assertEquals("A", Rank.ACE.getChars());
    }
    
    @Test
    public void testGetRankChar2() {
        assertEquals('2', Rank.TWO.getChar());
    }
    
    @Test
    public void testGetRankCharASCII2() {
        assertEquals('2', Rank.TWO.getCharASCII());
    }
    
    @Test
    public void testGetRankChars2() {
        assertEquals("2", Rank.TWO.getChars());
    }
    
    @Test
    public void testGetRankChar3() {
        assertEquals('3', Rank.THREE.getChar());
    }
    
    @Test
    public void testGetRankCharASCII3() {
        assertEquals('3', Rank.THREE.getCharASCII());
    }
    
    @Test
    public void testGetRankChars3() {
        assertEquals("3", Rank.THREE.getChars());
    }
    
    @Test
    public void testGetRankChar4() {
        assertEquals('4', Rank.FOUR.getChar());
    }
    
    @Test
    public void testGetRankCharASCII4() {
        assertEquals('4', Rank.FOUR.getCharASCII());
    }
    
    @Test
    public void testGetRankChars4() {
        assertEquals("4", Rank.FOUR.getChars());
    }
    
    @Test
    public void testGetRankChar5() {
        assertEquals('5', Rank.FIVE.getChar());
    }
    
    @Test
    public void testGetRankCharASCII5() {
        assertEquals('5', Rank.FIVE.getCharASCII());
    }
    
    @Test
    public void testGetRankChars5() {
        assertEquals("5", Rank.FIVE.getChars());
    }
    
    @Test
    public void testGetRankChar6() {
        assertEquals('6', Rank.SIX.getChar());
    }
    
    @Test
    public void testGetRankCharASCII6() {
        assertEquals('6', Rank.SIX.getCharASCII());
    }
    
    @Test
    public void testGetRankChars6() {
        assertEquals("6", Rank.SIX.getChars());
    }
    
    @Test
    public void testGetRankChar7() {
        assertEquals('7', Rank.SEVEN.getChar());
    }
    
    @Test
    public void testGetRankCharASCII7() {
        assertEquals('7', Rank.SEVEN.getCharASCII());
    }
    
    @Test
    public void testGetRankChars7() {
        assertEquals("7", Rank.SEVEN.getChars());
    }
    
    @Test
    public void testGetRankChar8() {
        assertEquals('8', Rank.EIGHT.getChar());
    }
    
    @Test
    public void testGetRankCharASCII8() {
        assertEquals('8', Rank.EIGHT.getCharASCII());
    }
    
    @Test
    public void testGetRankChars8() {
        assertEquals("8", Rank.EIGHT.getChars());
    }
    
    @Test
    public void testGetRankChar9() {
        assertEquals('9', Rank.NINE.getChar());
    }
    
    @Test
    public void testGetRankCharASCII9() {
        assertEquals('9', Rank.NINE.getCharASCII());
    }
    
    @Test
    public void testGetRankChars9() {
        assertEquals("9", Rank.NINE.getChars());
    }
    
    @Test
    public void testGetRankChar10() {
        assertEquals('\u2169', Rank.TEN.getChar());
    }
    
    @Test
    public void testGetRankCharASCII10() {
        assertEquals('0', Rank.TEN.getCharASCII());
    }
    
    @Test
    public void testGetRankChars10() {
        assertEquals("10", Rank.TEN.getChars());
    }
    
    @Test
    public void testGetRankCharJack() {
        assertEquals('J', Rank.JACK.getChar());
    }
    
    @Test
    public void testGetRankCharASCIIJack() {
        assertEquals('J', Rank.JACK.getCharASCII());
    }
    
    @Test
    public void testGetRankCharsJack() {
        assertEquals("J", Rank.JACK.getChars());
    }
    
    @Test
    public void testGetRankCharQueen() {
        assertEquals('Q', Rank.QUEEN.getChar());
    }
    
    @Test
    public void testGetRankCharASCIIQueen() {
        assertEquals('Q', Rank.QUEEN.getCharASCII());
    }
    
    @Test
    public void testGetRankCharsQueen() {
        assertEquals("Q", Rank.QUEEN.getChars());
    }
    
    @Test
    public void testGetRankCharKing() {
        assertEquals('K', Rank.KING.getChar());
    }
    
    @Test
    public void testGetRankCharASCIIKing() {
        assertEquals('K', Rank.KING.getCharASCII());
    }
    
    @Test
    public void testGetRankCharsKing() {
        assertEquals("K", Rank.KING.getChars());
    }
    
    @Test
    public void testGetRankWordAce() {
        System.out.println("getWord");
        assertEquals("Ace", Rank.ACE.getWord());
    }
    
    @Test
    public void testGetRankWord2() {
        assertEquals("Two", Rank.TWO.getWord());
    }
    
    @Test
    public void testGetRankWord3() {
        assertEquals("Three", Rank.THREE.getWord());
    }
    
    @Test
    public void testGetRankWord4() {
        assertEquals("Four", Rank.FOUR.getWord());
    }
    
    @Test
    public void testGetRankWord5() {
        assertEquals("Five", Rank.FIVE.getWord());
    }
    
    @Test
    public void testGetRankWord6() {
        assertEquals("Six", Rank.SIX.getWord());
    }
    
    @Test
    public void testGetRankWord7() {
        assertEquals("Seven", Rank.SEVEN.getWord());
    }
    
    @Test
    public void testGetRankWord8() {
        assertEquals("Eight", Rank.EIGHT.getWord());
    }
    
    @Test
    public void testGetRankWord9() {
        assertEquals("Nine", Rank.NINE.getWord());
    }
    
    @Test
    public void testGetRankWord10() {
        assertEquals("Ten", Rank.TEN.getWord());
    }
    
    @Test
    public void testGetRankWordJack() {
        assertEquals("Jack", Rank.JACK.getWord());
    }
    
    @Test
    public void testGetRankWordQueen() {
        assertEquals("Queen", Rank.QUEEN.getWord());
    }
    
    @Test
    public void testGetRankWordKing() {
        assertEquals("King", Rank.KING.getWord());
    }
    
    @Test
    public void testGetWordByLocale() {
        for (Locale locale : LOCALES) {
            ResourceBundle res = ResourceBundle.getBundle("i18n.CardNaming", 
                    locale);
            for (Rank rank : RANKS) {
                String key = "name" + rank.getChars();
                String expected = res.getString(key);
                String actual = rank.getWord(locale);
                String message = "Fetching name for " + rank.getChars() + " in " 
                        + locale.getDisplayName();
                assertEquals(message, expected, actual);
            }
        }
    }
    
    @Test
    public void testGetPluralWord() {
        for (Rank rank : RANKS) {
            String intersperse = rank == Rank.SIX ? "e" : "";
            String expected = rank.getWord() + intersperse + 's';
            String actual = rank.getPluralWord();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testGetPluralWordByLocale() {
        for (Locale locale : LOCALES) {
            ResourceBundle res = ResourceBundle.getBundle("i18n.CardNaming", 
                    locale);
            for (Rank rank : RANKS) {
                String key = "plural" + rank.getChars();
                String expected = res.getString(key);
                String actual = rank.getPluralWord(locale);
                String message = "Fetching name for " + rank.getChars() + " in " 
                        + locale.getDisplayName();
                assertEquals(message, expected, actual);
            }
        }
    }
    
    @Test
    public void testIsCourtRank() {
        System.out.println("isCourtRank");
        assert Rank.JACK.isCourtRank() : "Jacks are court cards";
        assert Rank.QUEEN.isCourtRank() : "Queens are court cards";
        assert Rank.KING.isCourtRank() : "Kings are court cards";
    }
    
    @Test
    public void testIsNotCourtRank() {
        Rank[] ranks = {Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, 
            Rank.SIX, Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN};
        for (Rank rank : ranks) {
            String msg = rank.toString() + " is not a court rank";
            assert !rank.isCourtRank() : msg;
        }
    }
    
    @Test
    public void testCharWordCorrespondence() {
        char expected, actual;
        String message;
        for (Rank rank : Rank.values()) {
            if (rank.getIntVal() > 10) {
                expected = rank.getWord().charAt(0);
                actual = rank.getChar();
                message = "Character for " + rank.toString() + " ought to be '" 
                        + expected + "'";
                assertEquals(message, expected, actual);
            }
        }
    }
    
    @Test
    public void testParseRankExceptionForInvalidInput() {
        String s = "For testing purposes";
        try {
            Rank badRank = Rank.parseRank(s);
            String msg = "Parse input \"" + s 
                    + "\" should have caused exception, not given " 
                    + badRank.getWord();
            fail(msg);
        } catch (NoSuchElementException nsee) {
            System.out.println("Parse input \"" + s 
                    + "\" correctly caused NoSuchElementException");
            System.out.println("\"" + nsee.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for parse input \"" + s + "\"";
            fail(msg);
        }
    }
    
    @Test
    public void testParseRank() {
        System.out.println("parseRank");
        Rank[] ranks = Rank.values();
        for (Rank expected : ranks) {
            Rank actual = Rank.parseRank(expected.getWord());
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testMatches() {
        System.out.println("matches");
        Rank[] ranks = Rank.values();
        CardDeck deck = new CardDeck();
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            for (Rank rank : ranks) {
                String msg = card.toString() + " should not match " 
                        + rank.getWord();
                boolean expected = rank == card.cardRank;
                if (expected) {
                    msg = msg.replace("not ", "");
                }
                boolean actual = rank.matches(card);
                assert expected == actual : msg;
            }
        }
    }
    
}
