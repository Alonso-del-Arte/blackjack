/*
 * Copyright (C) 2021 Alonso del Arte
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
package playingcards;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the TestingSpec enumeration.
 * @author Alonso del Arte
 */
public class TestingSpecTest {
    
    @Test
    public void testGetChar() {
        System.out.println("getChar");
        assertEquals('o', TestingSpec.ODD_PIP.getChar());
        assertEquals('e', TestingSpec.EVEN_PIP.getChar());
        assertEquals('c', TestingSpec.COURT.getChar());
    }
    
    @Test
    public void testGetCharASCII() {
        System.out.println("getCharASCII");
        assertEquals('o', TestingSpec.ODD_PIP.getCharASCII());
        assertEquals('e', TestingSpec.EVEN_PIP.getCharASCII());
        assertEquals('c', TestingSpec.COURT.getCharASCII());
    }
    
    @Test
    public void testGetWord() {
        System.out.println("getWord");
        assertEquals("odd", TestingSpec.ODD_PIP.getWord());
        assertEquals("even", TestingSpec.EVEN_PIP.getWord());
        assertEquals("court", TestingSpec.COURT.getWord());
    }
    
}
