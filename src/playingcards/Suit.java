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
 *
 * @author Alonso del Arte
 */
enum Suit {
    
    SPADES ('\u2664', 'S', "Spades"), 
    HEARTS ('\u2665', 'H', "Hearts"), 
    CLUBS ('\u2667', 'C', "Clubs"), 
    DIAMONDS ('\u2666', 'D', "Diamonds");
    
    private final char suitChar;
    private final char suitCharASCII;
    private final String suitWord;
    
    char getSuitChar() {
        return this.suitChar;
    }
    
    char getSuitCharASCII() {
        return this.suitCharASCII;
    }
    
    String getSuitWord() {
        return this.suitWord;
    }
    
    Suit(char ch, char bc, String word) {
        this.suitChar = ch;
        this.suitCharASCII = bc;
        this.suitWord = word;
    }
    
}
