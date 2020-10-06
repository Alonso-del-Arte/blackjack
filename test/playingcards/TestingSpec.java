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

/**
 * This class enumerates a characteristic of a playing card that might be useful 
 * for testing things that depend on either rank or suit but can be generalized 
 * so as to apply to either. Namely whether the card is an odd pip card, an even 
 * pip card or a court card. This enumeration is for testing only and is not to 
 * be made available for any production context.
 * @author Alonso del Arte
 */
public enum TestingSpec implements CardSpec {
    
    ODD_PIP ('o', "odd"),
    EVEN_PIP ('e', "even"),
    COURT ('c', "court");
    
    private final char specChar;
    private final String specWord;

    // STUB TO FAIL THE FIRST TEST
    @Override
    public char getChar() {
        return '?';
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    public char getCharASCII() {
        return '?';
    }

    // STUB TO FAIL THE FIRST TEST
    @Override
    public String getWord() {
        return "Sorry, not implemented yet";
    }

    TestingSpec(char ch, String word) {
        this.specChar = ch;
        this.specWord = word;
    }

}
