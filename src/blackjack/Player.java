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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player at a blackjack table.
 * @author Alonso del Arte
 */
public class Player {
    
    private final String playerName;
    
    private final ArrayList<Hand> hands = new ArrayList<>();
    
    public String getName() {
        return this.playerName;
    }
    
    public int getActiveHandsCount() {
        return this.hands.size();
    }
    
    public Hand getCurrentActiveHand() {
        java.util.Currency currency = java.util.Currency
                .getInstance(java.util.Locale.ITALY);
        currency.CurrencyAmount amount 
                = new currency.CurrencyAmount(100, currency);
        Wager wager = new Wager(amount);
        return new Hand(wager);
    }
    
    List<Hand> getHands() {
        return new ArrayList<>(this.hands);
    }
    
    void add(Hand hand) {
        this.hands.add(hand);
    }
    
    /**
     * Sole constructor.
     * @param name The player's name. For example, "Marla". Must not be null. 
     * Must not be empty.
     * @throws IllegalArgumentException If <code>name</code> is "".
     * @throws NullPointerException  If <code>name</code> is null.
     */
    public Player(String name) {
        if (name == null) {
            String excMsg = "Player name must not be null";
            throw new NullPointerException(excMsg);
        }
        if (name.equals("")) {
            String excMsg = "Player name must not be empty";
            throw new IllegalArgumentException(excMsg);
        }
        this.playerName = name;
    }
    
}
