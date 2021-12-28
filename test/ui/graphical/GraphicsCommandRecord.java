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
    
    private final Shape commShape;
    
    private final Font commFont, metricsInquire;
    
    private static final int[] EMPTY_ARRAY = {};
    
    public Point getXAndY() {
        return new Point(-1, -1);
    }
    
    public Dimension getWidthAndHeight() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    
    public Point getX2AndY2() {
        return new Point(-1, -1);
    }
    
    public Point getSX1AndSY1() {
        return new Point(-1, -1);
    }
    
    public Dimension getArcWidthAndArcHeight() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    
    public Point getSX2AndSY2() {
        return new Point(-1, -1);
    }
    
    /**
     * Constructor. Use this for any of the following commands: 
     * <code>create()</code>, <code>dispose()</code>, <code>getClip()</code>, 
     * <code>getClipBounds()</code>, <code>getColor()</code>, 
     * <code>getFont()</code>, <code>getFontMetrics()</code>, 
     * <code>setColor()</code>, <code>setFont()</code>, 
     * <code>setPaintMode()</code>, <code>setXORMode()</code>.
     * @param name The name of the command. For example, "create".
     * @param color The current color. For example, <code>Color.BLACK</code>.
     * @param font The current font. For example, 12-point Times New Roman.
     */
    public GraphicsCommandRecord(String name, Color color, Font font) {
        this(name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, color, null, 
                EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, font, null);
    }
    
    /**
     * Constructor. Use this for the <code>translate()</code> command.
     * @param name The name of the command. For example, "translate".
     * @param color The current color. For example, <code>Color.RED</code>.
     * @param font The current font. For example, 12-point Courier.
     * @param x The <code>x</code> parameter.
     * @param y The <code>y</code> parameter.
     */
    public GraphicsCommandRecord(String name, Color color, Font font, int x, 
            int y) {
        this(name, x, y, 0, 0, 0, 0, 0, 0, 0, 0, null, null, color, null, 
                EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, font, null);
    }
    
    /**
     * Constructor. Use this for the version of <code>setClip()</code> that 
     * takes a <code>Shape</code> parameter.
     * @param name The name of the command. For example, "create".
     * @param color The current color. For example, <code>Color.ORANGE</code>.
     * @param font The current font. For example, 12-point Helvetica.
     * @param shape The shape to set the clip to.
     */
    public GraphicsCommandRecord(String name, Color color, Font font, 
            Shape shape) {
        this(name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, color, null, 
                EMPTY_ARRAY, EMPTY_ARRAY, null, "", shape, font, null);
    }
    
    /**
     * Constructor. Use this for the version of <code>drawString()</code> that 
     * takes a <code>String</code> parameter.
     * @param name The name of the command. For example, "create".
     * @param color The current color. For example, <code>Color.YELLOW</code>.
     * @param font The current font. For example, 13-point Times New Roman.
     * @param text The text for <code>drawString()</code> to write to the 
     * <code>Graphics</code> context.
     */
    public GraphicsCommandRecord(String name, Color color, Font font, 
            String text) {
        this(name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, color, null, 
                EMPTY_ARRAY, EMPTY_ARRAY, null, text, null, font, null);
    }
    
    /**
     * Constructor. Use this for the version of <code>drawString()</code> that 
     * takes an <code>AttributedCharacterIterator</code> parameter.
     * @param name The name of the command. For example, "create".
     * @param color The current color. For example, <code>Color.GREEN</code>.
     * @param font The current font. For example, 13-point Georgia.
     * @param iterator The iterator with the text to be written to the graphics 
     * context.
     */
    public GraphicsCommandRecord(String name, Color color, Font font, 
            AttributedCharacterIterator iterator) {
        this(name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, color, null, 
                EMPTY_ARRAY, EMPTY_ARRAY, iterator, null, null, font, null);
    }
    
    /**
     * Constructor. Use this for any of the following commands: 
     * <code>drawLine()</code>, <code>drawOval()</code>, the version of 
     * <code>setClip()</code> that takes four integers, 
     * @param name The name of the command. For example, "drawLine".
     * @param color The current color. For example, <code>Color.BLUE</code>.
     * @param font The current font. For example, 12-point Zapf Dingbats.
     * @param x1 The <code>x1</code> parameter.
     * @param y1 The <code>y1</code> parameter.
     * @param x2 The <code>x2</code> or <code>width</code> parameter.
     * @param y2 The <code>y2</code> or <code>height</code> parameter.
     */
    public GraphicsCommandRecord(String name, Color color, Font font, int x1, 
            int y1, int x2, int y2) {
        this(name, x1, y1, x2, y2, 0, 0, 0, 0, 0, 0, null, null, color, null, 
                EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, font, null);
    }
    
    /**
     * Constructor. Use this for the version of <code>drawImage()</code> that 
     * specifies an image and an image observer but not a background color.
     * @param name The name of the command. For example, "drawImage".
     * @param foreground The current foreground color. Even though no background 
     * color is specified, this one is still necessary for a proper command 
     * record.
     * @param font The current font.
     * @param img The image to be drawn by the <code>drawImage()</code> command.
     * @param x The <code>x</code> parameter.
     * @param y The <code>y</code> parameter.
     * @param observer The image observer for the image.
     */
    public GraphicsCommandRecord(String name, Color foreground, Font font, 
            Image img, int x, int y, ImageObserver observer) {
        this(name, x, y, 0, 0, 0, 0, 0, 0, 0, 0, img, observer, foreground, 
                null, EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, font, null);
    }
    
    /**
     * Constructor. Use this for the version of <code>drawImage()</code> that 
     * specifies an image, an image observer and a background color.
     * @param name The name of the command. For example, "drawImage".
     * @param foreground The current foreground color.
     * @param background The specified background color.
     * @param font The current font.
     * @param img The image to be drawn by the <code>drawImage()</code> command.
     * @param x The <code>x</code> parameter.
     * @param y The <code>y</code> parameter.
     * @param observer The image observer for the image.
     */
    public GraphicsCommandRecord(String name, Color foreground, 
            Color background, Font font, Image img, int x, int y,  
            ImageObserver observer) {
        this(name, x, y, 0, 0, 0, 0, 0, 0, 0, 0, img, observer, foreground, 
                background, EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, font, 
                null);
    }
    
    /**
     * Constructor. Use this for the version of <code>drawImage()</code> that 
     * specifies an image, an image observer, a height and a width but not a 
     * background color.
     * @param name The name of the command. For example, "drawImage".
     * @param foreground The current foreground color.
     * @param font The current font.
     * @param img The image to be drawn by the <code>drawImage()</code> command.
     * @param x The <code>x</code> parameter.
     * @param y The <code>y</code> parameter.
     * @param width The <code>width</code> parameter.
     * @param height The <code>height</code> parameter.
     * @param observer The image observer for the image.
     */
    public GraphicsCommandRecord(String name, Color foreground, 
            Font font, Image img, int x, int y, int width, int height, 
            ImageObserver observer) {
        this(name, x, y, width, height, 0, 0, 0, 0, 0, 0, img, observer, 
                foreground, null, EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, 
                font, null);
    }
    
    /**
     * Constructor. Use this for the version of <code>drawImage()</code> that 
     * specifies an image, an image observer, a background color and a height 
     * and a width.
     * @param name The name of the command. For example, "drawImage".
     * @param foreground The current foreground color.
     * @param background The specified background color.
     * @param font The current font.
     * @param img The image to be drawn by the <code>drawImage()</code> command.
     * @param x The <code>x</code> parameter.
     * @param y The <code>y</code> parameter.
     * @param width The <code>width</code> parameter.
     * @param height The <code>height</code> parameter.
     * @param observer The image observer for the image.
     */
    public GraphicsCommandRecord(String name, Color foreground, 
            Color background, Font font, Image img, int x, int y, int width, 
            int height, ImageObserver observer) {
        this(name, x, y, width, height, 0, 0, 0, 0, 0, 0, img, observer, 
                foreground, background, EMPTY_ARRAY, EMPTY_ARRAY, null, "", 
                null, font, null);
    }
    
    /**
     * Constructor. Use this for the version of <code>drawImage()</code> that 
     * specifies an image, an image observer and scaling coordinates, but no 
     * background color.
     * @param name The name of the command. For example, "drawImage".
     * @param foreground The current foreground color.
     * @param font The current font.
     * @param img The image to be drawn by the <code>drawImage()</code> command.
     * @param dx1 The <code>dx1</code> parameter.
     * @param dy1 The <code>dy1</code> parameter.
     * @param dx2 The <code>dx2</code> parameter.
     * @param dy2 The <code>dy2</code> parameter.
     * @param sx1 The <code>sx1</code> parameter.
     * @param sy1 The <code>sy1</code> parameter.
     * @param sx2 The <code>sx2</code> parameter.
     * @param sy2 The <code>sy2</code> parameter.
     * @param observer The image observer for the image.
     */
    public GraphicsCommandRecord(String name, Color foreground, Font font, 
            Image img, int dx1, int dy1, int dx2, int dy2, 
            int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        this(name, dx1, dy1, dx2, dy2, 0, 0, sx1, sy1, sx2, sy2, img, observer, 
                foreground, null, EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, 
                font, null);
    }
    
    /**
     * Constructor. Use this for the version of <code>drawImage()</code> that 
     * specifies an image, an image observer, a background color and scaling 
     * coordinates.
     * @param name The name of the command. For example, "drawImage".
     * @param foreground The current foreground color.
     * @param background The background color given to the 
     * <code>drawImage()</code> command.
     * @param font The current font.
     * @param img The image to be drawn by the <code>drawImage()</code> command.
     * @param dx1 The <code>dx1</code> parameter.
     * @param dy1 The <code>dy1</code> parameter.
     * @param dx2 The <code>dx2</code> parameter.
     * @param dy2 The <code>dy2</code> parameter.
     * @param sx1 The <code>sx1</code> parameter.
     * @param sy1 The <code>sy1</code> parameter.
     * @param sx2 The <code>sx2</code> parameter.
     * @param sy2 The <code>sy2</code> parameter.
     * @param observer The image observer for the image.
     */
    public GraphicsCommandRecord(String name, Color foreground, 
            Color background, Font font, 
            Image img, int dx1, int dy1, int dx2, int dy2, 
            int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        this(name, dx1, dy1, dx2, dy2, 0, 0, sx1, sy1, sx2, sy2, img, observer, 
                foreground, background, EMPTY_ARRAY, EMPTY_ARRAY, null, "", 
                null, font, null);
    }
    
    /**
     * Constructor. Use this for any of the following commands: 
     * <code>clearRect()</code>, <code>clipRect()</code>, 
     * <code>fillRect()</code>, 
     * @param name The name of the command. For example, "clearRect".
     * @param x The <code>x</code> parameter.
     * @param y The <code>y</code> parameter.
     * @param width The <code>width</code> parameter.
     * @param height The <code>height</code> parameter.
     * @param color The current color. For example, <code>Color.BLACK</code>.
     * @param font The current font. For example, 12-point Times New Roman.
     */
    public GraphicsCommandRecord(String name, int x, int y, int width, 
            int height, Color color, Font font) {   
        this(name, x, y, width, height, 0, 0, 0, 0, 0, 0, null, null, 
                color, null, EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, font, 
                null);
    }
    
    /**
     * Constructor. Use this for any of the following commands: 
     * <code>copyArea()</code>, <code>drawArc()</code>, 
     * <code>drawRoundRect</code>, <code>fillArc()</code>, 
     * <code>fillOval()</code>, <code>fillRoundRect()</code>, 
     * @param name The name of the command. For example, "drawArc".
     * @param x The <code>x</code> parameter.
     * @param y The <code>y</code> parameter.
     * @param width The <code>width</code> parameter.
     * @param height The <code>height</code> parameter.
     * @param dx The <code>dx</code> or <code>arcWidth</code> parameter.
     * @param dy The <code>dy</code> or <code>arcHeight</code> parameter.
     * @param color The current color.
     * @param font The current font.
     */
    public GraphicsCommandRecord(String name, int x, int y, int width, 
            int height, int dx, int dy, Color color, Font font) {
        this(name, x, y, width, height, dx, dy, 0, 0, 0, 0, null, null, 
                color, null, EMPTY_ARRAY, EMPTY_ARRAY, null, "", null, font, 
                null);
    }
    
    /**
     * Constructor. Use this for any of the following commands: 
     * <code>drawPolygon()</code>, <code>drawPolyline()</code>, 
     * <code>fillPolygon()</code>
     * <p>Note that the <code>drawPolygon()</code> in <code>Graphics</code> that 
     * takes a <code>Polygon</code> object is not abstract (but it can be 
     * overridden, of course). Presumably that one extracts the data of the 
     * <code>Polygon</code> objects into <code>int</code> arrays. For that 
     * reason, I'm not providing a constructor that takes a <code>Polygon</code> 
     * object.</p>
     * @param name The name of the command. For example, "drawPolygon".
     * @param color The current color. For example, <code>Color.BLACK</code>.
     * @param font The current font. For example, 12-point Times New Roman.
     * @param xPoints The array with the <i>x</i> points.
     * @param yPoints The array with the <i>y</i> points.
     * @param nPoints The number of points. Presumably it matches the length of 
     * the <code>xPoints</code> and <code>yPoints</code> arrays.
     */
    public GraphicsCommandRecord(String name, Color color, Font font, 
            int[] xPoints, int[] yPoints, int nPoints) {
        this(name, nPoints, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, color, null, 
                xPoints, yPoints, null, "", null, font, null);
    }
    
    /**
     * Primary constructor. Note that it is private.
     * @param name The name of the command. For example, "drawRect".
     * @param x The <code>x</code> parameter.
     * @param y The <code>y</code> parameter.
     * @param width The <code>width</code> parameter.
     * @param height The <code>height</code> parameter.
     * @param dx The <code>dx</code> or <code>startAngle</code> parameter.
     * @param dy The <code>dy</code> or <code>arcAngle</code> parameter.
     * @param sx1 The <code>sx1</code> parameter.
     * @param sy1 The <code>sy1</code> parameter.
     * @param sx2 The <code>sx2</code> parameter.
     * @param sy2 The <code>sy2</code> parameter.
     * @param image An image for a <code>drawImage()</code> command.
     * @param observer An image observer for a <code>drawImage()</code> command.
     * @param foreground The foreground color, to be used for most draw and fill 
     * commands. Required for all auxiliary constructors.
     * @param background The background color, called for in a few commands. May 
     * be null.
     * @param xPoints The <code>xPoints</code> parameter.
     * @param yPoints The <code>yPoints</code> parameter.
     * @param iterator An iterator for one version of the 
     * <code>drawString()</code> command.
     * @param text The text for one version of the <code>drawString()</code> 
     * command.
     * @param font The current font, the one <code>getFont()</code> would 
     * return. Required for all auxiliary constructors.
     * @param inquiry A font for which to inquire <code>FontMetrics</code> for. 
     * May in most cases be null.
     */
    private GraphicsCommandRecord(String name, int x, int y, int width, 
            int height, int dx, int dy, int sx1, int sy1, int sx2, int sy2, 
            Image image, ImageObserver observer, Color foreground, 
            Color background, int[] xPoints, int[] yPoints, 
            AttributedCharacterIterator iterator, String text, Shape shape, 
            Font font, Font inquiry) {
        if (name == null) {
            String excMsg = "Command name must not be null";
            throw new NullPointerException(excMsg);
        }
        if (foreground == null || font == null) {
            String excMsg = "Current color, current font must both be not null";
            throw new NullPointerException(excMsg);
        }
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
        this.commShape = shape;
        this.commFont = font;
        this.metricsInquire = inquiry;
    }
    
    /**
     * A pass-through to the private primary constructor of the enclosing class. 
     * I thought of making the primary <code>GraphicsCommandRecord</code> 
     * package private. But it's, for the most part, more convenient for me to 
     * make that primary constructor class private. I only need a way around it 
     * when testing the primary constructor for null safety. Hence this 
     * pass-through.
     */
    static class PassThrough {
        
        static GraphicsCommandRecord getInstance(String name, int x, int y, 
                int width, int height, int dx, int dy, int sx1, int sy1, 
                int sx2, int sy2, Image image, ImageObserver observer, 
                Color foreground, Color background, int[] xPoints, 
                int[] yPoints, AttributedCharacterIterator iterator, 
                String text, Shape shape, Font font, Font inquiry) {
            return new GraphicsCommandRecord(name, x, y, width, height, dx, dy, 
                    sx1, sy1, sx2, sy2, image, observer, foreground, background, 
                    xPoints, yPoints, iterator, text, shape, font, inquiry);
        }
        
    }
    
}
