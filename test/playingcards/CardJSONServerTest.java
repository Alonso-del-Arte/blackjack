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

import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertDoesNotThrow;
import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the CardJSONServer class. And also of the static classes nested 
 * within it. Depending on how many seconds are specified by {@link 
 * CardJSONServer#DEFAULT_CLOSING_DELAY}, these tests may take twice or thrice 
 * as long to complete.
 * @author Alonso del Arte
 */
public class CardJSONServerTest {
    
    private static final String LOCATOR_START_FRAGMENT = "http://localhost:";
    
    private static final int DEFAULT_TESTING_HTTP_PORT = 8083;
    
    private static final String LOCATOR_END_FRAGMENT = "/dealcard/";
    
    private static final int DEFAULT_STOP = 75;
    
    static final Random RANDOM = new Random();
        
    @Test
    public void testContentTypeSpecification() {
        String expected = "application/json; charset=" 
                + StandardCharsets.UTF_8.toString();
        String actual = CardJSONServer.CONTENT_TYPE_SPECIFICATION;
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNoDoubleActivation() {
        int deckQty = RANDOM.nextInt(8) + 2;
        CardJSONServer server = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, 
                deckQty, DEFAULT_STOP);
        server.activate();
        String msg = "Should not be able to activate server twice";
        Throwable t = assertThrows(() -> {
            server.activate();
            System.out.println("Should not have been able to activate " 
                    + server.toString() + " twice in a row");
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testGiveCard() {
        System.out.println("giveCard");
        int deckQty = 2;
        int stop = 25;
        int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        CardJSONServer server = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, 
                deckQty, stop);
        Set<ProvenanceInscribedPlayingCard> cards = new HashSet<>(expected);
        int index = 0;
        while (index < expected) {
            cards.add(server.giveCard());
            index++;
        }
        int actual = cards.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGiveCardReplenishesAutomaticallyAfterRunningOut() {
        int deckQty = 2;
        int stop = 25;
        int initialCapacity = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        CardJSONServer server = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, 
                deckQty, stop);
        Set<Integer> deckIDNumbers = new HashSet<>(initialCapacity);
        int index = 0;
        while (index < initialCapacity) {
            deckIDNumbers.add(server.giveCard().getDeckHash());
            index++;
        }
        assertDoesNotThrow(() -> {
            ProvenanceInscribedPlayingCard card = server.giveCard();
            System.out.println("After running out of the first " 
                    + initialCapacity 
                    + " cards, server correctly replenished cards");
            assertEquals(deckQty, deckIDNumbers.size());
            deckIDNumbers.add(card.getDeckHash());
            String msg = "Card " + card.toString() 
                    + " should come from replenishment deck";
            assert deckIDNumbers.size() > deckQty : msg;
        });
    }
    
    // TODO: Rewrite this test
    @Test
    public void testGiveCardReplenishesWithSameDeckQtyAndStop() {
        int expected = RANDOM.nextInt(10) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        int initialCapacity = expected 
                * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - stop;
        CardJSONServer server = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, 
                expected, stop);
        Set<Integer> initialDeckHashes = new HashSet<>(initialCapacity);
        int index = 0;
        while (index < initialCapacity) {
            initialDeckHashes.add(server.giveCard().getDeckHash());
            index++;
        }
        Set<Integer> replenishmentDeckHashes = new HashSet<>(initialCapacity);
        index = 0;
        int midwayCheckPoint = initialCapacity / 2;
        try {
            while (index < midwayCheckPoint) {
                int hash = server.giveCard().getDeckHash();
                replenishmentDeckHashes.add(hash);
                index++;
                String msg = "Replenishment deck hash " + hash 
                        + " of card indexed "+ index 
                        + " should not be present among initial deck hashes";
                assert !initialDeckHashes.contains(hash) : msg;
            }
            int actual = replenishmentDeckHashes.size();
            assertEquals(expected, actual);
            while (index < initialCapacity) {
                int hash = server.giveCard().getDeckHash();
                assert !replenishmentDeckHashes.add(hash) 
                        : "All replenishment deck hashes should be known";
                index++;
                String msg = "Replenishment deck hash " + hash 
                        + " of card indexed " + index 
                        + " should not be present among initial deck hashes";
                assert !initialDeckHashes.contains(hash) : msg;
            }
            int hash = server.giveCard().getDeckHash();
            String msg = "Second replenishment deck hash " + hash 
                    + " should not be an initial hash nor first replenishment hash";
            assert !initialDeckHashes.contains(hash) : msg;
            assert !replenishmentDeckHashes.contains(hash) : msg;
        }
        catch (RuntimeException re) {
            fail(re.getMessage());
        }
    }
    
    @Test
    public void testServerRespondsOnSpecifiedPort() {
        int port = 8080 + RANDOM.nextInt(1000);
        int deckQty = RANDOM.nextInt(10) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        server.activate();
        System.out.println("Expecting localhost response on port " + port);
        String locator = LOCATOR_START_FRAGMENT + port + LOCATOR_END_FRAGMENT;
        String key = "User-Agent";
        String value = "Java/" + System.getProperty("java.version");
        assertDoesNotThrow(() -> {
            URI uri = new URI(locator);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty(key, value);
            int expected = HttpURLConnection.HTTP_OK;
            int actual = conn.getResponseCode();
            assertEquals(expected, actual);
        });
    }

@org.junit.Ignore
    @Test
    public void testServerRespondsToOptionsMethod() {
        int port = 8080 + RANDOM.nextInt(1000);
        int deckQty = RANDOM.nextInt(10) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        server.activate();
        String locator = LOCATOR_START_FRAGMENT + port + LOCATOR_END_FRAGMENT;
        String key = "User-Agent";
        String value = "Java/" + System.getProperty("java.version");
        assertDoesNotThrow(() -> {
            URI uri = new URI(locator);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("OPTIONS");
            conn.setRequestProperty(key, value);
            int responseCode = conn.getResponseCode();
            server.deactivate();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String header = conn.getHeaderField("Allow");
                assert header != null : "Header \"Allow\" should not be null";
                String expected = "GET,OPTIONS";
                String actual = header.toUpperCase()
                        .replace(" ", "");
                assertEquals(expected, actual);
            }
        });
    }
    
    @Test
    public void testNoDeactivationForInactive() {
        int deckQty = RANDOM.nextInt(8) + 4;
        CardJSONServer server = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, 
                deckQty, DEFAULT_STOP);
        Throwable t = assertThrows(() -> {
            server.deactivate();
            System.out.println("Should not have been able to deactivate " 
                    + server.toString() + " because it was already inactive");
        }, IllegalStateException.class);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testClose() {
        System.out.println("close");
        int port = DEFAULT_TESTING_HTTP_PORT - RANDOM.nextInt(80) - 1;
        int deckQty = RANDOM.nextInt(8) + 4;
        int stop = 75 + RANDOM.nextInt(25);
        @SuppressWarnings("resource")
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        server.activate();
        server.close();
        String locator = LOCATOR_START_FRAGMENT + port + LOCATOR_END_FRAGMENT;
        String key = "User-Agent";
        String value = "Java/" + System.getProperty("java.version");
        String msg = "Server should not give cards on port " + port 
                + " after closing";
        Throwable t = assertThrows(() -> {
            URI uri = new URI(locator);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty(key, value);
            int responseCode = conn.getResponseCode();
            System.out.println(msg + ", not responded with HTTP status code " 
                    + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream source = (InputStream) conn.getContent();
                Scanner scanner = new Scanner(source);
                while (scanner.hasNext()) {
                    System.out.println(scanner.nextLine());
                }
            }
        }, ConnectException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testClosingTwiceShouldHaveNoEffect() {
        int port = DEFAULT_TESTING_HTTP_PORT - RANDOM.nextInt(80) - 1;
        int deckQty = RANDOM.nextInt(8) + 4;
        int stop = 75 + RANDOM.nextInt(25);
        @SuppressWarnings("resource")
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        server.activate();
        server.close();
        String msg = "Closing twice should have no effect";
        assertDoesNotThrow(() -> {
            server.close();
        }, msg);
    }
    
    // TODO: Rewrite with assertDoesNotThrow()
    @Test
    public void testConstructorSetsSpecifiedDeckQuantityAndStop() {
        int deckQty = RANDOM.nextInt(8) + 4;
        int stop = 75 + RANDOM.nextInt(25);
        CardJSONServer server = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, deckQty, 
                stop);
        int totalCardQty = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        PlayingCard lastCard = new PlayingCard(Rank.JACK, Suit.CLUBS);
        int curr = 0;
        try {
            while (curr < totalCardQty) {
                lastCard = server.giveCard();
                curr++;
            }
            System.out.println("Gave out " + totalCardQty 
                    + " cards, last of which was " + lastCard.toASCIIString());
        } catch (RanOutOfCardsException roce) {
            String message = "Given shoe with " + totalCardQty 
                    + " cards, exception should not have occurred after " + curr 
                    + " cards, last of which was " + lastCard.toString();
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(message);
        }
    }
    
    @Test
    public void testConstructorShufflesShoe() {
        int expected = RANDOM.nextInt(8) + 3;
        CardJSONServer server = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, expected, 
                DEFAULT_STOP);
        Set<Integer> deckHashes = new HashSet<>(expected);
        for (int i = 0; i < CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK; i++) {
            deckHashes.add(server.giveCard().getDeckHash());
        }
        int actual = deckHashes.size();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testConstructorRejectsNegativePort() {
        int badPort = -RANDOM.nextInt(Short.MAX_VALUE) - 1;
        String numStr = Integer.toString(badPort);
        String msg = "Constructor should reject port " + numStr;
        Throwable t = assertThrows(() -> {
            CardJSONServer badServer = new CardJSONServer(badPort, 1, 0);
            System.out.println(msg + ", not created instance " 
                    + badServer.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should contain \"" + numStr 
                + "\"";
        assert excMsg.contains(numStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorRejectsOutOfRangePort() {
        int badPort = 4 * Short.MAX_VALUE + RANDOM.nextInt(Byte.MAX_VALUE) + 1;
        String numStr = Integer.toString(badPort);
        String msg = "Excessive port number " + badPort 
                + " should cause exception";
        Throwable t = assertThrows(() -> {
            CardJSONServer badServer = new CardJSONServer(badPort, 1, 0);
            System.out.println(msg + ", not created instance " + badServer.toString());
        }, IllegalArgumentException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should contain \"" + numStr 
                + "\"";
        assert excMsg.contains(numStr) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }
    
    // TODO: Rewrite with assertThrows()
    @Test
    public void testConstructorRejectsNegativeDeckQuantity() {
        int badQty = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, 
                    badQty, 0);
            String message = "Should not have been able to create " 
                    + badServer.toString() + " with bad deck quantity " 
                    + badQty;
            fail(message);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad quantity " + badQty 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck quantity " 
                    + badQty;
            fail(message);
        }
    }
    
    @Test
    public void testConstructorRejectsDeckQuantityZero() {
        int badQty = 0;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, 
                    badQty, 0);
            String message = "Should not have been able to create " 
                    + badServer.toString() + " with bad deck quantity " 
                    + badQty;
            fail(message);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad quantity " + badQty 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck quantity " 
                    + badQty;
            fail(message);
        }
    }
    
    @Test
    public void testConstructorRejectsNegativeStop() {
        int deckQty = RANDOM.nextInt(8) + 2;
        int badStop = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_TESTING_HTTP_PORT, 
                    deckQty, badStop);
            String message = "Should not have been able to create " 
                    + badServer.toString() + " with bad stop " + badStop;
            fail(message);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad stop " + badStop 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String message = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck stop " 
                    + badStop;
            fail(message);
        }
    }
    
}
