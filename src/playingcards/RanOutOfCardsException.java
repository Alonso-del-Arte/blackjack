/*
 * Copyright (C) 2020 Alonso del Arte
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

/**
 * Indicates that a deck, hand or other grouping of cards has run out of cards. 
 * This exception should generally only be used when the caller has ignored some 
 * other more normal indication that the deck, hand or other grouping has run 
 * out of cards. The object throwing this exception may indicate if the deficit 
 * is of a specific rank (e.g., aces) or a specific suit (e.g., spades), 
 * suggesting that cards of other ranks or suits may be available.
 * @author Alonso del Arte
 */
public class RanOutOfCardsException extends RuntimeException {
    
    private final Rank specRank;
    private final Suit specSuit;
    
    private final boolean rankNotNullFlag;
    private final boolean suitNotNullFlag;
    
    /**
     * Indicates whether this exception was caused by a deficiency of cards of a 
     * specific rank. An exception handler may opt to check
     * @return True if a rank was specified at the time this exception was 
     * constructed, false otherwise.
     */
    public boolean rankDeficient() {
        return this.rankNotNullFlag;
    }
    
    public boolean suitDeficient() {
        return this.suitNotNullFlag;
    }
    
    public Rank getRank() {
        return this.specRank;
    }
    
    public Suit getSuit() {
        return this.specSuit;
    }
    
    public RanOutOfCardsException(String msg) {
        this(msg, null, null);
    }
    
    public RanOutOfCardsException(String msg, Rank rank) {
        this(msg, rank, null);
    }
    
    public RanOutOfCardsException(String msg, Suit suit) {
        this(msg, null, suit);
    }
    
    private RanOutOfCardsException(String msg, Rank rank, Suit suit) {
        super(msg);
        this.specRank = rank;
        this.specSuit = suit;
        this.rankNotNullFlag = (this.specRank != null);
        this.suitNotNullFlag = (this.specSuit != null);
    }
    
}
