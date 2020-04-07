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
 * Defines three functions that objects that hand out cards should implement.
 * @author Alonso del Arte
 */
public interface CardSupplier {
    
    /**
     * Tells whether the card supplier can give another card.
     * @return True if the card supplier can give another card, false if not.
     */
    boolean hasNext();
    
    /**
     * Supplies one card to the caller. The card may or may not be random.
     * @return A playing card.
     * @throws RanOutOfCardsException If there is no card left to give.
     */
    PlayingCard getNextCard();
    
    /**
     * Determines if a card came from this card supplier.
     * @param card The playing card to check the provenance of.
     * @return True if the card came from this card supplier, false otherwise.
     */
    boolean provenance(PlayingCard card);
    
}
