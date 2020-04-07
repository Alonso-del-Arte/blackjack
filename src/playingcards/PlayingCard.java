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
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

/**
 * Represents a playing card from a standard 52-card deck. The idea of making 
 * the constructor private so as to make equality and referential equality 
 * identical was considered. However, given that some card games are played with 
 * two or more decks, I decided to make the constructor package private instead, 
 * so as to allow games to track from which deck a card comes from.
 * <p>No provision for wildcards. Implementing wildcards is left up to the game 
 * implementations.</p>
 * @author Alonso del Arte
 */
public class PlayingCard {

    private final Rank cardRank;
    private final Suit cardSuit;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(this.getClass().equals(obj.getClass()))) {
            return false;
        }
        final PlayingCard other = (PlayingCard) obj;
        if (!this.cardSuit.equals(other.cardSuit)) {
            return false;
        }
        return (this.cardRank.equals(other.cardRank));
    }

    @Override
    public int hashCode() {
        return this.cardValue() * this.cardSuit.getSuitChar();
    }

    @Override
    public String toString() {
        return this.cardRank.getRankChars() + this.cardSuit.getSuitChar();
    }

    public String toASCIIString() {
        return this.cardRank.getRankWord() + " of " + this.cardSuit.getSuitWord();
    }

    public int cardValue() {
        return this.cardRank.getRank();
    }
    
    public Rank getRank() {
        return this.cardRank;
    }
    
    public boolean isOfRank(Rank rank) {
        return this.cardRank == rank;
    }
    
    public boolean isSameRank(PlayingCard other) {
        return this.cardRank == other.cardRank;
    }
    
    public Suit getSuit() {
        return this.cardSuit;
    }
    
    public boolean isOfSuit(Suit suit) {
        return this.cardSuit == suit;
    }
    
    public boolean isSameSuit(PlayingCard other) {
        return this.cardSuit == other.cardSuit;
    }

    PlayingCard(Rank rank, Suit suit) {
        this.cardRank = rank;
        this.cardSuit = suit;
    }

}
