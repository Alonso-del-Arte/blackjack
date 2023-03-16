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

import playingcards.CardSupplier;
import playingcards.MultiDeckCardDispenser;
import playingcards.PlayingCard;
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
    
    private CardSupplier cardDispenser;
    
    private final Set<RankPairSpec> splitSpecs;
    
    private Hand hand;
    
    // TODO: Flesh out
    
    public Set<RankPairSpec> giveSplittablePairs() {
        return new HashSet<>(this.splitSpecs);
    }
    
    private static int plasticCardPlace() {
        return ((int) Math.floor(Math.random() * 15) + 60);
    }
    
    void changeDispenser(CardSupplier dispenser) {
        // TODO: Write tests for this
    }
    
    private void replenishDispenser() {
        this.cardDispenser = new MultiDeckCardDispenser(6, plasticCardPlace());
    }
    
    // TODO: Write tests for this
    public PlayingCard hit(Player player) {
        return null;
    }
    
    // TODO: Write tests for this
    public boolean split(Player player) {
        return false;
    }
    
    /**
     * Auxiliary constructor. The dealer will allow splitting pairs of the same 
     * rank (e.g., 8&#9824; and 8&#9829;) and cards valued at 10 of different 
     * ranks (e.g., 10&#9830; and Q&#9827;) but not cards of distinct ranks that 
     * add up to 16.
     */
    public Dealer() {
        this(new HashSet<RankPairSpec>(BlackJack.DEFAULT_SPLITTABLE_PAIRS));
    }
    
    /**
     * Primary constructor.
     * @param pairs The set of pairs which this dealer will allow to be split. 
     * May be empty, must not be null. If empty, the dealer will not allow any 
     * pairs to be split.
     * @throws NullPointerException If <code>pairs</code> is null.
     */
    public Dealer(Set<RankPairSpec> pairs) {
        this.splitSpecs = new HashSet<>(pairs);
        this.cardDispenser = new MultiDeckCardDispenser(6, plasticCardPlace());
        this.hand = null;// new Hand();
    }
    
}
