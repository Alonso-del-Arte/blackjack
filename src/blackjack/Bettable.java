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
    
    // TODO: Write a test for this
    public Wager getWager() {
        java.util.Currency currency = java.util.Currency.getInstance("XCD");
        currency.CurrencyAmount amount 
                = new currency.CurrencyAmount(1, currency);
        return new Wager(amount);
    }
    
    public abstract Wager.Settlement getSettlement();
    
    Bettable(Wager wager) {
        //
    }
    
}
