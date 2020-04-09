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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the Rank enumerated type.
 * @author Alonso del Arte
 */
public class RankTest {
    
    @Test
    public void testGetRankAce() {
        System.out.println("getRank");
        assertEquals(14, Rank.ACE.getRank());
    }
    
    @Test
    public void testGetRank2() {
        assertEquals(2, Rank.TWO.getRank());
    }
    
    @Test
    public void testGetRank3() {
        assertEquals(3, Rank.THREE.getRank());
    }
    
    @Test
    public void testGetRank4() {
        assertEquals(4, Rank.FOUR.getRank());
    }
    
    @Test
    public void testGetRank5() {
        assertEquals(5, Rank.FIVE.getRank());
    }
    
    @Test
    public void testGetRank6() {
        assertEquals(6, Rank.SIX.getRank());
    }
    
    @Test
    public void testGetRank7() {
        assertEquals(7, Rank.SEVEN.getRank());
    }
    
    @Test
    public void testGetRank8() {
        assertEquals(8, Rank.EIGHT.getRank());
    }
    
    @Test
    public void testGetRank9() {
        assertEquals(9, Rank.NINE.getRank());
    }
    
    @Test
    public void testGetRank10() {
        assertEquals(10, Rank.TEN.getRank());
    }
    
    @Test
    public void testGetRankJack() {
        assertEquals(11, Rank.JACK.getRank());
    }
    
    @Test
    public void testGetRankQueen() {
        assertEquals(12, Rank.QUEEN.getRank());
    }
    
    @Test
    public void testGetRankKing() {
        assertEquals(13, Rank.KING.getRank());
    }
    
    @Test
    public void testGetRankCharsAce() {
        System.out.println("getRankChars");
        assertEquals("A", Rank.ACE.getRankChars());
    }
    
    @Test
    public void testGetRankChars2() {
        assertEquals("2", Rank.TWO.getRankChars());
    }
    
    @Test
    public void testGetRankChars3() {
        assertEquals("3", Rank.THREE.getRankChars());
    }
    
    @Test
    public void testGetRankChars4() {
        assertEquals("4", Rank.FOUR.getRankChars());
    }
    
    @Test
    public void testGetRankChars5() {
        assertEquals("5", Rank.FIVE.getRankChars());
    }
    
    @Test
    public void testGetRankChars6() {
        assertEquals("6", Rank.SIX.getRankChars());
    }
    
    @Test
    public void testGetRankChars7() {
        assertEquals("7", Rank.SEVEN.getRankChars());
    }
    
    @Test
    public void testGetRankChars8() {
        assertEquals("8", Rank.EIGHT.getRankChars());
    }
    
    @Test
    public void testGetRankChars9() {
        assertEquals("9", Rank.NINE.getRankChars());
    }
    
    @Test
    public void testGetRankChars10() {
        assertEquals("10", Rank.TEN.getRankChars());
    }
    
    @Test
    public void testGetRankCharsJack() {
        assertEquals("J", Rank.JACK.getRankChars());
    }
    
    @Test
    public void testGetRankCharsQueen() {
        assertEquals("Q", Rank.QUEEN.getRankChars());
    }
    
    @Test
    public void testGetRankCharsKing() {
        assertEquals("K", Rank.KING.getRankChars());
    }
    
    @Test
    public void testGetRankWordAce() {
        System.out.println("getRankWord");
        assertEquals("Ace", Rank.ACE.getRankWord());
    }
    
    @Test
    public void testGetRankWord2() {
        assertEquals("Two", Rank.TWO.getRankWord());
    }
    
    @Test
    public void testGetRankWord3() {
        assertEquals("Three", Rank.THREE.getRankWord());
    }
    
    @Test
    public void testGetRankWord4() {
        assertEquals("Four", Rank.FOUR.getRankWord());
    }
    
    @Test
    public void testGetRankWord5() {
        assertEquals("Five", Rank.FIVE.getRankWord());
    }
    
    @Test
    public void testGetRankWord6() {
        assertEquals("Six", Rank.SIX.getRankWord());
    }
    
    @Test
    public void testGetRankWord7() {
        assertEquals("Seven", Rank.SEVEN.getRankWord());
    }
    
    @Test
    public void testGetRankWord8() {
        assertEquals("Eight", Rank.EIGHT.getRankWord());
    }
    
    @Test
    public void testGetRankWord9() {
        assertEquals("Nine", Rank.NINE.getRankWord());
    }
    
    @Test
    public void testGetRankWord10() {
        assertEquals("Ten", Rank.TEN.getRankWord());
    }
    
    @Test
    public void testGetRankWordJack() {
        assertEquals("Jack", Rank.JACK.getRankWord());
    }
    
    @Test
    public void testGetRankWordQueen() {
        assertEquals("Queen", Rank.QUEEN.getRankWord());
    }
    
    @Test
    public void testGetRankWordKing() {
        assertEquals("King", Rank.KING.getRankWord());
    }
    
    @Test
    public void testIsCourtRank() {
        System.out.println("isCourtRank");
        assert !Rank.ACE.isCourtRank() : "Aces are not court cards";
        assert !Rank.TWO.isCourtRank() : "Twos are not court cards";
        assert !Rank.THREE.isCourtRank() : "Threes are not court cards";
        assert !Rank.FOUR.isCourtRank() : "Fours are not court cards";
        assert !Rank.FIVE.isCourtRank() : "Fives are not court cards";
        assert !Rank.SIX.isCourtRank() : "Sixes are not court cards";
        assert !Rank.SEVEN.isCourtRank() : "Sevens are not court cards";
        assert !Rank.EIGHT.isCourtRank() : "Eights are not court cards";
        assert !Rank.NINE.isCourtRank() : "Nines are not court cards";
        assert !Rank.TEN.isCourtRank() : "Tens are not court cards";
        assert Rank.JACK.isCourtRank() : "Jacks are court cards";
        assert Rank.QUEEN.isCourtRank() : "Queens are court cards";
        assert Rank.KING.isCourtRank() : "Kings are court cards";
    }
    
}
