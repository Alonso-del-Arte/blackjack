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
 * Tests of the PlayingCard class.
 * @author Alonso del Arte
 */
public class PlayingCardTest {
    
    private static final char HIGH_SURROGATE = '\uD83C';

    /**
     * Test of equals method, of class PlayingCard. A playing card should be 
     * considered equal to itself.
     */
    @Test
    public void testReferentialEquality() {
        System.out.println("equals");
        PlayingCard clubsFive = new PlayingCard(Rank.FIVE, Suit.CLUBS);
        assertEquals(clubsFive, clubsFive);
    }

    /**
     * Another test of equals method, of class PlayingCard. A playing card 
     * reference should not be equal to a null reference.
     */
    @Test
    public void testNotEqualsNull() {
        PlayingCard diamondsTwo = new PlayingCard(Rank.TWO, Suit.DIAMONDS);
        assertNotEquals(diamondsTwo, null);
    }

    /**
     * Another test of equals method, of class PlayingCard. An instance of 
     * PlayingCard should not be considered equal to an instance of another 
     * class.
     */
    @Test
    public void testNotEqualsOtherClass() {
        PlayingCard spadesQueen = new PlayingCard(Rank.QUEEN, Suit.SPADES);
        assertNotEquals(spadesQueen, DocFlavor.SERVICE_FORMATTED.PAGEABLE);
    }

    /**
     * Another test of equals method, of class PlayingCard. A playing card of a 
     * given rank should not be considered equal to another playing card of the 
     * same rank but different suit.
     */
    @Test
    public void testNotEqualsSameRankDiffSuit() {
        PlayingCard heartsThree = new PlayingCard(Rank.THREE, Suit.HEARTS);
        PlayingCard clubsThree = new PlayingCard(Rank.THREE, Suit.CLUBS);
        assertNotEquals(heartsThree, clubsThree);
    }

    /**
     * Another test of equals method, of class PlayingCard. A playing card of a 
     * given suit should not be considered equal to another playing card of the 
     * same suit but different rank.
     */
    @Test
    public void testNotEqualsDiffRankSameSuit() {
        PlayingCard diamondsEight = new PlayingCard(Rank.EIGHT, Suit.DIAMONDS);
        PlayingCard diamondsJack = new PlayingCard(Rank.JACK, Suit.DIAMONDS);
        assertNotEquals(diamondsEight, diamondsJack);
    }

    /**
     * Another test of equals method, of class PlayingCard. Two playing cards 
     * should be considered equal if they are of the same suit and the same 
     * rank.
     */
    @Test
    public void testEqualsSameRankSameSuit() {
        PlayingCard someCard = new PlayingCard(Rank.SEVEN, Suit.SPADES);
        PlayingCard sameCard = new PlayingCard(Rank.SEVEN, Suit.SPADES);
        assertEquals(someCard, sameCard);
    }

    /**
     * Two playing cards said to be equal should have the same hash code.
     */
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

    /**
     * Test of hashCode method, of class PlayingCard. Given 52 distinct cards, 
     * there should 52 distinct hash codes.
     */
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

