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
import playingcards.TestingSpec;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the PairSpec class.
 * @author Alonso del Arte
 */
public class PairSpecTest {
    
    private static final TestingSpec[] SPECS = TestingSpec.values();
    
    private static final int NUMBER_OF_SPECS = SPECS.length;
    
    private static final CardServer SERVER = new CardServer(2);
    
    /**
     * Test of the toString function, of the PairSpec class.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        for (int a = 0; a < NUMBER_OF_SPECS; a++) {
            TestingSpec specA = SPECS[a];
            for (int b = a; b < NUMBER_OF_SPECS; b++) {
                TestingSpec specB = SPECS[b];
                PairSpec pair = new PairSpecImpl(specA, specB);
                String expected = "(" + specA.getWord() + "," + specB.getWord() 
                        + ")";
                String actual = pair.toString().replace(" ", "");
                assertEquals(expected, actual);
            }
        }
    }

    @Test
    public void testToStringInvertedAtConstruction() {
        int start = NUMBER_OF_SPECS - 1;
        for (int a = start; a > 0; a--) {
            TestingSpec specA = SPECS[a];
            for (int b = 0; b < a; b++) {
                TestingSpec specB = SPECS[b];
                PairSpec pair = new PairSpecImpl(specA, specB);
                String expected = "(" + specB.getWord() + "," + specA.getWord() 
                        + ")";
                String actual = pair.toString().replace(" ", "");
                assertEquals(expected, actual);
            }
        }
    }

    @Test
    public void testReferentialEquality() {
        String msgPart = " should be equal to itself";
        for (TestingSpec specA : SPECS) {
            for (TestingSpec specB : SPECS) {
                PairSpec obj = new PairSpecImpl(specA, specB);
                String msg = obj.toString() + msgPart;
                assert obj.equals(obj) : msg;
            }
        }
    }
    
    @Test
    public void testNotEqualsNull() {
        String msgPart = " should not equal null";
        for (TestingSpec specA : SPECS) {
            for (TestingSpec specB : SPECS) {
                PairSpec instance = new PairSpecImpl(specA, specB);
                String message = instance.toString() + msgPart;
                assertNotEquals(message, instance, null);
            }
        }
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        for (TestingSpec specA : SPECS) {
            for (TestingSpec specB : SPECS) {
                PairSpec instance = new PairSpecImpl(specA, specB);
                String message = instance.toString() + " should not equal " 
                        + specA.getWord() + " nor " + specB.getWord();
                assertNotEquals(message, instance, specA);
                assertNotEquals(message, instance, specB);
            }
        }
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        for (TestingSpec specA : SPECS) {
            for (TestingSpec specB : SPECS) {
                PairSpec instanceA = new PairSpecImpl(specA, specB);
                Set<TestingSpec> setA = new HashSet<>(2);
                setA.add(specA);
                setA.add(specB);
                String msgPart = instanceA.toString() + " should ";
                for (TestingSpec specC : SPECS) {
                    for (TestingSpec specD : SPECS) {
                        PairSpec instanceB = new PairSpecImpl(specC, specD);
                        Set<TestingSpec> setB = new HashSet<>(2);
                        setB.add(specC);
                        setB.add(specD);
                        boolean equality = setA.equals(setB);
                        if (equality) {
                            String message = msgPart + "equal " 
                                    + instanceB.toString();
                            assertEquals(message, instanceA, instanceB);
                        } else {
                            String message = msgPart + "not equal " 
                                    + instanceB.toString();
                            assertNotEquals(message, instanceA, instanceB);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Test of the hashCode function, of the PairSpec class.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int initialCapacity = 2 * NUMBER_OF_SPECS;
        Set<PairSpec> specs = new HashSet<>(initialCapacity);
        Set<Integer> hashes = new HashSet<>(initialCapacity);
        for (TestingSpec specA : SPECS) {
            for (TestingSpec specB : SPECS) {
                PairSpec instance = new PairSpecImpl(specA, specB);
                int hash = instance.hashCode();
                specs.add(instance);
                hashes.add(hash);
            }
        }
        int expected = specs.size();
        int actual = hashes.size();
        String message = "For " + expected 
                + " pairs there should be as many hashes";
        assertEquals(message, expected, actual);
    }
    
    /**
     * Test of the matches function, of the PairSpec class.
     */
    @org.junit.Ignore
    @Test
    public void testMatches() {
        System.out.println("matches");
        Rank cardARank = Rank.THREE;
        Rank cardBRank = Rank.QUEEN;
        PlayingCard cardA = SERVER.giveCard(cardARank);
        PlayingCard cardB = SERVER.giveCard(cardBRank);
        PairSpec spec = new PairSpecImpl(TestingSpec.COURT, 
                TestingSpec.ODD_PIP);
        String msg = "Pair specification for " + TestingSpec.COURT.getWord()
                + " and " + TestingSpec.ODD_PIP.getWord() + " should match " 
                + cardA.toString() + " and " + cardB.toString();
        assert spec.matches(cardA, cardB) : msg;
    }

