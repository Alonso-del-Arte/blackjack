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
public enum Rank {
    
    ACE (14, "A", "Ace", false),
    TWO (2, "2", "Two", false), 
    THREE (3, "3", "Three", false), 
    FOUR (4, "4", "Four", false), 
    FIVE (5, "5", "Five", false), 
    SIX (6, "6", "Six", false), 
    SEVEN (7, "7", "Seven", false), 
    EIGHT (8, "8", "Eight", false), 
    NINE (9, "9", "Nine", false), 
    TEN (10, "10", "Ten", false),
    JACK (11, "J", "Jack", true),
    QUEEN (12, "Q", "Queen", true),
    KING (13, "K", "King", true);
    
    private final int rank;
    private final String rankChars;
    private final String rankWord;
    private final boolean faceCardFlag;
    
    public int getRank() {
        return this.rank;
    }
    
    public String getRankChars() {
        return this.rankChars;
    }
    
    public String getRankWord() {
        return this.rankWord;
    }
    
    /**
     * Tells whether a given rank corresponds to a court card or not. Other 
     * terms for "court card" include "face card," "picture card" and "painting 
     * card."
     * @return True for Jacks, Queens and Kings, false for all others.
     */
    public boolean isCourtCard() {
        return this.faceCardFlag;
    }
    
    Rank(int n, String nchars, String word, boolean cc) {
        this.rank = n;
        this.rankChars = nchars;
        this.rankWord = word;
        this.faceCardFlag = cc;
    }
    
}
