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
package playingcards;

import java.util.Locale;

/**
 * Gives a specification for a characteristic of a playing card. Generally this 
 * should be either rank or suit.
 * @author Alonso del Arte
 */
public interface CardSpec {
    
    /**
     * Gives a character associated with the card specification. Preferably a 
     * character that appears on the card itself.
     * @return A character associated with the card specification. Any 
     * non-surrogate character from Unicode's Basic Multilingual Plane may be 
     * used.
     */
    char getChar();
    
    /**
     * Gives a character associated with the card specification which can be 
     * used in a context where most Unicode characters are unavailable.
     * @return A character associated with the card specification. It should be 
     * a printing ASCII character.
     */
    char getCharASCII();
    
    /**
     * Gives a word associated with the card specification. Preferably how the 
     * word is pronounced in the main locale.
     * @return A word associated with the card specification.
     */
    String getWord();
    
    /**
     * Gives a word associated with the card specification suitable for the 
     * specified location. A default implementation is given that merely returns 
     * the same as {@link #getWord()}, so for now this can be a placeholder for 
     * internationalization.
     * @param loc The locale for which to give the word. For example, 
     * <code>Locale.ITALY</code>.
     * @return The same as {@link #getWord()} if this default implementation is 
     * not overridden.
     */
    default String getWord(Locale loc) {
        return this.getWord();
    }
    
}
