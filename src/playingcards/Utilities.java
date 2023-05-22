/*
 * Copyright (C) 2023 Alonso del Arte
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

import java.util.Comparator;

/**
 *
 * @author Alonso del Arte
 */
public class Utilities {
    
    public static Comparator<PlayingCard> BRAND_NEW_DECK_ORDER 
            = new Comparator<PlayingCard>() {
                
                // TODO: Write tests for this
                // TODO: Check if NetBeans still gives lambda expression 
                //       suggestion
                @Override
                public int compare(PlayingCard cardA, PlayingCard cardB) {
                    return 0;
                }
            
            };
    
    public static Comparator<PlayingCard> DEFAULT_ORDER 
            = new Comparator<PlayingCard>() {
                
                // TODO: Write tests for this
                // TODO: Check if NetBeans still gives lambda expression 
                //       suggestion
                @Override
                public int compare(PlayingCard cardA, PlayingCard cardB) {
                    return 0;
                }
            
            };
    
    // TODO: Write tests for this
    public static int sortingValue(Rank rank) {
        return -1;
    }
    
    // TODO: Write tests for this
    public static int sortingValue(Suit suit) {
        return -1;
    }
    
}
