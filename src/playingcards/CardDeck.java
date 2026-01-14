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
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a standard deck of 52 cards, without Jokers. Since some card games
 * are played with two or more decks, this class provides the ability to detect
 * whether a given playing card object came from a particular deck or not.
 * @author Alonso del Arte
 */
public class CardDeck implements CardSupplier {

    /**
     * The deck is expected to have cards of thirteen ranks for each of four 
     * suits.
     */
    public static final int INITIAL_NUMBER_OF_CARDS_PER_DECK = 52;

    final ArrayList<PlayingCard> cards;

    private int dealCount = 0;

    /**
     * Tells whether the deck can give another card.
     * @return True if the deck can give another card, false if not.
     */
    @Override
    public boolean hasNext() {
        return (this.dealCount < this.cards.size());
    }

    /**
     * Supplies one card. The card is guaranteed to be of the next higher rank, 
     * or of the next suit, if {@link #shuffle()} has never been called on this 
     * deck.
     * @return A playing card. For example, 3&#9824;.
     * @throws RanOutOfCardsException If the deck has no more cards to give. 
     * This exception may be avoided by checking {@link #hasNext()}.
     */
    @Override
    public PlayingCard getNextCard() {
        if (this.dealCount == this.cards.size()) {
            throw new RanOutOfCardsException();
        }
        return this.cards.get(dealCount++);
    }
    
    // TODO: Write tests for this
    @Override
    public int countRemaining() {
        return -1;
    }

    /**
     * Determines whether this deck is in the same order as another deck.
     * @param other The deck to compare this deck to for order.
     * @return True if both decks have dealt out the same number of cards 
     * <em>and</em> the remaining cards are in the same order, false otherwise.
     */
    public boolean sameOrderAs(CardDeck other) {
        return (this.cards.equals(other.cards) 
                && this.dealCount == other.dealCount);
    }

    /**
     * Shuffles the cards of this deck. Only cards still in the deck are 
     * shuffled.
     * @throws IllegalStateException If the deck has only one or zero cards 
     * left.
     */
    public void shuffle() {
        switch (this.dealCount) {
            case 0 -> Collections.shuffle(this.cards);
            case INITIAL_NUMBER_OF_CARDS_PER_DECK - 1, 
                    INITIAL_NUMBER_OF_CARDS_PER_DECK -> {
                String excMsg = "Can't shuffle deck with one or no cards left";
                throw new IllegalStateException(excMsg);
            }
            default -> Collections.shuffle(this.cards.subList(this.dealCount, 
                    INITIAL_NUMBER_OF_CARDS_PER_DECK));
        }
    }

    /**
     * Determines if a card comes from this deck. This function is provided for
     * the benefit of games that use two or more decks. Theoretically, it is
     * possible to represent cheating (as in surreptitiously inserting a card
     * from a deck not dealt by the dealer).
     * @param card The card to check the provenance of.
     * @return True if the card came from this deck, false otherwise.
     */
    @Override
    public boolean provenance(PlayingCard card) {
        int index = this.cards.indexOf(card);
        PlayingCard ownCard = this.cards.get(index);
        return (card == ownCard);
    }

    /**
     * Constructs a new deck with four cards of each rank and thirteen cards of 
     * each suit. The cards are not shuffled unless {@link #shuffle()} is 
     * called.
     */
    public CardDeck() {
        this.cards = new ArrayList<>(INITIAL_NUMBER_OF_CARDS_PER_DECK);
        PlayingCard card;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                card = new PlayingCard(rank, suit);
                this.cards.add(card);
            }
        }
    }

}
