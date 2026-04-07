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
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * A deck of cards with certain ranks or suits taken out. This is to be used for 
 * games like Spanish 21, in which the Tens are removed.
 * @author Alonso del Arte
 */
public final class AbridgedDeck extends CardDeck {
    
    // TODO: Write tests for this
    @Override
    public boolean hasNext() {
        return this.dealCount < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
    }
    
    // TODO: Write tests for this
    @Override
    public PlayingCard getNextCard() {
        if (this.dealCount == CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
            throw new RanOutOfCardsException();
        }
        return this.cards.get(this.dealCount++);
    }
    
    // TODO: Write tests for this
    @Override
    public int countRemaining() {
        return CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - this.dealCount;
    }
    
    // TODO: Write tests for this
    @Override
    public boolean provenance(PlayingCard card) {
        return false;
    }
    
    @Override
    public void shuffle() {
        switch (this.dealCount) {
            case 0 -> Collections.shuffle(this.cards);
            case CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - 1/* ,  
                    CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK */ -> {
                String excMsg = "Can't shuffle deck with one or no cards left";
                throw new IllegalStateException(excMsg);
            }
            default -> Collections.shuffle(this.cards.subList(this.dealCount, 
                    CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK));
        }
    }
    
    private void removeCards(Predicate<PlayingCard> predicate) {
        List<PlayingCard> taggedCards 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);
        this.cards.stream().filter((card) 
                -> (predicate.test(card))).forEachOrdered((card) -> {
            taggedCards.add(card);
        });
        this.cards.removeAll(taggedCards);
    }

    /**
     * Constructor to omit the specified ranks. The resulting deck will most 
     * likely have fewer cards than a regular {@link CardDeck}, but never more 
     * cards.
     * @param ranks The rank or ranks to omit. For example, the Eights and the 
     * Tens. Special cases: no ranks removed means might as well have used the 
     * {@code CardDeck} constructor instead; all ranks removed depletes the deck 
     * completely before any game can even begin.
     */
    public AbridgedDeck(Rank... ranks) {
        if (ranks == null) {
            String excMsg = "Ranks array should not be null";
            throw new NullPointerException(excMsg);
        }
        for (Rank rankToRemove : ranks) {
            this.removeCards(card -> (card.getRank() == rankToRemove));
        }
    }
    
    /**
     * Constructor to omit the specified suits. The resulting deck will most 
     * likely have fewer cards than a regular {@link CardDeck}, but never more 
     * cards.
     * @param suits The suit or suits to omit. For example, the Spades and the 
     * Hearts. Special cases: no suits removed means might as well have used the 
     * {@code CardDeck} constructor instead; all suits removed depletes the deck 
     * completely before any game can even begin.
     */
    public AbridgedDeck(Suit... suits) {
        if (suits == null) {
            String excMsg = "Suits array should not be null";
            throw new NullPointerException(excMsg);
        }
        for (Suit suitToRemove : suits) {
            this.removeCards(card -> (card.getSuit() == suitToRemove));
        }
    }
    
}
