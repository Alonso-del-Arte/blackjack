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

import playingcards.CardProvider;
import playingcards.PlayingCard;
import playingcards.Suit;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import playingcards.Rank;

/**
 *
 * @author Alonso del Arte
 */
public class CardViewer {
    
    private static final CardProvider CARD_PROVIDER = new CardProvider();
    
    private static final PlayingCard ACE_OF_SPADES 
            = CARD_PROVIDER.giveCard(Rank.ACE, Suit.SPADES);
    
    private PlayingCard currCard = ACE_OF_SPADES;
    
    // TODO: Make this program
    
    CardViewer() {
        this(null);
    }
    
    CardViewer(PlayingCard card) {
        // TODO: Write tests for this
    }
    
    public static void main(String[] args) {
        //
    }
    
}
