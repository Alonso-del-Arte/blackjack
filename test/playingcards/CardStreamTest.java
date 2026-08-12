/*
 * Copyright (C) 2026 Alonso del Arte
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.testframe.api.Asserters.assertContainsSame;
import static org.testframe.api.Asserters.assertThrows;

import static playingcards.PlayingCardTest.RANDOM;

/**
 * Tests of the CardStream class.
 * @author Alonso del Arte
 */
public class CardStreamTest {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final int NUMBER_OF_RANKS = RANKS.length;
    
    private static final Suit[] SUITS = Suit.values();
    
    private static final int NUMBER_OF_SUITS = SUITS.length;
    
    @Test
    public void testGiveCard() {
        System.out.println("giveCard");
        int numberOfDecks = RANDOM.nextInt(2, 10);
        int numberOfCalls = numberOfDecks * NUMBER_OF_RANKS 
                + RANDOM.nextInt(NUMBER_OF_RANKS);
        String msgPartA = "Card ";
        String msgPartB1 = " should be of rank ";
        for (Rank rank : RANKS) {
            String msgPartB = msgPartB1 + rank.getWord();
            for (int i = 0; i < numberOfCalls; i++) {
                PlayingCard card = CardStream.giveCard(rank);
                String msg = msgPartA + card.toString() + msgPartB;
                assert card.isOf(rank) : msg;
            }
        }
        System.out.println("Successfully obtained " + numberOfCalls 
                + " cards of each rank");
    }
    
    @Test
    public void testGiveCardByRankGivesAllSuits() {
        int numberOfCalls = 10 * NUMBER_OF_SUITS;
        List<Suit> suits = Arrays.asList(SUITS);
        for (Rank rank : RANKS) {
            Set<PlayingCard> expected = suits.stream()
                    .map(suit -> new PlayingCard(rank, suit))
                    .collect(Collectors.toSet());
            Set<PlayingCard> actual = new HashSet<>(NUMBER_OF_SUITS);
            for (int i = 0; i < numberOfCalls; i++) {
                PlayingCard card = CardStream.giveCard(rank);
                actual.add(card);
            }
            assertContainsSame(expected, actual);
        }
    }
    
    @Test
    public void testGiveCardBySuit() {
        int numberOfDecks = RANDOM.nextInt(2, 10);
        int numberOfCalls = numberOfDecks * NUMBER_OF_SUITS 
                + RANDOM.nextInt(NUMBER_OF_SUITS);
        String msgPartA = "Card ";
        String msgPartB1 = " should be of suit ";
        for (Suit suit : SUITS) {
            String msgPartB = msgPartB1 + suit.getWord();
            for (int i = 0; i < numberOfCalls; i++) {
                PlayingCard card = CardStream.giveCard(suit);
                String msg = msgPartA + card.toString() + msgPartB;
                assert card.isOf(suit) : msg;
            }
        }
        System.out.println("Successfully obtained " + numberOfCalls 
                + " cards of each suit");
    }
    
    @Test
    public void testGiveCardBySuitGivesAllRanks() {
        int numberOfCalls = 12 * NUMBER_OF_RANKS;
        List<Rank> ranks = Arrays.asList(RANKS);
        for (Suit suit : SUITS) {
            Set<PlayingCard> expected = ranks.stream()
                    .map(rank -> new PlayingCard(rank, suit))
                    .collect(Collectors.toSet());
            Set<PlayingCard> actual = new HashSet<>(NUMBER_OF_RANKS);
            for (int i = 0; i < numberOfCalls; i++) {
                PlayingCard card = CardStream.giveCard(suit);
                actual.add(card);
            }
            assertContainsSame(expected, actual);
        }
    }
    
    @Test
    public void testGiveCardByPredicate() {
        int multiplier = RANDOM.nextInt(2, 5);
        int numberOfCalls = multiplier * NUMBER_OF_RANKS 
                + multiplier * NUMBER_OF_SUITS;
        String msgPartA = "Card ";
        String msgPartB = " should match predicate ";
        for (int i = 0; i < numberOfCalls; i++) {
            CardServerTest.PredicateWithDescription describedPredicate 
                    = CardServerTest.inventPredicate();
            Predicate<PlayingCard> predicate = describedPredicate.predicate;
            PlayingCard card = CardStream.giveCard(predicate);
            String msg = msgPartA + card.toString() + msgPartB 
                    + describedPredicate.description;
            assert predicate.test(card) : msg;
        }
    }
    
    @Test
    public void testGiveCardByPredicateEventuallyGivesAllMatching() {
        List<PlayingCard> cards 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        for (Rank rank : RANKS) {
            for (Suit suit : SUITS) {
                cards.add(new PlayingCard(rank, suit));
            }
        }
        CardServerTest.PredicateWithDescription describedPredicate 
                = CardServerTest.inventPredicate();
        Predicate<PlayingCard> predicate = describedPredicate.predicate;
        Set<PlayingCard> expected = cards.stream().filter(predicate)
                .collect(Collectors.toSet());
        int initialCapacity = expected.size();
        Set<PlayingCard> actual = new HashSet<>(initialCapacity);
        int numberOfCalls = 20 * initialCapacity;
        for (int i = 0; i < numberOfCalls; i++) {
            actual.add(CardStream.giveCard(predicate));
        }
        String msg = "Need to get all cards satisfying predicate " 
                + describedPredicate.description;
        assertContainsSame(expected, actual, msg);
    }
    
    @Test
    public void testImpossiblePredicateCausesException() {
        int unavailableValue = -RANDOM.nextInt(256);
        Predicate<PlayingCard> predicate 
                = (card) -> card.integerValue() == unavailableValue;
        String msg = "Predicate with unavailable value " + unavailableValue 
                + " should cause exception";
        Throwable t = assertThrows(() -> {
            PlayingCard card = CardStream.giveCard(predicate);
            System.out.println(msg + ", not given " + card.toString());
        }, NoSuchElementException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
}
