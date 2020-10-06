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
package playingcards.matchers;

import playingcards.CardSpec;
import playingcards.PlayingCard;

/**
 * Specifies a pair of characteristics (not necessarily distinct) which a pair 
 * of playing cards either matches or doesn't, regardless of order. This is 
 * mostly going to be used with rank and suit.
 * @param <E> An enumeration that represents a characteristic of a playing card. 
 * Generally this should be either <code>Rank</code> or <code>Suit</code>. Other 
 * characteristics are rare but allowed.
 * @author Alonso del Arte
 */
abstract class PairSpec<E extends Enum & CardSpec> {
    
    private final E elementA, elementB;
    
    // STUB TO FAIL THE INDIRECT TESTS
    protected boolean matches(E fromCardA, E fromCardB) {
        return false;
    }
    
    public abstract boolean matches(PlayingCard cardA, PlayingCard cardB);
    
    PairSpec(E elemA, E elemB) {
        this.elementA = elemA;
        this.elementB = elemB;
    }
    
}
