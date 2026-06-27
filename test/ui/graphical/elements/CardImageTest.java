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
package ui.graphical.elements;

import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.Suit;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the CardImage class.
 * @author Alonso del Arte
 */
public class CardImageTest {
    
    @Test
    public void testToggleNinePipsAreSymmetrical() {
        System.out.println("toggleNinePipsAreSymmetrical");
        boolean expected = !CardImage.ninePipsAreSymmetrical();
        CardImage.toggleNinePipsAreSymmetrical();
        assertEquals(expected, CardImage.ninePipsAreSymmetrical());
        CardImage.toggleNinePipsAreSymmetrical();
        assertEquals(!expected, CardImage.ninePipsAreSymmetrical());
    }
    
    @Test
    public void testConstructorRejectsNullCard() {
        String msg = "Null card should cause NPE";
        Throwable t = assertThrows(() -> {
            CardImage badImage = new CardImage(null);
            System.out.println(msg + ", not create " + badImage.toString() 
                    + " with null card");
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
}
