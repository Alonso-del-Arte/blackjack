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
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 *
 * @author Alonso del Arte
 */
public class GraphicsCommandRecord {
    
    private final String commName;
    
    private final Color currColor;
    
    private final Font currFont;
    
    public String getCommandName() {
        return "Sorry, not implemented yet...";
    }
    
    public Color getCurrentColor() {
        return Color.BLACK;
    }
    
    public Font getCurrentFont() {
        return null;
    }
    
    /**
     * Constructor. Use this for any of the following commands: 
     * <code>create()</code>, <code>dispose()</code>, <code>getClip()</code>, 
     * <code>getClipBounds()</code>, <code>getColor()</code>, 
     * <code>getFont()</code>, <code>getFontMetrics()</code>, 
     * <code>setColor()</code>, <code>setFont()</code>, 
     * <code>setPaintMode()</code>, <code>setXORMode()</code>.
     * @param name The name of the command. For example, "create".
     * @param color The current color, the one that <code>getColor()</code> from 
     * the <code>Graphics</code> instance would return. However, when recording 
     * a <code>setColor()</code> command, send the new color that is being set.
     * @param font The current font, the one that <code>getFont()</code> from 
     * the <code>Graphics</code> instance would return. However, when recording 
     * a <code>setFont()</code> command, send the new font that is being set.
     */
    public GraphicsCommandRecord(String name, Color color, Font font) {
        if (name == null) {
            String excMsg = "Command name must not be null";
            throw new NullPointerException(excMsg);
        }
        if (color == null || font == null) {
            String excMsg = "Both color and font must be non-null";
            throw new NullPointerException(excMsg);
        }
        this.commName = name;
        this.currColor = color;
        this.currFont = font;
    }
    
}
