/*
 * Copyright (C) 2020 Alonso del Arte
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

import java.util.HashSet;

import javax.print.DocFlavor;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class PlayingCardTest {
    
    @Test
    public void testReferentialEquality() {
        System.out.println("equals");
        PlayingCard clubsFive = new PlayingCard(Rank.FIVE, Suit.CLUBS);
        assertEquals(clubsFive, clubsFive);
    }
    
    @Test
    public void testNotEqualsNull() {
        PlayingCard diamondsTwo = new PlayingCard(Rank.TWO, Suit.DIAMONDS);
        assertNotEquals(diamondsTwo, null);
    }
    
    @Test
    public void testNotEqualsOtherClass() {
        PlayingCard spadesQueen = new PlayingCard(Rank.QUEEN, Suit.SPADES);
        assertNotEquals(spadesQueen, DocFlavor.SERVICE_FORMATTED.PAGEABLE);
    }
    
    @Test
    public void testNotEqualsSameRankDiffSuit() {
        PlayingCard heartsThree = new PlayingCard(Rank.THREE, Suit.HEARTS);
        PlayingCard clubsThree = new PlayingCard(Rank.THREE, Suit.CLUBS);
        assertNotEquals(heartsThree, clubsThree);
    }
    
    @Test
    public void testNotEqualsDiffRankSameSuit() {
        PlayingCard diamondsEight = new PlayingCard(Rank.EIGHT, Suit.DIAMONDS);
        PlayingCard diamondsJack = new PlayingCard(Rank.JACK, Suit.DIAMONDS);
        assertNotEquals(diamondsEight, diamondsJack);
    }
    
    @Test
    public void testEqualsSameRankSameSuit() {
        PlayingCard someCard = new PlayingCard(Rank.SEVEN, Suit.SPADES);
        PlayingCard sameCard = new PlayingCard(Rank.SEVEN, Suit.SPADES);
        assertEquals(someCard, sameCard);
    }
    
    @Test
    public void testEqualsHashCodeCorrespondence() {
        PlayingCard someCard = new PlayingCard(Rank.SEVEN, Suit.SPADES);
        PlayingCard sameCard = new PlayingCard(Rank.SEVEN, Suit.SPADES);
        System.out.println(someCard.toASCIIString() + " " 
                + System.identityHashCode(someCard) + " hashed as " 
                + someCard.hashCode());
        System.out.println(sameCard.toASCIIString() + " " 
                + System.identityHashCode(sameCard) + " hashed as " 
                + sameCard.hashCode());
        assertEquals(someCard.hashCode(), sameCard.hashCode());
    }
    
    @Test
    public void testHashCodeUniqueness() {
        System.out.println("hashCode");
        PlayingCard currCard;
        HashSet<PlayingCard> deck = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                currCard = new PlayingCard(rank, suit);
                deck.add(currCard);
                hashes.add(currCard.hashCode());
            }
        }
        int expected = deck.size();
        int actual = hashes.size();
        System.out.println("Deck has " + expected + " cards.");
        System.out.println("There are " + actual + " hash codes.");
        assertEquals(expected, actual);
    }
    
    // TODO: DELETE THIS TEST AFTER MORE THOROUGHGOING TESTS ARE WRITTEN
    @Test
    public void testEqualsHashCode() {
        PlayingCard someCard = new PlayingCard(Rank.TWO, Suit.CLUBS);
        PlayingCard sameCard = new PlayingCard(Rank.TWO, Suit.CLUBS);
        System.out.println(someCard.toASCIIString() + " " 
                + System.identityHashCode(someCard) + " hashed as " 
                + someCard.hashCode());
        System.out.println(sameCard.toASCIIString() + " " 
                + System.identityHashCode(sameCard) + " hashed as " 
                + sameCard.hashCode());
        assertEquals(someCard, sameCard);
        assertEquals(someCard.hashCode(), sameCard.hashCode());
    }
    
    /**
     * Test of toString method, of class PlayingCard.
     */
    @Test
    public void testToStringAceOfSpades() {
        System.out.println("toString");
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        String expResult = "A\u2664";
        String result = spadesAce.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Another of toString method, of class PlayingCard.
     */
    @Test
    public void testToStringFourOfDiamonds() {
        PlayingCard diamondsFour = new PlayingCard(Rank.FOUR, Suit.DIAMONDS);
        String expResult = "4\u2666";
        String result = diamondsFour.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Another of toString method, of class PlayingCard.
     */
    @Test
    public void testToStringQueenOfHearts() {
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        String expResult = "Q\u2665";
        String result = heartsQueen.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Another of toString method, of class PlayingCard.
     */
    @Test
    public void testToStringTenOfClubs() {
        PlayingCard clubsTen = new PlayingCard(Rank.TEN, Suit.CLUBS);
        String expResult = "10\u2667";
        String result = clubsTen.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toASCIIString method, of class PlayingCard.
     */
    @Test
    public void testToASCIIStringAceOfSpades() {
        System.out.println("toASCIIString");
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        String expResult = "Ace of Spades";
        String result = spadesAce.toASCIIString();
        assertEquals(expResult, result);
    }
    
    /**
     * Another of toASCIIString method, of class PlayingCard.
     */
    @Test
    public void testToASCIIStringFourOfDiamonds() {
        PlayingCard diamondsFour = new PlayingCard(Rank.FOUR, Suit.DIAMONDS);
        String expResult = "Four of Diamonds";
        String result = diamondsFour.toASCIIString();
        assertEquals(expResult, result);
    }
    
    /**
     * Another of toASCIIString method, of class PlayingCard.
     */
    @Test
    public void testToASCIIStringQueenOfHearts() {
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        String expResult = "Queen of Hearts";
        String result = heartsQueen.toASCIIString();
        assertEquals(expResult, result);
    }
    
    /**
     * Another of toASCIIString method, of class PlayingCard.
     */
    @Test
    public void testToASCIIStringTenOfClubs() {
        PlayingCard clubsTen = new PlayingCard(Rank.TEN, Suit.CLUBS);
        String expResult = "Ten of Clubs";
        String result = clubsTen.toASCIIString();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCardValue() {
        System.out.println("cardValue");
        PlayingCard currCard;
        int expected, actual;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                currCard = new PlayingCard(rank, suit);
                expected = rank.getRank();
                actual = currCard.cardValue();
                assertEquals(expected, actual);
            }
        }
    }
    
}
