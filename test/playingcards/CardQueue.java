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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides cards of specific ranks or suits. The idea is that if you need to 
 * write a test with the dealer giving a player a ten for blackjack or a spade 
 * for whist, for example, the test procedure can "cue up" such a card, and 
 * that's what the <code>getNextCard()</code> function gives next. This is to be 
 * used for testing purposes only.
 * @author Alonso del Arte
 */
public class CardQueue implements CardSupplier {
    
    private final List<PlayingCard> cards;
    
    private final CardDeck[] decks;
    
    private int currCardQty;

    @Override
    public boolean hasNext() {
        return this.currCardQty > 0;
    }
    
    /**
     * Cues up a card of a specified rank. Then the next call to {@link 
     * #getNextCard()} will give a card of that rank. No guarantee as to the 
     * suit.
     * @param rank The rank to get a card of. For example, a Ten.
     * @throws RanOutOfCardsException If there are no more cards of 
     * <code>rank</code> to cue up. The only remedy is to initialize the queue 
     * with more decks.
     */
    public void cueUp(Rank rank) {
        boolean found = false;
        int index = -1;
        int len = this.cards.size() - 1;
        while (!found && index < len) {
            index++;
            found = this.cards.get(index).getRank().equals(rank);
        }
        if (found) {
            if (index > 0) {
                Collections.swap(this.cards, 0, index);
                // TODO: Remove next line to get gradual sort test to pass
                Collections.swap(this.cards, 1, len);
            }
        } else {
            throw new RanOutOfCardsException(rank);
        }
    }

    /**
     * Cues up a card of a specified suit. Then the next call to {@link 
     * #getNextCard()} will give a card of that suit. No guarantee as to the 
     * rank.
     * @param suit The suit to get a card of. For example, Hearts.
     * @throws RanOutOfCardsException If there are no more cards of 
     * <code>suit</code> to cue up. The only remedy is to initialize the queue 
     * with more decks.
     */
    public void cueUp(Suit suit) {
        boolean found = false;
        int index = -1;
        int len = this.cards.size() - 1;
        while (!found && index < len) {
            index++;
            found = this.cards.get(index).getSuit().equals(suit);
        }
        if (found) {
            if (index > 0) {
                Collections.swap(this.cards, 0, index);
                // TODO: Remove next line to get gradual sort test to pass
                Collections.swap(this.cards, 1, len);
            }
        } else {
            throw new RanOutOfCardsException(suit);
        }
    }

    @Override
    public PlayingCard getNextCard() {
        if (this.currCardQty == 0) {
            String excMsg = "No more cards left to give";
            throw new RanOutOfCardsException(excMsg);
        }
        this.currCardQty--;
        return this.cards.remove(0);
    }

    @Override
    public boolean provenance(PlayingCard card) {
        return Arrays.stream(this.decks)
                .anyMatch(deck -> deck.provenance(card));
    }
    
    /**
     * Sole constructor. The cards are shuffled twice, no direct mechanism is 
     * provided for shuffling them any further.
     * @param deckQty How many decks to load into the queue.
     * @throws IllegalArgumentException If <code>deckQty</code> is 0 or 
     * negative.
     */
    public CardQueue(int deckQty) {
        if (deckQty < 1) {
            String excMsg = "Deck quantity " + deckQty 
                    + " is not valid, needs to be at least 1";
            throw new IllegalArgumentException(excMsg);
        }
        this.currCardQty = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        this.decks = new CardDeck[deckQty];
        this.cards = new ArrayList<>();
        for (int i = 0; i < deckQty; i++) {
            this.decks[i] = new CardDeck();
            this.decks[i].shuffle();
            while (this.decks[i].hasNext()) {
                this.cards.add(this.decks[i].getNextCard());
            }
        }
        Collections.shuffle(this.cards);
    }
    
}
