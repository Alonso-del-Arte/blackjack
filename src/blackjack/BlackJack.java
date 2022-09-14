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
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package blackjack;

import currency.CurrencyAmount;
import playingcards.MultiDeckCardDispenser;
import playingcards.PlayingCard;
import playingcards.Rank;
import playingcards.matchers.RankPairSpec;

import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/**
 * The blackjack game. This will eventually have a graphical user interface. For 
 * the time being, it's only a command line application.
 * @version 0.2.
 * @author Alonso del Arte
 */
public class BlackJack {
    
    private static final Set<RankPairSpec> DISTINCT_TEN_PAIRS = new HashSet<>();
    
    private static final Set<RankPairSpec> DISTINCT_ADD_TO_16 = new HashSet<>();
    
//    static {
//        DISTINCT_TEN_PAIRS.add(new RankPairSpec(Rank.TEN, Rank.JACK));
//        DISTINCT_TEN_PAIRS.add(new RankPairSpec(Rank.TEN, Rank.QUEEN));
//        DISTINCT_TEN_PAIRS.add(new RankPairSpec(Rank.TEN, Rank.KING));
//        DISTINCT_TEN_PAIRS.add(new RankPairSpec(Rank.JACK, Rank.QUEEN));
//        DISTINCT_TEN_PAIRS.add(new RankPairSpec(Rank.JACK, Rank.KING));
//        DISTINCT_TEN_PAIRS.add(new RankPairSpec(Rank.QUEEN, Rank.KING));
//    }
    
    // These flags are for when splitting functionality is enabled. For now, 
    // these flags don't actually do anything.
    private static boolean splitAcesAllowed = true;
    private static boolean splitAnyTimeAllowed = true;
    private static boolean splitDiffTensAllowed = true;
    private static boolean split16Allowed = false;
    private static boolean resplitAllowed = false;
    private static boolean resplitAcesAllowed = false;
    private static boolean multDrawSplitAcesAllowed = false;
    private static boolean discardSplitAllowed = false;
    
    private static boolean splitOptionsChanged = false;
    
    private final static Set<RankPairSpec> SPLITTABLE_PAIRS = new HashSet<>();
    
    private static Dealer dealer;
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final CurrencyAmount ONE_PENNY = new CurrencyAmount(1, 
            DOLLARS);
    
    private static final Wager DEALERS_WAGER = new Wager(ONE_PENNY);

