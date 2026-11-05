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
public class Insurance extends Bettable {
    
    private Wager associatedWager;
    
    @Override
    public Wager getWager() {
        return this.associatedWager;
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
    
    Insurance(Wager wager) {
        super(wager);
        if (wager != null) {
        if (!wager.isInsuranceWager()) {
            String excMsg = wager.toString() + " is not an insurance wager";
            throw new IllegalArgumentException(excMsg);
        }}
        this.associatedWager = wager;
    }
    
}
