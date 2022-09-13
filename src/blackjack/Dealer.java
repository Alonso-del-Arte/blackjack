/*
 * Copyright (C) 2022 Alonso del Arte
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

import playingcards.MultiDeckCardDispenser;
import playingcards.Rank;
import playingcards.matchers.RankPairSpec;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a blackjack dealer. The dealer is responsible for dealing cards, 
 * splitting hands per house rules, collecting wagers and paying winning bets.
 * @author Alonso del Arte
 */
public class Dealer {
    
    public static final int MAXIMUM_NUMBER_OF_PLAYERS_AT_TABLE = 7;
    
    private MultiDeckCardDispenser cardDispenser;
    
    private final Set<RankPairSpec> splitSpecs;
    
    private Hand hand;
    
    // TODO: Flesh out
    
    Set<RankPairSpec> giveSplittablePairs() {
//        return new HashSet<>(this.splitSpecs);
return this.splitSpecs;
    }
    
    private static int plasticCardPlace() {
        return ((int) Math.floor(Math.random() * 15) + 60);
    }
    
    private void replenishDispenser() {
        this.cardDispenser = new MultiDeckCardDispenser(6, plasticCardPlace());
    }
    
    /**
     * Sole constructor.
     * @param pairs The set pairs which this dealer will allow to be split. If 
     * empty, the dealer will not allow any pairs to be split.
     * @throws NullPointerException If <code>pairs</code> is null.
     */
    Dealer(Set<RankPairSpec> pairs) {
        if (pairs == null) {
            String excMsg 
                    = "Set of pair specs may be empty but should not be null";
            throw new NullPointerException(excMsg);
        }
        this.splitSpecs = pairs;
        this.cardDispenser = new MultiDeckCardDispenser(6, plasticCardPlace());
        this.hand = null;// new Hand();
    }
    
}
