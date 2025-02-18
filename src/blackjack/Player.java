/*
 * Copyright (C) 2025 Alonso del Arte
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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player at a blackjack table.
 * @author Alonso del Arte
 */
public class Player {
    
    private final String playerName;
    
    private final ArrayList<Hand> hands = new ArrayList<>();
    
    private CurrencyAmount bankroll = new CurrencyAmount(Long.MIN_VALUE, 
            java.util.Currency.getInstance("LYD"));
    
    public String getName() {
        return this.playerName;
    }
    
    /**
     * Tells how many active hands this player has. The count does not include 
     * hands for which the wager has been settled.
     * @return Usually 1 during a round, but might be 2 or more depending on 
     * whether the player has split any hands and how many splits are allowed. 
     * Should be 0 in between rounds.
     */
    public int getActiveHandsCount() {
        return (int) this.hands.stream().filter(hand -> !hand.isSettledHand())
                .count();
    }
    
    // TODO: Write tests for this
    public Hand getCurrentActiveHand() {
        java.util.Currency currency = java.util.Currency
                .getInstance(java.util.Locale.ITALY);
        currency.CurrencyAmount amount 
                = new currency.CurrencyAmount(100, currency);
        Wager wager = new Wager(amount);
        return new Hand(wager);
    }
    
    /**
     * Gives a list of all the player's hands. This includes hands for which the 
     * wager has been settled.
     * @return A fresh list of hands that the caller can modify freely.
     */
    public List<Hand> getHands() {
        return new ArrayList<>(this.hands);
    }
    
    /**
     * Tells how much the player's bankroll is.
     * @return The amount of the bankroll.
     */
    public CurrencyAmount getBalance() {
        return this.bankroll;
    }
    
    void add(Hand hand) {
        this.hands.add(hand);
    }
    
    /**
     * Adds money to the player's bankroll. This would be analogous to a player 
     * in real life buying more chips. This procedure is not meant to be used 
     * for settling wagers, a different mechanism will be provided for that.
     * @param amount The amount to be added. For example, $500. Must not be 
     * negative. May be zero, but there's not much point to that.
     * @throws currency.CurrencyConversionNeededException If the amount to be 
     * added is of a different currency. For example, if the player's bankroll 
     * is $2,000, trying to add 100&euro; would cause this exception.
     */
    public void add(CurrencyAmount amount) {
        if (amount.isNegative()) {
            String excMsg = "Can't add " + amount.toString() 
                    + " to bankroll, should not be negative";
            throw new IllegalArgumentException(excMsg);
        }
        this.bankroll = this.bankroll.plus(amount);
    }
    
    /**
     * Sole constructor.
     * @param name The player's name. For example, "Marla". Must not be null. 
     * Must not be empty.
     * @param initialBankroll How much money the player has in chips ready to 
     * wager.
     * @throws IllegalArgumentException If <code>name</code> is "" or if the 
     * initial bankroll is 0 or negative.
     * @throws NullPointerException  If <code>name</code> is null.
     */
    public Player(String name, CurrencyAmount initialBankroll) {
        if (name == null) {
            String excMsg = "Player name must not be null";
            throw new NullPointerException(excMsg);
        }
        if (name.equals("")) {
            String excMsg = "Player name must not be empty";
            throw new IllegalArgumentException(excMsg);
        }
        if (initialBankroll.getAmountInCents() == 0L) {
            String excMsg = "Initial bankroll should not be $0.00";
            throw new IllegalArgumentException(excMsg);
        }
//        if (initialBankroll.isNotPositive()) {
//            String excMsg = "Initial bankroll should be positive, not " 
//                    + initialBankroll.toString();
//            throw new IllegalArgumentException(excMsg);
//        }
        this.playerName = name;
        this.bankroll = initialBankroll;
    }
    
}