    /**
     * Test of toString method, of class PlayingCard.
     */
    @Test
    public void testToStringAceOfSpades() {
        System.out.println("toString");
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        String expResult = "A\u2660";
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
        String expResult = "10\u2663";
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
    
    /**
     * Test of toUnicodeSMPChar method, of class PlayingCard. This is testing 
     * A&#9824;, 2&#9824;, ..., 10&#9824;, J&#9824;.
     */
    @Test
    public void testToUnicodeSMPCharMostSpades() {
        System.out.println("toUnicodeSMPChar");
        Rank rank;
        PlayingCard spade;
        char aceBase = '\uDCA1';
        char lowSurrogate;
        String expected, actual;
        for (int r = 0; r < 11; r++) {
            rank = Rank.values()[r];
            spade = new PlayingCard(rank, Suit.SPADES);
            lowSurrogate = (char) (aceBase + r);
            expected = "" + HIGH_SURROGATE + lowSurrogate;
            actual = spade.toUnicodeSMPChar();
            assertEquals(expected, actual);
        }
    }
    
    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * specifically testing Q&#9824; on account of the need to skip over 
     * C&#9824.
     */
    @Test
    public void testToUnicodeSMPCharQueenOfSpades() {
        PlayingCard queenOfSpades = new PlayingCard(Rank.QUEEN, Suit.SPADES);
        String expected = "\uD83C\uDCAD";
        String actual = queenOfSpades.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * specifically testing K&#9824; on account of the need to skip over 
     * C&#9824.
     */
    @Test
    public void testToUnicodeSMPCharKingOfSpades() {
        PlayingCard kingOfSpades = new PlayingCard(Rank.KING, Suit.SPADES);
        String expected = "\uD83C\uDCAE";
        String actual = kingOfSpades.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * testing A&#9829;, 2&#9829;, ..., 10&#9829;, J&#9829;.
     */
    @Test
    public void testToUnicodeSMPCharMostHearts() {
        Rank rank;
        PlayingCard heart;
        char aceBase = '\uDCB1';
        char lowSurrogate;
        String expected, actual;
        for (int r = 0; r < 11; r++) {
            rank = Rank.values()[r];
            heart = new PlayingCard(rank, Suit.HEARTS);
            lowSurrogate = (char) (aceBase + r);
            expected = "" + HIGH_SURROGATE + lowSurrogate;
            actual = heart.toUnicodeSMPChar();
            assertEquals(expected, actual);
        }
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * specifically testing Q&#9829; on account of the need to skip over 
     * C&#9829.
     */
    @Test
    public void testToUnicodeSMPCharQueenOfHearts() {
        PlayingCard queenOfHearts = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        String expected = "\uD83C\uDCBD";
        String actual = queenOfHearts.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * specifically testing K&#9829; on account of the need to skip over 
     * C&#9829.
     */
    @Test
    public void testToUnicodeSMPCharKingOfHearts() {
        PlayingCard kingOfHearts = new PlayingCard(Rank.KING, Suit.HEARTS);
        String expected = "\uD83C\uDCBE";
        String actual = kingOfHearts.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * testing A&#9830;, 2&#9830;, ..., 10&#9830;, J&#9830;.
     */
    @Test
    public void testToUnicodeSMPCharMostDiamonds() {
        Rank rank;
        PlayingCard diamond;
        char aceBase = '\uDCC1';
        char lowSurrogate;
        String expected, actual;
        for (int r = 0; r < 11; r++) {
            rank = Rank.values()[r];
            diamond = new PlayingCard(rank, Suit.DIAMONDS);
            lowSurrogate = (char) (aceBase + r);
            expected = "" + HIGH_SURROGATE + lowSurrogate;
            actual = diamond.toUnicodeSMPChar();
            assertEquals(expected, actual);
        }
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * specifically testing Q&#9830; on account of the need to skip over 
     * C&#9830.
     */
    @Test
    public void testToUnicodeSMPCharQueenOfDiamonds() {
        PlayingCard queenOfDiamonds = new PlayingCard(Rank.QUEEN, Suit.DIAMONDS);
        String expected = "\uD83C\uDCCD";
        String actual = queenOfDiamonds.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * specifically testing K&#9830; on account of the need to skip over 
     * C&#9830.
     */
    @Test
    public void testToUnicodeSMPCharKingOfDiamonds() {
        PlayingCard kingOfDiamonds = new PlayingCard(Rank.KING, Suit.DIAMONDS);
        String expected = "\uD83C\uDCCE";
        String actual = kingOfDiamonds.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * testing A&#9827;, 2&#9827;, ..., 10&#9827;, J&#9827;.
     */
    @Test
    public void testToUnicodeSMPCharMostClubs() {
        Rank rank;
        PlayingCard club;
        char aceBase = '\uDCD1';
        char lowSurrogate;
        String expected, actual;
        for (int r = 0; r < 11; r++) {
            rank = Rank.values()[r];
            club = new PlayingCard(rank, Suit.CLUBS);
            lowSurrogate = (char) (aceBase + r);
            expected = "" + HIGH_SURROGATE + lowSurrogate;
            actual = club.toUnicodeSMPChar();
            assertEquals(expected, actual);
        }
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * specifically testing Q&#9827; on account of the need to skip over 
     * C&#9827.
     */
    @Test
    public void testToUnicodeSMPCharQueenOfClubs() {
        PlayingCard queenOfClubs = new PlayingCard(Rank.QUEEN, Suit.CLUBS);
        String expected = "\uD83C\uDCDD";
        String actual = queenOfClubs.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Another test of toUnicodeSMPChar method, of class PlayingCard. This is 
     * specifically testing K&#9827; on account of the need to skip over 
     * C&#9827.
     */
    @Test
    public void testToUnicodeSMPCharKingOfClubs() {
        PlayingCard kingOfClubs = new PlayingCard(Rank.KING, Suit.CLUBS);
        String expected = "\uD83C\uDCDE";
        String actual = kingOfClubs.toUnicodeSMPChar();
        assertEquals(expected, actual);
    }

    /**
     * Test of cardValue method, of class PlayingCard.
     */
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

    /**
     * Test of getRank method, of class PlayingCard.
     */
    @Test
    public void testGetRankOfATen() {
        System.out.println("getRank");
        PlayingCard spadesTen = new PlayingCard(Rank.TEN, Suit.CLUBS);
        assertEquals(Rank.TEN, spadesTen.getRank());
    }

    /**
     * Another test of getRank method, of class PlayingCard.
     */
    @Test
    public void testGetRankOfAKing() {
        PlayingCard diamondsKing = new PlayingCard(Rank.KING, Suit.DIAMONDS);
        assertEquals(Rank.KING, diamondsKing.getRank());
    }

    /**
     * Test of isOf(Rank) method, of class PlayingCard.
     */
    @Test
    public void testIsOfRank() {
        System.out.println("isOf(Rank)");
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        String assertionMessage = spadesAce.toString()
                + " should be recognized as an Ace";
        assert spadesAce.isOf(Rank.ACE) : assertionMessage;
    }

    /**
     * Another test of isOf(Rank) method, of class PlayingCard.
     */
    @Test
    public void testIsNotOfRank() {
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        String assertionMessage = spadesAce.toString()
                + " should not be considered to be a Three";
        assert !spadesAce.isOf(Rank.THREE) : assertionMessage;
    }

    /**
     * Test of isSameRank method, of class PlayingCard.
     */
    @Test
    public void testIsSameRank() {
        System.out.println("isSameRank");
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        PlayingCard heartsAce = new PlayingCard(Rank.ACE, Suit.HEARTS);
        String assertionMessage = spadesAce.toString()
                + " should be recognized as being the same rank as "
                + heartsAce.toString();
        assert spadesAce.isSameRank(heartsAce) : assertionMessage;
    }

    /**
     * Another test of isSameRank method, of class PlayingCard.
     */
    @Test
    public void testIsNotSameRank() {
        PlayingCard spadesAce = new PlayingCard(Rank.ACE, Suit.SPADES);
        PlayingCard spadesTwo = new PlayingCard(Rank.TWO, Suit.SPADES);
        String assertionMessage = spadesAce.toString()
                + " should not be considered as being the same rank as "
                + spadesTwo.toString();
        assert !spadesAce.isSameRank(spadesTwo) : assertionMessage;
    }

    /**
     * Test of getSuit method, of class PlayingCard.
     */
    @Test
    public void testGetSuitOfASpade() {
        System.out.println("getSuit");
        PlayingCard spadesAce = new PlayingCard(Rank.JACK, Suit.SPADES);
        assertEquals(Suit.SPADES, spadesAce.getSuit());
    }

    /**
     * Another test of getSuit method, of class PlayingCard.
     */
    @Test
    public void testGetSuitOfAClub() {
        PlayingCard spadesAce = new PlayingCard(Rank.SIX, Suit.CLUBS);
        assertEquals(Suit.CLUBS, spadesAce.getSuit());
    }

    /**
     * Test of isOf(Suit) method, of class PlayingCard.
     */
    @Test
    public void testIsOfSuit() {
        System.out.println("isOfSuit");
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        String assertionMessage = heartsQueen.toString()
                + " should be recognized as a card of hearts";
        assert heartsQueen.isOf(Suit.HEARTS) : assertionMessage;
    }

    /**
     * Another test of isOf(Suit) method, of class PlayingCard.
     */
    @Test
    public void testIsNotOfSuit() {
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        String assertionMessage = heartsQueen.toString()
                + " should not be considered a card of clubs";
        assert !heartsQueen.isOf(Suit.CLUBS) : assertionMessage;
    }

    /**
     * Test of isSameSuit method, of class PlayingCard.
     */
    @Test
    public void testIsSameSuit() {
        System.out.println("isSameSuit");
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        PlayingCard heartsKing = new PlayingCard(Rank.KING, Suit.HEARTS);
        String assertionMessage = heartsQueen.toString()
                + " should be recognized as being of the same suit as "
                + heartsKing.toString();
        assert heartsQueen.isSameSuit(heartsKing) : assertionMessage;
    }

    /**
     * Another test of isSameSuit method, of class PlayingCard.
     */
    @Test
    public void testIsNotSameSuit() {
        PlayingCard heartsQueen = new PlayingCard(Rank.QUEEN, Suit.HEARTS);
        PlayingCard spadesQueen = new PlayingCard(Rank.QUEEN, Suit.SPADES);
        String assertionMessage = heartsQueen.toString()
                + " should not be considered to be of the same suit as "
                + spadesQueen.toString();
        assert !heartsQueen.isSameSuit(spadesQueen) : assertionMessage;
    }

    /**
     * Test of isCourtCard method, of class PlayingCard.
     */
    @Test
    public void testJacksAreCourtCards() {
        System.out.println("isCourtCard");
        PlayingCard jack;
        String assertionMessage;
        for (Suit suit : Suit.values()) {
            jack = new PlayingCard(Rank.JACK, suit);
            assertionMessage = jack.toString() + " is a court card";
            assert jack.isCourtCard() : assertionMessage;
        }
    }

    /**
     * Another test of isCourtCard method, of class PlayingCard.
     */
    @Test
    public void testQueensAreCourtCards() {
        PlayingCard queen;
        String assertionMessage;
        for (Suit suit : Suit.values()) {
            queen = new PlayingCard(Rank.QUEEN, suit);
            assertionMessage = queen.toString() + " is a court card";
            assert queen.isCourtCard() : assertionMessage;
        }
    }

    /**
     * Another test of isCourtCard method, of class PlayingCard.
     */
    @Test
    public void testKingsAreCourtCards() {
        PlayingCard king;
        String assertionMessage;
        for (Suit suit : Suit.values()) {
            king = new PlayingCard(Rank.KING, suit);
            assertionMessage = king.toString() + " is a court card";
            assert king.isCourtCard() : assertionMessage;
        }
    }

    /**
     * Another test of isCourtCard method, of class PlayingCard.
     */
    @Test
    public void testPipCardsAreNotCourtCards() {
        PlayingCard card;
        String assertionMessage;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (!rank.isCourtRank()) {
                    card = new PlayingCard(rank, suit);
                    assertionMessage = card.toString() + " is not a court card";
                    assert !card.isCourtCard() : assertionMessage;
                }
            }
        }
    }

}
