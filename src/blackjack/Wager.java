/*
 * Copyright (C) 2023 Alonso del Arte
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

import currency.CurrencyAmount;

import java.util.Currency;
import java.util.Locale;

/**
 * Represents a wager for a blackjack hand or bet. Includes an enumeration of 
 * possible outcomes &mdash; natural blackjack, standoff, insurance lost, etc.
 * @author Alonso del Arte
 */
public class Wager {
    
    private final CurrencyAmount wagerAmount;
    
    private boolean settleFlag = false;
    
    public CurrencyAmount getAmount() {
        return this.wagerAmount;
    }
    
    /**
     * Tells whether the wager is settled or not. A wager is settled with a call 
     * to {@link #settle(blackjack.Wager.Outcome) settle()}.
     * @return True if the wager is settled, false otherwise.
     */
    public boolean isSettled() {
        return this.settleFlag;
    }
    
    // TODO: Write tests for this
    void settle(Outcome outcome) {
        this.settleFlag = true;
    }
    
    // TODO: Write tests for this
    public Settlement getSettlement() {
        if (!this.settleFlag) {
            String excMsg = "Wager is not settled yet";
            throw new IllegalStateException(excMsg);
        }
        return new Settlement(Outcome.BUST, this.wagerAmount);
    }
    
    public Wager(CurrencyAmount amount) {
        if (amount.getAmountInCents() < 1) {
            String excMsg = "Non-positive amount " + amount.toString() 
                    + " is not a valid wager amount";
            throw new IllegalArgumentException(excMsg);
        }
        this.wagerAmount = amount;
    }
    
    public static enum Outcome {
        
        /**
         * The player's first two cards are an ace and a ten or a royal card. 
         * Usually merits a 3/2 payout.
         */
        NATURAL_BLACKJACK, 
        
        /**
         * The player's hand of more than two cards is valued at 21. The player 
         * wins.
         */
        BLACKJACK, 
        
        /**
         * The player stood below 21 but the dealer has a lower score or busted. 
         * For example, say the player stood at 20 and the dealer drew to 19. 
         * The player wins the wager. Or say the player stood at 20 but the 
         * dealer busted at 22. The player also wins.
         */
        BETTER_SCORE,
        
        /**
         * The player wins an insurance bet that the dealer has a blackjack. The 
         * wager is usually equal to half the hand's wager and pays 2 to 1.
         */
        INSURANCE_WON,
        
        /**
         * Neither the dealer nor the player has blackjack but they're tied 
         * below 21. The dealer does not collect the player's wager for the hand 
         * (but might collect a player's insurance bet).
         */
        STANDOFF, 
        
        /**
         * The player loses an insurance bet that the dealer has a blackjack. 
         * The wager is usually equal to half the hand's wager and pays 2 to 1.
         */
        INSURANCE_LOST, 
        
        /**
         * The player's hand is valued at more than 21. The dealer collects the 
         * player's wager even if the dealer also goes bust.
         */
        BUST, 
        
        /**
         * The player did not get blackjack nor go bust, but the dealer has a 
         * higher score without busting. The dealer collects the player's wager.
         */
        LOWER_SCORE
        
    }
    
    public class Settlement {
        
        // TODO: Write tests for this
        public Outcome getOutcome() {
            return Outcome.STANDOFF;
        }
        
        // TODO: Write tests for this
        public CurrencyAmount getAmount() {
            return new CurrencyAmount(0, Currency.getInstance(Locale.ITALY));
        }
        
        private Settlement(Outcome outcome, CurrencyAmount amount) {
            //
        }
        
    }
    
}
