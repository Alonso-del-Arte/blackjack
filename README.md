# blackjack
The blackjack, or 21, game

For now, this is a console application limited to a dealer and only one player. There is for now no option to split a pair, double down nor make an insurance bet.

## The rules of blackjack, as I understand them

The concept seems simple enough. You make a wager. The dealer gives you cards ("hit") until you want no more cards ("stand") or you win by having a score of 21 or you lose by going over 21 ("going bust").

The court cards (J&#9824;, Q&#9824;, K&#9824;, J<span style="color: red">&#9829;</span>, etc.) are valued at 10 each. Aces are valued at 1 or 11 at the player's discretion (though in practice it's assumed the player wants them valued to win).

If you win, the dealer pays up. If you lose, the dealer collects your wager. Simple, right? Well, there are plenty of subtleties that threaten to make a blackjack program into a mess of heavily indented nested If statements (the current state of the console application source).

After the players make their initial wagers, the dealer gives each player two cards face up. The dealer also gets two cards, but one of them is face down. If the dealer's face-up card is an Ace, players may make side bets ("insurance") that the dealer's face-down card is a Ten or a court card. Insurance is not yet implemented in the console application.

When a player's first two cards add up to 9, 10 or 11, that player may make another wager equal to their original wager ("doubling down"). The dealer then gives that player a card face down, which stays face down until other bets are settled. Doubling down is also not yet implemented in the console application.

If a player's first two cards are a pair of equal rank (e.g., 7<span style="color: red">&#9830;</span> and 7&#9827;), the player may split them into separate hands. Some casinos don't allow certain splits, and other casinos allow some splits of cards of unequal rank. Although the `Hand` class has support for most splits of cards of equal rank, this is not yet used in any form in the console application.

If any player hits 21 from the first two cards, they have a "natural" blackjack, and the dealer should pay 3/2 times the player's wager.

It's not possible to go bust from the first two cards. Most likely no player has a natural blackjack at this point, so they must decide either to get more cards to try for 21 or stick to their cards in the hopes that the dealer goes bust. Players who bust lose their wager even if the dealer also goes bust. Players who hit 21 at this stage are paid their wager by the dealer.

At this point, if I'm understanding correctly, the dealer reveals their face-down card. If the dealer is under 17, the dealer must take cards until going over 16, even though this risks going bust. Once over 17, the dealer must stand.

If the dealer stands, the dealer pays up players who have a higher score without going over 21, and collects the wagers of players with a lower score. And if the dealer and a player have the same score without going over, it's a stand-off, and no wager is paid nor collected.

## Sample plays of the console applicaton

### Win with natural blackjack

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes blackjack.BlackJack

BLACKJACK

Enter your wager in whole dollars: $50

Your first card is King of Clubs
Dealer's face-up card is Three of Clubs

Your second card is Ace of Hearts
Your hand's value is 21

Dealer's face-down card is ?????

Congratulations, you have a natural blackjack
*** YOU WIN $75 ****
```

Note the 3/2 payout.

### Win with regular blackjack

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes blackjack.BlackJack

BLACKJACK

Enter your wager in whole dollars: $50

Your first card is Nine of Spades
Dealer's face-up card is Ten of Hearts

Your second card is Two of Hearts
Your hand's value is 11

Dealer's face-down card is ?????

Hit or stand? hit
Your next card is Four of Spades
Your hand's value is 15

Hit or stand? hit
Your next card is Six of Diamonds
Your hand's value is 21

Dealer's face-down card is Nine of Diamonds
Dealer's hand value is 19
*** YOU WIN $50 ****
```

### Win after standing because dealer has lower score

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes blackjack.BlackJack

BLACKJACK

Enter your wager in whole dollars: $50

Your first card is Jack of Diamonds
Dealer's face-up card is Three of Spades

Your second card is Six of Spades
Your hand's value is 16

Dealer's face-down card is ?????

Hit or stand? hit
Your next card is Four of Clubs
Your hand's value is 20

Hit or stand? stand

Dealer's face-down card is Five of Clubs
Dealer's hand value is 8
Dealer takes Jack of Spades
Dealer's hand value is 18

As you have a higher score,
*** YOU WIN $50 ****
```

### Win after standing because dealer went bust

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes blackjack.BlackJack

BLACKJACK

Enter your wager in whole dollars: $50

Your first card is Ten of Clubs
Dealer's face-up card is Two of Spades

Your second card is King of Hearts
Your hand's value is 20

Dealer's face-down card is ?????

Hit or stand? stand

Dealer's face-down card is Queen of Spades
Dealer's hand value is 12
Dealer takes Four of Clubs
Dealer's hand value is 16

Dealer takes Seven of Diamonds
Dealer's hand value is 23

Since you stood and the dealer went bust,
*** YOU WIN $50 ****
```

### Lose after standing

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes blackjack.BlackJack

BLACKJACK

Enter your wager in whole dollars: $50

Your first card is Ace of Diamonds
Dealer's face-up card is Three of Hearts

Your second card is Five of Hearts
Your hand's value is 16

Dealer's face-down card is ?????

Hit or stand? stand

Dealer's face-down card is Nine of Spades
Dealer's hand value is 12
Dealer takes Seven of Clubs
Dealer's hand value is 19

Dealer collects your $50
Better luck next time...
```

### Lose even though the dealer also went bust

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes blackjack.BlackJack

BLACKJACK

Enter your wager in whole dollars: $50

Your first card is Three of Clubs
Dealer's face-up card is Four of Hearts

Your second card is Six of Spades
Your hand's value is 9

Dealer's face-down card is ?????

Hit or stand? hit
Your next card is Four of Clubs
Your hand's value is 13

Hit or stand? hit
Your next card is Jack of Spades
Your hand's value is 23

Dealer's face-down card is Three of Clubs
Dealer's hand value is 7
Dealer takes Nine of Hearts
Dealer's hand value is 16

Dealer takes Eight of Hearts
Dealer's hand value is 24

Even though the dealer also went bust...
Dealer collects your $50
Better luck next time...
```

The House always wins.

### Lose after hitting recklessly

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes blackjack.BlackJack

BLACKJACK

Enter your wager in whole dollars: $50

Your first card is Nine of Hearts
Dealer's face-up card is Five of Diamonds

Your second card is Jack of Diamonds
Your hand's value is 19

Dealer's face-down card is ?????

Hit or stand? hit
Your next card is Three of Clubs
Your hand's value is 22

Dealer's face-down card is Five of Spades
Dealer's hand value is 10
Dealer takes King of Hearts
Dealer's hand value is 20

Dealer collects your $50
Better luck next time...
```

### Tie due to stand-off

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes blackjack.BlackJack

BLACKJACK

Enter your wager in whole dollars: $50

Your first card is Six of Diamonds
Dealer's face-up card is King of Hearts

Your second card is Jack of Hearts
Your hand's value is 16

Dealer's face-down card is ?????

Hit or stand? hit
Your next card is Four of Spades
Your hand's value is 20

Hit or stand? stand

Dealer's face-down card is Ten of Diamonds
Dealer's hand value is 20
Stand-off
```