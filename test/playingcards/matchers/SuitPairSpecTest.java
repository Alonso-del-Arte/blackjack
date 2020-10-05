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

import playingcards.CardServer;
import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.Suit;

import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the SuitPairSpec class.
 * @author Alonso del Arte
 */
public class SuitPairSpecTest {
    
    private static final CardServer SERVER = new CardServer(2);
    
    @Test
    public void testReferentialEquality() {
        SuitPairSpec spec = new SuitPairSpec(Suit.SPADES, Suit.SPADES);
        assertEquals(spec, spec);
    }
    
    @Test
    public void testNotEqualsNull() {
        SuitPairSpec spec = new SuitPairSpec(Suit.DIAMONDS, Suit.HEARTS);
        assertNotEquals(spec, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        SuitPairSpec suitSpec = new SuitPairSpec(Suit.SPADES, Suit.SPADES);
        RankPairSpec rankSpec = new RankPairSpec(Rank.NINE, Rank.SEVEN);
        assertNotEquals(suitSpec, rankSpec);
    }
    
    @Test
    public void testUnequalSpecs() {
        SuitPairSpec specClubs = new SuitPairSpec(Suit.CLUBS, Suit.CLUBS);
        SuitPairSpec specClubAndDiamond = new SuitPairSpec(Suit.CLUBS, 
                Suit.DIAMONDS);
        assertNotEquals(specClubs, specClubAndDiamond);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        SuitPairSpec someSpec = new SuitPairSpec(Suit.SPADES, Suit.HEARTS);
        SuitPairSpec sameSpec = new SuitPairSpec(Suit.SPADES, Suit.HEARTS);
        assertEquals(someSpec, sameSpec);
    }
    
    @Test
    public void testEqualsRegardlessOrder() {
        SuitPairSpec someSpec = new SuitPairSpec(Suit.SPADES, Suit.HEARTS);
        SuitPairSpec sameSpec = new SuitPairSpec(Suit.HEARTS, Suit.SPADES);
        assertEquals(someSpec, sameSpec);
    }
    
    @Test
    public void testHashCodeBySetSizes() {
        Suit[] suits = Suit.values();
        HashSet<SuitPairSpec> specs = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        SuitPairSpec spec;
        int hash;
        for (Suit first : suits) {
            for (Suit second: suits) {
                spec = new SuitPairSpec(first, second);
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
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        SuitPairSpec someSpec = new SuitPairSpec(Suit.SPADES, Suit.HEARTS);
        SuitPairSpec sameSpec = new SuitPairSpec(Suit.HEARTS, Suit.SPADES);
        assertEquals(someSpec.hashCode(), sameSpec.hashCode());
    }
    
    /**
     * Test of matches method, of class SuitPairSpec.
     */
    @Test
    public void testMatches() {
        System.out.println("matches");
        Suit cardASuit = Suit.HEARTS;
        Suit cardBSuit = Suit.CLUBS;
        PlayingCard cardA = SERVER.giveCard(cardASuit);
        PlayingCard cardB = SERVER.giveCard(cardBSuit);
        SuitPairSpec spec = new SuitPairSpec(cardASuit, cardBSuit);
        String msg = "Pair specification for " + cardASuit.getSuitWord()
                + " and " + cardBSuit.getSuitWord() + " should match " 
                + cardA.toString() + " and " + cardB.toString();
        assert spec.matches(cardA, cardB) : msg;
    }
    
    /**
     * Another test of matches method, of class SuitPairSpec.
     */
    @Test
    public void testDoesNotMatch() {
        Suit cardASuit = Suit.DIAMONDS;
        Suit cardBSuit = Suit.CLUBS;
        Suit cardCSuit = Suit.HEARTS;
        PlayingCard cardA = SERVER.giveCard(cardASuit);
        PlayingCard cardB = SERVER.giveCard(cardBSuit);
        SuitPairSpec spec = new SuitPairSpec(cardCSuit, cardASuit);
        String msg = "Pair specification for " + cardASuit.getSuitWord() 
                + " and " + cardCSuit.getSuitWord() + " should NOT match " 
                + cardA.toString() + " and " + cardB.toString();
        assert !spec.matches(cardA, cardB) : msg;
    }
    
}
