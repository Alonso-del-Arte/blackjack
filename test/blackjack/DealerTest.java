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
package blackjack;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class DealerTest {

    /**
     * Test of getDealer method, of class Dealer. Only one Dealer may be 
     * instantiated at a time.
     */
    @Test
    public void testGetDealer() {
        System.out.println("getDealer");
        Dealer firstDealer = Dealer.getDealer();
        try {
            Dealer secondDealer = Dealer.getDealer();
            String failMsg = "Already having " + firstDealer.toString() 
                    + ", should not have been able to get " 
                    + secondDealer.toString();
            fail(failMsg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to get second dealer correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to instantiate Dealer twice";
            fail(failMsg);
        }
    }
    
}
