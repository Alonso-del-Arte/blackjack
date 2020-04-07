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

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a standard deck of 52 cards, without Jokers. Since some card games
 * are played with two or more decks, this class provides the ability to detect
 * whether a given playing card object came from a particular deck or not.
 *
 * @author Alonso del Arte
 */
public class CardDeck implements CardSupplier {

    public static final int NUMBER_OF_CARDS_PER_DECK = 52;

    private final ArrayList<PlayingCard> cards;

    private int dealCount = 0;

    /**
     * Tells whether the deck can give another card.
     * @return A playing card. It should be random enough for most purposes.
     */
    @Override
    public boolean hasNext() {
        return (this.dealCount < NUMBER_OF_CARDS_PER_DECK);
    }

    @Override
    public PlayingCard getNextCard() {
        if (this.dealCount == NUMBER_OF_CARDS_PER_DECK) {
            throw new RanOutOfCardsException("All cards have been dealt");
        }
        return this.cards.get(dealCount++);
    }

    public boolean sameOrderAs(CardDeck other) {
        return (this.cards.equals(other.cards) && this.dealCount == other.dealCount);
    }

    /**
     * Shuffles the cards of this deck. Only cards still in the deck are 
     * shuffled.
     * @throws IllegalStateException If the deck has only one or zero cards 
     * left.
     */
    public void shuffle() {
        switch (this.dealCount) {
            case 0:
                Collections.shuffle(this.cards);
                break;
            case NUMBER_OF_CARDS_PER_DECK - 1:
            case NUMBER_OF_CARDS_PER_DECK:
                String excMsg = "No point shuffling a deck with one or no cards left";
                throw new IllegalStateException(excMsg);
            default:
                Collections.shuffle(this.cards.subList(this.dealCount, NUMBER_OF_CARDS_PER_DECK));
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
        int index = cards.indexOf(card);
        PlayingCard ownCard = this.cards.get(index);
        return (card == ownCard);
    }

    /**
     * Constructs a new deck with four cards of each rank and thirteen cards of 
     * each suit. The cards are not shuffled unless {@link #shuffle() shuffle()} 
     * is called.
     */
    public CardDeck() {
        this.cards = new ArrayList<>();
        PlayingCard card;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                card = new PlayingCard(rank, suit);
                this.cards.add(card);
            }
        }
    }

}
