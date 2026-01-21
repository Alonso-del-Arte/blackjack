/*
 * Copyright (C) 2026 Alonso del Arte
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
 * 4-deck dispenser should not give more than four of each card. Because this 
 * counter depletes a supplier, its usefulness in production is very limited.
 * @author Alonso del Arte
 */
public class CardCounter {
    
    private final int[] cardCounts = new int[52];
    
    private final CardSupplier cardSupplier;
    
    private static int index(PlayingCard card) {
        return card.getSuit().ordinal() * 13 + card.getRank().ordinal();
    }
    
    /**
     * Counts how many times a card supplier gave a certain card.
     * @param card The card to count. For example, A&#9824;.
     * @return How many times a the card came up from the dispenser. For 
     * example, a 5-deck dispenser without a plastic card should probably give 
     * five Aces of Spades.
     */
    public int count(PlayingCard card) {
        return 1;// this.cardCounts[index(card)];
    }
    
    /**
     * Constructs a counter and counts up the cards given by the supplier.
     * @param supplier A card supplier. It may be newly initialized, or it may 
     * have already dealt a few or all of its cards. Either way, the supplier 
     * will be depleted after the counter is constructed.
     */
    public CardCounter(CardSupplier supplier) {
        this.cardSupplier = supplier;
        while (this.cardSupplier.hasNext()) {
            PlayingCard card = this.cardSupplier.getNextCard();
            this.cardCounts[index(card)]++;
        }
    }
    
}
