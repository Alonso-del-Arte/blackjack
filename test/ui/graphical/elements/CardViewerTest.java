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

import playingcards.CardDeck;
import playingcards.CardProvider;
import playingcards.CardServer;
import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.Suit;

import java.awt.event.ActionEvent;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CardViewer class.
 * @author Alonso del Arte
 */
public class CardViewerTest {
    
    private static final CardProvider CARD_PROVIDER = new CardProvider();
    
    private static final CardServer CARD_SERVER = new CardServer();
    
    @Test
    public void testConstructorRejectsNull() {
        try {
            CardViewer badViewer = new CardViewer(null);
            String msg = "Should not have been able to create " 
                    + badViewer.toString() + " with a null card";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Null card to constructor correctly caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for null card";
            fail(msg);
        }
    }
    
    @Test
    public void testGetDisplayedCard() {
        System.out.println("getDisplayedCard");
        CardDeck deck = new CardDeck();
        deck.shuffle();
        PlayingCard expected = deck.getNextCard();
        CardViewer viewer = new CardViewer(expected);
        PlayingCard actual = viewer.getDisplayedCard();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAuxiliaryConstructor() {
        CardViewer viewer = new CardViewer();
        PlayingCard expected = CARD_PROVIDER.giveCard(Rank.ACE, Suit.SPADES);
        PlayingCard actual = viewer.getDisplayedCard();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSetDisplayedCardRejectsNullCard() {
        CardViewer viewer = new CardViewer();
        try {
            viewer.setDisplayedCard(null);
            String msg = "Should not have been able to set viewer to null card";
            fail(msg);
        } catch (NullPointerException npe) {
            System.out.println("Setting viewer to null card caused NPE");
            System.out.println("\"" + npe.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for setting viewer to null card";
            fail(msg);
        }
    }
    
    @Test
    public void testSetDisplayedCard() {
        System.out.println("setDisplayedCard");
        CardDeck deck = new CardDeck();
        deck.shuffle();
        PlayingCard expected, actual;
        CardViewer viewer = new CardViewer();
        viewer.setUpViewer();
        while (deck.hasNext()) {
            expected = deck.getNextCard();
            viewer.setDisplayedCard(expected);
            actual = viewer.getDisplayedCard();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testNinePipsSymmetricalToggling() {
        if (!CardImage.ninePipsAreSymmetrical()) {
            CardImage.toggleNinePipsAreSymmetrical();
        }
        PlayingCard nine = CARD_SERVER.giveCard(Rank.NINE);
        CardViewer viewer = new CardViewer(nine);
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_FIRST, 
                CardViewer.NINE_PIPS_LABEL);
        viewer.actionPerformed(event);
        assert !CardImage.ninePipsAreSymmetrical() 
                : "Nine pips should be asymmetrical after toggling off";
        viewer.actionPerformed(event);
        assert CardImage.ninePipsAreSymmetrical() 
                : "Nine pips should be symmetrical after toggling back on";
    }
    
}
