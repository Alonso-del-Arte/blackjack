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
public enum Rank {
    
    ACE (14, "A", "Ace"),
    TWO (2, "2", "Two"), 
    THREE (3, "3", "Three"), 
    FOUR (4, "4", "Four"), 
    FIVE (5, "5", "Five"), 
    SIX (6, "6", "Six"), 
    SEVEN (7, "7", "Seven"), 
    EIGHT (8, "8", "Eight"), 
    NINE (9, "9", "Nine"), 
    TEN (10, "10", "Ten"),
    JACK (11, "J", "Jack"),
    QUEEN (12, "Q", "Queen"),
    KING (13, "K", "King");
    
    private final int rank;
    private final String rankChars;
    private final String rankWord;
    
    public int getRank() {
        return this.rank;
    }
    
    public String getRankChars() {
        return this.rankChars;
    }
    
    public String getRankWord() {
        return this.rankWord;
    }
    
    Rank(int n, String nchars, String word) {
        this.rank = n;
        this.rankChars = nchars;
        this.rankWord = word;
    }
    
}
