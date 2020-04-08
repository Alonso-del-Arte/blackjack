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
 * Provides a way to count the cards given out by a card supplier. This class 
 * should only be accessible to test classes, to facilitate the testing of card 
 * distributions. For example, a deck should only give one of each card, a 
 * 4-deck dispenser should not give more than four of each card.
 * @author Alonso del Arte
 */
public class CardCounter {
    
    private final int[] cardCounts = new int[52];
    
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
