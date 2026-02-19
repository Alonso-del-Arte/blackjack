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

import java.awt.Color;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Suit enumerated type.
 * @author Alonso del Arte
 */
public class SuitTest {
    
    private static final Locale[] LOCALES = Locale.getAvailableLocales();
    
    private static final Suit[] SUITS = Suit.values();
    
    @Test
    public void testGetSpadesChar() {
        System.out.println("getChar");
        assertEquals('\u2660', Suit.SPADES.getChar());
    }
    
    @Test
    public void testGetDiamondsChar() {
        assertEquals('\u2666', Suit.DIAMONDS.getChar());
    }
    
    @Test
    public void testGetHeartsChar() {
        assertEquals('\u2665', Suit.HEARTS.getChar());
    }
    
    @Test
    public void testGetClubsChar() {
        assertEquals('\u2663', Suit.CLUBS.getChar());
    }
    
    @Test
    public void testGetSpadesAltChar() {
        System.out.println("getAltChar");
        assertEquals('\u2664', Suit.SPADES.getAltChar());
    }
    
    @Test
    public void testGetDiamondsAltChar() {
        assertEquals('\u2662', Suit.DIAMONDS.getAltChar());
    }
    
    @Test
    public void testGetHeartsAltChar() {
        assertEquals('\u2661', Suit.HEARTS.getAltChar());
    }
    
    @Test
    public void testGetClubsAltChar() {
        assertEquals('\u2667', Suit.CLUBS.getAltChar());
    }
    
    @Test
    public void testGetSpadesASCIIChar() {
        System.out.println("getCharASCII");
        assertEquals('S', Suit.SPADES.getCharASCII());
    }
    
    @Test
    public void testGetDiamondsASCIIChar() {
        assertEquals('D', Suit.DIAMONDS.getCharASCII());
    }
    
    @Test
    public void testGetHeartsASCIIChar() {
        assertEquals('H', Suit.HEARTS.getCharASCII());
    }
    
    @Test
    public void testGetClubsASCIIChar() {
        assertEquals('C', Suit.CLUBS.getCharASCII());
    }
    
    @Test
    public void testGetSpadesCharEmojiVariant() {
        System.out.println("getCharEmojiVariant");
        assertEquals("\u2660\uFE0F", Suit.SPADES.getCharEmojiVariant());
    }
    
    @Test
    public void testGetDiamondsCharEmojiVariant() {
        assertEquals("\u2666\uFE0F", Suit.DIAMONDS.getCharEmojiVariant());
    }
    
    @Test
    public void testGetHeartsCharEmojiVariant() {
        assertEquals("\u2665\uFE0F", Suit.HEARTS.getCharEmojiVariant());
    }
    
    @Test
    public void testGetClubsCharEmojiVariant() {
        assertEquals("\u2663\uFE0F", Suit.CLUBS.getCharEmojiVariant());
    }
    
    @Test
    public void testGetSpadesWord() {
        System.out.println("getWord");
        assertEquals("Spades", Suit.SPADES.getWord());
    }
    
    @Test
    public void testGetDiamondsWord() {
        assertEquals("Diamonds", Suit.DIAMONDS.getWord());
    }
    
    @Test
    public void testGetHeartsWord() {
        assertEquals("Hearts", Suit.HEARTS.getWord());
    }
    
    @Test
    public void testGetClubsWord() {
        assertEquals("Clubs", Suit.CLUBS.getWord());
    }
    
    @Test
    public void testGetWordByLocale() {
        for (Locale locale : LOCALES) {
            ResourceBundle res = ResourceBundle.getBundle("i18n.CardNaming", 
                    locale);
            for (Suit suit : SUITS) {
                String key = suit.getWord().toLowerCase() + "Name";
                String expected = res.getString(key);
                String actual = suit.getWord(locale);
                String message = "Fetching name for " + suit.getChar() + " in " 
                        + locale.getDisplayName();
                assertEquals(message, expected, actual);
            }
        }
    }
    
    @Test
    public void testGetPluralWord() {
        for (Suit suit : SUITS) {
            String expected = suit.getWord();
            String actual = suit.getPluralWord();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testGetPluralWordByLocale() {
        for (Locale locale : LOCALES) {
            ResourceBundle res = ResourceBundle.getBundle("i18n.CardNaming", 
                    locale);
            for (Suit suit : SUITS) {
                String key = suit.getWord().toLowerCase() + "Name";
                String expected = res.getString(key);
                String actual = suit.getPluralWord(locale);
                String message = "Fetching name for " + suit.getChar() + " in " 
                        + locale.getDisplayName();
                assertEquals(message, expected, actual);
            }
        }
    }
    
    /**
     * Test of the getTextColor function, of the Suit enumerated type.
     */
    @Test
    public void testGetTextColor() {
        System.out.println("getTextColor");
        assertEquals(Color.BLACK, Suit.SPADES.getTextColor());
        assertEquals(Color.RED, Suit.HEARTS.getTextColor());
        assertEquals(Color.RED, Suit.DIAMONDS.getTextColor());
        assertEquals(Color.BLACK, Suit.CLUBS.getTextColor());
    }
    
    @Test
    public void testParseSuitExceptionForInvalidInput() {
        String s = "For testing purposes";
        try {
            Suit badSuit = Suit.parseSuit(s);
            String msg = "Parse input \"" + s 
                    + "\" should have caused exception, not given " 
                    + badSuit.getWord();
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
    public void testParseSuit() {
        System.out.println("parseSuit");
        Suit[] suits = Suit.values();
        for (Suit expected : suits) {
            Suit actual = Suit.parseSuit(expected.getWord());
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testMatches() {
        System.out.println("matches");
        Suit[] suits = Suit.values();
        CardDeck deck = new CardDeck();
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            for (Suit suit : suits) {
                String msg = card.toString() + " should not match " 
                        + suit.getWord();
                boolean expected = suit == card.cardSuit;
                if (expected) {
                    msg = msg.replace("not ", "");
                }
                boolean actual = suit.matches(card);
                assert expected == actual : msg;
            }
        }
    }
    
}
