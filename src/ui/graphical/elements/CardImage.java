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
import java.awt.Point;

/**
 *
 * @author Alonso del Arte
 */
public class CardImage {
    
    private final PlayingCard playingCard;
    
    private final Color textColor;
    
    private final Rank rank;
    
    private final Suit suit;
    
    private final String suitChar;
    
    private Font pipFont, invertedPipFont;
    
    private void writeEdgeLegend(Graphics g, final Point p, 
            final Dimension size) {
        Point point = new Point(p.x, p.y);
        point.translate(10, 40);
        g.setColor(this.textColor);
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 32));
        g.drawString(this.playingCard.getRank().getChars(), point.x, point.y);
        point.translate(0, 32);
        g.drawString(Character.toString(this.playingCard.getSuit().getChar()), 
                point.x, point.y);
        point = new Point(p.x + size.width - 10, p.y + size.height - 40);
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, -32));
        g.drawString(this.playingCard.getRank().getChars(), point.x, point.y);
        point.translate(0, -32);
        g.drawString(Character.toString(this.playingCard.getSuit().getChar()), 
                point.x, point.y);
    }
    
    /* 
drawMiddlePipCentered: ACE, THREE, FIVE, NINE
drawCornerPips: FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN
drawTwoMiddlePips: SIX, SEVEN, EIGHT, TEN
drawMiddlePipInUpperHalf: SEVEN, EIGHT, TEN
drawMiddlePipInLowerHalf: EIGHT, TEN
drawFourMiddlePips: NINE, TEN
    */
    
    private void drawAceOfSpades(Graphics g, final Point p, 
            final Dimension size) {
        // TODO: Replace with special Ace of Spades drawing
        this.drawMiddlePipCentered(g, p, size);
    }
    
    private void drawMiddlePipCentered(Graphics g, final Point p, 
            final Dimension size) {
        g.drawString(this.suitChar, p.x + 90, p.y + 210);
    }
    
    private void drawTwoPipsCentered(Graphics g, final Point p, 
            final Dimension size) {
        //
    }
    
    private void drawCornerPips(Graphics g, final Point p, 
            final Dimension size) {
        //
    }
    
    private void drawTwoMiddlePips(Graphics g, final Point p, 
            final Dimension size) {
        //
    }
    
    private void drawMiddlePipInUpperHalf(Graphics g, final Point p, 
            final Dimension size) {
        //
    }
    
    private void drawMiddlePipInLowerHalf(Graphics g, final Point p, 
            final Dimension size) {
        //
    }
    
    private void drawFourMiddlePips(Graphics g, final Point p, 
            final Dimension size) {
        //
    }
    
    private void drawPips(Graphics g, final Point p, final Dimension size) {
        g.setColor(this.textColor);
        this.pipFont = new Font(g.getFont().getFontName(), Font.PLAIN, 72);
        g.setFont(this.pipFont);
        if (this.rank.equals(Rank.ACE)) {
            if (this.suit.equals(Suit.SPADES)) {
                this.drawAceOfSpades(g, p, size);
            } else {
                this.drawMiddlePipCentered(g, p, size);
            }
        } else {
            this.invertedPipFont = new Font(g.getFont().getFontName(), 
                    Font.PLAIN, -72);
            switch (this.rank) {
                case THREE:
                    this.drawMiddlePipCentered(g, p, size);
                case TWO:
                    this.drawTwoPipsCentered(g, p, size);
                    break;
                case FIVE:
                    this.drawMiddlePipCentered(g, p, size);
                case FOUR:
                    this.drawCornerPips(g, p, size);
                    break;
                case SEVEN:
                    this.drawMiddlePipInUpperHalf(g, p, size);
                case SIX:
                    this.drawCornerPips(g, p, size);
                    this.drawTwoMiddlePips(g, p, size);
                    break;
                case TEN:
                    this.drawMiddlePipInLowerHalf(g, p, size);
                case NINE:
                    this.drawMiddlePipInUpperHalf(g, p, size);
                case EIGHT:
                    this.drawFourMiddlePips(g, p, size);
                    this.drawCornerPips(g, p, size);
                    break;
                default:
                    throw new RuntimeException("Unexpected rank: " 
                            + this.rank.toString());
            }
        }
    }
    
    private void drawRoyal(Graphics g, final Point p, final Dimension size) {
        g.setColor(Color.BLACK);
        g.drawRect(p.x + 30, p.y + 30, size.width - 60, size.height - 50);
        g.drawString("PLACEHOLDER", p.x + 20, p.y + 200);
    }
    
    private void paintBlankCard(Graphics g, final Point p, 
            final Dimension size) {
        g.setColor(Color.BLACK);
        g.drawRoundRect(p.x, p.y, size.width, size.height, 10, 10);
        g.setColor(Color.WHITE);
        g.fillRoundRect(p.x + 1, p.y + 1, size.width, size.height, 10, 10);
    }
    
    public void paintFaceUp(Graphics g, final Point p, final Dimension size) {
        // TODO: Write tests for this
        this.paintBlankCard(g, p, size);
        if (this.playingCard.isCourtCard()) {
            this.drawRoyal(g, p, size);
        } else {
            this.drawPips(g, p, size);
        }
        this.writeEdgeLegend(g, p, size);
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Courier New", Font.BOLD, 56));
        g.drawString("DRAFT", p.x + 20, p.y + 250);
        // TODO: Delete previous line after first fail on the tests
    }
    
    public void paintFaceDown(Graphics g, final Point p, final Dimension size) {
        // TODO: Write tests for this
        this.paintBlankCard(g, p, size);
        g.setColor(Color.YELLOW);
        g.drawString("TODO: Write tests for this", p.x, p.y);
    }
    
    public CardImage(PlayingCard card) {
        if (card == null) {
            String excMsg = "Playing card should not be null";
            throw new NullPointerException(excMsg);
        }
        this.playingCard = card;
        this.textColor = this.playingCard.getTextColor();
        this.rank = this.playingCard.getRank();
        this.suit = this.playingCard.getSuit();
        this.suitChar = Character.toString(this.suit.getChar());
    }
    
}
