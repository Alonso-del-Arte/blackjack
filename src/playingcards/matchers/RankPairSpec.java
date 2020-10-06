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
    
    @Override
    public boolean matches(PlayingCard cardA, PlayingCard cardB) {
        return this.matches(cardA.getRank(), cardB.getRank());
    }
    
    public RankPairSpec(Rank rankA, Rank rankB) {
        super(rankA, rankB);
    }

}
