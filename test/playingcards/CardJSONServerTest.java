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
package playingcards;

import java.io.InputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CardJSONServer class. And also of the static classes nested 
 * within it. Depending on how many seconds are specified by {@link 
 * CardJSONServer#DEFAULT_CLOSING_DELAY}, these tests may take twice or thrice 
 * as long to complete.
 * @author Alonso del Arte
 */
public class CardJSONServerTest {
    
    private static final int DEFAULT_HTTP_PORT = 8080;
    
    private static final int DEFAULT_STOP = 75;
    
    private static final Random RANDOM = new Random();
    
    @Test
    public void testCardGetDeckHash() {
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
    public void testCardGetShoeHash() {
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
    public void testCardToJSONString() {
        System.out.println("ProvenanceInscribedPlayingCard.toJSONString");
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                CardJSONServer.ProvenanceInscribedPlayingCard card 
                        = new CardJSONServer
                                .ProvenanceInscribedPlayingCard(rank, suit, 
                                        deckHash, shoeHash);
                String expected = "{\"name\":\"" + card.toString() 
                        + "\",\"rank\":\"" + card.getRank().getWord() 
                        + "\",\"suit\":\"" + card.getSuit().getWord() 
                        + "\",\"shoeID\":" + card.getShoeHash() + ",\"deckID\":" 
                        + card.getDeckHash() + ",\"unicodeSMPChar\":\"" 
                        + card.toUnicodeSMPChar() + "\"}";
                String actual = card.toJSONString();
                assertEquals(expected, actual);
            }
        }
    }
    
    @Test
    public void testCardReferentialEquality() {
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        CardJSONServer.ProvenanceInscribedPlayingCard card 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(Rank.JACK, 
                        Suit.CLUBS, deckHash, shoeHash);
        assertEquals(card, card);
    }
    
    @Test
    public void testCardNotEqualsNull() {
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        CardJSONServer.ProvenanceInscribedPlayingCard card 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(Rank.JACK, 
                        Suit.CLUBS, deckHash, shoeHash);
        assertNotEquals(card, null);
    }
    
    @Test
    public void testCardNotEqualsDiffClass() {
        Rank rank = Rank.ACE;
        Suit suit = Suit.SPADES;
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        CardJSONServer.ProvenanceInscribedPlayingCard card 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suit, 
                        deckHash, shoeHash);
        PlayingCard diffClassCard = new PlayingCard(rank, suit);
        assertNotEquals(card, diffClassCard);
    }
    
    @Test
    public void testCardNotEqualsDiffRank() {
        Rank rankA = Rank.ACE;
        Rank rankB = Rank.TWO;
        Suit suit = Suit.SPADES;
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        CardJSONServer.ProvenanceInscribedPlayingCard cardA 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rankA, suit, 
                        deckHash, shoeHash);
        CardJSONServer.ProvenanceInscribedPlayingCard cardB 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rankB, suit, 
                        deckHash, shoeHash);
        assertNotEquals(cardA, cardB);
    }
    
    @Test
    public void testCardEquals() {
        System.out.println("ProvenanceInscribedPlayingCard.equals");
        Rank rank = Rank.THREE;
        Suit suit = Suit.HEARTS;
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        CardJSONServer.ProvenanceInscribedPlayingCard someCard
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suit, 
                        deckHash, shoeHash);
        CardJSONServer.ProvenanceInscribedPlayingCard sameCard
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suit, 
                        deckHash, shoeHash);
        assertEquals(someCard, sameCard);
    }
    
    @Test
    public void testCardNotEqualsDiffSuit() {
        Rank rank = Rank.FIVE;
        Suit suitA = Suit.CLUBS;
        Suit suitB = Suit.DIAMONDS;
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        CardJSONServer.ProvenanceInscribedPlayingCard cardA 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suitA, 
                        deckHash, shoeHash);
        CardJSONServer.ProvenanceInscribedPlayingCard cardB 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suitB, 
                        deckHash, shoeHash);
        assertNotEquals(cardA, cardB);
    }
    
    @Test
    public void testCardNotEqualsDiffDeckHash() {
        Rank rank = Rank.SIX;
        Suit suit = Suit.SPADES;
        int deckHashA = RANDOM.nextInt();
        int deckHashB = deckHashA + 1;
        int shoeHash = RANDOM.nextInt();
        CardJSONServer.ProvenanceInscribedPlayingCard cardA 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suit, 
                        deckHashA, shoeHash);
        CardJSONServer.ProvenanceInscribedPlayingCard cardB 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suit, 
                        deckHashB, shoeHash);
        assertNotEquals(cardA, cardB);
    }
    
    @Test
    public void testCardNotEqualsDiffShoeHash() {
        Rank rank = Rank.SIX;
        Suit suit = Suit.SPADES;
        int deckHash = RANDOM.nextInt();
        int shoeHashA = RANDOM.nextInt();
        int shoeHashB = shoeHashA + 1;
        CardJSONServer.ProvenanceInscribedPlayingCard cardA 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suit, 
                        deckHash, shoeHashA);
        CardJSONServer.ProvenanceInscribedPlayingCard cardB 
                = new CardJSONServer.ProvenanceInscribedPlayingCard(rank, suit, 
                        deckHash, shoeHashB);
        assertNotEquals(cardA, cardB);
    }
    
    /**
     * Test of the hashCode function of the ProvenanceInscribedPlayingCard, a 
     * static class nested in CardJSONServer. The hash code of a 
     * ProvenanceInscribedPlayingCard should not be equal to that of the 
     * corresponding plain PlayingCard instance, but they should match modulo 
     * 65536.
     */
    @Test
    public void testCardHashCode() {
        System.out.println("ProvenanceInscribedPlayingCard.hashCode");
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                CardJSONServer.ProvenanceInscribedPlayingCard card 
                        = new CardJSONServer
                                .ProvenanceInscribedPlayingCard(rank, suit, 
                                        deckHash, shoeHash);
                PlayingCard correspondingCard = new PlayingCard(rank, suit);
                int plainCardHash = correspondingCard.hashCode();
                int expected = ((deckHash * shoeHash) << 16) + plainCardHash;
                int actual = card.hashCode();
                String msg = "Expecting hash code for " + card.toString() 
                        + " to correspond to hash code " + plainCardHash;
                assertEquals(msg, expected, actual);
            }
        }
    }
    
    @Test
    public void testCardParseJSONThrowsExceptionForInvalidInput() {
        String s = "For testing purposes only";
        try {
            PlayingCard badCard 
                    = CardJSONServer.ProvenanceInscribedPlayingCard
                            .parseJSON(s);
            String msg = "\"" + s 
                    + "\" should have caused an exception, not given " 
                    + badCard.toString();
            fail(msg);
        } catch (NoSuchElementException nsee) {
            System.out.println("Bad parse input \"" + s 
                    + "\" correctly caused NoSuchElementException");
            System.out.println("\"" + nsee.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for bad parse input \"" + s 
                    + "\"";
            fail(msg);
        }
    }
    
    @Test
    public void testCardParseJSON() {
        System.out.println("ProvenanceInscribedPlayingCard.parseJSON");
        int shoeHash = RANDOM.nextInt();
        int deckHash = RANDOM.nextInt();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                CardJSONServer.ProvenanceInscribedPlayingCard expected 
                        = new CardJSONServer
                                .ProvenanceInscribedPlayingCard(rank, suit, 
                                        deckHash, shoeHash);
                String json = expected.toJSONString();
                CardJSONServer.ProvenanceInscribedPlayingCard actual 
                        = CardJSONServer.ProvenanceInscribedPlayingCard
                                .parseJSON(json);
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
            String msg = card.toString() + " that was drawn from deck " 
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
    public void testDeckThrowsExceptionWhenOutOfCards() {
        CardJSONServer.Deck deck = new CardJSONServer.Deck(this.hashCode());
        deck.shuffle();
        PlayingCard lastGivenCard = new PlayingCard(Rank.ACE, Suit.SPADES);
        while (deck.hasNext()) {
            lastGivenCard = deck.getNextCard();
        }
        String msgPart = "After giving last card " + lastGivenCard.toString() 
                + ", trying to get another card ";
        try {
            PlayingCard badCard = deck.getNextCard();
            String msg = msgPart + "should not have given " 
                    + badCard.toString();
            fail(msg);
        } catch (RanOutOfCardsException roce) {
            System.out.println(msgPart 
                    + "correctly caused RanOutOfCardsException");
            System.out.println("\"" + roce.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = msgPart 
                    + "should have caused RanOutOfCardsException, not " 
                    + re.getClass().getName();
            fail(msg);
        }
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
    
    @Test
    public void testShoeHasNextLimitedByStop() {
        int deckQty = RANDOM.nextInt(8) + 2;
        int stop = 75 + RANDOM.nextInt(15);
        int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        CardSupplier shoe = new CardJSONServer.Shoe(deckQty, stop);
        List<PlayingCard> cards = new ArrayList<>(expected);
        while (shoe.hasNext()) {
            cards.add(shoe.getNextCard());
        }
        int actual = cards.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testShoeGetNextCard() {
        System.out.println("Shoe.getNextCard");
        int expected = RANDOM.nextInt(8) + 2;
        CardSupplier shoe = new CardJSONServer.Shoe(expected);
        Map<PlayingCard, Integer> counts 
                = new HashMap<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (shoe.hasNext()) {
            PlayingCard card = shoe.getNextCard();
            PlayingCard transferCard = new PlayingCard(card.getRank(), 
                    card.getSuit());
            if (counts.containsKey(transferCard)) {
                counts.put(transferCard, counts.get(transferCard) + 1);
            } else {
                counts.put(transferCard, 1);
            }
        }
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                PlayingCard card = new PlayingCard(rank, suit);
                String containsMsg = "Map of card counts should have " 
                        + card.toString();
                assert counts.containsKey(card) : containsMsg;
                int actual = counts.get(card);
                String msg = "Shoe with " + expected 
                        + " decks should have as many of " + card.toString();
                assertEquals(msg, expected, actual);
            }
        }
    }
    
    @Test
    public void testShoeProvenance() {
        System.out.println("Shoe.provenance");
        int deckQty = RANDOM.nextInt(8) + 2;
        int stop = 75 + RANDOM.nextInt(15);
        CardSupplier shoe = new CardJSONServer.Shoe(deckQty, stop);
        while (shoe.hasNext()) {
            PlayingCard card = shoe.getNextCard();
            String msg = card.toString() + " that was drawn from shoe " 
                    + shoe.toString() + " should not be disavowed";
            assert shoe.provenance(card) : msg;
        }
    }
    
    @Test
    public void testShoeProvenanceFromDifferentShoe() {
        int deckQty = RANDOM.nextInt(8) + 2;
        int stop = 75 + RANDOM.nextInt(15);
        CardSupplier shoeA = new CardJSONServer.Shoe(deckQty, stop);
        CardSupplier shoeB = new CardJSONServer.Shoe(deckQty, stop);
        PlayingCard cardFromShoeA = shoeA.getNextCard();
        PlayingCard cardFromShoeB = shoeB.getNextCard();
        String msgA = "Card " + cardFromShoeA.toString() 
                + " from Shoe A should not be said to be from Shoe B";
        assert !shoeB.provenance(cardFromShoeA) : msgA;
        String msgB = "Card " + cardFromShoeB.toString() 
                + " from Shoe B should not be said to be from Shoe A";
        assert !shoeA.provenance(cardFromShoeB) : msgB;
    }
    
    @Test
    public void testShoeProvenanceNotInscribedCard() {
        int deckQty = RANDOM.nextInt(8) + 2;
        int stop = 75 + RANDOM.nextInt(15);
        CardSupplier shoe = new CardJSONServer.Shoe(deckQty, stop);
        CardDeck deck = new CardDeck();
        deck.shuffle();
        PlayingCard nonInscribedCard = deck.getNextCard();
        String msg = "Card " + nonInscribedCard.toString() 
                + " is not provenance-inscribed, should not be avowed by shoe";
        assert !shoe.provenance(nonInscribedCard) : msg;
    }
    
    @Test
    public void testShoeInscribesCardWithProvenance() {
        int deckQty = RANDOM.nextInt(8) + 2;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer.Shoe shoe = new CardJSONServer.Shoe(deckQty, stop);
        int expected = shoe.hashCode();
        while (shoe.hasNext()) {
            PlayingCard card = shoe.getNextCard();
            String typeMsg = "Card " + card.toASCIIString() 
                    + " should be a ProvenanceInscribedPlayingCard";
            assert card instanceof CardJSONServer.ProvenanceInscribedPlayingCard 
                    : typeMsg;
            int actual = ((CardJSONServer.ProvenanceInscribedPlayingCard) 
                    card).getShoeHash();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    public void testShoeShuffle() {
        System.out.println("Shoe.shuffle");
        int expected = RANDOM.nextInt(8) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer.Shoe shoe = new CardJSONServer.Shoe(expected, stop);
        shoe.shuffle();
        Set<Integer> deckHashes = new HashSet<>(expected);
        int sampleSize = 3 * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK / 2;
        int curr = 0;
        while (curr < sampleSize) {
            CardJSONServer.ProvenanceInscribedPlayingCard card 
                    = shoe.getNextCard();
            deckHashes.add(card.getDeckHash());
            curr++;
        }
        int actual = deckHashes.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testShoeThrowsExceptionWhenOutOfCards() {
        int deckQty = RANDOM.nextInt(8) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer.Shoe shoe = new CardJSONServer.Shoe(deckQty, stop);
        shoe.shuffle();
        PlayingCard lastGivenCard = new PlayingCard(Rank.ACE, Suit.SPADES);
        while (shoe.hasNext()) {
            lastGivenCard = shoe.getNextCard();
        }
        String msgPart = "After giving last card " + lastGivenCard.toString() 
                + ", trying to get another card ";
        try {
            PlayingCard badCard = shoe.getNextCard();
            String msg = msgPart + "should not have given " 
                    + badCard.toString();
            fail(msg);
        } catch (RanOutOfCardsException roce) {
            System.out.println(msgPart 
                    + "correctly caused RanOutOfCardsException");
            System.out.println("\"" + roce.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = msgPart 
                    + "should have caused RanOutOfCardsException, not " 
                    + re.getClass().getName();
            fail(msg);
        }
    }
    
    @Test
    public void testShoeConstructorRejectsNegativeDeckQuantity() {
        int badQty = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer.Shoe badShoe = new CardJSONServer.Shoe(badQty);
            String msg = "Should not have been able to create " 
                    + badShoe.toString() + " with bad deck quantity " + badQty;
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
    public void testShoeConstructorRejectsDeckQuantityZero() {
        int badQty = 0;
        try {
            CardJSONServer.Shoe badShoe = new CardJSONServer.Shoe(badQty);
            String msg = "Should not have been able to create " 
                    + badShoe.toString() + " with bad deck quantity " + badQty;
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
    public void testShoeConstructorRejectsNegativeStop() {
        int deckQty = RANDOM.nextInt(8) + 2;
        int badStop = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer.Shoe badShoe = new CardJSONServer.Shoe(deckQty, 
                    badStop);
            String msg = "Should not have been able to create " 
                    + badShoe.toString() + " with bad stop " + badStop;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad stop " + badStop 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck stop " 
                    + badStop;
            fail(msg);
        }
    }
    
    @Test
    public void testNoDoubleActivation() {
        int deckQty = RANDOM.nextInt(8) + 2;
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTP_PORT, deckQty, 
                DEFAULT_STOP);
        server.activate();
        try {
            server.activate();
            String msg = "Should not have been able to activate " 
                    + server.toString() + " twice in a row";
            server.deactivate();
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to activate " + server.toString() 
                    + " twice in a row correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
            server.deactivate();
        } catch (RuntimeException re) {
            server.deactivate();
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to activate " 
                    + server.toString() + " twice in a row";
            fail(msg);
        }
    }
    
    @Test
    public void testGiveCard() {
        System.out.println("giveCard");
        int port = 8080;
        int deckQty = 2;
        int stop = 25;
        int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        Set<CardJSONServer.ProvenanceInscribedPlayingCard> cards 
                = new HashSet<>(expected);
        int index = 0;
        while (index < expected) {
            cards.add(server.giveCard());
            index++;
        }
        int actual = cards.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGiveCardReplenishesAutomaticallyAfterRunningOut() {
        int port = 8080;
        int deckQty = 2;
        int stop = 25;
        int initialCardQty = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        Set<Integer> deckIDNumbers = new HashSet<>(initialCardQty);
        int index = 0;
        while (index < initialCardQty) {
            deckIDNumbers.add(server.giveCard().getDeckHash());
            index++;
        }
        try {
            CardJSONServer.ProvenanceInscribedPlayingCard card 
                    = server.giveCard();
            System.out.println("After running out of the first " 
                    + initialCardQty 
                    + " cards, server correctly replenished cards");
            assertEquals(deckQty, deckIDNumbers.size());
            deckIDNumbers.add(card.getDeckHash());
            String msg = "Card " + card.toString() 
                    + " should come from replenishment deck";
            assert deckIDNumbers.size() > deckQty : msg;
        } catch (RanOutOfCardsException roce) {
            System.out.println("\"" + roce.getMessage() + "\"");
            String msg = "RanOutOfCardsException should not have occurred";
            fail(msg);
        }
    }
    
    @Test
    public void testGiveCardReplenishesWithSameDeckQtyAndStop() {
        int port = 8080;
        int expected = RANDOM.nextInt(10) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        int initialCardQty = expected 
                * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - stop;
        CardJSONServer server = new CardJSONServer(port, expected, stop);
        Set<Integer> initialDeckHashes = new HashSet<>(initialCardQty);
        int index = 0;
        while (index < initialCardQty) {
            initialDeckHashes.add(server.giveCard().getDeckHash());
            index++;
        }
        Set<Integer> replenishmentDeckHashes = new HashSet<>(initialCardQty);
        index = 0;
        int midwayCheckPoint = initialCardQty / 2;
        while (index < midwayCheckPoint) {
            int hash = server.giveCard().getDeckHash();
            replenishmentDeckHashes.add(hash);
            index++;
            String msg = "Replenishment deck hash " + hash + " of card indexed "
                    + index 
                    + " should not be present among initial deck hashes";
            assert !initialDeckHashes.contains(hash) : msg;
        }
        int actual = replenishmentDeckHashes.size();
        assertEquals(expected, actual);
        while (index < initialCardQty) {
            int hash = server.giveCard().getDeckHash();
            assert !replenishmentDeckHashes.add(hash) 
                    : "All replenishment deck hashes should already be known";
            index++;
            String msg = "Replenishment deck hash " + hash + " of card indexed "
                    + index 
                    + " should not be present among initial deck hashes";
            assert !initialDeckHashes.contains(hash) : msg;
        }
        int hash = server.giveCard().getDeckHash();
        String msg = "Second replenishment deck hash " + hash 
                + " should not be an initial hash nor first replenishment hash";
        assert !initialDeckHashes.contains(hash) : msg;
        assert !replenishmentDeckHashes.contains(hash) : msg;
    }
    
    @Test
    public void testServerRespondsOnSpecifiedPort() {
        int port = 8080 + RANDOM.nextInt(1000);
        int deckQty = RANDOM.nextInt(10) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        server.activate();
        System.out.println("Expecting localhost response on port " + port);
        String locator = "http://localhost:" + port + "/dealcard";
        String key = "User-Agent";
        String value = "Java/" + System.getProperty("java.version");
        try {
            URL url = new URL(locator);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty(key, value);
            int expected = HttpURLConnection.HTTP_OK;
            int actual = conn.getResponseCode();
            server.deactivate();
            assertEquals(expected, actual);
        } catch (IOException ioe) {
            server.deactivate();
            String msg = ioe.getClass().getName() + " should not have occurred";
            fail(msg);
        }
    }
    
    @Test
    public void testNoDeactivationForInactive() {
        int deckQty = RANDOM.nextInt(8) + 4;
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTP_PORT, deckQty, 
                DEFAULT_STOP);
        try {
            server.deactivate();
            String msg = "Should not have been able to deactivate " 
                    + server.toString() + " because it was already inactive";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to activate " + server.toString() 
                    + " twice correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to activate " 
                    + server.toString() + " twice";
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorSetsSpecifiedDeckQuantityAndStop() {
        int deckQty = RANDOM.nextInt(8) + 4;
        int stop = 75 + RANDOM.nextInt(25);
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTP_PORT, deckQty, 
                stop);
        int totalCardQty = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        PlayingCard lastCard = new PlayingCard(Rank.JACK, Suit.CLUBS);
        int curr = 0;
        try {
            while (curr < totalCardQty) {
                lastCard = server.giveCard();
                curr++;
            }
            System.out.println("Gave out " + totalCardQty 
                    + " cards, last of which was " + lastCard.toASCIIString());
        } catch (RanOutOfCardsException roce) {
            String msg = "Given shoe with " + totalCardQty 
                    + " cards, exception should not have occurred after " + curr 
                    + " cards, last of which was " + lastCard.toString();
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorShufflesShoe() {
        int expected = RANDOM.nextInt(8) + 3;
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTP_PORT, expected, 
                DEFAULT_STOP);
        Set<Integer> deckHashes = new HashSet<>(expected);
        for (int i = 0; i < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK; i++) {
            deckHashes.add(server.giveCard().getDeckHash());
        }
        int actual = deckHashes.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testConstructorRejectsNegativePort() {
        int badPort = -RANDOM.nextInt(Short.MAX_VALUE) - 1;
        try {
            CardJSONServer badServer = new CardJSONServer(badPort, 1, 0);
            String msg = "Should not have been able to create " 
                    + badServer.toString() + " with bad port number " + badPort;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad port number " + badPort 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad port number " 
                    + badPort;
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsOutOfRangePort() {
        int badPort = 4 * Short.MAX_VALUE + RANDOM.nextInt(Byte.MAX_VALUE) + 1;
        try {
            CardJSONServer badServer = new CardJSONServer(badPort, 1, 0);
            String msg = "Should not have been able to create " 
                    + badServer.toString() + " with bad port number " + badPort;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad port number " + badPort 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad port number " 
                    + badPort;
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsNegativeDeckQuantity() {
        int badQty = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTP_PORT, 
                    badQty, 0);
            String msg = "Should not have been able to create " 
                    + badServer.toString() + " with bad deck quantity " 
                    + badQty;
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
    public void testConstructorRejectsDeckQuantityZero() {
        int badQty = 0;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTP_PORT, 
                    badQty, 0);
            String msg = "Should not have been able to create " 
                    + badServer.toString() + " with bad deck quantity " 
                    + badQty;
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
    public void testConstructorRejectsNegativeStop() {
        int deckQty = RANDOM.nextInt(8) + 2;
        int badStop = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTP_PORT, 
                    deckQty, badStop);
            String msg = "Should not have been able to create " 
                    + badServer.toString() + " with bad stop " + badStop;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad stop " + badStop 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck stop " 
                    + badStop;
            fail(msg);
        }
    }
    
}
