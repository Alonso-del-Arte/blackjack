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

/**
 * Represents a wager for a blackjack hand or bet. Includes an enumeration of 
 * possible outcomes &mdash; natural blackjack, standoff, insurance lost, etc.
 * @author Alonso del Arte
 */
public class Wager {
    
    private final CurrencyAmount wagerAmount;
    
    private boolean settleFlag = false;
    
    private Settlement settlement = null;
    
    /**
     * Retrieves the wager amount. May be called regardless of whether or not 
     * the wager has been settled.
     * @return The wager amount. For example, $100.00.
     */
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
    
    /**
     * Settles the wager.
     * @param outcome The outcome for the settlement. For example, 
     */
    void settle(Outcome outcome) {
        if (this.settleFlag) {
            String excMsg = "Wager was already settled";
            throw new IllegalStateException(excMsg);
        }
        this.settleFlag = true;
        this.settlement = new Settlement(outcome);
    }
    
    /**
     * Gets the settlement. May only be called after
     * @return 
     */
    public Settlement getSettlement() {
        if (!this.settleFlag) {
            String excMsg = "Wager is not settled yet";
            throw new IllegalStateException(excMsg);
        }
        return this.settlement;
    }
    
    /**
     * Sole constructor.
     * @param amount The wager amount. For example, $100.00.
     */
    public Wager(CurrencyAmount amount) {
        if (amount.getAmountInCents() < 1) {
            String excMsg = "Amount " + amount.toString() 
                    + " is not a valid wager amount, needs to be more than " 
                    + amount.getCurrency().getSymbol() + "0";
            throw new IllegalArgumentException("Sorry");
        }
        this.wagerAmount = amount;
    }
    
    /**
     * Enumerates the possible outcomes of a blackjack wager. This includes the 
     * result of a player's hand compared to the dealer's, as well as winning or 
     * losing an insurance bet.
     */
    public static enum Outcome {
        
        /**
         * The player's first two cards are an ace and a ten or a royal card. 
         * Usually merits a 3/2 payout.
         */
        NATURAL_BLACKJACK, 
        
        /**
         * The player's hand of more than two cards is valued at 21. The player 
         * wins, unless the dealer also has a blackjack, in which case it's a 
         * standoff.
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
    
    /**
     * Represents the settlement of a wager.
     */
    public class Settlement {
        
        private final Outcome wagerOutcome;
        
        private final CurrencyAmount outcomeAmount;
        
        /**
         * Gives the outcome that was settled.
         * @return One of {@link Outcome#NATURAL_BLACKJACK}, 
         * {@link Outcome#BLACKJACK}, {@link Outcome#BETTER_SCORE}, {@link 
         * Outcome#STANDOFF}, {@link Outcome#BUST} or {@link 
         * Outcome#LOWER_SCORE} in the case of a wager on a hand, or {@link 
         * Outcome#INSURANCE_WON} or {@link Outcome#INSURANCE_LOST} in the case 
         * of an insurance bet.
         */
        public Outcome getOutcome() {
            return this.wagerOutcome;
        }
        
        /**
         * Gives the amount that was settled. 
         * @return The amount.
         */
        public CurrencyAmount getAmount() {
            return this.outcomeAmount;
        }
        
        /**
         * Constructor. The idea here is that when a hand is settled, the wager 
         * amount goes back to the player's bankroll. And then the settlement 
         * amount will be positive for winning bets, zero for standoffs, or 
         * negative for losing bets, and then that settlement amount is added to 
         * the player's bankroll.
         * @param outcome The outcome, one of {@link Outcome#NATURAL_BLACKJACK}, 
         * {@link Outcome#BLACKJACK}, {@link Outcome#BETTER_SCORE}, {@link 
         * Outcome#STANDOFF}, {@link Outcome#BUST} or {@link 
         * Outcome#LOWER_SCORE} in the case of a wager on a hand, or {@link 
         * Outcome#INSURANCE_WON} or {@link Outcome#INSURANCE_LOST} in the case 
         * of an insurance bet.
         */
        private Settlement(Outcome outcome) {
            switch (outcome) {
                case NATURAL_BLACKJACK:
                    this.outcomeAmount = Wager.this.wagerAmount.times(3)
                            .divides(2);
                    break;
                case BLACKJACK:
                case BETTER_SCORE:
                case INSURANCE_WON:
                    this.outcomeAmount = Wager.this.wagerAmount;
                    break;
                case STANDOFF:
                    this.outcomeAmount = new CurrencyAmount(0, 
                            Wager.this.wagerAmount.getCurrency());
                    break;
                case INSURANCE_LOST:
                case BUST:
                case LOWER_SCORE:
                    this.outcomeAmount = Wager.this.wagerAmount.negate();
                    break;
                default:
                    String excMsg = "Unexpected outcome " + outcome.toString();
                    throw new RuntimeException(excMsg);
            }
            this.wagerOutcome = outcome;
        }
        
    }
    
}
