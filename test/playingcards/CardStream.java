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

import java.util.function.Predicate;

import static playingcards.PlayingCardTest.RANDOM;

/**
 *
 * @author Alonso del Arte
 */
public class CardStream {
    
    private static final Rank[] RANKS = Rank.values();
    
    private static final int RANK_CHOICE_BOUND = RANKS.length;
    
    private static final Suit[] SUITS = Suit.values();
    
    private static final int SUIT_CHOICE_BOUND = SUITS.length;
    
    private static Rank chooseRank() {
        return RANKS[RANDOM.nextInt(RANK_CHOICE_BOUND)];
    }
    
    private static Suit chooseSuit() {
        return SUITS[RANDOM.nextInt(SUIT_CHOICE_BOUND)];
    }
    
    /**
     * Gives a card of a specified rank. The choice of suit is pseudorandom. The 
     * stream keeps no record of provenance.
     * @param rank The requested rank. For example, {@link Rank#ACE}.
     * @return A card of the requested rank. For example, A&#9829;.
     */
    // TODO: Test suit is not always the same
    public static PlayingCard giveCard(Rank rank) {
        Suit suit = chooseSuit();
        return new PlayingCard(rank, Suit.CLUBS);
    }
    
    /**
     * Gives a card of a specified suit. The choice of rank is pseudorandom. The 
     * stream keeps no record of provenance.
     * @param suit The requested suit. For example, {@link Suit#SPADES}.
     * @return A card of the requested suit. For example, 10&#9824;.
     */
    // TODO: Test rank is not always the same
    public static PlayingCard giveCard(Suit suit) {
        Rank rank = chooseRank();
        return new PlayingCard(Rank.ACE, suit);
    }
    
    // TODO: Test that this doesn't always give the first matching card in an 
    // unshuffled deck
    // The idea here is to make blackjack.HandTest less brittle
    public static PlayingCard giveCard(Predicate<PlayingCard> predicate) {
        CardDeck deck = new CardDeck();
        while (deck.hasNext()) {
            PlayingCard card = deck.getNextCard();
            if (predicate.test(card)) {
                return card;
            }
        }
        return new PlayingCard(Rank.JACK, Suit.CLUBS);
    }
    
}
