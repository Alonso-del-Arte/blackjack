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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    
    private static final int DEFAULT_HTTPS_PORT = 443;
    
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
        int expected = RANDOM.nextInt(8) + 2;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer.Shoe shoe = new CardJSONServer.Shoe(expected, stop);
        shoe.shuffle();
        Set<Integer> hashes = new HashSet<>(expected);
        int sampleSize = 3 * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK / 2;
        int curr = 0;
        while (curr < sampleSize) {
            CardJSONServer.ProvenanceInscribedPlayingCard card 
                    = shoe.getNextCard();
            hashes.add(card.getDeckHash());
            curr++;
        }
        int actual = hashes.size();
        assertEquals(expected, actual);
    }
    
//    @Test
    public void testShoeThrowsExceptionWhenOutOfCards() {
        int deckQty = RANDOM.nextInt(8) + 2;
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
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTPS_PORT, deckQty, 
                DEFAULT_STOP);
        server.activate();
        try {
            server.activate();
            String msg = "Should not have been able to activate " 
                    + server.toString() + " twice in a row";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to activate " + server.toString() 
                    + " twice in a row correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to activate " 
                    + server.toString() + " twice in a row";
            fail(msg);
        }
    }
    
    @Test
    public void testNoDeactivationForInactive() {
        int deckQty = RANDOM.nextInt(8) + 2;
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTPS_PORT, deckQty, 
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
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTPS_PORT, 
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
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTPS_PORT, 
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
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTPS_PORT, 
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
