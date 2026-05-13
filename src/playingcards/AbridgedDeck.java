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
import java.util.List;
import java.util.function.Predicate;

/**
 * A deck of cards with certain ranks or suits taken out. This is to be used for 
 * games like Spanish 21, in which the Tens are removed. But note that you need 
 * to have a proper license from Masque Publishing for Spanish 21.
 * @author Alonso del Arte
 */
public final class AbridgedDeck extends CardDeck {
    
    @Override
    public boolean sameOrderAs(CardDeck other) {
        return this.cards.equals(other.cards) 
                && this.dealCount == other.dealCount;
    }
    
    /**
     * Determines if a card comes from this deck. If a card was discarded from 
     * this deck at initialization, its provenance from this deck is then 
     * disavowed. For the examples, suppose this is a deck from which the Tens 
     * were removed prior to the first deal.
     * @param card The card to check the provenance of. Three examples: a Seven 
     * of Spades that came from this deck, a Seven of Spades from another deck, 
     * and a Ten of Clubs.
     * @return True if the card came from this deck and was ever available to be 
     * dealt to a player, false otherwise. In the examples, true for the Seven 
     * of Spades from this deck, false for the Seven of Spades from a different 
     * deck, and false for the Ten of Spades regardless of whether or not it was 
     * present in the deck before the Tens were removed.
     */
    @Override
    public boolean provenance(PlayingCard card) {
        int index = this.cards.indexOf(card);
        if (index < 0) {
            return false;
        } else {
            PlayingCard ownCard = this.cards.get(index);
            return (card == ownCard);
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
        for (Suit suitToRemove : suits) {
            this.removeCards(card -> (card.getSuit() == suitToRemove));
        }
    }
    
}