    // TODO: Break up playGameAtCommandLine() into smaller units
    /**
     * EARLY PROOF OF CONCEPT. Play blackjack at the command line. Dealer and
     * only one player.
     * @since 0.2.
     */
    public static void playGameAtCommandLine() {
        try (Scanner input = new Scanner(System.in)) {
            int wager = 1;
            System.out.print("Enter your wager in whole dollars: $");
            try {
                wager = Integer.parseInt(input.nextLine());
                System.out.println();
            } catch (NumberFormatException nfe) {
                System.out.println();
                System.out.println("Sorry, didn't catch " + nfe.getMessage()
                        + ", substituting $10");
            }
            CurrencyAmount playersWagerAmount = new CurrencyAmount(wager * 100, 
                    DOLLARS);
            Wager playersWager = new Wager(playersWagerAmount);
            MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(6, 
                    75);
            Hand dealerHand = new Hand(DEALERS_WAGER);
            Hand playerHand = new Hand(playersWager);
            PlayingCard card = dispenser.getNextCard();
            playerHand.add(card);
            System.out.println("Your first card is " + card.toASCIIString());
            card = dispenser.getNextCard();
            dealerHand.add(card);
            System.out.println("Dealer's face-up card is " 
                    + card.toASCIIString());
            System.out.println();
            card = dispenser.getNextCard();
            playerHand.add(card);
            System.out.println("Your second card is " + card.toASCIIString());
            System.out.println("Your hand's value is " 
                    + playerHand.cardsValue());
            System.out.println();
            PlayingCard faceDownCard = dispenser.getNextCard();
            dealerHand.add(faceDownCard);
            System.out.println("Dealer's face-down card is ?????");
            System.out.println();
            // TODO: Give option to split pair, when applicable
            // TODO: Give option to double down, when applicable
            // TODO: Give option for insurance bet
            if (playerHand.isWinningHand()) {
                if (dealerHand.isWinningHand()) {
                    System.out.println(
                            "You have natural blackjack, but so does the dealer"
                    );
                    System.out.println("You keep your $" + wager);
                } else {
                    int payout = wager * 3 / 2;
                    System.out.println(
                            "Congratulations, you have a natural blackjack"
                    );
                    System.out.println("*** YOU WIN $" + payout + " ****");
                }
            } else {
                boolean keepHitting = true;
                String answer;
                while (playerHand.isOpenHand() && keepHitting) {
                    System.out.print("Hit or stand? ");
                    answer = input.nextLine();
                    if (answer.toLowerCase().startsWith("h")) {
                        card = dispenser.getNextCard();
                        playerHand.add(card);
                        System.out.println("Your next card is " 
                                + card.toASCIIString());
                        System.out.println("Your hand's value is " 
                                + playerHand.cardsValue());
                    } else {
                        keepHitting = false;
                    }
                    System.out.println();
                }
                System.out.println("Dealer's face-down card is " 
                        + faceDownCard.toASCIIString());
                System.out.println("Dealer's hand value is " 
                        + dealerHand.cardsValue());
                while (dealerHand.cardsValue() < 17) {
                    card = dispenser.getNextCard();
                    dealerHand.add(card);
                    System.out.println("Dealer takes " + card.toASCIIString());
                    System.out.println("Dealer's hand value is " 
                            + dealerHand.cardsValue());
                    System.out.println();
                }
                if (dealerHand.isWinningHand()) {
                    if (playerHand.isWinningHand()) {
                        System.out.println("Stand-off, you keep your wager.");
                    } else {
                        System.out.println("You lose your wager.");
                    }
                } else {
                    int dealerScore = dealerHand.cardsValue();
                    int playerScore = playerHand.cardsValue();
                    if (dealerHand.isBustedHand()) {
                        dealerScore = 30;
                    }
                    if (playerHand.isBustedHand()) {
                        playerScore = 30;
                    }
                    int outcome = 100 * dealerScore + playerScore;
                    switch (outcome) {
                        case 1721:
                        case 1821:
                        case 1921:
                        case 2021:
                        case 3021:
                            System.out.println("*** YOU WIN $" + wager 
                                    + " ****");
                            break;
                        case 1717:
                        case 1818:
                        case 1919:
                        case 2020:
                            System.out.println("Stand-off");
                            break;
                        case 1718:
                        case 1719:
                        case 1720:
                        case 1819:
                        case 1820:
                        case 1920:
                            System.out.println("As you have a higher score,");
                            System.out.println("*** YOU WIN $" + wager 
                                    + " ****");
                            break;
                        case 3012:
                        case 3013:
                        case 3014:
                        case 3015:
                        case 3016:
                        case 3017:
                        case 3018:
                        case 3019:
                        case 3020:
                            System.out.println(
                                    "Since you stood and the dealer went bust,"
                            );
                            System.out.println("*** YOU WIN $" + wager 
                                    + " ****");
                            break;
                        case 3030:
                            System.out.println(
                                    "Even though the dealer also went bust..."
                            );
                        default:
                            System.out.println("Dealer collects your $" 
                                    + wager);
                            System.out.println("Better luck next time...");
                    }
                }
            }
        }
    }
    
    private static void informSplitOptions() {
        if (splitAcesAllowed) {
            System.out.println("You may split Aces");
        }
        if (splitAnyTimeAllowed) {
            System.out.println("You may split at any point in the game");
        }
        if (splitDiffTensAllowed) {
            System.out.println("You may split tens even if not the same");
        }
        if (split16Allowed) {
            System.out.println("You may split any pair valued at 16");
        }
        if (resplitAllowed) {
            System.out.println("You can split more than once");
        }
        if (resplitAcesAllowed) {
            System.out.println("You can split Aces more than once");
        }
        if (multDrawSplitAcesAllowed) {
            System.out.println("You can draw more than one after split aces");
        }
        if (discardSplitAllowed) {
            System.out.println("You may discard a split hand");
        }
        // TODO: Remove next line once splitting IS implemented
        System.out.println("Splitting hands is not actually implemented yet");
        System.out.println();
    }
    
