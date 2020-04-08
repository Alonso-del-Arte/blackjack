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
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class MultiDeckCardDispenserTest {

    /**
     * Test of hasNext method, of class MultiDeckCardDispenser.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        int numberOfDecks = 3;
        int expectedMax = numberOfDecks * CardDeck.NUMBER_OF_CARDS_PER_DECK;
        MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(numberOfDecks);
        assert dispenser.hasNext() : "Dispenser should have cards to give";
        int counter = 0;
        try {
            while (counter < expectedMax) {
                dispenser.getNextCard();
                counter++;
            }
            assert !dispenser.hasNext() : "Dispenser should have run out of cards";
        } catch (RanOutOfCardsException roce) {
            String failMsg = "Expected dispenser to have " + expectedMax
                    + " cards but it ran out after giving " + counter + " cards";
            System.out.println(failMsg);
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(failMsg);
        }
    }

    /**
     * Test of getNextCard method, of class MultiDeckCardDispenser.
     */
    @Test
    public void testGetNextCard() {
        System.out.println("getNextCard");
        MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(2);
        CardCounter counter = new CardCounter(dispenser);
        PlayingCard card;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                card = new PlayingCard(rank, suit);
                assertEquals(2, counter.count(card));
            }
        }
    }

    /**
     * Another test of getNextCard method, of class MultiDeckCardDispenser.
     */
    @Test
    public void testGetNextCardStopsAtPlasticCard() {
        int numberOfDecks = 6;
        int plasticCardPos = 75;
        int expectedMax = numberOfDecks * CardDeck.NUMBER_OF_CARDS_PER_DECK
                - plasticCardPos;
        MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(numberOfDecks, plasticCardPos);
        assert dispenser.hasNext() : "Dispenser should have cards to give";
        int counter = 0;
        try {
            while (counter < expectedMax) {
                dispenser.getNextCard();
                counter++;
            }
            assert !dispenser.hasNext() : "Dispenser should have run out of cards";
        } catch (RanOutOfCardsException roce) {
            String failMsg = "Expected dispenser to have " + expectedMax
                    + " cards but it ran out after giving " + counter + " cards";
            System.out.println(failMsg);
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(failMsg);
        }
    }

    /**
     * Another test of getNextCard method, of class MultiDeckCardDispenser.
     */
    @Test
    public void testGetNextCardProperDistributionBeforePlasticCard() {
        int numberOfDecks = 6;
        int plasticCardPos = 75;
        MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(numberOfDecks, plasticCardPos);
        CardCounter counter = new CardCounter(dispenser);
        CardDeck verifDeck = new CardDeck();
        PlayingCard verifCard;
        int cardCount;
        int cardsWithAllSix = 0;
        String assertionMessage;
        while (verifDeck.hasNext()) {
            verifCard = verifDeck.getNextCard();
            cardCount = counter.count(verifCard);
            if (cardCount == 6) cardsWithAllSix++;
            assertionMessage = "There shouldn't be more than six of " 
                    + verifCard.toString();
            assert cardCount < 7 : assertionMessage;
        }
        PlayingCard ace;
        for (Suit suit : Suit.values()) {
            ace = new PlayingCard(Rank.ACE, suit);
            System.out.println("Dispenser gave " + counter.count(ace) + " of " 
                    + ace.toASCIIString());
        }
        assert cardsWithAllSix < 30 : "There shouldn't be more than 29 cards with all five";
    }
    
    /**
     * Test of provenance method, of class MultiDeckCardDispenser.
     */
    @Test
    public void testProvenance() {
        System.out.println("provenance");
        MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(4);
        int counter = 0;
        PlayingCard card;
        String assertionMessage = "Card should be said to come from this dispenser";
        while (counter < 40) {
            card = dispenser.getNextCard();
            assert dispenser.provenance(card) : assertionMessage;
            counter++;
        }
    }
    
    // TODO: Determine if there is any meaningful way to test card disavowal.
    
    /**
     * Another test of provenance method, of class MultiDeckCardDispenser.
     */
    @Test
    public void testNotProvenance() {
        MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(4);
        int counter = 0;
        PlayingCard dispensedCard, copiedCard;
        String assertionMessage = "Copied card should not be said to come from this dispenser";
        while (counter < 40) {
            dispensedCard = dispenser.getNextCard();
            copiedCard = new PlayingCard(dispensedCard.getRank(), 
                    dispensedCard.getSuit());
            assert !dispenser.provenance(copiedCard) : assertionMessage;
            counter++;
        }
    }
    
    @Test
    public void testConstructorRejectsZeroDecks() {
        try {
            MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(0);
            String failMsg = "Should not have been able to create "
                    + dispenser.toString() + " with zero decks";
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create dispenser with zero decks correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName()
                    + " is the wrong exception for trying to create dispenser with zero decks";
            fail(failMsg);
        }
    }

    @Test
    public void testConstructorRejectsNegativeDecks() {
        try {
            MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(-1);
            String failMsg = "Should not have been able to create "
                    + dispenser.toString() + " with negative number of decks";
            fail(failMsg);
        } catch (NegativeArraySizeException nase) {
            System.out.println("Trying to create dispenser with negative number of decks correctly caused NegativeArraySizeException");
            System.out.println("\"" + nase.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName()
                    + " is the wrong exception for trying to create dispenser with negative number of decks";
            fail(failMsg);
        }
    }

    @Test
    public void testConstructorRejectsExcessivePlasticCardPos() {
        try {
            MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(2, 104);
            String failMsg = "Should not have been able to create "
                    + dispenser.toString() + " with excessive plastic card position";
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create dispenser with excessive plastic card position correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName()
                    + " is the wrong exception for trying to create dispenser with excessive plastic card position";
            fail(failMsg);
        }
    }

    @Test
    public void testConstructorRejectsNegativePlasticCardPos() {
        try {
            MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(2, -1);
            String failMsg = "Should not have been able to create "
                    + dispenser.toString() + " with negative plastic card position";
            fail(failMsg);
        } catch (NegativeArraySizeException nase) {
            System.out.println("Trying to create dispenser with negative plastic card position correctly caused NegativeArraySizeException");
            System.out.println("\"" + nase.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName()
                    + " is the wrong exception for trying to create dispenser with negative plastic card position";
            fail(failMsg);
        }
    }

}
