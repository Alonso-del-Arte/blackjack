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
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CardJSONServer class. And also of the static classes nested 
 * within it.
 * @author Alonso del Arte
 */
public class CardJSONServerTest {
    
    private static final Random RANDOM = new Random();
    
    @Test
    public void getDeckHash() {
        System.out.println("ProvenanceInscribedPlayingCard.getDeckHash");
        int expected = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                CardJSONServer.ProvenanceInscribedPlayingCard card 
                        = new CardJSONServer
                                .ProvenanceInscribedPlayingCard(rank, suit, 
                                        expected, 0);
                int actual = card.getDeckHash();
                assertEquals(expected, actual);
            }
        }
    }
    
    @Test
    public void getShoeHash() {
        System.out.println("ProvenanceInscribedPlayingCard.getShoeHash");
        int expected = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                CardJSONServer.ProvenanceInscribedPlayingCard card 
                        = new CardJSONServer
                                .ProvenanceInscribedPlayingCard(rank, suit, 
                                        0, expected);
                int actual = card.getShoeHash();
                assertEquals(expected, actual);
            }
        }
    }
    
    @Test
    public void testDeckHasNext() {
        System.out.println("Deck.hasNext");
        CardSupplier deck = new CardJSONServer.Deck(this.hashCode());
        List<PlayingCard> cards 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (deck.hasNext()) {
            cards.add(deck.getNextCard());
        }
        assertEquals(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK, cards.size());
    }
    
    @Test
    public void testDeckGetNextCard() {
        System.out.println("Deck.getNextCard");
        CardSupplier deck = new CardJSONServer.Deck(this.hashCode());
        Set<PlayingCard> cards 
                = new HashSet<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (deck.hasNext()) {
            cards.add(deck.getNextCard());
        }
        assertEquals(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK, cards.size());
    }
    
    @Test
    public void testDeckProvenance() {
        System.out.println("Deck.provenance");
        CardSupplier deck = new CardJSONServer.Deck(this.hashCode());
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            String msg = card.toASCIIString() + " that was drawn from deck " 
                    + deck.toString() + " should not be disavowed";
            assert deck.provenance(card) : msg;
        }
    }
    
    @Test
    public void testDeckDiffProvenance() {
        CardSupplier deck = new CardJSONServer.Deck(this.hashCode());
        CardSupplier diffDeck = new CardDeck();
        while (diffDeck.hasNext()) {
            PlayingCard card = diffDeck.getNextCard();
            String msg = card.toASCIIString() + " that was drawn from deck " 
                    + diffDeck.toString() 
                    + " should not be acknowledged as coming from " 
                    + deck.toString();
            assert !deck.provenance(card) : msg;
        }
    }
    
    @Test
    public void testDeckShuffle() {
        System.out.println("Deck.shuffle");
        CardSupplier firstDeck = new CardJSONServer.Deck(this.hashCode());
        List<PlayingCard> originalOrderCards 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (firstDeck.hasNext()) {
            originalOrderCards.add(firstDeck.getNextCard());
        }
        CardJSONServer.Deck secondDeck 
                = new CardJSONServer.Deck(this.hashCode());
        secondDeck.shuffle();
        List<PlayingCard> shuffledCards 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (secondDeck.hasNext()) {
            shuffledCards.add(secondDeck.getNextCard());
        }
        assertNotEquals(originalOrderCards, shuffledCards);
    }
    
    @Test
    public void testDeckInscribesCardsWithProvenance() {
        CardJSONServer.Deck deck = new CardJSONServer.Deck(this.hashCode());
        int expected = deck.hashCode();
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            String typeMsg = "Card " + card.toASCIIString() 
                    + " should be a ProvenanceInscribedPlayingCard";
            assert card instanceof CardJSONServer.ProvenanceInscribedPlayingCard 
                    : typeMsg;
            int actual = ((CardJSONServer.ProvenanceInscribedPlayingCard) 
                    card).getDeckHash();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testShoeHasNext() {
        System.out.println("Shoe.hasNext");
        int deckQty = RANDOM.nextInt(8) + 2;
        int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        CardSupplier shoe = new CardJSONServer.Shoe(deckQty);
        List<PlayingCard> cards = new ArrayList<>(expected);
        while (shoe.hasNext()) {
            cards.add(shoe.getNextCard());
        }
        int actual = cards.size();
        assertEquals(expected, actual);
    }
    
//    @Test
    public void testShoeInscribesCardWithProvenance() {
        //
    }
    
    @Test
    public void testShoeRejectsNegativeDeckQuantity() {
        int badQty = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer.Shoe badShoe = new CardJSONServer.Shoe(badQty);
            String msg = "Should not have been able to create " 
                    + badShoe.toString() + " with bad quantity " + badQty;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad quantity " + badQty 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck quantity " 
                    + badQty;
            fail(msg);
        }
    }
    
    @Test
    public void testShoeRejectsDeckQuantityZero() {
        int badQty = 0;
        try {
            CardJSONServer.Shoe badShoe = new CardJSONServer.Shoe(badQty);
            String msg = "Should not have been able to create " 
                    + badShoe.toString() + " with bad quantity " + badQty;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad quantity " + badQty 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck quantity " 
                    + badQty;
            fail(msg);
        }
    }
    
}
