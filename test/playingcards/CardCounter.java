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
 *
 * @author Alonso del Arte
 */
public class CardCounter {
    
    private static PlayingCard[] cardArray = new PlayingCard[52];
    
    static {
        int index = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cardArray[index] = new PlayingCard(rank, suit);
                index++;
            }
        }
    }
    
    private int[] cardCounts = new int[52];
    
    private final CardSupplier cardSupplier;
    
    private int index(PlayingCard card) {
        return card.getSuit().ordinal() * 13 + card.getRank().ordinal();
    }
    
    public int count(PlayingCard card) {
        return this.cardCounts[index(card)];
    }
    
    public CardCounter(CardSupplier supplier) {
        this.cardSupplier = supplier;
        PlayingCard card;
        while (this.cardSupplier.hasNext()) {
            card = this.cardSupplier.getNextCard();
            this.cardCounts[index(card)]++;
        }
    }
    
}
