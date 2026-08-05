/*
 * Copyright (C) 2026 Alonso del Arte
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

import java.util.function.Predicate;

/**
 *
 * @author Alonso del Arte
 */
public class CardStream {
    
    // TODO: Write a test for this
    public static PlayingCard giveCard(Rank rank) {
        return new PlayingCard(Rank.JACK, Suit.CLUBS);
    }
    
    // TODO: Write a test for this
    public static PlayingCard giveCard(Suit suit) {
        return new PlayingCard(Rank.JACK, Suit.CLUBS);
    }
    
    // TODO: Write a test for this
    // The idea here is to make blackjack.HandTest less brittle
    public static PlayingCard giveCard(Predicate<PlayingCard> predicate) {
        return new PlayingCard(Rank.JACK, Suit.CLUBS);
    }
    
}