    private static void processOptions(String[] args) {
        RankPairSpec aces = new RankPairSpec(Rank.ACE, Rank.ACE);
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "-splitaces":
                    splitAcesAllowed = true;
                    SPLITTABLE_PAIRS.add(aces);
                    break;
                case "-nosplitaces":
                    splitAcesAllowed = false;
                    splitOptionsChanged = splitOptionsChanged 
                            || SPLITTABLE_PAIRS.remove(aces);
                    break;
                case "-splitanytime":
                    splitAnyTimeAllowed = true;
                    break;
                case "-splitbeginonly":
                    splitAnyTimeAllowed = false;
                    splitOptionsChanged = true;
                    break;
                case "-splitdifftens":
                    splitDiffTensAllowed = true;
                    splitOptionsChanged = splitOptionsChanged 
                            || SPLITTABLE_PAIRS.addAll(DISTINCT_TEN_PAIRS);
                    break;
                case "-nosplitdifftens":
                    splitDiffTensAllowed = false;
                    splitOptionsChanged = splitOptionsChanged 
                            || SPLITTABLE_PAIRS.removeAll(DISTINCT_TEN_PAIRS);
                    break;
                case "-split16":
                    split16Allowed = true;
                    splitOptionsChanged = splitOptionsChanged 
                            || SPLITTABLE_PAIRS.addAll(DISTINCT_ADD_TO_16);
                    break;
                case "-nosplit16":
                    split16Allowed = false;
                    splitOptionsChanged = splitOptionsChanged 
                            || SPLITTABLE_PAIRS.removeAll(DISTINCT_ADD_TO_16);
                    break;
                case "-resplit":
                    resplitAllowed = true;
                    splitOptionsChanged = true;
                    break;
                case "-noresplit":
                    resplitAllowed = false;
                    break;
                case "-resplitaces":
                    resplitAcesAllowed = true;
                    splitOptionsChanged = true;
                    break;
                case "-noresplitaces":
                    resplitAcesAllowed = false;
                    break;
                case "-multdrawsplitaces":
                    multDrawSplitAcesAllowed = true;
                    splitOptionsChanged = true;
                    break;
                case "-singledrawsplitaces":
                    multDrawSplitAcesAllowed = false;
                    break;
                case "-discardsplit":
                    discardSplitAllowed = true;
                    splitOptionsChanged = true;
                    break;
                case "-nodiscardsplit":
                    discardSplitAllowed = false;
                    break;
                case "-text": // TODO: Revise once GUI's available
                    System.out.println("Since the GUI isn't available yet,");
                    System.out.println("game's only available on the terminal");
                    System.out.println();
                    break;
                case "-v":
                case "-version":
                    System.out.println("Version 0.3");
                    System.out.println();
                    break;
                default:
                    if (arg.startsWith("-")) {
                        System.err.println("Command line option '" + arg 
                                + "' not recognized");
                    } else {
                        System.err.println("Parameter '" + arg 
                                + "' not recognized");
                    }
            }
        }
        if (splitOptionsChanged) informSplitOptions();
    }
    
    // TODO: Once splitting is enabled, update main() Javadoc
    /**
     * Play the game. Eventually it will be possible to play the game with a 
     * graphical user interface. For now, though, it's only possible to play at 
     * the command line.
     * @param args The command line arguments. They are case-insensitive, but 
     * many of them don't do anything yet. Earlier options that are contradicted 
     * by later options are simply ignored.
     * <ul>
     * <li><code>-splitAces</code> You may split Aces. This is the default.</li>
     * <li><code>-noSplitAces</code> You can't split Aces.</li>
     * <li><code>-splitAnyTime</code> You can split any time you get two 
     * consecutive cards for which a split is allowed. For example, if you get 
     * A&#9824;, 8&#9824; and 8&#9829;, you may split. But if you get 8&#9824;, 
     * A&#9824; and 8&#9829;, you may not split regardless of this option.  This 
     * is the default.</li>
     * <li><code>-splitBeginOnly</code> You can split only after drawing the 
     * second card in your hand but before drawing the third card.</li>
     * <li><code>-splitDiffTens</code> You can split two cards valued 10 even if 
     * they are different, e.g., 10&#9829; and Q&#9827; can be split, as well as 
     * of course pairs like J&#9824; and J&#9830;.</li>
     * <li><code>-noSplitDiffTens</code> You can't split two cards valued 10 if 
     * they are different, e.g., 10&#9829; and Q&#9827; can't be split, they 
     * would either both have to be 10s or both Queens.</li>
     * <li><code>-split16</code> You can split any pair valued at 16.</li>
     * <li><code>-noSplit16</code> You can split Eights but no other pair valued 
     * at 16.</li>
     * <li><code>-resplit</code> You can split multiple times, infinitely, in 
     * theory.</li>
     * <li><code>-noResplit</code> You can't split a second time.</li>
     * <li><code>-resplitAces</code> You can re-split Aces.</li>
     * <li><code>-noResplitAces</code> You can't re-split Aces.</li>
     * <li><code>-multDrawSplitAces</code> You can draw more than one card after 
     * splitting Aces.</li>
     * <li><code>-singleDrawSplitAces</code> You can only draw one more card 
     * after splitting Aces.</li>
     * <li><code>-discardSplit</code> You can discard a split hand.</li>
     * <li><code>-noDiscardSplit</code> You can't discard a split hand.</li>
     * <li><code>-text</code> Play the game as text-based on the command line. 
     * But since I haven't even started work on the graphics, only the 
     * text-based version is currently available.</li>
     * <li><code>-version</code> Gives the version number.</li>
     * </ul>
     */
    public static void main(String[] args) {
        System.out.println();
        System.out.println("BLACKJACK");
        System.out.println();
        processOptions(args);
        dealer = new Dealer(SPLITTABLE_PAIRS);
        playGameAtCommandLine();
    }

}