    /**
     * Another test of the matches function, of the PairSpec class.
     */
    @org.junit.Ignore
    @Test
    public void testDoesNotMatch() {
        Rank cardARank = Rank.THREE;
        Rank cardBRank = Rank.QUEEN;
        PlayingCard cardA = SERVER.giveCard(cardARank);
        PlayingCard cardB = SERVER.giveCard(cardBRank);
        PairSpec spec = new PairSpecImpl(TestingSpec.EVEN_PIP, 
                TestingSpec.COURT);
        String msg = "Pair specification for " + TestingSpec.EVEN_PIP.getWord()
                + " and " + TestingSpec.COURT.getWord() + " should NOT match " 
                + cardA.toString() + " and " + cardB.toString();
        assert !spec.matches(cardA, cardB) : msg;
    }

    /**
     * Another test of the matches function, of the PairSpec class.
     */
    @org.junit.Ignore
    @Test
    public void testMatchesRegardlessOrder() {
        Rank cardARank = Rank.QUEEN;
        Rank cardBRank = Rank.THREE;
        PlayingCard cardA = SERVER.giveCard(cardARank);
        PlayingCard cardB = SERVER.giveCard(cardBRank);
        PairSpec spec = new PairSpecImpl(TestingSpec.COURT, 
                TestingSpec.ODD_PIP);
        String msg = "Pair specification for " + TestingSpec.COURT.getWord()
                + " and " + TestingSpec.ODD_PIP.getWord() + " should match " 
                + cardA.toString() + " and " + cardB.toString();
        assert spec.matches(cardA, cardB) : msg;
    }
    
    @Test
    public void testConstructorRejectsNullElementA() {
        for (TestingSpec specB : SPECS) {
            String msg = "Constructor should reject null A with B " 
                    + specB.getWord();
            Throwable t = assertThrows(() -> {
                PairSpec badInstance = new PairSpecImpl(null, specB);
                System.out.println(msg + ", not given " 
                        + badInstance.getClass().getName() + "@" 
                        + Integer.toHexString(System
                                .identityHashCode(badInstance)));
            }, NullPointerException.class, msg);
            String excMsg = t.getMessage();
            assert excMsg != null : "Exception message should not be null";
            assert !excMsg.isBlank() : "Exception message should not be blank";
            System.out.println("\"" + excMsg + "\"");
        }
    }
           
    
    @Test
    public void testConstructorRejectsNullElementB() {
        for (TestingSpec specA : SPECS) {
            String msg = "Constructor should reject A " + specA.getWord() 
                    + " with null B";
            Throwable t = assertThrows(() -> {
                PairSpec badInstance = new PairSpecImpl(specA, null);
                System.out.println(msg + ", not given " 
                        + badInstance.getClass().getName() + "@" 
                        + Integer.toHexString(System
                                .identityHashCode(badInstance)));
            }, NullPointerException.class, msg);
            String excMsg = t.getMessage();
            assert excMsg != null : "Exception message should not be null";
            assert !excMsg.isBlank() : "Exception message should not be blank";
            System.out.println("\"" + excMsg + "\"");
        }
    }
           
    
    class PairSpecImpl extends PairSpec<TestingSpec> {
        
        private TestingSpec classify(PlayingCard card) {
            switch (card.getRank()) {
                case ACE, THREE, FIVE, SEVEN, NINE -> {
                    return TestingSpec.ODD_PIP;
                }
                case TWO, FOUR, SIX, EIGHT, TEN -> {
                    return TestingSpec.EVEN_PIP;
                }
                case JACK, QUEEN, KING -> {
                    return TestingSpec.COURT;
                }
                default -> throw new RuntimeException("Match error");
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
