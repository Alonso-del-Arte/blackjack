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

/**
 * Represents a round of blackjack. A round starts with the dealer and the 
 * players getting cards, and continues until all wagers have been settled.
 * @author Alonso del Arte
 */
public class Round {
    
    private boolean started = false;
    
    final Player[] gamers;
    
    /**
     * Indicates whether or not this round has begun.
     * @return True if the round has begun, false otherwise.
     */
    public boolean begun() {
        return this.started;
    }
    
    /**
     * Begins the round.
     * @throws IllegalStateException If the round has already begun.
     */
    public void begin() {
        if (this.started) {
            String excMsg = "Round begun already";
            throw new IllegalStateException(excMsg);
        }
        this.started = true;
    }
    
    // TODO: Write tests for this
    public void insure(Player player) {
        //
    }
    
    // TODO: Write tests for this
    public void hit(Player player) {
        //
    }
    
    // TODO: Write tests for this
    public void split(Player player) {
        //
    }
    
    // TODO: Write tests for this
    public boolean completed() {
        return true;
    }
    
    public Round(Dealer dealer, Player... players) {
        if (dealer == null) {
            String excMsg = "Dealer must not be null";
            throw new NullPointerException(excMsg);
        }
        if (players.length == 0) {
            String excMsg = "At least one player required";
            throw new IllegalArgumentException(excMsg);
        }
        for (Player player : players) {
            if (player == null) {
                String excMsg = "No player may be null";
                throw new NullPointerException(excMsg);
            }
        }
        this.gamers = players;
    }
    
}
