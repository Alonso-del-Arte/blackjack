/*
 * Copyright (C) 2026 Alonso del Arte
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

import playingcards.CardServer;
import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the RankPairSpec class.
 * @author Alonso del Arte
 */
public class RankPairSpecTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final int NUMBER_OF_RANKS = RANKS.length;
    
    private static final List<Rank> RANKS_LIST = Arrays.asList(RANKS);
    
    private static final CardServer SERVER = new CardServer(2);
    
    @Test
    public void testToString() {
        System.out.println("toString");
        for (int a = 0; a < NUMBER_OF_RANKS; a++) {
            Rank rankA = RANKS[a];
            for (int b = a; b < NUMBER_OF_RANKS; b++) {
                Rank rankB = RANKS[b];
                RankPairSpec instance = new RankPairSpec(rankA, rankB);
                String expected = "(" + rankA.getWord() + "," + rankB.getWord() 
                        + ")";
                String actual = instance.toString().replace(" ", "");
                assertEquals(expected, actual);
            }
        }
    }
    
    @Test
    public void testToStringInvertedAtConstruction() {
        int start = NUMBER_OF_RANKS - 1;
        for (int a = start; a > 0; a--) {
            Rank rankA = RANKS[a];
            for (int b = 0; b < a; b++) {
                Rank rankB = RANKS[b];
                RankPairSpec instance = new RankPairSpec(rankA, rankB);
                String expected = "(" + rankB.getWord() + "," + rankA.getWord() 
                        + ")";
                String actual = instance.toString().replace(" ", "");
                assertEquals(expected, actual);
            }
        }
    }
    
    @Test
    public void testReferentialEquality() {
        for (Rank rankA : RANKS) {
            for (Rank rankB : RANKS) {
                RankPairSpec instance = new RankPairSpec(rankA, rankB);
                String message = instance.toString() 
                        + " should be equal to itself";
                assertEquals(message, instance, instance);
            }
        }
    }
    
    @Test
    public void testNotEqualsNull() {
        Rank rankA = SERVER.getNextCard().getRank();
        Rank rankB = SERVER.getNextCard().getRank();
        RankPairSpec spec = new RankPairSpec(rankA, rankB);
        String message = spec.toString() + " should not equal null";
        assertNotEquals(message, spec, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        PlayingCard cardA = SERVER.getNextCard();
        PlayingCard cardB = SERVER.getNextCard();
        Rank rankA = cardA.getRank();
        Rank rankB = cardB.getRank();
        RankPairSpec rankSpec = new RankPairSpec(rankA, rankB);
        Suit suitA = cardA.getSuit();
        Suit suitB = cardB.getSuit();
        SuitPairSpec suitSpec = new SuitPairSpec(suitA, suitB);
        String message = rankSpec.toString() + " should not equal " 
                + suitSpec.toString();
        assertNotEquals(message, rankSpec, suitSpec);
    }
    
    @Test
    public void testNotEqualsDiffRankA() {
        Collections.shuffle(RANKS_LIST);
        Rank rankB = RANKS_LIST.getFirst();
        Collections.shuffle(RANKS_LIST);
        List<Rank> ranks = new ArrayList<>(RANKS_LIST);
        Rank origRankA = ranks.removeFirst();
        RankPairSpec unexpected = new RankPairSpec(origRankA, rankB);
        String msgPart = unexpected.toString() + " should not equal ";
        for (Rank rankA : ranks) {
            RankPairSpec actual = new RankPairSpec(rankA, rankB);
            String message = msgPart + actual.toString();
            assertNotEquals(message, unexpected, actual);
        }
    }
    
    @Test
    public void testNotEqualsDiffRankB() {
        Collections.shuffle(RANKS_LIST);
        Rank rankA = RANKS_LIST.getFirst();
        Collections.shuffle(RANKS_LIST);
        List<Rank> ranks = new ArrayList<>(RANKS_LIST);
        Rank origRankB = ranks.removeFirst();
        RankPairSpec unexpected = new RankPairSpec(rankA, origRankB);
        String msgPart = unexpected.toString() + " should not equal ";
        for (Rank rankB : ranks) {
            RankPairSpec actual = new RankPairSpec(rankA, rankB);
            String message = msgPart + actual.toString();
            assertNotEquals(message, unexpected, actual);
        }
    }
        
    @Test
    public void testEquals() {
        System.out.println("equals");
        Collections.shuffle(RANKS_LIST);
        Rank rankA = RANKS_LIST.getFirst();
        Collections.shuffle(RANKS_LIST);
        Rank rankB = RANKS_LIST.getLast();
        RankPairSpec someSpec = new RankPairSpec(rankA, rankB);
        RankPairSpec sameSpec = new RankPairSpec(rankA, rankB);
        assertEquals(someSpec, sameSpec);
    }
    
    @Test
    public void testEqualsRegardlessOrder() {
        Collections.shuffle(RANKS_LIST);
        Rank rankA = RANKS_LIST.getFirst();
        Collections.shuffle(RANKS_LIST);
        Rank rankB = RANKS_LIST.getLast();
        RankPairSpec someSpec = new RankPairSpec(rankA, rankB);
        RankPairSpec sameSpec = new RankPairSpec(rankB, rankA);
        assertEquals(someSpec, sameSpec);
    }
    
    @org.junit.Ignore
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Rank[] ranks = Rank.values();
        Set<RankPairSpec> specs = new HashSet<>();
        Set<Integer> hashes = new HashSet<>();
        for (Rank rankA : ranks) {
            for (Rank rankB: ranks) {
                RankPairSpec instance = new RankPairSpec(rankA, rankB);
                specs.add(instance);
                hashes.add(instance.hashCode());
            }
        }
        int expected = specs.size();
        int actual = hashes.size();
        String message = "For " + expected 
                + " specs there should be as many hash codes";
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of matches method, of class RankPairSpec.
     */
    @org.junit.Ignore
    @Test
    public void testMatches() {
        System.out.println("matches");
        Rank cardARank = Rank.QUEEN;
        Rank cardBRank = Rank.THREE;
        PlayingCard cardA = SERVER.giveCard(cardARank);
        PlayingCard cardB = SERVER.giveCard(cardBRank);
        RankPairSpec spec = new RankPairSpec(cardARank, cardBRank);
        String msg = "Pair specification for " + cardARank.getWord() 
                + " and " + cardBRank.getWord() + " should match " 
                + cardA.toString() + " and " + cardB.toString();
        assert spec.matches(cardA, cardB) : msg;
    }
    
    /**
     * Another test of matches method, of class RankPairSpec.
     */
    @org.junit.Ignore
    @Test
    public void testDoesNotMatch() {
        Rank cardARank = Rank.QUEEN;
        Rank cardBRank = Rank.FOUR;
        Rank cardCRank = Rank.FIVE;
        PlayingCard cardA = SERVER.giveCard(cardARank);
        PlayingCard cardB = SERVER.giveCard(cardBRank);
        RankPairSpec spec = new RankPairSpec(cardCRank, cardARank);
        String msg = "Pair specification for " + cardARank.getWord() 
                + " and " + cardCRank.getWord() + " should NOT match " 
                + cardA.toString() + " and " + cardB.toString();
        assert !spec.matches(cardA, cardB) : msg;
    }
    
}
