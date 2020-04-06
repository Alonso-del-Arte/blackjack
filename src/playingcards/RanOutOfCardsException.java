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
     * specific rank. An exception handler may opt to check this before calling 
     * {@link #getRank() getRank()}.
     * @return True if a rank was specified at the time this exception was 
     * constructed, false otherwise.
     */
    public boolean rankDeficient() {
        return this.rankNotNullFlag;
    }
    
    /**
     * Indicates whether this exception was caused by a deficiency of cards of a 
     * specific suit. An exception handler may opt to check this before calling 
     * {@link #getSuit() getSuit()}.
     * @return True if a suit was specified at the time this exception was 
     * constructed, false otherwise.
     */
    public boolean suitDeficient() {
        return this.suitNotNullFlag;
    }
    
    /**
     * Retrieves the rank specified at the time the exception was constructed. 
     * An exception handler may call {@link #rankDeficient() rankDeficient()} 
     * before using this getter.
     * @return The rank specified at the time this exception was constructed 
     * (for example, Queen). May be null.
     */
    public Rank getRank() {
        return this.specRank;
    }
    
    /**
     * Retrieves the suit specified at the time the exception was constructed. 
     * An exception handler may call {@link #suitDeficient() suitDeficient()} 
     * before using this getter.
     * @return The suit specified at the time this exception was constructed 
     * (for example, Spades). May be null.
     */
    public Suit getSuit() {
        return this.specSuit;
    }
    
    /**
     * Constructs a RanOutOfCardsException with the specified detail message. No 
     * rank nor suit is specified.
     * @param msg The detail message. For example, "Ran out of cards".
     */
    public RanOutOfCardsException(String msg) {
        this(msg, null, null);
    }
    
    /**
     * Constructs a RanOutOfCardsException with the specified detail message and 
     * card rank.
     * @param msg The detail message. For example, "Ran out of Eights".
     * @param rank The rank that caused this exception. For example, 
     * <code>Rank.EIGHT</code>.
     */
    public RanOutOfCardsException(String msg, Rank rank) {
        this(msg, rank, null);
    }
    
    /**
     * Constructs a RanOutOfCardsException with the specified detail message and 
     * card suit.
     * @param msg The detail message. For example, "Ran out of Diamonds".
     * @param suit  The suit that caused this exception. For example, 
     * <code>Suit.DIAMONDS</code>.
     */
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
