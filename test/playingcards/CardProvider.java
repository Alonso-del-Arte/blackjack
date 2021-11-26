/*
 * Copyright (C) 2021 Alonso del Arte
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
 * Provides cards according to specifications. This is to be used for testing 
 * purposes only. And even then only under very narrow circumstances. Use only 
 * if {@link CardServer} doesn't quite meet your testing needs.
 * @author Alonso del Arte
 */
public class CardProvider {
    
    /**
     * Gives the four cards of a given rank. Most likely they will be in the 
     * order given in the {@link Suit} enumeration.
     * @param rank The rank. For example, Ten.
     * @return An array with the four cards of the specified rank. In the 
     * example of Ten, this would be 10&spades;, 10&hearts;, 10&diams; and 
     * 10&clubs;.
     */
    public PlayingCard[] giveCards(Rank rank) {
        PlayingCard[] array = new PlayingCard[4];
        Suit[] suits = Suit.values();
        for (Suit suit : suits) {
            array[suit.ordinal()] = new PlayingCard(rank, suit);
        }
        return array;
    }
    
    /**
     * Gives the thirteen cards of a given suit. Most likely will be in the 
     * order given in the {@link Rank} enumeration.
     * @param suit The suit. For example, Spades.
     * @return An array with thirteen cards of the specified suit. In the 
     * example, that would be A&spades;, 2&spades;, 3&spades;, 4&spades;, 
     * 5&spades;, 6&spades;, 7&spades;, 8&spades;, 9&spades;, 10&spades;, 
     * J&spades;, Q&spades; and K&spades;.
     */
    public PlayingCard[] giveCards(Suit suit) {
        PlayingCard[] array = new PlayingCard[13];
        Rank[] ranks = Rank.values();
        for (Rank rank : ranks) {
            array[rank.ordinal()] = new PlayingCard(rank, suit);
        }
        return array;
    }
    
    /**
     * Provides a card of a specified rank and suit. Remember that the playing 
     * card constructor is package private.
     * @param rank The rank of the card. For example, Aces.
     * @param suit The suit of the card. For example, Spades.
     * @return A card of the specified rank and suit. The card will be 
     * recognized as equal to other cards matching in both rank and suit, but it 
     * won't be considered to come from any deck.
     */
    public PlayingCard giveCard(Rank rank, Suit suit) {
        return new PlayingCard(rank, suit);
    }
    
}
