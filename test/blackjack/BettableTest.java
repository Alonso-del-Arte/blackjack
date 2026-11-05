/*
 * Copyright (C) 2026 Alonso del Arte
 *
 * This program is free software; you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation; either version 2 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package blackjack;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Bettable class.
 * @author Alonso del Arte
 */
public class BettableTest {
    
    public BettableTest() {
    }

    /**
     * Test of getWager method, of class Bettable.
     */
    @Test
    public void testGetWager() {
        System.out.println("getWager");
        Bettable instance = null;
        Wager expResult = null;
        Wager result = instance.getWager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class BettableImpl extends Bettable {

        @Override
        public Wager.Settlement getSettlement() {
            return null;
        }
        
        public BettableImpl(Wager wager) {
            super(wager);
        }

    }
    
}
