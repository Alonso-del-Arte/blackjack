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
    
    private static final int HASH_SEP = 65536;
    
    private final E elementA, elementB;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        final PairSpec other = (PairSpec) obj;
        return (this.elementA.equals(other.elementA) 
                && this.elementB.equals(other.elementB));
    }
    
    @Override
    public int hashCode() {
        return (this.elementA.ordinal() + 1) * HASH_SEP 
                + (this.elementB.ordinal()) + 1;
    }
    
    protected boolean matches(E fromCardA, E fromCardB) {
        return (this.elementA.equals(fromCardA) 
                && this.elementB.equals(fromCardB)) 
                || (this.elementA.equals(fromCardB) 
                && this.elementB.equals(fromCardA));
    }
    
    public abstract boolean matches(PlayingCard cardA, PlayingCard cardB);
    
    PairSpec(E elemA, E elemB) {
        if (elemB.ordinal() > elemA.ordinal()) {
            this.elementA = elemA;
            this.elementB = elemB;
        } else {
            this.elementA = elemB;
            this.elementB = elemA;
        }
    }
    
}
