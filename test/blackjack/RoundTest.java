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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Round class.
 * @author Alonso del Arte
 */
public class RoundTest {
    
    @Test
    public void testConstructorRejectsNullDealer() {
        Player player = new Player("Johnny Q. Test");
        try {
            Round badRound = new Round(null, player);
            String msg = "Should not have been able to create " 
                    + badRound.toString() + " with null dealer";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null dealer correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null dealer";
            fail(msg);
        }
    }
    
}
