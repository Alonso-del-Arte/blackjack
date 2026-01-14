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
 * Defines three functions that objects that hand out cards should implement. 
 * The implementing class may be a deck or a group of decks. This interface does 
 * not require implementing classes to provide a shuffling procedure.
 * @author Alonso del Arte
 */
public interface CardSupplier {
    
    /**
     * Tells whether the card supplier can give another card. It's up to the 
     * implementer to keep track of which cards have been handed out. 
     * @return True if the card supplier can give another card, false if not.
     */
    boolean hasNext();
    
    /**
     * Supplies one card to the caller. The card may or may not be random. The 
     * implementer should not lose track of dealt cards, so as to be able to 
     * attest to their provenance later on.
     * @return A playing card. For example, 4&#9827;.
     * @throws RanOutOfCardsException If there is no card left to give.
     */
    PlayingCard getNextCard();
    
    /**
     * Reports how many cards are remaining to be dealt. For the example, 
     * suppose this is a shoe of ten decks from which a blackjack dealer has so 
     * far given himself two cards and a pair of cards each to three players at 
     * the table.
     * @return The number of cards available to be dealt. In the example, this 
     * would be 512. Edge case: 0 for a depleted deck (in which case {@link 
     * #hasNext()} should return false and {@link #getNextCard()} should throw 
     * {@link RanOutOfCardsException}).
     */
    int countRemaining();
    
    /**
     * Determines if a card came from this card supplier. This should generally 
     * be implemented through referential equality.
     * @param card The playing card to check the provenance of.
     * @return True if the card came from this card supplier, false otherwise.
     */
    boolean provenance(PlayingCard card);
    
}
