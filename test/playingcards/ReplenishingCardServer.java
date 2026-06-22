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

/**
 *
 * @author Alonso del Arte
 */
public class ReplenishingCardServer extends CardServer {
    
    // TODO: Write tests for this
    @Override
    public boolean hasNext() {
        return false;
    }
    
    // TODO: Write tests for this
    @Override
    public PlayingCard getNextCard() {
        return new PlayingCard(Rank.JACK, Suit.CLUBS);
    }
    
    // TODO: Write tests for this
    @Override
    public int countRemaining() {
        return Integer.MIN_VALUE;
    }
    
    // TODO: Write tests for this
    @Override
    public boolean provenance(PlayingCard card) {
        return false;
    }
    
    // TODO: Write tests for this
    @Override
    public PlayingCard giveCard(Rank rank) {
        return new PlayingCard(rank, Suit.CLUBS);
    }
    
    // TODO: Write tests for this
    @Override
    public PlayingCard giveCard(Suit suit) {
        return new PlayingCard(Rank.JACK, suit);
    }
    
    // TODO: Write tests for this
    // TODO: Write tests for this
    // TODO: Write tests for this
    // TODO: Write tests for this
}
