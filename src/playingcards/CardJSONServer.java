/*
 * Copyright (C) 2022 Alonso del Arte
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

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides playing cards in JSON format. The cards are inscribed with the hash 
 * codes of the deck and the shoe they came from.
 * @author Alonso del Arte
 */
public class CardJSONServer {
    
    public static class ProvenanceInscribedPlayingCard extends PlayingCard {
        
        private final int deckHashCode, shoeHashCode;

        public int getDeckHash() {
            return this.deckHashCode;
        }
        
        public int getShoeHash() {
            return this.shoeHashCode;
        }

        ProvenanceInscribedPlayingCard(Rank rank, Suit suit, 
                int deckHash, int shoeHash) {
            super(rank, suit);
            this.deckHashCode = deckHash;
            this.shoeHashCode = shoeHash;
        }
        
    }
    
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
            return this.cards.get(this.dealCount++);
        }

        @Override
        public boolean provenance(PlayingCard card) {
            boolean found = false;
            int index = 0;
            int hash = System.identityHashCode(card);
            while (!found && index < this.cards.size()) {
                found = hash == System.identityHashCode(this.cards.get(index));
                index++;
            }
            return found;
        }
        
        public void shuffle() {
            // TODO: Write tests for this
        }
        
        Deck(int shoeID) {
            int deckID = this.hashCode();
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    this.cards.add(new ProvenanceInscribedPlayingCard(rank, 
                            suit, deckID, 0));
                }
            }
        }
        
    }
    
    public static class Shoe implements CardSupplier {
        
        // TODO: Write tests for this
        @Override
        public boolean hasNext() {
            return false;
        }

        // TODO: Write tests for this
        @Override
        public PlayingCard getNextCard() {
            return new PlayingCard(Rank.EIGHT, Suit.CLUBS);
        }

        // TODO: Write tests for this
        @Override
        public boolean provenance(PlayingCard card) {
            return false;
        }
        
        public void shuffle() {
            // TODO: Write tests for this
        }
        
        Shoe(int deckQty) {
            // TODO: Write tests for this
        }
        
    }
    
}
