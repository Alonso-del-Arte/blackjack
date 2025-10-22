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
 * <p>At the time of construction, multiple decks may be specified. However, the
 * decks are shuffled separately and all cards are theoretically available to be
 * dealt. So there isn't much protection against card counting, which is another
 * reason this card supplier should not be used in production.</p>
 * @author Alonso del Arte
 */
public class CardServer implements CardSupplier {

    private final CardDeck[] decks;

    private int currDeckIndex = 0;

    /**
     * Tells whether this server can give another card.
     * @return True if this server can give another card, false if not.
     */
    @Override
    public boolean hasNext() {
        if (this.decks[this.currDeckIndex].hasNext()) {
            return true;
        }
        return this.currDeckIndex < (this.decks.length - 1);
    }

    /**
     * Supplies one card to the caller. The card will be somewhat random, but 
     * perhaps not random enough for production use.
     * @return A playing card. For example, 3&#9829;.
     * @throws RanOutOfCardsException If this server has run out of cards. Since 
     * this server should only be used for testing purposes, it would probably 
     * be acceptable to simply initialize this server with more decks.
     */
    @Override
    public PlayingCard getNextCard() {
        if (!this.decks[this.currDeckIndex].hasNext()) {
            this.currDeckIndex++;
        }
        if (this.currDeckIndex == this.decks.length) {
            String excMsg = "Ran out of decks to deal from";
            throw new RanOutOfCardsException(excMsg);
        }
        return this.decks[this.currDeckIndex].getNextCard();
    }

    /**
     * Determines if a card came from this server or not. This implementation is 
     * provided only to fulfill the expectations of the {@link 
     * playingcards.CardSupplier CardSupplier} interface.
     * @param card The playing card to check the provenance of.
     * @return True if the card came from one of the decks used by this server, 
     * false otherwise.
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
     * Gives a card of a specified rank. The card can be of any suit.
     * @param rank The rank of the card. For example, <code>Rank.SEVEN</code>.
     * @return A card of the specified rank. For example, 7&#9829;.
     * @throws RanOutOfCardsException If this server has run out of cards of the 
     * specified rank. The exception object will include the specified rank. 
     * Since this server should only be used for testing purposes, it would 
     * probably be acceptable to simply initialize this server with more decks.
     */
    public PlayingCard giveCard(Rank rank) {
        PlayingCard card;
        while (this.currDeckIndex < this.decks.length) {
            while (this.decks[this.currDeckIndex].hasNext()) {
                card = this.decks[this.currDeckIndex].getNextCard();
                if (card.isOf(rank)) {
                    return card;
                }
            }
            this.currDeckIndex++;
        }
        String excMsg = "Ran out of cards of rank " + rank.getWord();
        throw new RanOutOfCardsException(excMsg, rank);
    }

    /**
     * Gives a card of a specified suit. The card can be of any rank.
     * @param suit The suit of the card. For example, 
     * <code>Suit.DIAMONDS</code>.
     * @return A card of the specified suit. For example, 4&#9830;.
     * @throws RanOutOfCardsException If this server has run out of cards of the 
     * specified suit. The exception object will include the specified suit. 
     * Since this server should only be used for testing purposes, it would 
     * probably be acceptable to simply initialize this server with more decks.
     */
    public PlayingCard giveCard(Suit suit) {
        PlayingCard card;
        while (this.currDeckIndex < this.decks.length) {
            while (this.decks[this.currDeckIndex].hasNext()) {
                card = this.decks[this.currDeckIndex].getNextCard();
                if (card.isOf(suit)) {
                    return card;
                }
            }
            this.currDeckIndex++;
        }
        String excMsg = "Ran out of cards of suit " + suit.getWord();
        throw new RanOutOfCardsException(excMsg, suit);
    }
    
    // TODO: Write tests for this
    public PlayingCard giveCardNotOf(Rank rank) {
        return this.giveCard(rank);
    }
    
    // TODO: Write tests for this
    public PlayingCard giveCardNotOf(Suit suit) {
        return this.giveCard(suit);
    }
    
