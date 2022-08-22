/*
 * Copyright (C) 2022 Alonso del Arte
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
package blackjack;

/**
 * Represents outcomes of a player's wager at the blackjack table.
 * @author Alonso del Arte
 */
public enum Outcome {
    
    /**
     * A natural blackjack is when the player gets to 21 right from the first 
     * two cards they're dealt (meaning an ace and a ten or royal card). Payout 
     * is 3/2, e.g., if the player wagered $100, the dealer pays them $150.
     */
    NATURAL_BLACKJACK, 
    
    /**
     * A regular blackjack is when the player gets to 21 from three or more 
     * cards in their hand. For example, if the player has 8&#9824;, 7&#9829; 
     * and 6&#9830;, that's blackjack.
     */
    BLACKJACK, 
    
    /**
     * When a player goes over 21, they're said to have gone bust.
     */
    BUST, 
    
    /**
     * When a player goes over 21, they're said to have gone bust. A dealer can 
     * go bust, too, but if both the dealer and the player go bust, the dealer 
     * still collects the player's wager.
     */
    BUST_WITH_DEALER_BUST,
    
    // TODO: Finish writing Javadoc
    /**
     * When neither the player nor the dealer has 21, but neither [FINISH 
     * WRITING]
     */
    STANDOFF, 
    
    OVER,
    
    UNDER;
    
}
