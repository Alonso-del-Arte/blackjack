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
package blackjack;

import currency.CurrencyAmount;
import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.matchers.RankPairSpec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testframe.api.Asserters.assertThrows;

/**
 * Tests of the Dealer class.
 * @author Alonso del Arte
 */
public class DealerTest {
    
    static final Random RANDOM = new Random();
    
    private static Player[] makePlayers() {
        int numberOfPlayers 
                = RANDOM.nextInt(Dealer.MAXIMUM_NUMBER_OF_PLAYERS_AT_TABLE) + 1;
        Player[] players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            int cents = RANDOM.nextInt(262144) + 256;
            CurrencyAmount playerBankroll = new CurrencyAmount(cents, 
                    WagerTest.DOLLARS);
            Player player = new Player("Johnny Q. Public " + i, playerBankroll);
            players[i] = player;
        }
        return players;
    }
    
    @Test
    public void testMaxNumberOfPlayersAtTable() {
        int expected = 7;
        String message = "Maximum number of players at table should be " + expected;
        int actual = Dealer.MAXIMUM_NUMBER_OF_PLAYERS_AT_TABLE;
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testReserveMultiplier() {
        String msg = "Constant RESERVE_MULTIPLIER " + Dealer.RESERVE_MULTIPLIER 
                + " should be more than 1.0";
        assert Dealer.RESERVE_MULTIPLIER > 1.0 : msg;
    }
    
    @Test
    public void testGiveSplittablePairs() {
        System.out.println("giveSplittablePairs");
        List<RankPairSpec> pairSpecs = new ArrayList<>();
        Rank[] ranks = Rank.values();
        for (Rank rank : ranks) {
            RankPairSpec pairSpec = new RankPairSpec(rank, rank);
            pairSpecs.add(pairSpec);
        }
        int index = RANDOM.nextInt(pairSpecs.size());
        pairSpecs.remove(index);
        Set<RankPairSpec> expected = new HashSet<>(pairSpecs);
        Dealer dealer = new Dealer(expected);
        Set<RankPairSpec> actual = dealer.giveSplittablePairs();
        String msg = "Pair spec set should contain all of " 
                + expected.toString();
        assert actual.containsAll(expected) : msg;
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void testGiveSplittablePairsDoesNotLeakReference() {
        RankPairSpec pairSpec1 = new RankPairSpec(Rank.EIGHT, Rank.EIGHT);
        Set<RankPairSpec> pairSpecSet = new HashSet<>();
        pairSpecSet.add(pairSpec1);
        Dealer dealer = new Dealer(pairSpecSet);
        Set<RankPairSpec> retrievedPairSpecSet1 = dealer.giveSplittablePairs();
        RankPairSpec pairSpec2 = new RankPairSpec(Rank.NINE, Rank.NINE);
        retrievedPairSpecSet1.add(pairSpec2);
        Set<RankPairSpec> retrievedPairSpecSet2 = dealer.giveSplittablePairs();
        assertNotEquals(retrievedPairSpecSet1, retrievedPairSpecSet2);
    }
    
    @Test
    public void testNewlyInstantiatedNotActiveAlready() {
        Dealer dealer = new Dealer();
        String msg = "New dealer should not be active in round already";
        assert !dealer.active() : msg;
    }
    
    @Test
    public void testNoFaceUpCardIfDealerNotActiveInRound() {
        Dealer dealer = new Dealer();
        PlayingCard card = dealer.tellFaceUpCard();
        if (card != null) {
            String message = "New dealer shouldn't have face up card " 
                    + card.toString() + " before first round";
            fail(message);
        }
    }
    
    @Test
    public void testActive() {
        Dealer dealer = new Dealer();
        Player player = PlayerTest.getPlayer();
        Round round = new Round(dealer, player);
        dealer.start(round);
        String msg = "After starting round with " + dealer.toString() + " and " 
                + " player " + player.getName() 
                + ", dealer should be active in round";
        assert dealer.active() : msg;
    }
    
    @Test
    public void testBankrollNullAtFirst() {
        Dealer dealer = new Dealer();
        String message = "Dealer bankroll should be null at first";
        assertNull(message, dealer.reportBankroll());
    }
    
    @Test
    public void testReportBankroll() {
        System.out.println("reportBankroll");
        Dealer dealer = new Dealer();
        Player[] players = makePlayers();
        CurrencyAmount totalPlayerBankroll = new CurrencyAmount(0, 
                WagerTest.DOLLARS);
        for (Player player : players) {
            totalPlayerBankroll = totalPlayerBankroll.plus(player.getBalance());
        }
        Round round = new Round(dealer, players);
        dealer.start(round);
        CurrencyAmount expected 
                = totalPlayerBankroll.times(Dealer.RESERVE_MULTIPLIER);
        CurrencyAmount actual = dealer.reportBankroll();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNoStartNewRoundWhileCurrentRoundActive() {
        Dealer dealer = new Dealer();
        Player[] firstRoundPlayers = makePlayers();
        Round firstRound = new Round(dealer, firstRoundPlayers);
        dealer.start(firstRound);
        Player[] secondRoundPlayers = makePlayers();
        Round secondRound = new Round(dealer, secondRoundPlayers);
        String msg = "Should not be able to start " + secondRound.toString() 
                + " while " + firstRound.toString() + " is still active";
        Throwable t = assertThrows(() -> {
            dealer.start(secondRound);
            System.out.println(msg);
        }, IllegalStateException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testTellFaceUpCard() {
        System.out.println("tellFaceUpCard");
        Dealer dealer = new Dealer();
        Player[] firstRoundPlayers = makePlayers();
        Round round = new Round(dealer, firstRoundPlayers);
        dealer.start(round);
        PlayingCard card = dealer.tellFaceUpCard();
        assert card != null : "Dealer's face up card shouldn't be null";
        System.out.println("Dealer's face up card is " + card.toASCIIString());
    }
    
//    @Test
    public void testBankrollCarriesOverToNextRound() {
        fail("Haven't written test yet");
    }
    
    @Test
    public void testAuxConstructorHasTypicalSplitPairSpecs() {
        Dealer dealer = new Dealer();
        Set<RankPairSpec> expected = new HashSet<>(BlackJack.SAME_RANK_PAIRS);
        expected.addAll(BlackJack.DISTINCT_TEN_PAIRS);
        Set<RankPairSpec> actual = dealer.giveSplittablePairs();
        String msg = "Pair spec set should contain all of " 
                + expected.toString();
        assert actual.containsAll(expected) : msg;
        assertEquals(expected.size(), actual.size());
    }
    
    @Test
    public void testConstructorRejectsNullSet() {
        Set<RankPairSpec> badSet = null;
        String msg = "Should not be able to start dealer with null pairs set";
        Throwable t = assertThrows(() -> {
            Dealer badDealer = new Dealer(badSet);
            System.out.println(msg + ", not gotten " + badDealer.toString());
        }, NullPointerException.class, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        System.out.println("\"" + excMsg + "\"");
    }
    
    @Test
    public void testConstructorMakesNewPairSpecSetFromParam() {
        RankPairSpec pairSpec1 = new RankPairSpec(Rank.EIGHT, Rank.EIGHT);
        Set<RankPairSpec> pairSpecSet = new HashSet<>();
        pairSpecSet.add(pairSpec1);
        Dealer dealer = new Dealer(pairSpecSet);
        RankPairSpec pairSpec2 = new RankPairSpec(Rank.NINE, Rank.NINE);
        pairSpecSet.add(pairSpec2);
        Set<RankPairSpec> retrievedPairSpecSet = dealer.giveSplittablePairs();
        assertNotEquals(pairSpecSet, retrievedPairSpecSet);
    }
    
}
