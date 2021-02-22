/*
 * Copyright (C) 2021 Alonso del Arte
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

/**
 * A multi-deck card dispenser with the option of a plastic card to prevent a 
 * certain number of cards from being dealt out. This disrupts card counting. 
 * For example, with a 3-deck dispenser and a plastic card holding back the 
 * bottom twenty cards, it's possible that the dispenser might provide all three 
 * Aces of Spades but only one Ace of Clubs.
 * @author Alonso del Arte
 */
public class MultiDeckCardDispenser implements CardSupplier {
    
    private ArrayList<PlayingCard> cards;
    
    private CardDeck[] decks;
    
    private int dispenseIndex = 0;
    
    /**
     * Tells whether this dispenser can give another card. Call this function to 
     * avoid incurring {@link RanOutOfCardsException} (or call the constructor 
     * with a greater number of decks).
     * @return True if this dispenser can give another card, false otherwise.
     */
    @Override
    public boolean hasNext() {
        return (this.dispenseIndex < this.cards.size());
    }
    
    /** 
     * Supplies one card. The card should be random. Depending on how this 
     * dispenser was constructed, some rank and suit combinations may occur more 
     * or less than others. For example, a 6-deck dispenser with a plastic card 
     * holding back 75 cards might dispense six Aces of Diamonds but only three 
     * Aces of Hearts.
     * @return A playing card. For example, 2&#9830;.
     * @throws RanOutOfCardsException If this dispenser has run out of cards. To 
     * avoid this exception, the caller can check {@link #hasNext()}.
     */
    @Override
    public PlayingCard getNextCard() {
        if (this.dispenseIndex == this.cards.size()) {
            throw new RanOutOfCardsException("Ran out of cards");
        }
        return this.cards.get(this.dispenseIndex++);
    }
    
    /**
     * Determines if a card came from this dispenser. Cards held back by the 
     * plastic card should not be leaked out, so theoretically there is no need 
     * to worry about disavowing those cards.
     * @param card The playing card to check the provenance of. 
     * @return True if the card came from one of the decks used by this 
     * dispenser, false otherwise.
     */
    @Override
    public boolean provenance(PlayingCard card) {
        boolean matchFoundFlag = false;
        int index = 0;
        while (!matchFoundFlag && index < this.decks.length) {
            matchFoundFlag = this.decks[index].provenance(card);
            index++;
        }
        return matchFoundFlag;
    }
    
    /**
     * Constructs a new multi-deck card dispenser. The decks may or may not be 
     * shuffled individually, but the whole bunch of cards should be shuffled 
     * together, so that two or more of the same card could possibly wind up as 
     * consecutive cards. With this constructor, all cards from all decks will 
     * be available to be dealt out.
     * @param numberOfDecks How many decks to put into the dispenser. Should be 
     * a positive number, preferably greater than 1. For example, 6 for six 
     * decks.
     * @throws IllegalArgumentException If <code>numberOfDecks</code> is 0.
     * @throws NegativeArraySizeException If either <code>numberOfDecks</code> 
     * is negative.
     */
    public MultiDeckCardDispenser(int numberOfDecks) {
        this(numberOfDecks, 0);
    }
    
    /**
     * Constructs a new multi-deck card dispenser. The decks may or may not be 
     * shuffled individually, but the whole bunch of cards should be shuffled 
     * together, so that two or more of the same card could possibly wind up as 
     * consecutive cards.
     * @param numberOfDecks How many decks to put into the dispenser. Should be 
     * a positive number, preferably greater than 1. For example, 6 for six 
     * decks.
     * @param plasticCardPos At which position from the bottom to place a 
     * plastic card, which will then prevent the cards under it from being 
     * dealt. May be 0 but should not be negative. For example, 75, which will 
     * then prevent the bottom seventy-five cards from being dealt out. Note 
     * that the placing of the plastic card occurs after the decks have been 
     * shuffled together, so a setting of 52 or even as high as 104 is unlikely 
     * to shut out all cards from a particular deck.
     * @throws IllegalArgumentException If <code>numberOfDecks</code> is 0, or 
     * if <code>plasticCardPos</code> is in excess of how many cards there are 
     * in the decks in total.
     * @throws NegativeArraySizeException If either <code>numberOfDecks</code>  
     * or <code>plasticCardPos</code> is negative.
     */
    public MultiDeckCardDispenser(int numberOfDecks, int plasticCardPos) {
        if (numberOfDecks == 0) {
            String excMsg = "At least one deck of cards needed to dispense";
            throw new IllegalArgumentException(excMsg);
        }
        if (numberOfDecks < 0) {
            String excMsg = "Dispenser needs a positive number of decks";
            throw new NegativeArraySizeException(excMsg);
        }
        int maxPlasticCardPos = numberOfDecks 
                * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - 1;
        if (plasticCardPos > maxPlasticCardPos) {
            String excMsg = "Plastic card position " + plasticCardPos 
                    + " is excessive for just " + numberOfDecks + " decks.";
            throw new IllegalArgumentException(excMsg);
        }
        if (plasticCardPos < 0) {
            String excMsg = "Negative plastic card position not allowed";
            throw new NegativeArraySizeException(excMsg);
        }
        this.decks = new CardDeck[numberOfDecks];
        this.cards = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            this.decks[i] = new CardDeck();
            this.decks[i].shuffle();
            while (this.decks[i].hasNext()) {
                this.cards.add(this.decks[i].getNextCard());
            }
        }
        Collections.shuffle(this.cards);
        int fromIndex = this.cards.size() - 1;
        int toIndex = this.cards.size() - plasticCardPos;
        for (int j = fromIndex; j >= toIndex; j--) {
            this.cards.remove(j);
        }
    }
    
}
