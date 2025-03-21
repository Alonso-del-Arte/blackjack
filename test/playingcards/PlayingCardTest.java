/*
 * Copyright (C) 2025 Alonso del Arte
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

import java.awt.Color;
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the PlayingCard class.
 * @author Alonso del Arte
 */
public class PlayingCardTest {
    
    private static final char HIGH_SURROGATE = '\uD83C';
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final Suit[] SUITS = Suit.values();
    
    /**
     * Test of the toString function, of the PlayingCard class.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                PlayingCard card = new PlayingCard(rank, suit);
                String expected = rank.getChars() + suit.getChar();
                String actual = card.toString();
                assertEquals(expected, actual);
            }
        }
    }

    /**
     * Test of the equals function, of the PlayingCard class. A playing card 
     * should be considered equal to itself.
     */
    @Test
    public void testReferentialEquality() {
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                PlayingCard card = new PlayingCard(rank, suit);
                String message = card.toString() + " should be equal to itself";
                assertEquals(message, card, card);
            }
        }
    }
    
    private static Object provideNull() {
        return null;
    }

    /**
     * Another test of the equals function, of the PlayingCard class. A playing 
     * card reference should not be equal to a null reference.
     */
    @Test
    public void testNotEqualsNull() {
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                PlayingCard card = new PlayingCard(rank, suit);
                String msg = card.toString() + " should not equal null";
                assert !card.equals(provideNull()) : msg;
            }
        }
    }
    
    private static Object passThrough(Object obj) {
        return obj;
    }

    /**
     * Another test of the equals function, of the PlayingCard class. An 
     * instance of PlayingCard should not be considered equal to an instance of 
     * another class.
     */
    @Test
    public void testNotEqualsOtherClass() {
        String typeName = this.getClass().getName();
        String msgPart = " should not equal object of type " + typeName;
        Object obj = passThrough(this);
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                PlayingCard card = new PlayingCard(rank, suit);
                String msg = card.toString() + msgPart;
                assert !card.equals(obj) : msg;
            }
        }
    }

    /**
     * Another test of the equals function, of the PlayingCard class. A playing 
     * card of a given rank should not be considered equal to another playing 
     * card of the same rank but different suit.
     */
    @Test
    public void testNotEqualsSameRankDiffSuit() {
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                PlayingCard cardA = new PlayingCard(rank, suit);
                String msgPart = cardA.toString() + " should not equal ";
                for (Suit suitB : SUITS) {
                    if (!suit.equals(suitB)) {
                        PlayingCard cardB = new PlayingCard(rank, suitB);
                        String message = msgPart + cardB.toString();
                        assertNotEquals(message, cardA, cardB);
                    }
                }
            }
        }
    }

    /**
     * Another test of the equals function, of the PlayingCard class. Two 
     * playing cards should be considered equal if they are of the same suit and 
     * the same rank.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                PlayingCard someCard = new PlayingCard(rank, suit);
                PlayingCard sameCard = new PlayingCard(rank, suit);
                assertEquals(someCard, sameCard);
            }
        }
    }

    /**
     * Another test of the equals function, of the PlayingCard class. A playing 
     * card of a given suit should not be considered equal to another playing 
     * card of the same suit but different rank.
     */
    @Test
    public void testNotEqualsDiffRankSameSuit() {
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                PlayingCard cardA = new PlayingCard(rank, suit);
                String msgPart = cardA.toString() + " should not equal ";
                for (Rank rankB : RANKS) {
                    if (!rank.equals(rankB)) {
                        PlayingCard cardB = new PlayingCard(rankB, suit);
                        String message = msgPart + cardB.toString();
                        assertNotEquals(message, cardA, cardB);
                    }
                }
            }
        }
    }

    /**
     * Test of the hashCode function, of the PlayingCard class. Given 52 
     * distinct cards, there should 52 distinct hash codes. The hash codes of  
     * two PlayingCard instances of the same rank and same suit should be the 
     * same.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        HashSet<PlayingCard> deck = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                PlayingCard currCard = new PlayingCard(rank, suit);
                deck.add(currCard);
                hashes.add(currCard.hashCode());
                PlayingCard dupCard = new PlayingCard(rank, suit);
                hashes.add(dupCard.hashCode());
            }
        }
        int expected = deck.size();
        int actual = hashes.size();
        System.out.println("Deck has " + expected + " cards");
        System.out.println("There are " + actual + " hash codes");
        assertEquals(expected, actual);
    }

    /**
     * Test of the toASCIIString function, of the PlayingCard class.
     */
    @Test
    public void testToASCIIString() {
        System.out.println("toASCIIString");
        for (Suit suit : SUITS) {
            String suitPart = " of " + suit.getWord();
            for (Rank rank : RANKS) {
                PlayingCard instance = new PlayingCard(rank, suit);
                String expected = rank.getWord() + suitPart;
                String actual = instance.toASCIIString();
                assertEquals(expected, actual);
            }
        }
    }
    
    /**
     * Test of the toUnicodeSMPChar function, of the PlayingCard class. This is 
     * testing A&#9824;, 2&#9824;, ..., 10&#9824;, J&#9824;.
     */
    @Test
    public void testToUnicodeSMPCharMostSpades() {
        System.out.println("toUnicodeSMPChar");
        char aceBase = '\uDCA1';
        for (int r = 0; r < 11; r++) {
            Rank rank = RANKS[r];
            PlayingCard spade = new PlayingCard(rank, Suit.SPADES);
            char lowSurrogate = (char) (aceBase + r);
            String expected = "" + HIGH_SURROGATE + lowSurrogate;
            String actual = spade.toUnicodeSMPChar();
            assertEquals(expected, actual);
        }
    }
    
    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is specifically testing Q&#9824; on account of the need to skip over 
     * C&#9824.
     */
    @org.junit.Ignore
    @Test
    public void testToUnicodeSMPCharQueenOfSpades() {
        PlayingCard queenOfSpades = new PlayingCard(Rank.QUEEN, Suit.SPADES);
        String expected = "\uD83C\uDCAD";
        String actual = queenOfSpades.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is specifically testing K&#9824; on account of the need to skip over 
     * C&#9824.
     */
    @org.junit.Ignore
    @Test
    public void testToUnicodeSMPCharKingOfSpades() {
        PlayingCard kingOfSpades = new PlayingCard(Rank.KING, Suit.SPADES);
        String expected = "\uD83C\uDCAE";
        String actual = kingOfSpades.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is testing A&#9829;, 2&#9829;, ..., 10&#9829;, J&#9829;.
     */
    @Test
    public void testToUnicodeSMPCharMostHearts() {
        char aceBase = '\uDCB1';
        for (int r = 0; r < 11; r++) {
            Rank rank = RANKS[r];
            PlayingCard heart = new PlayingCard(rank, Suit.HEARTS);
            char lowSurrogate = (char) (aceBase + r);
            String expected = "" + HIGH_SURROGATE + lowSurrogate;
            String actual = heart.toUnicodeSMPChar();
            assertEquals(expected, actual);
        }
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is specifically testing Q&#9829; on account of the need to skip over 
     * C&#9829.
     */
    @org.junit.Ignore
    @Test
    public void testToUnicodeSMPCharQueenOfHearts() {
        PlayingCard queenOfHearts = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        String expected = "\uD83C\uDCBD";
        String actual = queenOfHearts.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is specifically testing K&#9829; on account of the need to skip over 
     * C&#9829.
     */
    @org.junit.Ignore
    @Test
    public void testToUnicodeSMPCharKingOfHearts() {
        PlayingCard kingOfHearts = new PlayingCard(Rank.KING, Suit.HEARTS);
        String expected = "\uD83C\uDCBE";
        String actual = kingOfHearts.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is testing A&#9830;, 2&#9830;, ..., 10&#9830;, J&#9830;.
     */
    @Test
    public void testToUnicodeSMPCharMostDiamonds() {
        char aceBase = '\uDCC1';
        for (int r = 0; r < 11; r++) {
            Rank rank = RANKS[r];
            PlayingCard diamond = new PlayingCard(rank, Suit.DIAMONDS);
            char lowSurrogate = (char) (aceBase + r);
            String expected = "" + HIGH_SURROGATE + lowSurrogate;
            String actual = diamond.toUnicodeSMPChar();
            assertEquals(expected, actual);
        }
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is specifically testing Q&#9830; on account of the need to skip over 
     * C&#9830.
     */
    @org.junit.Ignore
    @Test
    public void testToUnicodeSMPCharQueenOfDiamonds() {
        PlayingCard queenOfDiamonds = new PlayingCard(Rank.QUEEN, Suit.DIAMONDS);
        String expected = "\uD83C\uDCCD";
        String actual = queenOfDiamonds.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is specifically testing K&#9830; on account of the need to skip over 
     * C&#9830.
     */
    @org.junit.Ignore
    @Test
    public void testToUnicodeSMPCharKingOfDiamonds() {
        PlayingCard kingOfDiamonds = new PlayingCard(Rank.KING, Suit.DIAMONDS);
        String expected = "\uD83C\uDCCE";
        String actual = kingOfDiamonds.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is testing A&#9827;, 2&#9827;, ..., 10&#9827;, J&#9827;.
     */
    @Test
    public void testToUnicodeSMPCharMostClubs() {
        char aceBase = '\uDCD1';
        for (int r = 0; r < 11; r++) {
            Rank rank = RANKS[r];
            PlayingCard club = new PlayingCard(rank, Suit.CLUBS);
            char lowSurrogate = (char) (aceBase + r);
            String expected = "" + HIGH_SURROGATE + lowSurrogate;
            String actual = club.toUnicodeSMPChar();
            assertEquals(expected, actual);
        }
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is specifically testing Q&#9827; on account of the need to skip over 
     * C&#9827.
     */
    @org.junit.Ignore
    @Test
    public void testToUnicodeSMPCharQueenOfClubs() {
        PlayingCard queenOfClubs = new PlayingCard(Rank.QUEEN, Suit.CLUBS);
        String expected = "\uD83C\uDCDD";
        String actual = queenOfClubs.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of the toUnicodeSMPChar function, of the PlayingCard class. 
     * This is specifically testing K&#9827; on account of the need to skip over 
     * C&#9827.
     */
    @org.junit.Ignore
    @Test
    public void testToUnicodeSMPCharKingOfClubs() {
        PlayingCard kingOfClubs = new PlayingCard(Rank.KING, Suit.CLUBS);
        String expected = "\uD83C\uDCDE";
        String actual = kingOfClubs.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Test of the cardValue function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testCardValue() {
        System.out.println("cardValue");
        PlayingCard currCard;
        int expected, actual;
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                currCard = new PlayingCard(rank, suit);
                expected = rank.getRank();
                actual = currCard.cardValue();
                assertEquals(expected, actual);
            }
        }
    }

    /**
     * Test of the getRank function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testGetRankOfATen() {
        System.out.println("getRank");
        PlayingCard spadesTen = new PlayingCard(Rank.TEN, Suit.CLUBS);
        assertEquals(Rank.TEN, spadesTen.getRank());
    }

    /**
     * Another test of the getRank function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testGetRankOfAKing() {
        PlayingCard diamondsKing = new PlayingCard(Rank.KING, Suit.DIAMONDS);
        assertEquals(Rank.KING, diamondsKing.getRank());
    }

    /**
     * Test of the isOf(Rank) function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testIsOfRank() {
        System.out.println("isOf(Rank)");
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        String msg = spadesAce.toString() + " should be recognized as an Ace";
        assert spadesAce.isOf(Rank.ACE) : msg;
    }

    /**
     * Another test of the isOf(Rank) function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testIsNotOfRank() {
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        String msg = spadesAce.toString() 
                + " should not be considered to be a Three";
        assert !spadesAce.isOf(Rank.THREE) : msg;
    }

    /**
     * Test of the isSameRank function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testIsSameRank() {
        System.out.println("isSameRank");
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        PlayingCard heartsAce = new PlayingCard(Rank.ACE, Suit.HEARTS);
        String msg = spadesAce.toString()
                + " should be recognized as being the same rank as "
                + heartsAce.toString();
        assert spadesAce.isSameRank(heartsAce) : msg;
    }

    /**
     * Another test of the isSameRank function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testIsNotSameRank() {
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        PlayingCard spadesTwo = new PlayingCard(Rank.TWO, Suit.SPADES);
        String msg = spadesAce.toString() 
                + " should not be considered as being the same rank as "
                + spadesTwo.toString();
        assert !spadesAce.isSameRank(spadesTwo) : msg;
    }

    /**
     * Test of the getSuit function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testGetSuitOfASpade() {
        System.out.println("getSuit");
        PlayingCard spadesAce = new PlayingCard(Rank.JACK, Suit.SPADES);
        assertEquals(Suit.SPADES, spadesAce.getSuit());
    }

    /**
     * Another test of the getSuit function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testGetSuitOfAClub() {
        PlayingCard spadesAce = new PlayingCard(Rank.SIX, Suit.CLUBS);
        assertEquals(Suit.CLUBS, spadesAce.getSuit());
    }

    /**
     * Test of the isOf(Suit) function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testIsOfSuit() {
        System.out.println("isOfSuit");
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        String msg = heartsQueen.toString()
                + " should be recognized as a card of hearts";
        assert heartsQueen.isOf(Suit.HEARTS) : msg;
    }

    /**
     * Another test of the isOf(Suit) function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testIsNotOfSuit() {
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        String msg = heartsQueen.toString()
                + " should not be considered a card of clubs";
        assert !heartsQueen.isOf(Suit.CLUBS) : msg;
    }

    /**
     * Test of the isSameSuit function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testIsSameSuit() {
        System.out.println("isSameSuit");
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        PlayingCard heartsKing = new PlayingCard(Rank.KING, Suit.HEARTS);
        String msg = heartsQueen.toString()
                + " should be recognized as being of the same suit as "
                + heartsKing.toString();
        assert heartsQueen.isSameSuit(heartsKing) : msg;
    }

    /**
     * Another test of the isSameSuit function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testIsNotSameSuit() {
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        PlayingCard spadesQueen = new PlayingCard(Rank.QUEEN, Suit.SPADES);
        String msg = heartsQueen.toString()
                + " should not be considered to be of the same suit as "
                + spadesQueen.toString();
        assert !heartsQueen.isSameSuit(spadesQueen) : msg;
    }

    /**
     * Test of the isCourtCard function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testJacksAreCourtCards() {
        System.out.println("isCourtCard");
        PlayingCard jack;
        String msg;
        for (Suit suit : SUITS) {
            jack = new PlayingCard(Rank.JACK, suit);
            msg = jack.toString() + " is a court card";
            assert jack.isCourtCard() : msg;
        }
    }

    /**
     * Another test of the isCourtCard function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testQueensAreCourtCards() {
        PlayingCard queen;
        String msg;
        for (Suit suit : SUITS) {
            queen = new PlayingCard(Rank.QUEEN, suit);
            msg = queen.toString() + " is a court card";
            assert queen.isCourtCard() : msg;
        }
    }

    /**
     * Another test of the isCourtCard function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testKingsAreCourtCards() {
        PlayingCard king;
        String msg;
        for (Suit suit : SUITS) {
            king = new PlayingCard(Rank.KING, suit);
            msg = king.toString() + " is a court card";
            assert king.isCourtCard() : msg;
        }
    }

    /**
     * Another test of the isCourtCard function, of the PlayingCard class.
     */
    @org.junit.Ignore
    @Test
    public void testPipCardsAreNotCourtCards() {
        PlayingCard card;
        String msg;
        for (Suit suit : SUITS) {
            for (Rank rank : RANKS) {
                if (!rank.isCourtRank()) {
                    card = new PlayingCard(rank, suit);
                    msg = card.toString() + " is not a court card";
                    assert !card.isCourtCard() : msg;
                }
            }
        }
    }
    
    /**
     * Test of the getTextColor function, of the PlayingCard class. For cards 
     * with Spades or Clubs, the color should be black, and for cards with 
     * Hearts or Diamonds, the color should be red.
     */
    @org.junit.Ignore
    @Test
    public void testGetTextColor() {
        System.out.println("getTextColor");
        PlayingCard card;
        for (Rank rank : RANKS) {
            card = new PlayingCard(rank, Suit.SPADES);
            assertEquals(Color.BLACK, card.getTextColor());
            card = new PlayingCard(rank, Suit.CLUBS);
            assertEquals(Color.BLACK, card.getTextColor());
        }
        for (Rank rank : RANKS) {
            card = new PlayingCard(rank, Suit.HEARTS);
            assertEquals(Color.RED, card.getTextColor());
            card = new PlayingCard(rank, Suit.DIAMONDS);
            assertEquals(Color.RED, card.getTextColor());
        }
    }


}
