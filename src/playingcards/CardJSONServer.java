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
    
    public static class Deck implements CardSupplier {
        
        private int dispensedSoFar = 0;

        @Override
        public boolean hasNext() {
            return this.dispensedSoFar 
                    < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK;
        }

        // TODO: Write tests for this
        @Override
        public PlayingCard getNextCard() {
            this.dispensedSoFar++;
            return new PlayingCard(Rank.JACK, Suit.DIAMONDS);
        }

        // TODO: Write tests for this
        @Override
        public boolean provenance(PlayingCard card) {
            return false;
        }
        
        public void shuffle() {
            // TODO: Write tests for this
        }
        
        Deck(int shoeID) {
            // TODO: Write tests for this
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
