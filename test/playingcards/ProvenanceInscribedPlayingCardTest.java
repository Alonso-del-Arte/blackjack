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
package playingcards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static playingcards.CardJSONServerTest.RANDOM;

/**
 * Tests of the ProvenanceInscribedPlayingCard class.
 * @author Alonso del Arte
 */
public class ProvenanceInscribedPlayingCardTest {
    
    /**
     * Test of the getDeckHash function, of the ProvenanceInscribedPlayingCard 
     * class.
     */
    @Test
    public void testGetDeckHash() {
        System.out.println("getDeckHash");
        int expected = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                ProvenanceInscribedPlayingCard card 
                        = new ProvenanceInscribedPlayingCard(rank, suit, 
                                expected, 0);
                int actual = card.getDeckHash();
                assertEquals(expected, actual);
            }
        }
    }

    /**
     * Test of the getShoeHash function, of the ProvenanceInscribedPlayingCard 
     * class.
     */
    @Test
    public void testGetShoeHash() {
        System.out.println("getShoeHash");
        int expected = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                ProvenanceInscribedPlayingCard card 
                        = new ProvenanceInscribedPlayingCard(rank, suit, 0, 
                                expected);
                int actual = card.getShoeHash();
                assertEquals(expected, actual);
            }
        }
    }

    /**
     * Test of the toJSONString function, of the ProvenanceInscribedPlayingCard 
     * class.
     */
    @Test
    public void testToJSONString() {
        System.out.println("toJSONString");
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                ProvenanceInscribedPlayingCard card 
                        = new ProvenanceInscribedPlayingCard(rank, suit, 
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
    public void testReferentialEquality() {
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        ProvenanceInscribedPlayingCard card 
                = new ProvenanceInscribedPlayingCard(Rank.JACK, Suit.CLUBS, 
                        deckHash, shoeHash);
        assertEquals(card, card);
    }

    @Test
    public void testNotEqualsNull() {
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        ProvenanceInscribedPlayingCard card 
                = new ProvenanceInscribedPlayingCard(Rank.JACK, Suit.CLUBS, 
                        deckHash, shoeHash);
        assertNotEquals(card, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        Rank rank = Rank.ACE;
        Suit suit = Suit.SPADES;
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        ProvenanceInscribedPlayingCard card 
                = new ProvenanceInscribedPlayingCard(rank, suit, deckHash, 
                        shoeHash);
        PlayingCard diffClassCard = new PlayingCard(rank, suit);
        assertNotEquals(card, diffClassCard);
    }
    
    @Test
    public void testNotEqualsDiffRank() {
        Rank rankA = Rank.ACE;
        Rank rankB = Rank.TWO;
        Suit suit = Suit.SPADES;
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        ProvenanceInscribedPlayingCard cardA 
                = new ProvenanceInscribedPlayingCard(rankA, suit, deckHash, 
                        shoeHash);
        ProvenanceInscribedPlayingCard cardB 
                = new ProvenanceInscribedPlayingCard(rankB, suit, deckHash, 
                        shoeHash);
        assertNotEquals(cardA, cardB);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        Rank rank = Rank.THREE;
        Suit suit = Suit.HEARTS;
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        ProvenanceInscribedPlayingCard someCard
                = new ProvenanceInscribedPlayingCard(rank, suit, deckHash, 
                        shoeHash);
        ProvenanceInscribedPlayingCard sameCard
                = new ProvenanceInscribedPlayingCard(rank, suit, deckHash, 
                        shoeHash);
        assertEquals(someCard, sameCard);
    }
    
    @Test
    public void testNotEqualsDiffSuit() {
        Rank rank = Rank.FIVE;
        Suit suitA = Suit.CLUBS;
        Suit suitB = Suit.DIAMONDS;
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        ProvenanceInscribedPlayingCard cardA 
                = new ProvenanceInscribedPlayingCard(rank, suitA, deckHash, 
                        shoeHash);
        ProvenanceInscribedPlayingCard cardB 
                = new ProvenanceInscribedPlayingCard(rank, suitB, deckHash, 
                        shoeHash);
        assertNotEquals(cardA, cardB);
    }
    
    @Test
    public void testNotEqualsDiffDeckHash() {
        Rank rank = Rank.SIX;
        Suit suit = Suit.SPADES;
        int deckHashA = RANDOM.nextInt();
        int deckHashB = deckHashA + 1;
        int shoeHash = RANDOM.nextInt();
        ProvenanceInscribedPlayingCard cardA 
                = new ProvenanceInscribedPlayingCard(rank, suit, deckHashA, 
                        shoeHash);
        ProvenanceInscribedPlayingCard cardB 
                = new ProvenanceInscribedPlayingCard(rank, suit, deckHashB, 
                        shoeHash);
        assertNotEquals(cardA, cardB);
    }
    
    @Test
    public void testNotEqualsDiffShoeHash() {
        Rank rank = Rank.SIX;
        Suit suit = Suit.SPADES;
        int deckHash = RANDOM.nextInt();
        int shoeHashA = RANDOM.nextInt();
        int shoeHashB = shoeHashA + 1;
        ProvenanceInscribedPlayingCard cardA 
                = new ProvenanceInscribedPlayingCard(rank, suit, deckHash, 
                        shoeHashA);
        ProvenanceInscribedPlayingCard cardB 
                = new ProvenanceInscribedPlayingCard(rank, suit, deckHash, 
                        shoeHashB);
        assertNotEquals(cardA, cardB);
    }
    
    /**
     * Test of the hashCode function, of the ProvenanceInscribedPlayingCard 
     * class. The hash code of a ProvenanceInscribedPlayingCard should not be 
     * equal to that of the corresponding plain PlayingCard instance, but they 
     * should match modulo 65536.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int deckHash = RANDOM.nextInt();
        int shoeHash = RANDOM.nextInt();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                ProvenanceInscribedPlayingCard card 
                        = new ProvenanceInscribedPlayingCard(rank, suit, 
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
    public void testParseJSONThrowsExceptionForInvalidInput() {
        String s = "For testing purposes only";
        try {
            PlayingCard badCard = ProvenanceInscribedPlayingCard.parseJSON(s);
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
    
    /**
     * Test of the parseJSON function, of the ProvenanceInscribedPlayingCard 
     * class.
     */
    @Test
    public void testParseJSON() {
        System.out.println("parseJSON");
        int shoeHash = RANDOM.nextInt();
        int deckHash = RANDOM.nextInt();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                ProvenanceInscribedPlayingCard expected 
                        = new ProvenanceInscribedPlayingCard(rank, suit, 
                                deckHash, shoeHash);
                String json = expected.toJSONString();
                ProvenanceInscribedPlayingCard actual 
                        = ProvenanceInscribedPlayingCard.parseJSON(json);
                assertEquals(expected, actual);
            }
        }
    }
    
    @Test
    public void testDeckHasNext() {
        System.out.println("Deck.hasNext");
        CardSupplier deck = new ProvenanceInscribedPlayingCard
                .Deck(this.hashCode());
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
        CardSupplier deck = new ProvenanceInscribedPlayingCard
                .Deck(this.hashCode());
        Set<PlayingCard> cards 
                = new HashSet<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (deck.hasNext()) {
            cards.add(deck.getNextCard());
        }
        assertEquals(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK, cards.size());
    }
    
    @Test
    public void testNoDeckProvenanceIfDiffType() {
        ProvenanceInscribedPlayingCard.Deck inscribedDeck 
                = new ProvenanceInscribedPlayingCard.Deck(this.hashCode());
        CardDeck deck = new CardDeck();
        deck.shuffle();
        PlayingCard card = deck.getNextCard();
        String msg = "Card " + card.toString() + " which is of type " 
                + card.getClass().getName() 
                + " should not be said to come from deck of cards of type " 
                + ProvenanceInscribedPlayingCard.class.getName();
        assert !inscribedDeck.provenance(card) : msg;
    }
    
    @Test
    public void testDeckProvenance() {
        System.out.println("Deck.provenance");
        CardSupplier deck = new ProvenanceInscribedPlayingCard
                .Deck(this.hashCode());
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            String msg = card.toString() + " that was drawn from deck " 
                    + deck.toString() + " should not be disavowed";
            assert deck.provenance(card) : msg;
        }
    }
    
    @Test
    public void testDeckDiffProvenance() {
        CardSupplier deck = new ProvenanceInscribedPlayingCard
                .Deck(this.hashCode());
        CardSupplier diffDeck = new CardDeck();
        while (diffDeck.hasNext()) {
            PlayingCard card = diffDeck.getNextCard();
            String msg = card.toString() + " that was drawn from deck " 
                    + diffDeck.toString() 
                    + " should not be acknowledged as coming from " 
                    + deck.toString();
            assert !deck.provenance(card) : msg;
        }
    }
    
    private static PlayingCard removeProvenanceInfo(PlayingCard card) {
        Rank rank = card.cardRank;
        Suit suit = card.cardSuit;
        return new PlayingCard(rank, suit);
    }
    
    @Test
    public void testDeckShuffle() {
        System.out.println("Deck.shuffle");
        CardSupplier firstDeck = new ProvenanceInscribedPlayingCard
                .Deck(this.hashCode());
        List<PlayingCard> originalOrderCards 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (firstDeck.hasNext()) {
            PlayingCard plainCard 
                    = removeProvenanceInfo(firstDeck.getNextCard());
            originalOrderCards.add(plainCard);
        }
        ProvenanceInscribedPlayingCard.Deck secondDeck 
                = new ProvenanceInscribedPlayingCard.Deck(this.hashCode());
        secondDeck.shuffle();
        List<PlayingCard> shuffledCards 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (secondDeck.hasNext()) {
            PlayingCard plainCard 
                    = removeProvenanceInfo(secondDeck.getNextCard());
            shuffledCards.add(plainCard);
        }
        assertNotEquals(originalOrderCards, shuffledCards);
    }
    
    @Test
    public void testDeckThrowsExceptionWhenOutOfCards() {
        ProvenanceInscribedPlayingCard.Deck deck 
                = new ProvenanceInscribedPlayingCard.Deck(this.hashCode());
        deck.shuffle();
        PlayingCard lastGivenCard = new PlayingCard(Rank.ACE, Suit.SPADES);
        while (deck.hasNext()) {
            lastGivenCard = deck.getNextCard();
        }
        String msgPart = "After giving last card " 
                + lastGivenCard.toASCIIString() 
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
    public void testShoeHasNext() {
        System.out.println("Shoe.hasNext");
        int deckQty = RANDOM.nextInt(8) + 2;
        int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        CardSupplier shoe = new ProvenanceInscribedPlayingCard.Shoe(deckQty);
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
        CardSupplier shoe = new ProvenanceInscribedPlayingCard.Shoe(deckQty, 
                stop);
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
        CardSupplier shoe = new ProvenanceInscribedPlayingCard.Shoe(expected);
        Map<PlayingCard, Integer> counts 
                = new HashMap<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        while (shoe.hasNext()) {
            PlayingCard transferCard = removeProvenanceInfo(shoe.getNextCard());
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
    public void testNoShoeProvenanceIfDiffType() {
        int deckQty = RANDOM.nextInt(8) + 2;
        ProvenanceInscribedPlayingCard.Shoe shoe 
                = new ProvenanceInscribedPlayingCard.Shoe(deckQty);
        CardDeck deck = new CardDeck();
        deck.shuffle();
        PlayingCard card = deck.getNextCard();
        String msg = "Card " + card.toString() + " which is of type " 
                + card.getClass().getName() 
                + " should not be said to come from shoe of cards of type " 
                + ProvenanceInscribedPlayingCard.class.getName();
        assert !shoe.provenance(card) : msg;
    }
    
    @Test
    public void testShoeProvenance() {
        System.out.println("Shoe.provenance");
        int deckQty = RANDOM.nextInt(8) + 2;
        int stop = 75 + RANDOM.nextInt(15);
        CardSupplier shoe = new ProvenanceInscribedPlayingCard.Shoe(deckQty, 
                stop);
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
        CardSupplier shoeA 
                = new ProvenanceInscribedPlayingCard.Shoe(deckQty, stop);
        CardSupplier shoeB 
                = new ProvenanceInscribedPlayingCard.Shoe(deckQty, stop);
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
    public void testShoeShuffle() {
        System.out.println("Shoe.shuffle");
        int expected = RANDOM.nextInt(8) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        ProvenanceInscribedPlayingCard.Shoe shoe 
                = new ProvenanceInscribedPlayingCard.Shoe(expected, stop);
        shoe.shuffle();
        Set<Integer> deckHashes = new HashSet<>(expected);
        int sampleSize = 3 * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK / 2;
        int curr = 0;
        while (curr < sampleSize) {
            ProvenanceInscribedPlayingCard card = shoe.getNextCard();
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
        ProvenanceInscribedPlayingCard.Shoe shoe 
                = new ProvenanceInscribedPlayingCard.Shoe(deckQty, stop);
        shoe.shuffle();
        PlayingCard lastGivenCard = new PlayingCard(Rank.ACE, Suit.SPADES);
        while (shoe.hasNext()) {
            lastGivenCard = shoe.getNextCard();
        }
        String msgPart = "After giving last card " 
                + lastGivenCard.toASCIIString() 
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
            ProvenanceInscribedPlayingCard.Shoe badShoe 
                    = new ProvenanceInscribedPlayingCard.Shoe(badQty);
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
            ProvenanceInscribedPlayingCard.Shoe badShoe 
                    = new ProvenanceInscribedPlayingCard.Shoe(badQty);
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
            ProvenanceInscribedPlayingCard.Shoe badShoe 
                    = new ProvenanceInscribedPlayingCard.Shoe(deckQty, badStop);
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
    
}
