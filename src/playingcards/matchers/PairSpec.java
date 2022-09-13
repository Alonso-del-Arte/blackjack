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
    
    /**
     * Determines whether the specified characteristics match this pair 
     * specification. This function should be called by the classes implementing 
     * this abstract class.
     * @param fromCardA The characteristic of one of the cards.
     * @param fromCardB The characteristic of the other card of the pair.
     * @return True if the characteristics match this pair specification, false 
     * otherwise. Order does not matter.
     */
    protected boolean matches(E fromCardA, E fromCardB) {
        return (this.elementA.equals(fromCardA) 
                && this.elementB.equals(fromCardB)) 
                || (this.elementA.equals(fromCardB) 
                && this.elementB.equals(fromCardA));
    }
    
    /**
     * Determines whether or not two cards match this pair specification. Order 
     * should not matter. This function should be implemented by calling the 
     * protected <code>matches(E, E)</code> function.
     * @param cardA One card of the pair.
     * @param cardB The other card of the pair.
     * @return 
     */
    public abstract boolean matches(PlayingCard cardA, PlayingCard cardB);
    
    /**
     * Determines whether an object is equal to this pair specification.
     * @param obj The object to compare for equality. May be null.
     * @return True if <code>obj</code> is of the same runtime class as this 
     * pair specification <em>and</em> both specify the same characteristics for 
     * a pair of playing cards, false under any other circumstance. Order is not 
     * considered, but that depends on the subclasses letting this class take 
     * care of holding on to the pair characteristics.
     */
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
    
    /**
     * Gives a hash code for this pair specification.
     * @return A positive integer derived from the enumeration ordinals of the 
     * pair of characteristics.
     */
    @Override
    public int hashCode() {
        return (this.elementA.ordinal() + 1) * HASH_SEP 
                + (this.elementB.ordinal()) + 1;
    }
    
    /**
     * Identifies the two card specifications of this pair. This override is 
     * provided only for the sake of testing.
     * @return "(a, b)", where "a" stands for the first card specification and 
     * "b" stands for the second card classification.
     */
    @Override
    public String toString() {
        return "(" + this.elementA.getWord() + ", " + this.elementB.getWord() 
                + ")";
    }
    
    /**
     * Superclass constructor. This constructor takes care of making sure the 
     * elements are stored so that their order as constructor parameters does 
     * not matter for equality and matching operations.
     * @param elemA Enumerated element specifying the characteristic of one card 
     * of a pair of cards. May or may not be the same as <code>elemB</code>.
     * @param elemB Enumerated element specifying the characteristic of the 
     * other card of the pair of cards. May or may not be the same as 
     * <code>elemA</code>.
     */
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
