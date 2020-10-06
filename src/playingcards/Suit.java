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
 * Enumerates the suits of a standard deck of playing cards. These suits are 
 * listed in the order they appear in the Playing Cards block (U+1F0A0 to 
 * U+1F0FF) of Unicode's Supplementary Multilingual Plane: Spades, Hearts, 
 * Diamonds, Clubs.
 * @author Alonso del Arte
 */
public enum Suit implements CardSpec {
    
    /**
     * Spades. Main Unicode character: &#9824;, alternate &#9828;.
     */
    SPADES ('\u2660', '\u2664', 'S', "Spades"), 
    
    /**
     * Hearts. Main Unicode character: &#9829;, alternate &#9825;.
     */
    HEARTS ('\u2665', '\u2661', 'H', "Hearts"), 
    
    /**
     * Diamonds. Main Unicode character: &#9830;, alternate &#9826;.
     */
    DIAMONDS ('\u2666', '\u2662', 'D', "Diamonds"),
    
    /**
     * Clubs. Main Unicode character: &#9827;, alternate &#9831;.
     */
    CLUBS ('\u2663', '\u2667', 'C', "Clubs");
    
    private final char suitChar;
    private final char altSuitChar;
    private final char suitCharASCII;
    private final String suitWord;
    
    /**
     * Gives the "main" Unicode character for a given suit. Depending on the 
     * operating system and the available fonts, the characters for hearts and 
     * diamonds might show up with a red color. Some programs might even apply 
     * the emoji variant.
     * @return '&#9824;' for spades, '&#9829;' for hearts, '&#9827;' for clubs 
     * and '&#9830;' for diamonds.
     */
    @Override
    public char getChar() {
        return this.suitChar;
    }
    
    /**
     * Gives an "alternative" Unicode character for a given suit. Some programs 
     * that give special treatment to the "main" characters don't give that same 
     * treatment to these characters.
     * @return '&#9828;' for spades, '&#9825;' for hearts, '&#9831;' for clubs 
     * and '&#9826;' for diamonds.
     */
    public char getAltChar() {
        return this.altSuitChar;
    }
    
    /**
     * Gives an ASCII character to represent the suit. This is for use in cases 
     * where a suitable font for the "main" or "alternative" characters is not 
     * available.
     * @return 'S' for spades, 'H' for hearts, 'C' for clubs and 'D' for 
     * diamonds.
     */
    @Override
    public char getCharASCII() {
        return this.suitCharASCII;
    }
    
    /**
     * Gives the emoji variant for the "main" Unicode character for a suit. 
     * Depending on the operating system and available fonts, this might be 
     * displayed as the emoji variant of the suit character, or the text 
     * character followed by a box or question mark, or just two boxes or just 
     * two question marks.
     * @return  "&#9824;&#65039;" for spades, "&#9829;&#65039;" for hearts, 
     * "&#9827;&#65039;" for clubs and "&#9830;&#65039;" for diamonds.
     */
    public String getCharEmojiVariant() {
        return "" + this.suitChar + '\uFE0F';
    }
    
    /**
     * Gives the English word for a suit.  This is for use in cases where a 
     * suitable font for the "main" or "alternative" characters is not 
     * available.
     * @return "Spades" for spades, "Hearts" for hearts, "Clubs" for clubs and 
     * "Diamonds" for diamonds.
     */
    @Override
    public String getWord() {
        return this.suitWord;
    }
    
    Suit(char ch, char ach, char bch, String word) {
        this.suitChar = ch;
        this.altSuitChar = ach;
        this.suitCharASCII = bch;
        this.suitWord = word;
    }
    
}
