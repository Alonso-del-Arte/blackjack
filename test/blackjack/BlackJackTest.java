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

import playingcards.Rank;
import playingcards.matchers.RankPairSpec;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the BlackJack class.
 * @author Alonso del Arte
 */
public class BlackJackTest {
    
    @Test
    public void testSameRankPairs() {
        Set<RankPairSpec> pairs = new HashSet<>();
        Rank[] ranks = Rank.values();
        for (Rank rank : ranks) {
            pairs.add(new RankPairSpec(rank, rank));
        }
        String msg = "SAME_RANK_PAIRS should contain " + pairs.toString() 
                + " and no others";
        assert BlackJack.SAME_RANK_PAIRS.containsAll(pairs) : msg;
        assertEquals(msg, pairs.size(), BlackJack.SAME_RANK_PAIRS.size());
    }
    
    @Test
    public void testDistinctTenPairs() {
        Set<RankPairSpec> pairs = new HashSet<>();
        pairs.add(new RankPairSpec(Rank.TEN, Rank.JACK));
        pairs.add(new RankPairSpec(Rank.TEN, Rank.QUEEN));
        pairs.add(new RankPairSpec(Rank.TEN, Rank.KING));
        pairs.add(new RankPairSpec(Rank.JACK, Rank.QUEEN));
        pairs.add(new RankPairSpec(Rank.JACK, Rank.KING));
        pairs.add(new RankPairSpec(Rank.QUEEN, Rank.KING));
        String msg = "DISTINCT_TEN_PAIRS should contain " + pairs.toString() 
                + " and no others";
        assert BlackJack.DISTINCT_TEN_PAIRS.containsAll(pairs) : msg;
        assertEquals(msg, pairs.size(), BlackJack.DISTINCT_TEN_PAIRS.size());
    }
    
    @Test
    public void testDistinctAddToSixteenPairs() {
        Set<RankPairSpec> pairs = new HashSet<>();
        pairs.add(new RankPairSpec(Rank.ACE, Rank.FIVE));
        pairs.add(new RankPairSpec(Rank.SIX, Rank.TEN));
        pairs.add(new RankPairSpec(Rank.SIX, Rank.JACK));
        pairs.add(new RankPairSpec(Rank.SIX, Rank.QUEEN));
        pairs.add(new RankPairSpec(Rank.SIX, Rank.KING));
        pairs.add(new RankPairSpec(Rank.SEVEN, Rank.NINE));
        String msg = "DISTINCT_ADD_TO_16 should contain " + pairs.toString() 
                + " and no others";
        assert BlackJack.DISTINCT_ADD_TO_16.containsAll(pairs) : msg;
        assertEquals(msg, pairs.size(), BlackJack.DISTINCT_ADD_TO_16.size());
    }
    
}
