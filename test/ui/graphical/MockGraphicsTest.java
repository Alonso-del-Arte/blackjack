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
package ui.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the MockGraphics class.
 * @author Alonso del Arte
 */
public class MockGraphicsTest {
    
    private static final Font[] FONTS 
            = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    
    private static final Random RANDOM = new Random();
    
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        Graphics g = new MockGraphics();
        Color color = g.getColor();
        String msg = "Color should not be null";
        assert color != null : msg;
    }
    
    @Test
    public void testGetFont() {
        System.out.println("getFont");
        Graphics g = new MockGraphics();
        Font font = g.getFont();
        String msg = "Font should not be null";
        assert font != null : msg;
    }
    
    @Test
    public void testPrimaryConstructor() {
        int rgb = RANDOM.nextInt();
        Color color = new Color(rgb);
        Font font = FONTS[FONTS.length - 1];
        Graphics g = new MockGraphics(color, font);
        assertEquals(color, g.getColor());
        assertEquals(font, g.getFont());
    }
    
//    @Test
//    public void testSetColor() {
//        System.out.println("setColor");
//        fail("Haven't written test yet");
//    }
    
}
