/*
 * Copyright (C) 2025 Alonso del Arte
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

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * Provides playing cards in JSON format. The cards are inscribed with the hash 
 * codes of the deck and the shoe they came from.
 * @author Alonso del Arte
 */
public class CardJSONServer implements Closeable {
    
    private static final int DEFAULT_PORT = 8080;
    
    private static final int DEFAULT_NUMBER_OF_DECKS = 10;
    
    private static final int DEFAULT_PLASTIC_CARD_INDEX = 75;
    
    /**
     * The content type specification "application/json; charset=UTF-8".
     */
    static final String CONTENT_TYPE_SPECIFICATION 
            = "application/json; charset=" + StandardCharsets.UTF_8.toString();
    
    /**
     * How many seconds to wait before closing down the server socket. May close 
     * sooner if there are no pending requests.
     */
    public static final int DEFAULT_CLOSING_DELAY = 2;
    
    private final int portNumber;
    
    private final int numberOfDecks;
    
    private final int plasticCardIndex;
    
    private boolean active = false;
    
    private ProvenanceInscribedPlayingCard.Shoe shoe;
    
    private HttpServer httpServer;
    
    private final HttpHandler handler = (HttpExchange exchange) -> {
        final Headers headers = exchange.getResponseHeaders();
//        final String method = exchange.getRequestMethod().toUpperCase();
//        switch (method) {
//            case "GET":
                String responseBody = this.giveCard().toJSONString();
                headers.set("Content-Type", CONTENT_TYPE_SPECIFICATION);
                byte[] rawResponseBody 
                        = responseBody.getBytes(StandardCharsets.UTF_8);
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
    
    ProvenanceInscribedPlayingCard giveCard() {
        if (!this.shoe.hasNext()) {
            this.shoe 
                    = new ProvenanceInscribedPlayingCard.Shoe(this
                            .numberOfDecks, this.plasticCardIndex);
            this.shoe.shuffle();
        }
        return this.shoe.getNextCard();
    }
    
    /**
     * Activates the server.
     * @throws IllegalStateException If the server has already been activated.
     * @throws RuntimeException A runtime exception wrapping an {@code 
     * IOException} if an input/output problem occurs, such as {@code 
     * BindException}.
     */
    public void activate() {
        if (this.active) {
            String excMsg = "Can't activate, already active";
            throw new IllegalStateException(excMsg);
        }
        String hostname = "localhost";
        try {
            this.httpServer = HttpServer
                    .create(new InetSocketAddress(hostname, this.portNumber), 
                            1);
            this.httpServer.createContext("/dealcard/", this.handler);
            this.httpServer.start();
            System.out.println("Started server " + hostname + " on port " 
                    + this.portNumber);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        this.active = true;
    }
    
    /**
     * Deactivates the server. If the server has already been deactivated, or if 
     * it was never activated in the first place (with {@link #activate()}), an 
     * exception will occur. If that behavior is not needed, use {@link 
     * #close()} instead.
     * @throws IllegalStateException If the server was already deactivated, or 
     * was never activated in the first place.
     * @deprecated Use {@link #close()} instead. This procedure is scheduled to 
     * be removed after Hacktoberfest 2025.
     */
    @Deprecated(forRemoval = true)
    public void deactivate() {
        if (!this.active) {
            String excMsg = "Can't deactivate, already inactive";
            throw new IllegalStateException(excMsg);
        }
        this.httpServer.stop(DEFAULT_CLOSING_DELAY);
        this.active = false;
    }
    
    /**
     * Deactivates the server if it's currently active. Use {@link 
     * #deactivate()} if it's necessary to throw {@code IllegalStateException} 
     * for an already inactive server.
     */
    @Override
    public void close() {
        this.httpServer.stop(DEFAULT_CLOSING_DELAY);
    }
    
    /**
     * Sole constructor. This constructor is not sufficient to activate. There  
     * needs to be a call to {@link #activate()} to get the server running.
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
        if (port < 0 || port > 4 * Short.MAX_VALUE || stop < 0) {
            String excMsg = "Check port number " + port + ", deck quantity " 
                    + deckQty + ", stop " + stop;
            throw new IllegalArgumentException(excMsg);
        }
        this.portNumber = port;
        this.numberOfDecks = deckQty;
        this.plasticCardIndex = stop;
        this.shoe = new ProvenanceInscribedPlayingCard.Shoe(this.numberOfDecks, 
                this.plasticCardIndex);
        this.shoe.shuffle();
    }
    
    public static void main(String[] args) {
        // TODO: Write tests for this?
        int port = DEFAULT_PORT;
        int deckQty = DEFAULT_NUMBER_OF_DECKS;
        int stop = DEFAULT_PLASTIC_CARD_INDEX;
        int numAckCmdLineParams = args.length > 2 ? 3 : args.length;
        switch (numAckCmdLineParams) {
            case 0:
                System.out.println("Using default parameters");
                break;
            case 3:
                stop = Integer.parseInt(args[2]);
            case 2:
                deckQty = Integer.parseInt(args[1]);
            case 1:
                port = Integer.parseInt(args[0]);
        }
        System.out.println("Port " + port);
        System.out.println("Deck quantity " + deckQty);
        System.out.println("Plastic card index " + stop);
        System.out.println("Starting card server...");
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        server.activate();
    }
    
}
