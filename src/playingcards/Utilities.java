/*
 * Copyright (C) 2023 Alonso del Arte
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

import java.util.Comparator;

/**
 *
 * @author Alonso del Arte
 */
public class Utilities {
    
    /**
     * Comparator for playing cards in a new standard deck. Note that for now 
     * {@link PlayingCard} doesn't represent Jokers nor ad cards. The new deck 
     * order consists of two Jokers, Spades from Ace to King, Diamonds from Ace 
     * to King, Clubs from King to Ace (notice the reversal) and Hearts from 
     * King to Ace.
     */
    public static Comparator<PlayingCard> BRAND_NEW_DECK_ORDER 
            = (PlayingCard cardA, PlayingCard cardB) 
                    -> Integer.compare(sortingValueInNewDeck(cardA), 
                            sortingValueInNewDeck(cardB));
    
    /**
     * Comparator for playing cards in the default order as given by {@link 
     * CardDeck} before calling {@link CardDeck#shuffle() shuffle()}.
     */
    public static Comparator<PlayingCard> DEFAULT_ORDER 
            = (PlayingCard cardA, PlayingCard cardB) 
                    -> Integer.compare(sortingValue(cardA), 
                            sortingValue(cardB));
    
    /**
     * Gives the sorting value of a rank.
     * @param rank The rank for which a sorting value is needed. For example, 
     * {@link Rank#QUEEN}.
     * @return The sorting value. For example, 12.
     */
    public static int sortingValue(Rank rank) {
        return rank.ordinal() + 1;
    }
    
    /**
     * Gives the sorting value of a suit. The order is Spades, Diamonds, Clubs, 
     * Hearts. This may or may not match the order in the {@link Suit} 
     * enumeration.
     * @param suit The suit for which a sorting value is needed. For example, 
     * {@link Suit#DIAMONDS}.
     * @return The sorting value. For example, 1.
     */
    public static int sortingValue(Suit suit) {
        switch (suit) {
            case SPADES:
                return 0;
            case DIAMONDS:
                return 1;
            case CLUBS:
                return 2;
            case HEARTS:
                return 3;
            default:
                String excMsg = "Suit " + suit.getPluralWord() 
                        + " not recognized";
                throw new RuntimeException(excMsg);
        }
    }
    
    private static int sortingValueInNewDeck(PlayingCard card) {
        int suitValue = sortingValue(card.getSuit()) * 13;
        int rankValue = sortingValue(card.getRank());
        if (suitValue > 25) {
            rankValue = 14 - rankValue;
        }
        return suitValue + rankValue;
    }
    
    private static int sortingValue(PlayingCard card) {
        int suitValue = card.getSuit().ordinal() * 13;
        int rankValue = sortingValue(card.getRank());
        return suitValue + rankValue;
    }
    
}
