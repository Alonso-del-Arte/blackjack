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
 *
 * @author Alonso del Arte
 */
public class MultiDeckCardDispenser implements CardSupplier {
    
    private ArrayList<PlayingCard> cards;
    
    private CardDeck[] decks;
    
    private int dispenseIndex = 0;
    
    @Override
    public boolean hasNext() {
        return (this.dispenseIndex < this.cards.size());
    }
    
    @Override
    public PlayingCard getNextCard() {
        if (this.dispenseIndex == this.cards.size()) {
            throw new RanOutOfCardsException("Ran out of cards");
        }
        return this.cards.get(this.dispenseIndex++);
    }
    
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
    
    public MultiDeckCardDispenser(int numberOfDecks) {
        this(numberOfDecks, 0);
    }
    
    public MultiDeckCardDispenser(int numberOfDecks, int plasticCardPos) {
        if (numberOfDecks == 0) {
            String excMsg = "Dispenser needs at least one deck of cards to dispense, preferably more";
            throw new IllegalArgumentException(excMsg);
        }
        if (numberOfDecks < 0) {
            String excMsg = "Dispenser needs a positive number of decks";
            throw new NegativeArraySizeException(excMsg);
        }
        int maxPlasticCardPos = numberOfDecks * CardDeck.NUMBER_OF_CARDS_PER_DECK - 1;
        if (plasticCardPos > maxPlasticCardPos) {
            String excMsg = "Plastic card position " + plasticCardPos 
                    + " is excessive for just " + numberOfDecks + " decks.";
            throw new IllegalArgumentException(excMsg);
        }
        if (plasticCardPos < 0) {
            String excMsg = "Negative plastic card position not allowed";
            throw new IndexOutOfBoundsException(excMsg);
        }
        this.decks = new CardDeck[numberOfDecks];
        this.cards = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            this.decks[i] = new CardDeck();
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
