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
package ui.graphical;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of GraphicsCommandRecord class.
 * @author Alonso del Arte
 */
public class GraphicsCommandRecordTest {
    
    private static final Font[] FONTS 
            = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    
    private static final int TOTAL_NUMBER_OF_FONTS = FONTS.length;
    
    private static final Random RANDOM = new Random();
    
    @Test
    public void testGetCommandName() {
        System.out.println("getCommandName");
        String expected = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        GraphicsCommandRecord record = new GraphicsCommandRecord(expected, 
                color, font);
        String actual = record.getCommandName();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetCurrentColor() {
        System.out.println("getCurrentColor");
        String command = "command" + RANDOM.nextInt();
        Color expected = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        GraphicsCommandRecord record = new GraphicsCommandRecord(command, 
                expected, font);
        Color actual = record.getCurrentColor();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetCurrentFont() {
        System.out.println("getCurrentFont");
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font expected = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        GraphicsCommandRecord record = new GraphicsCommandRecord(command, 
                color, expected);
        Font actual = record.getCurrentFont();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testPrimaryConstructorRejectsNullCommandName() {
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[0];
        try {
            GraphicsCommandRecord badRecord = new GraphicsCommandRecord(null, 
                    color, font);
            String msg = "Should not have been able to create " 
                    + badRecord.toString() + " with null command name";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null command name correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null command name";
            fail(msg);
        }
    }
    
    @Test
    public void testPrimaryConstructorRejectsNullColor() {
        String cmd = "doSomething";
        Font font = FONTS[1];
        try {
            GraphicsCommandRecord badRecord = new GraphicsCommandRecord(cmd, 
                    null, font);
            String msg = "Should not have been able to create " 
                    + badRecord.toString() + " with null color";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null color correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null color";
            fail(msg);
        }
    }
    
    @Test
    public void testPrimaryConstructorRejectsNullFont() {
        String cmd = "doSomething";
        Color color = new Color(RANDOM.nextInt());
        try {
            GraphicsCommandRecord badRecord = new GraphicsCommandRecord(cmd, 
                    color, null);
            String msg = "Should not have been able to create " 
                    + badRecord.toString() + " with null font";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null font correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null font";
            fail(msg);
        }
    }
    
    @Test
    public void testGetXAndY() {
        System.out.println("WithXAndY.getPoint");
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        int x = RANDOM.nextInt(1920);
        int y = RANDOM.nextInt(1080);
        GraphicsCommandRecord.WithXAndY record 
                = new GraphicsCommandRecord.WithXAndY(command, color, font, 
                        x, y);
        Point expected = new Point(x, y);
        Point actual = record.getPoint();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetText() {
        System.out.println("WithString.getText");
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        int x = RANDOM.nextInt(1920);
        int y = RANDOM.nextInt(1080);
        String expected = "Some text " + RANDOM.nextInt();
        GraphicsCommandRecord.WithString record 
                = new GraphicsCommandRecord.WithString(command, color, font, x, 
                        y, expected);
        String actual = record.getText();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testConstructorRejectsNullText() {
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        int x = RANDOM.nextInt(1920);
        int y = RANDOM.nextInt(1080);
        try {
            GraphicsCommandRecord.WithString badRecord 
                    = new GraphicsCommandRecord.WithString(command, color, font, 
                            x, y, null);
            String msg = "should not have been able to create " 
                    + badRecord.toString() + " with null text";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null text correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null text";
            fail(msg);
        }
    }
    
    // TODO: Write test for non-null AttributedCharacterIterator
    
    @Test
    public void testConstructorRejectsNullAttributedCharacterIterator() {
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        int x = RANDOM.nextInt(1920);
        int y = RANDOM.nextInt(1080);
        try {
            GraphicsCommandRecord.WithAttributedCharacterIterator badRecord 
                    = new GraphicsCommandRecord
                            .WithAttributedCharacterIterator(command, color, 
                                    font, x, y, null);
            String msg = "should not have been able to create " 
                    + badRecord.toString() + " with null character iterator";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null character iterator correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null character iterator";
            fail(msg);
        }
    }
    
    @Test
    public void testGetSecondPoint() {
        System.out.println("getSecondPoint");
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        int x = RANDOM.nextInt(1920);
        int y = RANDOM.nextInt(1080);
        int dx = RANDOM.nextInt(1920);
        int dy = RANDOM.nextInt(1080);
        GraphicsCommandRecord.WithSecondXAndY record 
                = new GraphicsCommandRecord.WithSecondXAndY(command, color, 
                        font, x, y, dx, dy);
        Point expected = new Point(dx, dy);
        Point actual = record.getSecondPoint();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        int x = RANDOM.nextInt(1920);
        int y = RANDOM.nextInt(1080);
        int height = RANDOM.nextInt(1920);
        int width = RANDOM.nextInt(1080);
        GraphicsCommandRecord.WithSecondXAndY record 
                = new GraphicsCommandRecord.WithSecondXAndY(command, color, 
                        font, x, y, height, width);
        Dimension expected = new Dimension(height, width);
        Dimension actual = record.getDimension();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetShape() {
        System.out.println("WithShape.getShape");
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        int height = RANDOM.nextInt(1920) + 1;
        int width = RANDOM.nextInt(1080) + 1;
        int x = RANDOM.nextInt(height) + 1;
        int y = RANDOM.nextInt(width) + 1;
        Shape expected = new Rectangle(x, y, height, width);
        GraphicsCommandRecord.WithShape record 
                = new GraphicsCommandRecord.WithShape(command, color, font, 
                        expected);
        Shape actual = record.getShape();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testWithShapeConstructorRejectsNullShape() {
        String command = "command" + RANDOM.nextInt();
        Color color = new Color(RANDOM.nextInt());
        Font font = FONTS[RANDOM.nextInt(TOTAL_NUMBER_OF_FONTS)];
        try {
            GraphicsCommandRecord.WithShape badRecord 
                    = new GraphicsCommandRecord.WithShape(command, color, font, 
                            null);
            String msg = "Should not have been able to create " 
                    + badRecord.toString() + " with null shape";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Trying to use null shape correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null shape";
            fail(msg);
        }
    }
    
    @Test
    public void testGetImage() {
        System.out.println("WithImage.getImage");
        int width = 480;
        int height = 270;
        BufferedImage expected = new BufferedImage(width, height, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = expected.createGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString("image", width / 16, height / 9);
        Font font = FONTS[RANDOM.nextInt(FONTS.length)];
        ImageObserver observer = new Checkbox();
        GraphicsCommandRecord.WithImage record 
                = new GraphicsCommandRecord.WithImage("drawImage", Color.BLACK, 
                        font, expected, observer, Color.yellow, width, width, 
                        width, width, width, width, width, width);
        Image actual = record.getImage();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetObserver() {
        System.out.println("WithImage.getObserver");
        int width = 480;
        int height = 270;
        BufferedImage image = new BufferedImage(width, height, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString("image", width / 16, height / 9);
        Font font = FONTS[RANDOM.nextInt(FONTS.length)];
        ImageObserver expected = new Checkbox("FOR TESTING PURPOSES ONLY");
        GraphicsCommandRecord.WithImage record 
                = new GraphicsCommandRecord.WithImage("drawImage", Color.BLACK, 
                        font, image, expected, Color.yellow, width, width, 
                        width, width, width, width, width, width);
        ImageObserver actual = record.getObserver();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetBackgroundColor() {
        System.out.println("WithImage.getBackgroundColor");
        int width = 480;
        int height = 270;
        BufferedImage image = new BufferedImage(width, height, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString("image", width / 16, height / 9);
        Font font = FONTS[RANDOM.nextInt(FONTS.length)];
        ImageObserver observer = new Checkbox("FOR TESTING PURPOSES ONLY");
        Color expected = new Color(RANDOM.nextInt());
        GraphicsCommandRecord.WithImage record 
                = new GraphicsCommandRecord.WithImage("drawImage", Color.BLACK, 
                        font, image, observer, expected, width, width, 
                        width, width, width, width, width, width);
        Color actual = record.getBackgroundColor();
        assertEquals(expected, actual);
    }
    
}
