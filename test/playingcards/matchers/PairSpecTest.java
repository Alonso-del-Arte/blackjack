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
import playingcards.TestingSpec;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class PairSpecTest {
    
    /**
     * Test of matches method, of class PairSpec.
     */
    @Test
    public void testMatches() {
        System.out.println("matches");
//        PlayingCard cardA = null;
//        PlayingCard cardB = null;
//        PairSpec instance = null;
//        boolean expResult = false;
//        boolean result = instance.matches(cardA, cardB);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    class PairSpecImpl extends PairSpec<TestingSpec> {

        @Override
        public boolean matches(PlayingCard cardA, PlayingCard cardB) {
            return false;
        }

        public PairSpecImpl(TestingSpec specA, TestingSpec specB) {
            super(specA, specB);
        }

    }
    
}
