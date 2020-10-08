/*
 * Copyright (C) 2020 Alonso del Arte
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
package playingcards.matchers;

import playingcards.PlayingCard;
import playingcards.Rank;

/**
 * Specifies ranks for a pair of cards. Order is not taken into account.
 * @author Alonso del Arte
 */
public class RankPairSpec extends PairSpec<Rank> {
    
    /**
     * Determines whether or not two cards match this pair specification. Order 
     * does not matter. For example, suppose this pair specification is for 
     * Sevens and Queens.
     * @param cardA One of the cards of the pair. For example, 7&#9830;.
     * @param cardB The other card of the pair. For example, Q&#9827;.
     * @return True if the cards match this pair specification, false otherwise. 
     * In the example, 7&#9830; and Q&#9827; match this pair specification. If 
     * the cards were switched, with <code>cardA</code> being Q&#9827; and 
     * <code>cardB</code> being 7&#9830;, the result would also be true. But it 
     * would be false if both cards were Sevens, or if both cards were Queens, 
     * etc.
     * @throws NullPointerException If either card is null.
     */
    @Override
    public boolean matches(PlayingCard cardA, PlayingCard cardB) {
        return this.matches(cardA.getRank(), cardB.getRank());
    }
    
    /**
     * Constructor. Order does not matter, the superclass takes care of ensuring 
     * equality and matching operations are carried out without regard for the 
     * order of constructor parameters.
     * @param rankA Rank of one card of a pair of cards. May or may not be the 
     * same as <code>rankB</code>. For example, Seven.
     * @param rankB  Rank of one card of a pair of cards. May or may not be the 
     * same as <code>rankA</code>. For example, Queen.
     */
    public RankPairSpec(Rank rankA, Rank rankB) {
        super(rankA, rankB);
    }

}
