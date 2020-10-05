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

import playingcards.PlayingCard;
import playingcards.Suit;

/**
 * Specifies suits for a pair of cards. Order is not taken into account.
 * @author Alonso del Arte
 */
public class SuitPairSpec extends PairSpec<Suit> {
    
    private static final int HASH_SEP = 65536;
    
    private final Suit suit1, suit2;
    
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
        SuitPairSpec other = (SuitPairSpec) obj;
        return (this.suit1.equals(other.suit1) && this.suit2.equals(other.suit2)) 
                || (this.suit1.equals(other.suit2) && this.suit2.equals(other.suit1));
    }
    
    @Override
    public int hashCode() {
        return this.suit1.ordinal() * HASH_SEP + this.suit2.ordinal();
    }
    
    @Override
    public boolean matches(PlayingCard cardA, PlayingCard cardB) {
        if (this.suit1.equals(cardA.getSuit()) && this.suit2.equals(cardB.getSuit())) {
            return true;
        }
        return this.suit2.equals(cardA.getSuit()) && this.suit1.equals(cardB.getSuit());
    }
    
    public SuitPairSpec(Suit suitA, Suit suitB) {
        super(suitA, suitB);
        // The following will be redundant once the abstract superclass is done
        if (suitA.ordinal() > suitB.ordinal()) {
            this.suit1 = suitB;
            this.suit2 = suitA;
        } else {
            this.suit1 = suitA;
            this.suit2 = suitB;
        }
    }
    
}
