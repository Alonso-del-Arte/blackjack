/*
 * Copyright (C) 2022 Alonso del Arte
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
import playingcards.Rank;
import playingcards.Suit;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * Just a simple program to display playing card images. This is not meant to 
 * supplant automated tests, but rather to help me write those tests.
 * @author Alonso del Arte
 */
public class CardViewer extends JPanel implements ActionListener, ItemListener {
    
    private static final Color BACKGROUND_COLOR = new Color(0, 128, 0);
    
    private static final Dimension USUAL_CARD_SIZE = new Dimension(250, 350);
    
    private static final Dimension PANEL_SIZE = new Dimension(270, 370);
    
    private static final Point TOP_LEFT_CORNER_PLACE = new Point(10, 10);
    
    private static final CardProvider CARD_GIVER = new CardProvider();
    
    private static final PlayingCard ACE_OF_SPADES 
            = CARD_GIVER.giveCard(Rank.ACE, Suit.SPADES);
    
    private PlayingCard currCard;
    
    private JFrame frame;
    
    private final JComboBox<Rank> rankList = new JComboBox<>(Rank.values());
    private final JComboBox<Suit> suitList = new JComboBox<>(Suit.values());
    
    private boolean faceUp = true;
    
    private CardImage cardImage;
    
    static final String NINE_PIPS_LABEL = "Nine pips symmetrical";
    
    private final JCheckBox ninePipsCheckBox = new JCheckBox(NINE_PIPS_LABEL, 
            CardImage.ninePipsAreSymmetrical());
    
    PlayingCard getDisplayedCard() {
        return this.currCard;
    }
    
    void setDisplayedCard(PlayingCard card) {
        if (card == null) {
            String excMsg = "Card must not be null";
            throw new NullPointerException(excMsg);
        }
        this.currCard = card;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.faceUp) {
            this.cardImage.paintFaceUp(g, TOP_LEFT_CORNER_PLACE, 
                    USUAL_CARD_SIZE);
        } else {
            this.cardImage.paintFaceDown(g, TOP_LEFT_CORNER_PLACE, 
                    USUAL_CARD_SIZE);
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie) {
        Rank rank = (Rank) this.rankList.getSelectedItem();
        Suit suit = (Suit) this.suitList.getSelectedItem();
        this.currCard = CARD_GIVER.giveCard(rank, suit);
        this.cardImage = new CardImage(this.currCard);
        this.frame.setTitle("Card Viewer: " + this.currCard.toString());
        this.ninePipsCheckBox.setEnabled(this.currCard.getRank()
                .equals(Rank.NINE));
        this.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
//        String command = ae.getActionCommand();
//        switch (command) {
//            case NINE_PIPS_LABEL:
                CardImage.toggleNinePipsAreSymmetrical();
                this.repaint();
//                break;
//            default:
//                System.err.println("Command \"" + command 
//                        + "\" not recognized");
//        }
    }
    
    public void setUpViewer() {
        this.frame = new JFrame("Card Viewer: " + this.currCard.toString());
        this.setBackground(BACKGROUND_COLOR);
        this.setPreferredSize(PANEL_SIZE);
        JPanel dropDowns = new JPanel();
        dropDowns.add(this.rankList);
        dropDowns.add(this.suitList);
        this.rankList.addItemListener(this);
        this.suitList.addItemListener(this);
        this.frame.add(dropDowns, BorderLayout.PAGE_START);
        this.frame.add(this, BorderLayout.CENTER);
        this.ninePipsCheckBox.addActionListener(this);
        this.ninePipsCheckBox.setEnabled(this.currCard.getRank()
                .equals(Rank.NINE));
        JPanel ninePipsCheckBoxPanel = new JPanel();
        ninePipsCheckBoxPanel.add(this.ninePipsCheckBox);
        this.frame.add(ninePipsCheckBoxPanel, BorderLayout.PAGE_END);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
    }
    
    CardViewer() {
        this(ACE_OF_SPADES);
    }
    
    /**
     * Primary constructor.
     * @param card The card to first display.
     * @throws NullPointerException If <code>card</code> is null.
     */
    CardViewer(PlayingCard card) {
        if (card == null) {
            String excMsg = "Card must not be null";
            throw new NullPointerException(excMsg);
        }
        this.currCard = card;
        this.cardImage = new CardImage(this.currCard);
    }
    
    public static void main(String[] args) {
        CardViewer viewer = new CardViewer();
        viewer.setUpViewer();
    }
    
}