    /**
     * Gives a specified number of cards. The cards are given in an array. They 
     * may be of any rank and of any suit, and probably won't be in any 
     * discernible order.
     * @param cardQty How many cards to give. For example, five.
     * @return An array with the specified number of cards. For example, 
     * 4&#9830;, 4&#9829;, Q&#9827;, 5&#9830;, 7&#9824;.
     * @throws NegativeArraySizeException If <code>cardQty</code> is negative.
     * @throws RanOutOfCardsException If this server has run out of cards, or it 
     * runs out of cards while trying to fulfill this request. Since this server 
     * should only be used for testing purposes, it would probably be acceptable 
     * to simply initialize this server with more decks.
     */
    public PlayingCard[] giveCards(int cardQty) {
        PlayingCard[] cards = new PlayingCard[cardQty];
        for (int i = 0; i < cardQty; i++) {
            cards[i] = this.getNextCard();
        }
        return cards;
    }

    /**
     * Gives a specified number of cards, of a specified rank. The cards are 
     * given in an array. They may be of any suit, and probably won't be in any 
     * discernible order.
     * @param rank The rank of cards to give. For example, 
     * <code>Rank.ACE</code>.
     * @param cardQty How many cards to give. For example, five.
     * @return An array with the specified number of cards. For example, 
     * A&#9830;, A&#9829;, A&#9827;, A&#9830;, A&#9824;.
     * @throws NegativeArraySizeException If <code>cardQty</code> is negative.
     * @throws RanOutOfCardsException If this server has run out of cards of the 
     * specified rank, or runs out of such cards while trying to fulfill this 
     * request. The exception object will include the specified rank. Since this 
     * server should only be used for testing purposes, it would probably be 
     * acceptable to simply initialize this server with more decks. In the 
     * example above, this exception would certainly occur if the server is 
     * initialized with only one deck, as this function would obtain one Ace of 
     * each suit and then it would fail to find another Ace of any suit.
     */
    public PlayingCard[] giveCards(Rank rank, int cardQty) {
        PlayingCard[] cards = new PlayingCard[cardQty];
        for (int i = 0; i < cardQty; i++) {
            cards[i] = this.giveCard(rank);
        }
        return cards;
    }

    /**
     * Gives a specified number of cards, of a specified suit. The cards are 
     * given in an array. They may be of any rank, and probably won't be in any 
     * discernible order.
     * @param suit The suit of cards to give. For example, 
     * <code>Suit.CLUBS</code>.
     * @param cardQty How many cards to give. For example, five.
     * @return An array with the specified number of cards. For example, 
     * 4&#9827;, 4&#9827;, Q&#9827;, 5&#9827;, 7&#9827;.
     * @throws NegativeArraySizeException If <code>cardQty</code> is negative.
     * @throws RanOutOfCardsException If this server has run out of cards of the 
     * specified suit, or runs out of such cards while trying to fulfill this 
     * request. The exception object will include the specified suit. Since this 
     * server should only be used for testing purposes, it would probably be 
     * acceptable to simply initialize this server with more decks.
     */
    public PlayingCard[] giveCards(Suit suit, int cardQty) {
        PlayingCard[] cards = new PlayingCard[cardQty];
        for (int i = 0; i < cardQty; i++) {
            cards[i] = this.giveCard(suit);
        }
        return cards;
    }

    /**
     * Initializes a card server with only one deck. New decks can't be added
     * later, except by creating another card server with more decks.
     */
    public CardServer() {
        this(1);
    }

    /**
     * Initializes a card server with a given number of decks. The decks are
     * shuffled separately. New decks can't be added later, except by creating
     * another card server. Each deck is shuffled separately as it's added to 
     * the server, but the cards are not shuffled again once all the decks are 
     * in.
     * @param deckQty How many decks to initialize the card server with. Should
     * be a positive integer.
     * @throws IllegalArgumentException If <code>deckQty</code> is 0.
     * @throws NegativeArraySizeException If <code>deckQty</code> is negative.
     */
    public CardServer(int deckQty) {
        if (deckQty < 0) {
            String excMsg = "Deck quantity " + deckQty + " is not valid";
            throw new NegativeArraySizeException(excMsg);
        }
        if (deckQty == 0) {
            String excMsg = "Deck quantity 0 is not allowed";
            throw new IllegalArgumentException(excMsg);
        }
        this.decks = new CardDeck[2];
        this.decks[0] = new CardDeck();
        this.decks[1] = new CardDeck();
//        this.decks = new CardDeck[deckQty];
//        for (int i = 0; i < deckQty; i++) {
//            this.decks[i] = new CardDeck();
//            this.decks[i].shuffle();
//        }
    }

}
