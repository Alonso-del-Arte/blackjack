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

import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the RankPairSpec class.
 * @author Alonso del Arte
 */
public class RankPairSpecTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final int NUMBER_OF_RANKS = RANKS.length;
    
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
    
    @org.junit.Ignore
    @Test
    public void testNotEqualsNull() {
        RankPairSpec spec = new RankPairSpec(Rank.ACE, Rank.SEVEN);
        assertNotEquals(spec, null);
    }
    
    @org.junit.Ignore
    @Test
    public void testNotEqualsDiffClass() {
        RankPairSpec rankSpec = new RankPairSpec(Rank.NINE, Rank.SEVEN);
        SuitPairSpec suitSpec = new SuitPairSpec(Suit.SPADES, Suit.SPADES);
        assertNotEquals(rankSpec, suitSpec);
    }
    
    @org.junit.Ignore
    @Test
    public void testUnequalSpecs() {
        RankPairSpec spec20 = new RankPairSpec(Rank.JACK, Rank.JACK);
        RankPairSpec spec16 = new RankPairSpec(Rank.NINE, Rank.SEVEN);
        assertNotEquals(spec20, spec16);
    }
    
    @org.junit.Ignore
    @Test
    public void testEquals() {
        System.out.println("equals");
        RankPairSpec someSpec = new RankPairSpec(Rank.SEVEN, Rank.NINE);
        RankPairSpec sameSpec = new RankPairSpec(Rank.SEVEN, Rank.NINE);
        assertEquals(someSpec, sameSpec);
    }
    
    @org.junit.Ignore
    @Test
    public void testEqualsRegardlessOrder() {
        RankPairSpec someSpec = new RankPairSpec(Rank.SEVEN, Rank.NINE);
        RankPairSpec sameSpec = new RankPairSpec(Rank.NINE, Rank.SEVEN);
        assertEquals(someSpec, sameSpec);
    }
    
    @org.junit.Ignore
    @Test
    public void testHashCodeBySetSizes() {
        fail("CONSIDER HAVING ONLY ONE hashCode( ) TEST");
        Rank[] ranks = Rank.values();
        HashSet<RankPairSpec> specs = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        RankPairSpec spec;
        int hash;
        for (Rank first : ranks) {
            for (Rank second: ranks) {
                spec = new RankPairSpec(first, second);
                hash = spec.hashCode();
                specs.add(spec);
                hashes.add(hash);
            }
        }
        int specSetSize = specs.size();
        int hashSetSize = hashes.size();
        String msg = "Set of specs should be the same size as set of hash codes";
        assertEquals(msg, specSetSize, hashSetSize);
    }
    
    @org.junit.Ignore
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        fail("CONSIDER HAVING ONLY ONE hashCode( ) TEST");
        RankPairSpec someSpec = new RankPairSpec(Rank.SEVEN, Rank.NINE);
        RankPairSpec sameSpec = new RankPairSpec(Rank.NINE, Rank.SEVEN);
        assertEquals(someSpec.hashCode(), sameSpec.hashCode());
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
