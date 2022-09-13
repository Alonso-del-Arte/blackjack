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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Dealer class.
 * @author Alonso del Arte
 */
public class DealerTest {
    
    private static final Random RANDOM = new Random();
    
    @Test
    public void testGiveSplittablePairs() {
        System.out.println("giveSplittablePairs");
        List<RankPairSpec> pairSpecs = new ArrayList<>();
        Rank[] ranks = Rank.values();
        for (Rank rank : ranks) {
            RankPairSpec pairSpec = new RankPairSpec(rank, rank);
            pairSpecs.add(pairSpec);
        }
        int index = RANDOM.nextInt(pairSpecs.size());
        pairSpecs.remove(index);
        Set<RankPairSpec> expected = new HashSet<>(pairSpecs);
        Dealer dealer = new Dealer(expected);
        Set<RankPairSpec> actual = dealer.giveSplittablePairs();
        assertEquals(expected, actual);
    }

    @Test
    public void testGiveSplittablePairsDoesNotLeakReference() {
        fail("Haven't written test yet");
    }
    
    @Test
    public void testConstructorRejectsNullSet() {
        Set<RankPairSpec> badSet = null;
        try {
            Dealer badDealer = new Dealer(badSet);
            String msg = "Should not have been able to create " 
                    + badDealer.toString() 
                    + " with null set of rank pair specifications";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to use null set correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null constructor parameter";
            fail(msg);
        }
    }
    
}
