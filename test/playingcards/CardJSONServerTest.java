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

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
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
    
    private static final int DEFAULT_HTTP_PORT = 8080;
    
    private static final int DEFAULT_STOP = 75;
    
    static final Random RANDOM = new Random();
        
    @Test
    public void testNoDoubleActivation() {
        int deckQty = RANDOM.nextInt(8) + 2;
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTP_PORT, deckQty, 
                DEFAULT_STOP);
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
        int port = 8080;
        int deckQty = 2;
        int stop = 25;
        int expected = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        Set<ProvenanceInscribedPlayingCard> cards = new HashSet<>(expected);
        int index = 0;
        while (index < expected) {
            cards.add(server.giveCard());
            index++;
        }
        int actual = cards.size();
        assertEquals(expected, actual);
    }
    
    // TODO: Rewrite this test
    @org.junit.Ignore
    @Test
    public void testGiveCardReplenishesAutomaticallyAfterRunningOut() {
        int port = 8080;
        int deckQty = 2;
        int stop = 25;
        int initialCardQty = deckQty * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK 
                - stop;
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        Set<Integer> deckIDNumbers = new HashSet<>(initialCardQty);
        int index = 0;
        while (index < initialCardQty) {
            deckIDNumbers.add(server.giveCard().getDeckHash());
            index++;
        }
        try {
            ProvenanceInscribedPlayingCard card = server.giveCard();
            System.out.println("After running out of the first " 
                    + initialCardQty 
                    + " cards, server correctly replenished cards");
            assertEquals(deckQty, deckIDNumbers.size());
            deckIDNumbers.add(card.getDeckHash());
            String msg = "Card " + card.toString() 
                    + " should come from replenishment deck";
            assert deckIDNumbers.size() > deckQty : msg;
        } catch (RanOutOfCardsException roce) {
            System.out.println("\"" + roce.getMessage() + "\"");
            String msg = "RanOutOfCardsException should not have occurred";
            fail(msg);
        }
    }
    
    // TODO: Rewrite this test
    @org.junit.Ignore
    @Test
    public void testGiveCardReplenishesWithSameDeckQtyAndStop() {
        int port = 8080;
        int expected = RANDOM.nextInt(10) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        int initialCardQty = expected 
                * CardDeck.INITIAL_NUMBER_OF_CARDS_PER_DECK - stop;
        CardJSONServer server = new CardJSONServer(port, expected, stop);
        Set<Integer> initialDeckHashes = new HashSet<>(initialCardQty);
        int index = 0;
        while (index < initialCardQty) {
            initialDeckHashes.add(server.giveCard().getDeckHash());
            index++;
        }
        Set<Integer> replenishmentDeckHashes = new HashSet<>(initialCardQty);
        index = 0;
        int midwayCheckPoint = initialCardQty / 2;
        while (index < midwayCheckPoint) {
            int hash = server.giveCard().getDeckHash();
            replenishmentDeckHashes.add(hash);
            index++;
            String msg = "Replenishment deck hash " + hash + " of card indexed "
                    + index 
                    + " should not be present among initial deck hashes";
            assert !initialDeckHashes.contains(hash) : msg;
        }
        int actual = replenishmentDeckHashes.size();
        assertEquals(expected, actual);
        while (index < initialCardQty) {
            int hash = server.giveCard().getDeckHash();
            assert !replenishmentDeckHashes.add(hash) 
                    : "All replenishment deck hashes should already be known";
            index++;
            String msg = "Replenishment deck hash " + hash + " of card indexed "
                    + index 
                    + " should not be present among initial deck hashes";
            assert !initialDeckHashes.contains(hash) : msg;
        }
        int hash = server.giveCard().getDeckHash();
        String msg = "Second replenishment deck hash " + hash 
                + " should not be an initial hash nor first replenishment hash";
        assert !initialDeckHashes.contains(hash) : msg;
        assert !replenishmentDeckHashes.contains(hash) : msg;
    }
    
    @Test
    public void testServerRespondsOnSpecifiedPort() {
        int port = 8080 + RANDOM.nextInt(1000);
        int deckQty = RANDOM.nextInt(10) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        server.activate();
        System.out.println("Expecting localhost response on port " + port);
        String locator = "http://localhost:" + port + "/dealcard";
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
    
    @Test
    public void testServerRespondsToOptionsMethod() {
        int port = 8080 + RANDOM.nextInt(1000);
        int deckQty = RANDOM.nextInt(10) + 4;
        int stop = 75 + RANDOM.nextInt(15);
        CardJSONServer server = new CardJSONServer(port, deckQty, stop);
        server.activate();
        String locator = "http://localhost:" + port + "/dealcard";
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
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTP_PORT, deckQty, 
                DEFAULT_STOP);
        Throwable t = assertThrows(() -> {
            server.deactivate();
            String msg = "Should not have been able to deactivate " 
                    + server.toString() + " because it was already inactive";
            fail(msg);
        }, IllegalStateException.class);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorSetsSpecifiedDeckQuantityAndStop() {
        int deckQty = RANDOM.nextInt(8) + 4;
        int stop = 75 + RANDOM.nextInt(25);
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTP_PORT, deckQty, 
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
            String msg = "Given shoe with " + totalCardQty 
                    + " cards, exception should not have occurred after " + curr 
                    + " cards, last of which was " + lastCard.toString();
            System.out.println("\"" + roce.getMessage() + "\"");
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorShufflesShoe() {
        int expected = RANDOM.nextInt(8) + 3;
        CardJSONServer server = new CardJSONServer(DEFAULT_HTTP_PORT, expected, 
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
    
    @Test
    public void testConstructorRejectsNegativeDeckQuantity() {
        int badQty = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTP_PORT, 
                    badQty, 0);
            String msg = "Should not have been able to create " 
                    + badServer.toString() + " with bad deck quantity " 
                    + badQty;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad quantity " + badQty 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck quantity " 
                    + badQty;
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsDeckQuantityZero() {
        int badQty = 0;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTP_PORT, 
                    badQty, 0);
            String msg = "Should not have been able to create " 
                    + badServer.toString() + " with bad deck quantity " 
                    + badQty;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad quantity " + badQty 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck quantity " 
                    + badQty;
            fail(msg);
        }
    }
    
    @Test
    public void testConstructorRejectsNegativeStop() {
        int deckQty = RANDOM.nextInt(8) + 2;
        int badStop = -RANDOM.nextInt(256) - 4;
        try {
            CardJSONServer badServer = new CardJSONServer(DEFAULT_HTTP_PORT, 
                    deckQty, badStop);
            String msg = "Should not have been able to create " 
                    + badServer.toString() + " with bad stop " + badStop;
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad stop " + badStop 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for bad deck stop " 
                    + badStop;
            fail(msg);
        }
    }
    
}
