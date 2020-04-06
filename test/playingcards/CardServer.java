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
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package playingcards;

/**
 * Provides cards of a specific rank or suit. This class should only be 
 * accessible to test classes, to facilitate the testing of hand scoring. 
 * Callers may ask for a card of a given rank or suit, or for an array of cards 
 * of a given rank or suit.
 * @author Alonso del Arte
 */
public class CardServer {
    
    private final CardDeck[] decks;
    
    private int currDeckIndex = 0;
    
    public PlayingCard giveCard() {
        if (!this.decks[this.currDeckIndex].hasNext()) this.currDeckIndex++;
        if (this.currDeckIndex == this.decks.length) {
            String excMsg = "Ran out of decks to deal from";
            throw new RanOutOfCardsException(excMsg);
        }
        return this.decks[this.currDeckIndex].getNextCard();
    }
    
    public PlayingCard giveCard(Rank rank) {
        PlayingCard card;
        while (this.currDeckIndex < this.decks.length) {
            while (this.decks[this.currDeckIndex].hasNext()) {
                card = this.decks[this.currDeckIndex].getNextCard();
                if (card.isOfRank(rank)) return card;
            }
            this.currDeckIndex++;
        }
        String excMsg = "Ran out of cards of rank " + rank.getRankWord();
        throw new RuntimeException(excMsg);
    }
    
    public PlayingCard giveCard(Suit suit) {
        PlayingCard card;
        while (this.currDeckIndex < this.decks.length) {
            while (this.decks[this.currDeckIndex].hasNext()) {
                card = this.decks[this.currDeckIndex].getNextCard();
                if (card.isOfSuit(suit)) return card;
            }
            this.currDeckIndex++;
        }
        String excMsg = "Ran out of cards of suit " + suit.getSuitWord();
        throw new RuntimeException(excMsg);
    }
    
    public PlayingCard[] giveCards(int cardQty) {
        PlayingCard[] cards = new PlayingCard[cardQty];
        for (int i = 0; i < cardQty; i++) {
            cards[i] = this.giveCard();
        }
        return cards;
    }
    
    public PlayingCard[] giveCards(Rank rank, int cardQty) {
        PlayingCard[] cards = new PlayingCard[cardQty];
        for (int i = 0; i < cardQty; i++) {
            cards[i] = this.giveCard(rank);
        }
        return cards;
    }
    
    public PlayingCard[] giveCards(Suit suit, int cardQty) {
        PlayingCard[] cards = new PlayingCard[cardQty];
        for (int i = 0; i < cardQty; i++) {
            cards[i] = this.giveCard(suit);
        }
        return cards;
    }
    
    /**
     * Initializes a card server with only one deck. New decks can't be added 
     * later, except by creating another card server.
     */
    public CardServer() {
        this(1);
    }
    
    /**
     * Initializes a card server with a given number of decks. The decks are 
     * shuffled separately. New decks can't be added later, except by creating 
     * another card server.
     * @param deckQty How many decks to initialize the card server with. Should 
     * be a positive integer.
     * @throws IllegalArgumentException If <code>deckQty</code> is 0.
     * @throws NegativeArraySizeException If <code>deckQty</code> is negative.
     */
    public CardServer(int deckQty) {
        if (deckQty == 0) {
            String excMsg = "Deck quantity zero not allowed";
            throw new IllegalArgumentException(excMsg);
        }
        this.decks = new CardDeck[deckQty];
        for (int i = 0; i < deckQty; i++) {
            this.decks[i] = new CardDeck();
            this.decks[i].shuffle();
        }
    }
    
}
