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
 * Objects of this class hold the state of what the wager is and whether or not 
 * it has been settled. It is not a responsibility of this class to check that 
 * the dealer does indeed have an Ace showing, nor to check that no player has 
 * more than one active insurance bet in a given round. Payout for insurance is 
 * 2:1, the wager should be half the player's wager on the first hand of the 
 * round.
 * @author Alonso del Arte
 */
public class Insurance extends Bettable {
    
    private boolean settleFlag = false;
    
    private boolean wonFlag = false;
    
    void markSettled(boolean won) {
        this.settleFlag = true;
        this.wonFlag = won;
    }
    
    @Override
    public boolean isSettled() {
        return this.settleFlag;
    }
    
    // TODO: Write tests for this
    @Override
    public Wager.Settlement getSettlement() {
        java.util.Currency currency = java.util.Currency.getInstance("XCD");
        currency.CurrencyAmount amount 
                = new currency.CurrencyAmount(1, currency);
        Wager wager = new Wager(amount);
        wager.settle(Wager.Outcome.BUST);
        return wager.getSettlement();
    }
    
    /**
     * Sole constructor.
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
