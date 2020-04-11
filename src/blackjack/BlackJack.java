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
package blackjack;

import playingcards.MultiDeckCardDispenser;
import playingcards.PlayingCard;

import java.util.Scanner;

/**
 *
 * @author Alonso del Arte
 */
public class BlackJack {

    /**
     * EARLY PROOF OF CONCEPT. Play blackjack at the command line. Dealer and
     * only one player.
     * @param args the command line arguments; don't actually do anything with
     * these, yet
     */
    public static void main(String[] args) {
        System.out.println();
        System.out.println("BLACKJACK");
        System.out.println();
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
            MultiDeckCardDispenser dispenser = new MultiDeckCardDispenser(6, 75);
            Hand dealerHand = new Hand();
            Hand playerHand = new Hand();
            PlayingCard card = dispenser.getNextCard();
            playerHand.add(card);
            System.out.println("Your first card is " + card.toASCIIString());
            card = dispenser.getNextCard();
            dealerHand.add(card);
            System.out.println("Dealer's face-up card is " + card.toASCIIString());
            System.out.println();
            card = dispenser.getNextCard();
            playerHand.add(card);
            System.out.println("Your second card is " + card.toASCIIString());
            System.out.println("Your hand's value is " + playerHand.cardsValue());
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
                    System.out.println("You have natural blackjack, but so does the dealer");
                    System.out.println("You keep your $" + wager);
                } else {
                    int payout = wager * 3 / 2;
                    System.out.println("Congratulations, you have a natural blackjack");
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
                        System.out.println("Your next card is " + card.toASCIIString());
                        System.out.println("Your hand's value is " + playerHand.cardsValue());
                    } else {
                        keepHitting = false;
                    }
                    System.out.println();
                }
                System.out.println("Dealer's face-down card is " + faceDownCard.toASCIIString());
                System.out.println("Dealer's hand value is " + dealerHand.cardsValue());
                while (dealerHand.cardsValue() < 17) {
                    card = dispenser.getNextCard();
                    dealerHand.add(card);
                    System.out.println("Dealer takes " + card.toASCIIString());
                    System.out.println("Dealer's hand value is " + dealerHand.cardsValue());
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
                            System.out.println("*** YOU WIN $" + wager + " ****");
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
                            System.out.println("*** YOU WIN $" + wager + " ****");
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
                            System.out.println("Since you stood and the dealer went bust,");
                            System.out.println("*** YOU WIN $" + wager + " ****");
                            break;
                        case 3030:
                            System.out.println("Even though the dealer also went bust...");
                        default:
                            System.out.println("Dealer collects your $" + wager);
                            System.out.println("Better luck next time...");
                    }
                }
            }
        }
    }

}
