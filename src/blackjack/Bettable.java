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
package blackjack;

/**
 *
 * @author Alonso del Arte
 */
abstract class Bettable {
    
    boolean settled = false;
    
    Wager associatedWager;
    
    /**
     * Indicates whether the hand or insurance bet has been settled. A hand is 
     * settled if there is an outcome (blackjack, bust, stand-off, etc.), 
     * <em>and</em> the payout has been paid or the losing wager has been 
     * collected. Examples: a natural blackjack that has been paid at 3:2, a 
     * player's hand with two Tens but the dealer still has a face down card, a 
     * player's hand with two Tens and the dealer's hand has the same value and 
     * the dealer has collected the wager. An insurance bet is settled once the 
     * dealer's face down card is revealed and the dealer pays out or collects 
     * accordingly.
     * @return True if the hand or insurance bet is settled, false otherwise. In 
     * the examples, true for the natural blackjack paid at 3:2 and also true 
     * for the two Tens that stood off with the dealer, but false for the two 
     * Tens still waiting on the dealer's hand.
     */
    public boolean isSettled() {
        return this.settled;
    }
    
    public Wager getWager() {
        return this.associatedWager;
    }
    
    public abstract Wager.Settlement getSettlement();
    
    Bettable(Wager wager) {
        if (wager == null) {
            String excMsg = "Wager should not be null";
            throw new NullPointerException(excMsg);
        }
        this.associatedWager = wager;
    }
    
}
