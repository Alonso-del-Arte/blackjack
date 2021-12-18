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
package ui.graphical.elements;

import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.Suit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Alonso del Arte
 */
public class CardImage {
    
    private final PlayingCard playingCard;
    
    private void writeEdgeLegend(Graphics g, final Point p, 
            final Dimension size) {
        Point point = new Point(p.x, p.y);
        point.translate(10, 100);
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 72));
        g.drawString(this.playingCard.toString(), point.x, point.y);
    }
    
    private void drawPips(Graphics g, final Point p, final Dimension size) {
        System.out.println(p.toString());
        //
    }
    
    private void drawRoyal(Graphics g, final Point p, final Dimension size) {
        System.out.println(p.toString());
        //
    }
    
    public void paintFaceUp(Graphics g, final Point p, final Dimension size) {
        // TODO: Write tests for this
        g.setColor(Color.BLACK);
        g.drawRect(p.x, p.y, size.width, size.height);
        g.setColor(Color.WHITE);
        g.fillRect(p.x, p.y, size.width, size.height);
//        g.setColor(this.playingCard.getSuit());
// TODO: Shift this to the Suit enumeration
Color textColor = Color.GREEN;
switch (this.playingCard.getSuit()) {
    case CLUBS:
    case SPADES:
        textColor = Color.BLACK;
        break;
    case DIAMONDS:
    case HEARTS:
        textColor = Color.RED;
        break;
    default:
        throw new RuntimeException("Unexpected suit " 
                + this.playingCard.getSuit().toString());
}
g.setColor(textColor);
        this.writeEdgeLegend(g, p, size);
        if (this.playingCard.isCourtCard()) {
            this.drawRoyal(g, p, size);
        } else {
            this.drawPips(g, p, size);
        }
        g.setColor(Color.YELLOW);
        g.drawString("TODO: Write tests for this", p.x + 20, p.y + 300);
    }
    
    public void paintFaceDown(Graphics g, final Point p, final Dimension size) {
        // TODO: Write tests for this
        g.setColor(Color.YELLOW);
        g.drawString("TODO: Write tests for this", p.x, p.y);
    }
    
    public CardImage(PlayingCard card) {
        if (card == null) {
            String excMsg = "Playing card should not be null";
            throw new NullPointerException(excMsg);
        }
        this.playingCard = card;
    }
    
}
