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

import playingcards.MultiDeckCardDispenser;
import playingcards.Rank;
import playingcards.matchers.RankPairSpec;

import java.util.HashSet;

/**
 * Represents a blackjack dealer. The dealer is responsible for dealing cards, 
 * splitting hands per house rules, collecting wagers and paying winning bets.
 * @author Alonso del Arte
 */
public class Dealer {
    
    private MultiDeckCardDispenser cardDispenser;
    
    private final HashSet<RankPairSpec> splitSpecs;
    
    private Hand hand;
    
    // TODO: Flesh out
    
    // STUB TO FAIL THE FIRST TEST
    HashSet<RankPairSpec> giveUsualPairs() {
        HashSet<RankPairSpec> pairs = new HashSet<>();
        return pairs;
    }
    
    // STUB TO FAIL THE FIRST TEST
    HashSet<RankPairSpec> giveTenPairs() {
        HashSet<RankPairSpec> pairs = new HashSet<>();
        return pairs;
    }
    
    // STUB TO FAIL THE FIRST TEST
    HashSet<RankPairSpec> giveSixteenPairs() {
        HashSet<RankPairSpec> pairs = new HashSet<>();
        return pairs;
    }
    
    private static int plasticCardPlace() {
        return ((int) Math.floor(Math.random() * 15) + 60);
    }
    
    private void replenishDispenser() {
        this.cardDispenser = new MultiDeckCardDispenser(6, plasticCardPlace());
    }
    
    Dealer(HashSet<RankPairSpec> pairs) {
        this.splitSpecs = pairs;
        this.cardDispenser = new MultiDeckCardDispenser(6, plasticCardPlace());
        this.hand = null;// new Hand();
    }
    
}
