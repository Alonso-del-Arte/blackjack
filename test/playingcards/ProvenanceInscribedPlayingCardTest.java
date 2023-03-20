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

import java.util.NoSuchElementException;

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
    
}
