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
import playingcards.TestingSpec;

import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the PairSpec class.
 * @author Alonso del Arte
 */
public class PairSpecTest {
    
    private static final CardServer SERVER = new CardServer(2);
    
    @Test
    public void testReferentialEquality() {
        PairSpec spec = new PairSpecImpl(TestingSpec.COURT, TestingSpec.COURT);
        assertEquals(spec, spec);
    }
    
    @Test
    public void testNotEqualsNull() {
        PairSpec spec = new PairSpecImpl(TestingSpec.EVEN_PIP, 
                TestingSpec.ODD_PIP);
        assertNotEquals(spec, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        PairSpec spec = new PairSpecImpl(TestingSpec.COURT, TestingSpec.ODD_PIP);
        PlayingCard card = SERVER.getNextCard();
        assertNotEquals(spec, card);
    }
    
    @Test
    public void testUnequalSpecs() {
        PairSpec specOddCourt = new PairSpecImpl(TestingSpec.ODD_PIP, 
                TestingSpec.COURT);
        PairSpec specEvenCourt = new PairSpecImpl(TestingSpec.EVEN_PIP, 
                TestingSpec.COURT);
        assertNotEquals(specOddCourt, specEvenCourt);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        PairSpec someSpec = new PairSpecImpl(TestingSpec.COURT, 
                TestingSpec.ODD_PIP);
        PairSpec sameSpec = new PairSpecImpl(TestingSpec.COURT, 
                TestingSpec.ODD_PIP);
        assertEquals(someSpec, sameSpec);
    }
    
    @Test
    public void testEqualsRegardlessOrder() {
        PairSpec someSpec = new PairSpecImpl(TestingSpec.COURT, 
                TestingSpec.EVEN_PIP);
        PairSpec sameSpec = new PairSpecImpl(TestingSpec.EVEN_PIP, 
                TestingSpec.COURT);
        assertEquals(someSpec, sameSpec);
    }
    
    @Test
    public void testHashCodeBySetSizes() {
        TestingSpec[] classifs = TestingSpec.values();
        HashSet<PairSpec> specs = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        PairSpec spec;
        int hash;
        for (TestingSpec outer : classifs) {
            for (TestingSpec inner: classifs) {
                spec = new PairSpecImpl(outer, inner);
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
        PairSpec someSpec = new PairSpecImpl(TestingSpec.COURT, 
                TestingSpec.ODD_PIP);
        PairSpec sameSpec = new PairSpecImpl(TestingSpec.ODD_PIP, 
                TestingSpec.COURT);
        assertEquals(someSpec.hashCode(), sameSpec.hashCode());
    }
    
    /**
     * Test of matches method, of class PairSpec.
     */
    @Test
    public void testMatches() {
        System.out.println("matches");
        Rank cardARank = Rank.THREE;
        Rank cardBRank = Rank.QUEEN;
        PlayingCard cardA = SERVER.giveCard(cardARank);
        PlayingCard cardB = SERVER.giveCard(cardBRank);
        PairSpec spec = new PairSpecImpl(TestingSpec.COURT, TestingSpec.ODD_PIP);
        // TODO: Use getWord() instead of toString() once TestingSpec passes tests
        String msg = "Pair specification for " + TestingSpec.COURT.toString()
                + " and " + TestingSpec.ODD_PIP.toString() + " should match " 
                + cardA.toString() + " and " + cardB.toString();
        assert spec.matches(cardA, cardB) : msg;
    }

    /**
     * Another test of matches method, of class PairSpec.
     */
    @Test
    public void testDoesNotMatch() {
        Rank cardARank = Rank.THREE;
        Rank cardBRank = Rank.QUEEN;
        PlayingCard cardA = SERVER.giveCard(cardARank);
        PlayingCard cardB = SERVER.giveCard(cardBRank);
        PairSpec spec = new PairSpecImpl(TestingSpec.EVEN_PIP, TestingSpec.COURT);
        // TODO: Use getWord() instead of toString() once TestingSpec passes tests
        String msg = "Pair specification for " + TestingSpec.EVEN_PIP.toString()
                + " and " + TestingSpec.COURT.toString() + " should NOT match " 
                + cardA.toString() + " and " + cardB.toString();
        assert !spec.matches(cardA, cardB) : msg;
    }

    /**
     * Another test of matches method, of class PairSpec.
     */
    @Test
    public void testMatchesRegardlessOrder() {
        Rank cardARank = Rank.QUEEN;
        Rank cardBRank = Rank.THREE;
        PlayingCard cardA = SERVER.giveCard(cardARank);
        PlayingCard cardB = SERVER.giveCard(cardBRank);
        PairSpec spec = new PairSpecImpl(TestingSpec.COURT, TestingSpec.ODD_PIP);
        // TODO: Use getWord() instead of toString() once TestingSpec passes tests
        String msg = "Pair specification for " + TestingSpec.COURT.toString()
                + " and " + TestingSpec.ODD_PIP.toString() + " should match " 
                + cardA.toString() + " and " + cardB.toString();
        assert spec.matches(cardA, cardB) : msg;
    }

    class PairSpecImpl extends PairSpec<TestingSpec> {
        
        private TestingSpec classify(PlayingCard card) {
            switch (card.getRank()) {
                case ACE:
                case THREE:
                case FIVE:
                case SEVEN:
                case NINE:
                    return TestingSpec.ODD_PIP;
                case TWO:
                case FOUR:
                case SIX:
                case EIGHT:
                case TEN:
                    return TestingSpec.EVEN_PIP;
                case JACK:
                case QUEEN:
                case KING:
                    return TestingSpec.COURT;
                // TODO: Re-assess default case
                default:
                    throw new RuntimeException("Match error");
            }
        }

        @Override
        public boolean matches(PlayingCard cardA, PlayingCard cardB) {
            return this.matches(this.classify(cardA), this.classify(cardB));
        }

        public PairSpecImpl(TestingSpec specA, TestingSpec specB) {
            super(specA, specB);
        }

    }
    
}
