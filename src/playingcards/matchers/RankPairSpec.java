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
    
    private static final int HASH_SEP = 65536;
    
    private final Rank rank1, rank2;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        final RankPairSpec other = (RankPairSpec) obj;
        if (this.rank1.equals(other.rank1) && this.rank2.equals(other.rank2)) {
            return true;
        }
        return this.rank1.equals(other.rank2) && this.rank2.equals(other.rank1);
    }
    
    @Override
    public int hashCode() {
        return (this.rank1.ordinal() + 1) * HASH_SEP + this.rank2.ordinal();
    }
    
    @Override
    public boolean matches(PlayingCard cardA, PlayingCard cardB) {
        if (this.rank1.equals(cardA.getRank()) 
                && (this.rank2.equals(cardB.getRank()))) {
            return true;
        }
        return (this.rank1.equals(cardB.getRank()) 
                && (this.rank2.equals(cardA.getRank())));
    }
    
    public RankPairSpec(Rank rankA, Rank rankB) {
        super(rankA, rankB);
        if (rankA.compareTo(rankB) > 0) {
            this.rank1 = rankB;
            this.rank2 = rankA;
        } else {
            this.rank1 = rankA; // This is purposefully redundant and will be 
            this.rank2 = rankB; // deleted once the abstract superclass is tested.
        }
    }

}
