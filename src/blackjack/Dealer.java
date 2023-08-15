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
import playingcards.CardSupplier;
import playingcards.MultiDeckCardDispenser;
import playingcards.PlayingCard;
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
    
    private boolean inRound = false;
    
    private Hand hand;
    
    private CurrencyAmount bankroll;
    
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
    
    boolean active() {
        return this.inRound;
    }
    
    void start(Round round) {
        this.inRound = true;
// TODO: Write tests for this
    }
    
    PlayingCard tellFaceUpCard() {
        return this.cardDispenser.getNextCard();
    }
    
        // TODO: Write tests for this
    CurrencyAmount reportBankroll() {
        return null;
    }
    
    // TODO: Write tests for this
    PlayingCard hit(Player player) {
        return null;
    }
    
    // TODO: Write tests for this
    boolean split(Player player) {
        return false;
    }
    
    void stand(Player player) {
        // TODO: Write tests for this
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
        this(pairs, new MultiDeckCardDispenser(6, plasticCardPlace()));
    }
    
    /**
     * Package private constructor. This is mainly to be used by this class and 
     * by test classes in this package.
     * @param pairs The set of pairs which this dealer will allow to be split. 
     * May be empty, must not be null. If empty, the dealer will not allow any 
     * pairs to be split.
     * @param cardSupplier A card supplier. In production use, this should be a 
     * fresh {@link playingcards.MultiDeckCardDispenser} with six decks and a 
     * plastic card placed at a somewhat random spot near the bottom.
     * @throws NullPointerException If <code>pairs</code> is null.
     */
    public Dealer(Set<RankPairSpec> pairs, CardSupplier cardSupplier) {
        this.splitSpecs = new HashSet<>(pairs);
        this.cardDispenser = cardSupplier;
        this.hand = null;// new Hand();
    }
    
}
