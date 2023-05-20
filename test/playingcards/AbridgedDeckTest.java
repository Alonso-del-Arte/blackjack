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
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests of the AbridgedDeck class.
 * @author Alonso del Arte
 */
public class AbridgedDeckTest {
    
    @Test
    public void testOmitSingleRank() {
        for (Rank omittedRank : Rank.values()) {
            AbridgedDeck deck = new AbridgedDeck(omittedRank);
            deck.shuffle();
            String msgPart = " should not be " + omittedRank.getWord();
            while (deck.hasNext()) {
                PlayingCard card = deck.getNextCard();
                String msg = card.toString() + msgPart;
                assert card.getRank() != omittedRank : msg;
            }
        }
    }
    
    @Test
    public void testOmitSingleSuit() {
        for (Suit omittedSuit : Suit.values()) {
            AbridgedDeck deck = new AbridgedDeck(omittedSuit);
            deck.shuffle();
            String msgPart = " should not be of " + omittedSuit.getPluralWord();
            while (deck.hasNext()) {
                PlayingCard card = deck.getNextCard();
                String msg = card.toString() + msgPart;
                assert card.getSuit() != omittedSuit : msg;
            }
        }
    }
    
    private static CardDeck getChoosingDeck() {
        CardDeck deck = new CardDeck();
        deck.shuffle();
        return deck;
    }
    
    private static Rank[] chooseRanksToOmit() {
        CardDeck choosingDeck = getChoosingDeck();
        int capacity = (choosingDeck.getNextCard().cardValue() % 5) + 2;
        Set<Rank> rankSet = new HashSet<>(capacity);
        while (rankSet.size() < capacity) {
            rankSet.add(choosingDeck.getNextCard().getRank());
        }
        Rank[] ranks = new Rank[capacity];
        Arrays.sort(rankSet.toArray(ranks));
        return ranks;
    }
    
    @Test
    public void testOmitMultipleRanks() {
        Rank[] ranksToOmit = chooseRanksToOmit();
        CardDeck abridgedDeck = new AbridgedDeck(ranksToOmit);
        String msgPartA = "After omitting " + Arrays.toString(ranksToOmit) 
                + ", given card ";
        String msgPartB = " should not be of an omitted rank";
        while (abridgedDeck.hasNext()) {
            PlayingCard card = abridgedDeck.getNextCard();
            String msg = msgPartA + card.toString() + msgPartB;
            assert Arrays.binarySearch(ranksToOmit, card.getRank()) < 0 : msg;
        }
    }
    
    private static Suit[] chooseTwoSuitsToOmit() {
        CardDeck choosingDeck = getChoosingDeck();
        Suit suitA = choosingDeck.getNextCard().getSuit();
        Suit suitB = suitA;
        while (suitA == suitB) {
            suitB = choosingDeck.getNextCard().getSuit();
        }
        Suit[] suits = {suitA, suitB};
        Arrays.sort(suits);
        return suits;
    }
    
    @Test
    public void testOmitTwoSuits() {
        Suit[] suitsToOmit = chooseTwoSuitsToOmit();
        CardDeck abridgedDeck = new AbridgedDeck(suitsToOmit);
        String msgPartA = "After omitting " + Arrays.toString(suitsToOmit) 
                + ", given card ";
        String msgPartB = " should not be of an omitted suit";
        while (abridgedDeck.hasNext()) {
            PlayingCard card = abridgedDeck.getNextCard();
            String msg = msgPartA + card.toString() + msgPartB;
            assert Arrays.binarySearch(suitsToOmit, card.getSuit()) < 0 : msg;
        }
    }
    
}
