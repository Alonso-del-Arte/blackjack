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

/**
 * Enumerates the ranks of the playing cards: Aces, Twos, Threes, ..., Queens, 
 * Kings. Provides certain basic information, but no image data. This 
 * enumeration does not include Jokers.
 * @author Alonso del Arte
 */
public enum Rank implements CardSpec {
    
    /**
     * Ace. Pip card. The letter A appears on the card. The value of an Ace  
     * depends on the particular game being played. The numerical value given 
     * here is 14.
     */
    ACE (14, 'A', "A", "Ace", false),
    
    /**
     * Two. Perfectly symmetrical pip card. The number 2 appears on the card.
     */
    TWO (2, '2', "2", "Two", false), 
    
    /**
     * Three. Pip card. The number 3 appears on the card.
     */
    THREE (3, '3', "3", "Three", false), 
    
    /**
     * Four. Pip card. The number 4 appears on the card.
     */
    FOUR (4, '4', "4", "Four", false), 
    
    /**
     * Five. Pip card. The number 5 appears on the card.
     */
    FIVE (5, '5', "5", "Five", false), 
    
    /**
     * Six. Pip card. The number 6 appears on the card.
     */
    SIX (6, '6', "6", "Six", false), 
    
    /**
     * Seven. Pip card. The number 7 appears on the card.
     */
    SEVEN (7, '7', "7", "Seven", false), 
    
    /**
     * Eight. Pip card. The number 8 appears on the card.
     */
    EIGHT (8, '8', "8", "Eight", false), 
    
    /**
     * Nine. Pip card. The number 9 appears on the card.
     */
    NINE (9, '9', "9", "Nine", false), 
    
    /**
     * Ten. Perfectly symmetrical pip card. The number 10 appears on the card.
     */
    TEN (10, '\u2169', "10", "Ten", false),
    
    /**
     * Jack. The lowest court card, generally valued at 11. The letter J appears 
     * on the card.
     */
    JACK (11, 'J', "J", "Jack", true),
    
    /**
     * Queen. Court card, generally valued at 12. The letter Q appears on the 
     * card.
     */
    QUEEN (12, 'Q', "Q", "Queen", true),
    
    /**
     * King. The highest court card, generally valued at 13. The letter K 
     * appears on the card.
     */
    KING (13, 'K', "K", "King", true);
    
    private final int rank;
    private final char rankChar;
    private final String rankChars;
    private final String rankWord;
    private final boolean faceCardFlag;
    
    /**
     * Gives the integer associated with this rank.
     * @return 14 for ACE, 2 for TWO, 3 for THREE, ..., 10 for TEN, 11 for JACK, 
     * 12 for QUEEN, 13 for KING.
     */
    public int getRank() {
        return this.rank;
    }
    
    /**
     * Gives the character associated with this rank, which appears on the card 
     * itself, except for {@link #TEN}.
     * @return The character for this rank, except for {@link #TEN}, for which 
     * this returns &#x2169; (Roman numeral 10).
     */
    @Override
    public char getChar() {
        return this.rankChar;
    }
    
    /**
     * Gives the character associated with this rank, which appears on the card 
     * itself, except for {@link #TEN}.
     * @return The character for this rank, except for {@link #TEN}, for which 
     * this returns '0'.
     */
    @Override
    public char getCharASCII() {
        switch (this) {
            case TEN:
                return '0';
            default:
                return this.rankChar;
        }
    }
    
    /**
     * Gives one or two characters suitable for concatenating with a suit 
     * character.
     * @return "A" for ACE, "2" for TWO, "3" for THREE, ..., "10" for TEN, "J" 
     * for JACK, "Q" for QUEEN, "K" for KING.
     */
    public String getChars() {
        return this.rankChars;
    }
    
    /**
     * Gives the English word for this rank.
     * @return "Ace" for ACE, "Two" for TWO, "Three" for THREE, ..., "Ten" for 
     * TEN, "Jack" for JACK, "Queen" for QUEEN, "King" for KING.
     */
    @Override
    public String getWord() {
        return this.rankWord;
    }
    
    /**
     * Tells whether a given rank corresponds to courts card or not. Other terms 
     * for "court card" include "face card," "picture card" and "painting card."
     * @return True for Jacks, Queens and Kings, false for all others.
     */
    public boolean isCourtRank() {
        return this.faceCardFlag;
    }
    
    Rank(int n, char ch, String nChars, String word, boolean cr) {
        this.rank = n;
        this.rankChar = ch;
        this.rankChars = nChars;
        this.rankWord = word;
        this.faceCardFlag = cr;
    }
    
}
