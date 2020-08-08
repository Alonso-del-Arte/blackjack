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

import playingcards.PlayingCard;
import playingcards.Rank;

import java.util.ArrayList;

/**
 * A class to represent a blackjack hand, and keep track of its status (in play, 
 * won or busted). The hand's value is recalculated as each card is added, and 
 * the values of the Aces are reassessed if necessary.
 * @author Alonso del Arte
 */
public class Hand {

    private final ArrayList<PlayingCard> cards;
    
    private int handScore = 0;
    
    private boolean openFlag = true;
    private boolean winFlag = false;
    private boolean bustFlag = false;
    private boolean closedFlag = false;
    
    private void updateCardsValue() {
        int cumulRank = 0;
        int aceCount = 0;
        for (PlayingCard card : cards) {
            if (card.isCourtCard()) {
                cumulRank += 10;
            } else {
                if (card.isOf(Rank.ACE)) {
                    cumulRank++;
                    aceCount++;
                } else {
                    cumulRank += card.cardValue();
                }
            }
        }
        if (aceCount > 0 && cumulRank < 12) cumulRank += 10;
        this.openFlag = (cumulRank < 21);
        this.winFlag = (cumulRank == 21);
        this.bustFlag = (cumulRank > 21);
        this.closedFlag = !this.openFlag;
        this.handScore = cumulRank;
    }

    /**
     * Gives the value of the hand. Court cards are counted as 10 each. An Ace 
     * is counted as 11 unless that causes the hand to go bust, in which case it 
     * is counted as 1. All the pip cards are counted at face value.
     * @return The value of the hand: 0 for a new hand, 2 to 20 for a hand that 
     * has at least one card and may take more cards, 21 for a winning hand and 
     * 22 to 30 for a hand that has gone bust. The value 1 should not occur 
     * because a hand with an Ace as the only card is valued at 11.
     */
    public int cardsValue() {
        return this.handScore;
    }
    
    /**
     * Shows the cards in the hand. The cards are still held by the hand 
     * afterwards.
     * @return An array of the cards. For example, a 2-element array containing 
     * an Ace of Spades (A&#9824;) and a Two of Hearts (2&#9829;).
     */
    public PlayingCard[] inspectCards() {
        PlayingCard[] cardsToShow = new PlayingCard[this.cards.size()];
        for (int i = 0; i < this.cards.size(); i++) {
            cardsToShow[i] = this.cards.get(i);
        }
        return cardsToShow;
    }
    
    /**
     * Determines if the hand can be split. If it can, use {@link #split()} to 
     * split the hand.
     * @return True if and only if the hand has only a pair of cards of the same 
     * rank, false otherwise. For example, this would be true for 10&#9824; and 
     * 10&#9827;, false for 10&#9824; and J&#9827; even though they are both 
     * valued 10. There is no stricture on splitting Aces, Fours, Fives or Tens 
     * (which some British casinos might enforce).
     */
    public boolean isSplittableHand() {
        return (this.cards.size() == 2 
                && this.cards.get(0).isSameRank(this.cards.get(1)));
    }
    
    /**
     * Splits off one of a pair to a separate hand. If the split occurs 
     * successfully, this hand will then contain one card and the other hand 
     * will contain the other card. The value of this hand is reduced 
     * accordingly. Some casinos may limit how many times one player may split a 
     * hand during a game; this class provides no mechanism for such a 
     * limitation.
     * @return The split off hand, containing one card that was previously in 
     * this hand. That card may or may not be the former second card of this 
     * hand: that's an implementation detail callers should not rely upon and 
     * tests should not test for.
     * @throws IllegalStateException If this hand can't be split according to 
     * {@link #isSplittableHand()}.
     */
    public Hand split() {
        if (!this.isSplittableHand()) {
            String excMsg = "Can't split this hand";
            throw new IllegalStateException(excMsg);
        }
        Hand splitOffHand = new Hand();
        splitOffHand.add(this.cards.remove(1));
        this.updateCardsValue();
        return splitOffHand;
    }
    
    /**
     * Indicates if cards may be added to this hand.
     * @return True if the hand is new or has cards valued at 2 to 20, false 
     * otherwise.
     */
    public boolean isOpenHand() {
        return this.openFlag;
    }
    
    /**
     * Indicates if this is a winning hand. This depends only on the value of 
     * the cards in this hand, not how it compares to other players' hands 
     * (e.g., if this hand is not a natural blackjack but another player's is).
     * @return True if and only if this hand is valued at 21, false otherwise, 
     * without regard to how this hand compares to other players' hands.
     */
    public boolean isWinningHand() {
        return this.winFlag;
    }
    
    /**
     * Indicates if this hand has gone over 21.
     * @return True if cards' aggregate value is in excess of 21 (even after 
     * counting all Aces as 1 each), false otherwise.
     */
    public boolean isBustedHand() {
        return this.bustFlag;
    }
    
    /**
     * Indicates if no more cards may be added to this hand.
     * @return True if this is a winning hand or has gone bust, false otherwise.
     */
    public boolean isClosedHand() {
        return this.closedFlag;
    }

    /**
     * Adds a card to the hand.
     * @param card The card to add. For example, 5&#9824;.
     * @throws IllegalStateException If the hand has blackjack or has gone bust.
     */
    public void add(PlayingCard card) {
        if (this.closedFlag) {
            String excMsg = "Can't add card to hand vlaued at " + this.handScore;
            throw new IllegalStateException(excMsg);
        }
        int index = 0;
        PlayingCard alreadyCard;
        while (index < this.cards.size()) {
            alreadyCard = this.cards.get(index);
            if (card.equals(alreadyCard)) {
                int hash = System.identityHashCode(card);
                if (hash == System.identityHashCode(alreadyCard)) {
                    String excMsg = "Can't add card " + card.toASCIIString() 
                            + " (" + hash + ") twice";
                    throw new IllegalArgumentException(excMsg);
                }
            }
            index++;
        }
        this.cards.add(card);
        this.updateCardsValue();
    }

    /**
     * Creates a new hand. The hand has no cards and is valued at 0. Add cards 
     * using {@link #add(playingcards.PlayingCard) add()}.
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

}
