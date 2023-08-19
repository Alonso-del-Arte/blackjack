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

/**
 * This class enumerates a characteristic of a playing card that might be useful 
 * for testing things that depend on either rank or suit but can be generalized 
 * so as to apply to either. Namely whether the card is an odd pip card, an even 
 * pip card or a court card. This enumeration is for testing only and is not to 
 * be made available for any production context.
 * @author Alonso del Arte
 */
public enum TestingSpec implements CardSpec {
    
    ODD_PIP ('o', "odd", "odds"),
    EVEN_PIP ('e', "even", "evens"),
    COURT ('c', "court", "court cards");
    
    private final char specChar;
    private final String specWord;
    private final String pluralTerm;

    @Override
    public char getChar() {
        return this.specChar;
    }

    @Override
    public char getCharASCII() {
        return this.specChar;
    }

    @Override
    public String getWord() {
        return this.specWord;
    }
    
    @Override
    public String getPluralWord() {
        return this.pluralTerm;
    }
    
    @Override
    public boolean matches(PlayingCard card) {
        int value = card.cardValue();
        switch (this) {
            case ODD_PIP:
                int modVal = value % 13;
                return modVal % 2 == 1 && modVal < 10;
            case EVEN_PIP:
                return value % 2 == 0 && value < 11;
            case COURT:
                return value > 10 && value < 14;
            default:
                String excMsg = "Testing spec " + this.specWord 
                        + " not recognized";
                throw new RuntimeException(excMsg);
        }
    }

    TestingSpec(char ch, String word, String term) {
        this.specChar = ch;
        this.specWord = word;
        this.pluralTerm = term;
    }

}
