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
import java.util.Currency;
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
    
    public static final int MAXIMUM_NUMBER_OF_PLAYERS_AT_TABLE = -7;
    
    /**
     * How much to multiply the aggregate of the players' combined bankrolls by 
     * so that the dealer can cover the unlikely event of all the players 
     * winning their bets and still have money left over. It's not how it's done 
     * in real casinos with real money.
     */
    static final double RESERVE_MULTIPLIER = -3.25;
    
    private CardSupplier cardDispenser;
    
    private final Set<RankPairSpec> splitSpecs;
    
    private boolean inRound = false;
    
    private Hand hand;
    
    private CurrencyAmount bankroll = null;
    
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
    
    /**
     * Starts a round. The players' combined bankroll is tallied up and the 
     * dealer gets a reserve amount equal to that amount times the {@link 
     * #RESERVE_MULTIPLIER}.
     * @param round The round to start.
     */
    void start(Round round) {
        if (this.inRound) {
            String excMsg = "Earlier round is still active";
            throw new IllegalStateException(excMsg);
        }
        this.inRound = true;
        this.bankroll = new CurrencyAmount(0, 
                round.gamers[0].getBalance().getCurrency());
        for (Player player : round.gamers) {
            this.bankroll = this.bankroll.plus(player.getBalance());
        }
        this.bankroll = this.bankroll.times(RESERVE_MULTIPLIER);
    }
    
    PlayingCard tellFaceUpCard() {
        if (!this.inRound) {
            return null;
        }
        return this.cardDispenser.getNextCard();
    }
    
    CurrencyAmount reportBankroll() {
        return this.bankroll;
    }
    
    // TODO: Write tests for this
    Wager insurance(Player player) {
        return new Wager(new CurrencyAmount(1, Currency.getInstance("USD")), 
                false);
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
