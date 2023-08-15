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
    
    static final Random RANDOM = new Random();
    
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
        String msg = "Pair spec set should contain all of " 
                + expected.toString();
        assert actual.containsAll(expected) : msg;
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void testGiveSplittablePairsDoesNotLeakReference() {
        RankPairSpec pairSpec1 = new RankPairSpec(Rank.EIGHT, Rank.EIGHT);
        Set<RankPairSpec> pairSpecSet = new HashSet<>();
        pairSpecSet.add(pairSpec1);
        Dealer dealer = new Dealer(pairSpecSet);
        Set<RankPairSpec> retrievedPairSpecSet1 = dealer.giveSplittablePairs();
        RankPairSpec pairSpec2 = new RankPairSpec(Rank.NINE, Rank.NINE);
        retrievedPairSpecSet1.add(pairSpec2);
        Set<RankPairSpec> retrievedPairSpecSet2 = dealer.giveSplittablePairs();
        assertNotEquals(retrievedPairSpecSet1, retrievedPairSpecSet2);
    }
    
    @Test
    public void testNewlyInstantiatedNotActiveAlready() {
        Dealer dealer = new Dealer();
        String msg = "New dealer should not be active in round already";
        assert !dealer.active() : msg;
    }
    
    @Test
    public void testActive() {
        Dealer dealer = new Dealer();
        Player player = PlayerTest.getPlayer();
        Round round = new Round(dealer, player);
        dealer.start(round);
        String msg = "After starting round with " + dealer.toString() + " and " 
                + " player " + player.getName() 
                + ", dealer should be active in round";
        assert dealer.active() : msg;
    }
    
    @Test
    public void testBankrollNullAtFirst() {
        Dealer dealer = new Dealer();
        assertNull(dealer.reportBankroll());
    }
    
    public void testReportBankroll() {
        Dealer dealer = new Dealer();
        Player player = PlayerTest.getPlayer();
        Round round = new Round(dealer, player);
        dealer.start(round);
        fail();
    }
    
    @Test
    public void testAuxConstructorHasTypicalSplitPairSpecs() {
        Dealer dealer = new Dealer();
        Set<RankPairSpec> expected = new HashSet<>(BlackJack.SAME_RANK_PAIRS);
        expected.addAll(BlackJack.DISTINCT_TEN_PAIRS);
        Set<RankPairSpec> actual = dealer.giveSplittablePairs();
        String msg = "Pair spec set should contain all of " 
                + expected.toString();
        assert actual.containsAll(expected) : msg;
        assertEquals(expected.size(), actual.size());
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
    
    @Test
    public void testConstructorMakesNewPairSpecSetFromParam() {
        RankPairSpec pairSpec1 = new RankPairSpec(Rank.EIGHT, Rank.EIGHT);
        Set<RankPairSpec> pairSpecSet = new HashSet<>();
        pairSpecSet.add(pairSpec1);
        Dealer dealer = new Dealer(pairSpecSet);
        RankPairSpec pairSpec2 = new RankPairSpec(Rank.NINE, Rank.NINE);
        pairSpecSet.add(pairSpec2);
        Set<RankPairSpec> retrievedPairSpecSet = dealer.giveSplittablePairs();
        assertNotEquals(pairSpecSet, retrievedPairSpecSet);
    }
    
}
