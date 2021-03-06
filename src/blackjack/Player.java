/*
 * Copyright (C) 2021 Alonso del Arte
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

/**
 * Represents a player at a blackjack table.
 * @author Alonso del Arte
 */
public class Player {
    
    // TODO: Write it
    
    private final ArrayList<Hand> hands = new ArrayList<>();
    
    ArrayList<Hand> getHands() {
        return new ArrayList<>(this.hands);
    }
    
    void add(Hand hand) {
        this.hands.add(hand);
    }
    
    Player(Hand initialHand) {
        this.hands.add(initialHand);
    }
    
}
