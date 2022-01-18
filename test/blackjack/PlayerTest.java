/*
 * Copyright (C) 2022 Alonso del Arte
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
 * Tests of the Player class.
 * @author Alonso del Arte
 */
public class PlayerTest {
    
//    @Test
    public void testGetName() {
        System.out.println("getName");
    }
    
    @Test
    public void testConstructorRejectsNullName() {
        try {
            Player badPlayer = new Player(null);
            String msg = "Should not have been able to create " 
                    + badPlayer.toString() + " with null name";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null name for player correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null player name";
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsEmptyName() {
        try {
            Player badPlayer = new Player("");
            String msg = "Should not have been able to create " 
                    + badPlayer.toString() + " with empty name";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Empty name correctly caused exception");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for empty player name";
            fail(msg);
        }
    }
    
}
