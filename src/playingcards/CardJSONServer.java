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
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;

/**
 * Provides playing cards in JSON format. The cards are inscribed with the hash 
 * codes of the deck and the shoe they came from.
 * @author Alonso del Arte
 */
public class CardJSONServer {
    
    private static final int DEFAULT_NUMBER_OF_DECKS = 6;
    
    private static final int DEFAULT_PLASTIC_CARD_INDEX = 75;
    
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    
    private static final String CONTENT_TYPE_SPECIFICATION 
            = "application/json; charset=" + UTF8.toString();
    
    private final int portNumber;
    
    private final int numberOfDecks;
    
    private final int plasticCardIndex;
    
    private boolean active = false;
    
    private Shoe shoe;
    
    private HttpServer httpServer;
    
    ProvenanceInscribedPlayingCard giveCard() {
        return new ProvenanceInscribedPlayingCard(Rank.JACK, Suit.CLUBS, 0, 0);
    }    
    private final HttpHandler handler = (HttpExchange exchange) -> {
        final Headers headers = exchange.getResponseHeaders();
        System.out.println("Got response headers " + headers.toString());
        final String method = exchange.getRequestMethod().toUpperCase();
        System.out.println("Request method is " + method);
//        switch (method) {
//            case "GET":
                String responseBody = this.giveCard().toJSONString();
                System.out.println("Response body is " + responseBody);
                headers.set("Content-Type", CONTENT_TYPE_SPECIFICATION);
                byte[] rawResponseBody = responseBody.getBytes(UTF8);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 
                        rawResponseBody.length);
                exchange.getResponseBody().write(rawResponseBody);
//                break;
//            case "OPTIONS":
//                headers.set("Allow", "GET,OPTIONS,PUT");
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
//                break;
//            default:
//                headers.set("Allow", "GET,OPTIONS,PUT");
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 
//                        -1);
//        }
    };
    
    public void activate() {
        if (this.active) {
            String excMsg = "Can't activate, already active";
            throw new IllegalStateException(excMsg);
        }
        this.active = true;
    }
    
    public void deactivate() {
        if (!this.active) {
            String excMsg = "Can't deactivate, already inactive";
            throw new IllegalStateException(excMsg);
        }
    }
    
    /**
     * Sole constructor. This constructor is sufficient to activate. There needs 
     * to be a call to {@link #activate()} to get the server running.
     * @param port Which local host port to send the cards to. Should probably 
     * be 80 or 445 or greater than 1024.
     * @param deckQty How many decks of cards to put in the shoe. Should be at 
     * least 1, preferably more than 2.
     * @param stop How many cards from the bottommost card in the shoe to place 
     * a figurative plastic card. Thus cards under the plastic card are 
     * unavailable for play. Should be at least 0, preferably more than 52 but 
     * less than 78, and certainly less than <code>deckQty</code> times 52.
     * @throws IllegalArgumentException If <code>port</code> is outside the 
     * range 0 to 65535 (other exceptions might occur for ports 0 to 1023), or 
     * if <code>deckQty</code> is less than 1, or if <code>stop</code> is less 
     * than 0.
     */
    public CardJSONServer(int port, int deckQty, int stop) {
        if (port < 0 || port > 4 * Short.MAX_VALUE || deckQty < 1 || stop < 0) {
            String excMsg = "Check port number " + port + ", deck quantity " 
                    + deckQty + ", stop " + stop;
            throw new IllegalArgumentException(excMsg);
        }
        this.portNumber = 81;
        this.numberOfDecks = 7;
        this.plasticCardIndex = 1;
        this.shoe = new Shoe(6, 75);
    }
    
    public static void main(String[] args) {
        // TODO: Write tests for this?
        System.out.println("Starting card server...");
        CardJSONServer server = new CardJSONServer(8080, 
                DEFAULT_NUMBER_OF_DECKS, DEFAULT_PLASTIC_CARD_INDEX);
        server.activate();
    }
    
    /**
     * A playing card inscribed with information about the deck and shoe from 
     * whence it came. Also provides a JSON function.
     */
    public static final class ProvenanceInscribedPlayingCard 
            extends PlayingCard {
        
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
        
        String toJSONString() {
            return "{\"name\":\"" + this.toString() + "\",\"rank\":\"" 
                    + this.cardRank.getWord() + "\",\"suit\":\"" 
                    + this.cardSuit.getWord() + "\",\"shoeID\":" 
                    + this.shoeHashCode + ",\"deckID\":" + this.deckHashCode 
                    + ",\"unicodeSMPChar\":\"" + this.toUnicodeSMPChar() 
                    + "\"}";
        }
        
        /**
         * 
         * @param s
         * @return 
         * @throws NoSuchElementException If <code>s</code> lacks the elements 
         * expected in a {@link #giveCard()} response.
         */
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
            return new ProvenanceInscribedPlayingCard(Rank.JACK, Suit.CLUBS, 
                    DEFAULT_NUMBER_OF_DECKS, DEFAULT_NUMBER_OF_DECKS);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ProvenanceInscribedPlayingCard) {
                ProvenanceInscribedPlayingCard other 
                        = (ProvenanceInscribedPlayingCard) obj;
                if (!this.cardRank.equals(other.cardRank) 
                        || !this.cardSuit.equals(other.cardSuit)) {
                    return false;
                }
                if (this.shoeHashCode != other.shoeHashCode) {
                    return false;
                }
                return this.deckHashCode == other.deckHashCode;
            } else {
                return false;
            }
        }
        
        @Override
        public int hashCode() {
            int hash = (this.deckHashCode * this.shoeHashCode) << 16;
            return hash + super.hashCode();
        }

        /**
         * Sole constructor. Just like the {@link PlayingCard} constructor, this 
         * one is package private.
         * @param rank The rank of the card. For example, Ten.
         * @param suit The suit of the card. For example, Diamonds.
         * @param deckHash The deck's hash code. For example, 716143810.
         * @param shoeHash The shoe's hash code.
         */
        ProvenanceInscribedPlayingCard(Rank rank, Suit suit, 
                int deckHash, int shoeHash) {
            super(rank, suit);
            this.deckHashCode = deckHash;
            this.shoeHashCode = shoeHash;
        }
        
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
            return this.dealCount < max;
        }

        @Override
        public ProvenanceInscribedPlayingCard getNextCard() {
            return this.cards.get(this.dealCount++);
        }

        @Override
        public boolean provenance(PlayingCard card) {
//            if (card instanceof ProvenanceInscribedPlayingCard) {
//                return ((ProvenanceInscribedPlayingCard) card).shoeHashCode 
//                        == this.hashCode();
//            } else {
                return true;
//            }
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
            this.max =  total - stop;
            Deck[] decks = new Deck[deckQty];
            for (int i = 0; i < deckQty; i++) {
                decks[i] = new Deck(this.hashCode());
            }
            for (Deck deck : decks) {
                deck.shuffle();
                while (deck.hasNext()) {
                    this.cards.add(deck.getNextCard());
                }
            }
        }
        
    }
    
}
