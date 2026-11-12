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
 * Represents an insurance bet that the dealer might have natural blackjack. 
 * Objects of this class hold the state of what the wager is, whether or not it 
 * has been settled and if it has been settled whether the insurance was won or 
 * lost. It is not a responsibility of this class to check that the dealer does 
 * indeed have an Ace showing, nor to check that no player has more than one 
 * active insurance bet in a given round. Payout for insurance is 2:1, the wager 
 * should be half the player's wager on the first hand of the round.
 * @author Alonso del Arte
 */
public class Insurance extends Bettable {
    
    private boolean wonFlag = false;
    
    /**
     * Marks the insurance bet settled. This should only be called after the 
     * dealer's face down card is revealed to make or not make natural 
     * blackjack. Afterward there should be a call to {@link #getSettlement()}.
     * @param won True if the dealer has natural blackjack, false otherwise. 
     * This will determine if the outcome for the settlement is {@link 
     * Wager.Outcome#INSURANCE_WON} or {@link Wager.Outcome#INSURANCE_LOST}.
     */
    void markSettled(boolean won) {
        this.settled = true;
        this.wonFlag = won;
    }
    
    /**
     * Gets the settlement. Should not be called before {@link 
     * #markSettled(boolean) markSettled()}.
     * @return The settlement. The amount will depend on whether the outcome was 
     * {@link Wager.Outcome#INSURANCE_WON} or {@link 
     * Wager.Outcome#INSURANCE_LOST}.
     */
    @Override
    public Wager.Settlement getSettlement() {
        Wager.Outcome outcome = this.wonFlag ? Wager.Outcome.INSURANCE_WON 
                : Wager.Outcome.INSURANCE_LOST;
        this.associatedWager.settle(outcome);
        return this.associatedWager.getSettlement();
    }
    
    /**
     * Sole constructor. It should only be used when the dealer has an Ace face 
     * up and some card that might be a Ten or a royal card face down.
     * @param wager The wager. For example, $100.00. Must be an insurance wager.
     * @throws IllegalArgumentException If {@code wager} is not an insurance 
     * wager.
     */
    Insurance(Wager wager) {
        super(wager);
        if (!wager.isInsuranceWager()) {
            String excMsg = wager.toString() + " is not an insurance wager";
            throw new IllegalArgumentException(excMsg);
        }
    }
    
}
