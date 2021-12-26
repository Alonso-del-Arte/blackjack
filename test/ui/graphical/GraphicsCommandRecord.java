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
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 *
 * @author Alonso del Arte
 */
public class GraphicsCommandRecord {
    
    private final String commName, commText;
    
    private final int commX, commY, commWidth, commHeight;
    
    /**
     * Use these for the <code>dx</code> and <code>dy</code> parameters of 
     * <code>copyArea()</code> or the <code>startAngle</code> and 
     * <code>arcAngle</code>
     */
    private final int commDX, commDY;
    
    private final int commSX1, commSY1, commSX2, commSY2;
    
    private final Image commImage;
    
    private final ImageObserver commObserver;
    
    private final Color commFore, commBack;
    
    private final int[] commXPoints, commYPoints;
    
    private final AttributedCharacterIterator commIter;
    
    private final Font commFont, metricsInquire;
    
    private static final int[] EMPTY_ARRAY = {};
    
    public GraphicsCommandRecord(String name, Color color, Font font) {
        this(name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, color, null, 
                EMPTY_ARRAY, EMPTY_ARRAY, null, "", font, null);
    }
    
    public GraphicsCommandRecord(String name, int x, int y, int width, 
            int height, Color color, Font font) {
        this(name, x, y, width, height, 0, 0, 0, 0, 0, 0, null, null, 
                color, null, EMPTY_ARRAY, EMPTY_ARRAY, null, "", font, 
                null);
    }
    
    public GraphicsCommandRecord(String name, int x, int y, int width, 
            int height, int dx, int dy, Color foreground, Font font) {
        this(name, x, y, width, height, dx, dy, 0, 0, 0, 0, null, null, 
                foreground, null, EMPTY_ARRAY, EMPTY_ARRAY, null, "", font, 
                null);
    }
    
    private GraphicsCommandRecord(String name, int x, int y, int width, 
            int height, int dx, int dy, int sx1, int sy1, int sx2, int sy2, 
            Image image, ImageObserver observer, Color foreground, 
            Color background, int[] xPoints, int[] yPoints, 
            AttributedCharacterIterator iterator, String text, Font font, 
            Font inquiry) {
        this.commName = name;
        this.commX = x;
        this.commY = y;
        this.commWidth = width;
        this.commHeight = height;
        this.commDX = dx;
        this.commDY = dy;
        this.commImage = image;
        this.commObserver = observer;
        this.commFore = foreground;
        this.commBack = background;
        this.commSX1 = sx1;
        this.commSX2 = sx2;
        this.commSY1 = sy1;
        this.commSY2 = sy2;
        this.commXPoints = xPoints;
        this.commYPoints = yPoints;
        this.commIter = iterator;
        this.commText = text;
        this.commFont = font;
        this.metricsInquire = inquiry;
    }
    
}
