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

/**
 * Represents a playing card from a standard 52-card deck. The idea of making 
 * the constructor private so as to make equality and referential equality 
 * identical was considered. However, given that some card games are played with 
 * two or more decks, I decided to make the constructor package private instead, 
 * so as to allow games to track from which deck a card comes from.
 * <p>No provision for wildcards. Implementing wildcards is left up to the game 
 * implementations. No Jokers, no ad cards.</p>
 * @author Alonso del Arte
 */
public class PlayingCard {

    final Rank cardRank;
    
    final Suit cardSuit;

    /**
     * Gives a text representation of the playing card. Uses the Unicode playing 
     * card characters from the Miscellaneous Symbols block, U+2600 to U+26FF. 
     * For console applications with potentially limited character sets, it may 
     * be preferable to use {@link #toASCIIString()}.
     * @return A text representation like "8&#9824;".
     */
    @Override
    public String toString() {
        return this.cardRank.getChars() + this.cardSuit.getChar();
    }

    /**
     * Gives an ASCII text representation of the playing card. This should be 
     * suitable for use in a console application with a potentially limited 
     * character set (e.g., if playing on the Windows command prompt, characters 
     * like '&#9824;' would probably show up as '?' &mdash; though the character 
     * for the rank, such as '8', should show up just fine). Note that this is 
     * in English regardless of locale.
     * @return A text representation using only ASCII digits and letters. For 
     * example, "Eight of Spades".
     */
    public String toASCIIString() {
        return this.cardRank.getWord() + " of " + this.cardSuit.getWord();
    }
    
    // TODO: Write tests for this
    // TODO: Update Javadoc for toUnicodeSMPChar() once tests for this function 
    // are passing
    public String toInternationalizedString() {
        return "SORRY, NOT IMPLEMENTED YET";
    }
    
    /**
     * Gives a Unicode Supplementary Multilingual Plane (SMP) character to 
     * represent this playing card. Because of spotty font support and the need 
     * for surrogate pairs, in most contexts it will be preferable to use {@link 
     * #toASCIIString()} or {@link #toString()}.
     * @return A Unicode SMP character represented as high surrogate U+D83C 
     * followed by a low surrogate in the range U+DCA1 (for A&#9824;) to U+DCD1 
     * (for K&#9827;).
     */
    public String toUnicodeSMPChar() {
        char highSurrogate = '\uD83C';
        char aceBase = '\uDCA1';
        int adj = this.cardSuit.ordinal() * 16 + this.cardRank.ordinal();
        int knightAdj = (this.cardRank.ordinal() > 10) ? 1 : 0;
        char lowSurrogate = (char) (aceBase + adj + knightAdj);
        return "" + highSurrogate + lowSurrogate;
    }

    /**
     * Gives the value of this card. It should be suitable for most games. Some 
     * games might need tweaking for some or all cards, or for some cards in 
     * certain contexts (e.g., an Ace as the highest card of a royal flush in 
     * poker, or an Ace in a blackjack hand that has another Ace).
     * @return An integer value. For most pip cards like 8&#9824;, it should be 
     * the number of pips, e.g., 8. Though for Aces like A&#9830; it will be 14. 
     * Jacks are 11, Queens are 12 and Kings are 13.
     */
    public int cardValue() {
        return this.cardRank.getRank();
    }
    
    /**
     * Gives the enumeration of this card's rank.
     * @return An enumeration field. For example, {@code Rank.NINE} for 
     * 9&#9827;.
     */
    public Rank getRank() {
        return this.cardRank;
    }
    
    /**
     * Determines if this card is of the specified rank.
     * @param rank The rank to check. For example, {@code Rank.SEVEN}.
     * @return True if this card is of the specified rank, false if it is not. 
     * For example, true for 7&#9827;, false for 8&#9827;.
     */
    public boolean isOf(Rank rank) {
        return this.cardRank == rank;
    }
    
    /**
     * Determines if this card is the same rank as another card. To match by 
     * suit, use {@link #isSameSuit(playingcards.PlayingCard) isSameSuit()}.
     * @param other The card to check against. For example, 7&#9827;.
     * @return True if the other card is of the same rank, false if it is not. 
     * For example, if this card is 7&#9830; and the other card is 7&#9827;, 
     * then true. But if this card is 8&#9830; and the other card is 7&#9827;, 
     * then false.
     */
    public boolean isSameRank(PlayingCard other) {
        return this.cardRank == other.cardRank;
    }
    
    /**
     * Gives the enumeration of this card's suit.
     * @return An enumeration field. For example, {@code Suit.HEARTS} for 
     * 10&#9829;.
     */
    public Suit getSuit() {
        return this.cardSuit;
    }
    
    /**
     * Determines if this card is of the specified suit.
     * @param suit The suit to check. For example, {@code Suit.HEARTS}.
     * @return True if this card is of the specified suit, false if it is not. 
     * For example, true for 10&#9829;, false for 10&#9830;.
     */
    public boolean isOf(Suit suit) {
        return this.cardSuit == suit;
    }
    
    /**
     * Determines if this card is the same suit as another card. To match by 
     * rank, use {@link #isSameRank(playingcards.PlayingCard) isSameRank()}.
     * @param other The card to check against. For example, 10&#9829;.
     * @return True if the other card is of the same rank, false if it is not. 
     * For example, if this card is 10&#9829; and the other card is Q&#9829;, 
     * then true. But if this card is 10&#9829; and the other card is 10&#9830;, 
     * then false.
     */
    public boolean isSameSuit(PlayingCard other) {
        return this.cardSuit == other.cardSuit;
    }
    
    /**
     * Tells whether this card is a court card or not. Other terms for "court 
     * card" include "face card," "picture card" and "painting card."
     * @return True for Jacks, Queens and Kings, false for all others.
     */
    public boolean isCourtCard() {
        return this.cardRank.isCourtRank();
    }
    
    /**
     * Gives the color to use for text elements on this playing card. The color 
     * is only a suggestion, it may be tweaked or altogether ignored depending 
     * on the use case.
     * @return {@code Color.BLACK} if this card is one of Spades or Clubs, 
     * {@code Color.RED} if this card is one of Hearts or Diamonds.
     */
    public Color getTextColor() {
        return this.cardSuit.getTextColor();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        PlayingCard other = (PlayingCard) obj;
        if (!this.cardSuit.equals(other.cardSuit)) {
            return false;
        }
        return this.cardRank.equals(((PlayingCard) obj).cardRank);
    }

    @Override
    public int hashCode() {
        return (this.cardSuit.hashCode() << 16) + this.cardRank.hashCode();
    }

    /**
     * Sole constructor. Constructs a card of the specified rank and suit.
     * @param rank The rank of the card. For example, {@code Rank.ACE}.
     * @param suit The suit of the card. For example, {@code Suit.SPADES}.
     */
    PlayingCard(Rank rank, Suit suit) {
        this.cardRank = rank;
        this.cardSuit = suit;
    }

}
