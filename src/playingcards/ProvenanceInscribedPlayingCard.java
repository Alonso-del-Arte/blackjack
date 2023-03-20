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
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A playing card inscribed with information about the deck and shoe from whence
 * it came. Also provides a JSON function.
 * @author Alonso del Arte
 */
final class ProvenanceInscribedPlayingCard extends PlayingCard {
    
    private final int deckHashCode, shoeHashCode;
    
    /**
     * Gives the hash code for the deck this card came from. The deck is 
     * supposed to identify itself to the card constructor.
     * @return The deck's hash code. For example, 716143810.
     */
    int getDeckHash() {
        return this.deckHashCode;
    }

    /**
     * Gives the hash code for the shoe this card came from. The shoe is 
     * supposed to identify itself to the deck constructor, which in turn  
     * identifies itself and the shoe to the card constructor.
     * @return The deck's hash code.
     */
    int getShoeHash() {
        return this.shoeHashCode;
    }
    
    /**
     * Provides a JSON representation of this provenance-inscribed playing card. 
     * The JSON fields include the deck hash and the shoe hash.
     * @return JSON ready to be sent by HTTP. For example, "{"name": "5&#9824;", 
     * "rank": "Five", "suit": "Spades", "shoeID": 135721597, "deckID": 
     * 295530567, "unicodeSMPChar": "&#127141;"}", but without any spaces.
     */
    String toJSONString() {
        return "{\"name\":\"" + this.toString() + "\",\"rank\":\"" 
                + this.cardRank.getWord() + "\",\"suit\":\"" 
                + this.cardSuit.getWord() + "\",\"shoeID\":" + this.shoeHashCode 
                + ",\"deckID\":" + this.deckHashCode + ",\"unicodeSMPChar\":\"" 
                + this.toUnicodeSMPChar() + "\"}";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProvenanceInscribedPlayingCard)) {
            return false;
        }
        ProvenanceInscribedPlayingCard other 
                = (ProvenanceInscribedPlayingCard) obj;
        if (!this.isSameRank(other) || !this.isSameSuit(other)) {
            return false;
        }
        return this.deckHashCode == other.deckHashCode 
                && this.shoeHashCode == other.shoeHashCode;
    }
    
    @Override
    public int hashCode() {
        int hash = (this.deckHashCode * this.shoeHashCode) << 16;
        return hash + super.hashCode();
    }
    
    static ProvenanceInscribedPlayingCard parseJSON(String s) {
        int rankIndex = s.indexOf("\"rank\":");
        int suitIndex = s.indexOf("\"suit\":");
        int shoeIndex = s.indexOf("\"shoeID\":");
        int deckIndex = s.indexOf("\"deckID\":");
        int absentFlag = rankIndex | suitIndex | shoeIndex | deckIndex;
        if (absentFlag < 0) {
            String excMsg = "Input \"" + s 
                    + "\" does not represent a valid card";
            throw new NoSuchElementException(excMsg);
        }
        Rank rank = Rank.parseRank(s.substring(rankIndex + 8, s.indexOf('"', 
                rankIndex + 9)));
        Suit suit = Suit.parseSuit(s.substring(suitIndex + 8, s.indexOf('"', 
                suitIndex + 9)));
        int shoeID = Integer.parseInt(s.substring(shoeIndex + 9, s.indexOf(',', 
                shoeIndex + 11)));
        int deckID = Integer.parseInt(s.substring(deckIndex + 9, s.indexOf(',', 
                deckIndex + 11)));
        return new ProvenanceInscribedPlayingCard(rank, suit, deckID, shoeID);        
    }

    /**
     * Sole constructor. Just like the {@link PlayingCard} constructor, this one
     * is package private.
     * @param rank The rank of the card. For example, Ten.
     * @param suit The suit of the card. For example, Diamonds.
     * @param deckHash The deck's hash code. For example, 716143810.
     * @param shoeHash The shoe's hash code.
     */
    ProvenanceInscribedPlayingCard(Rank rank, Suit suit, int deckHash, 
            int shoeHash) {
        super(rank, suit);
        this.deckHashCode = deckHash;
        this.shoeHashCode = shoeHash;
    }
    
    /**
     * Holds together a standard complement of provenance-inscribed cards. Its 
     * hash code is used to identify that a card came from this deck.
     */
    public static final class Deck implements CardSupplier {
        
        private int dealCount = 0;
        
        private final List<ProvenanceInscribedPlayingCard> cards 
                = new ArrayList<>(CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK);

        @Override
        public boolean hasNext() {
            return this.dealCount < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        }

        @Override
        public ProvenanceInscribedPlayingCard getNextCard() {
            if (this.dealCount >= CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK) {
                String excMsg = "Already gave out all " 
                        + CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
                throw new RanOutOfCardsException(excMsg);
            }
            return this.cards.get(this.dealCount++);
        }

        @Override
        public boolean provenance(PlayingCard card) {
            if (card instanceof ProvenanceInscribedPlayingCard) {
                return this.hashCode() 
                        == ((ProvenanceInscribedPlayingCard) card).deckHashCode;
            } else {
                return false;
            }
        }
        
        public void shuffle() {
            Collections.shuffle(this.cards);
        }
        
        /**
         * Sole constructor. Note that this constructor is package private.
         * @param shoeID The shoe's hash code.
         */
        Deck(int shoeID) {
            int deckID = this.hashCode();
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    this.cards.add(new ProvenanceInscribedPlayingCard(rank, 
                            suit, deckID, shoeID));
                }
            }
        }
        
    }
    
    public static final class Shoe implements CardSupplier {
        
        private int dealCount = 0;
        
        private final int max;
        
        private final List<ProvenanceInscribedPlayingCard> cards;
        
        @Override
        public boolean hasNext() {
            return this.dealCount < this.max;
        }

        @Override
        public ProvenanceInscribedPlayingCard getNextCard() {
            if (this.dealCount == this.max) {
                String excMsg = "After giving out " + this.max 
                        + " cards, there are no more cards to give";
                throw new RanOutOfCardsException(excMsg);
            }
            return this.cards.get(this.dealCount++);
        }

        @Override
        public boolean provenance(PlayingCard card) {
            if (card instanceof ProvenanceInscribedPlayingCard) {
                return ((ProvenanceInscribedPlayingCard) card).shoeHashCode 
                        == this.hashCode();
            } else {
                return false;
            }
        }
        
        public void shuffle() {
            Collections.shuffle(this.cards);
        }
        
        /**
         * Auxiliary constructor. All cards are available for dealing.
         * @param deckQty The number of decks to put into the shoe. For example, 
         * 6. Should not be 0 nor any negative number, preferably more than 2.
         */
        Shoe(int deckQty) {
            this(deckQty, 0);
        }
        
        /**
         * Primary constructor. Note that it is package private.
         * @param deckQty The number of decks to put into the shoe. For example, 
         * 6. Should not be 0 nor any negative number, preferably more than 2.
         * @param stop How many cards from the bottommost card in the shoe to 
         * place a figurative plastic card. Thus cards under the plastic card 
         * are unavailable for play.
         * @throws IllegalArgumentException If <code>deckQty</code> is 0 or 
         * less.
         */
        Shoe(int deckQty, int stop) {
            if (deckQty < 1 || stop < 0) {
                String excMsg = "Deck quantity " + deckQty 
                        + " should be at least 1 and plastic card stop " + stop 
                        + " should be at least 0";
                throw new IllegalArgumentException(excMsg);
            }
            int total = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
            this.cards = new ArrayList<>(total);
            this.max = total - stop;
            Deck[] decks = new Deck[deckQty];
            for (int i = 0; i < deckQty; i++) {
                decks[i] = new Deck(this.hashCode());
            }
            for (Deck deck : decks) {
//                deck.shuffle();
                while (deck.hasNext()) {
                    this.cards.add(deck.getNextCard());
                }
            }
        }
        
    }
    
}
