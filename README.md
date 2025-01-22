# The blackjack, or 21, game

The game is ready to play on the console or terminal (text only), limited to a 
dealer and only one player. I am working on a graphical user interface for this, 
using Java Swing. I also plan to do a card server, so as to enable the game as a 
website.

There is for now no option to split a pair, double down nor make an insurance 
bet.

If you're interested in participating in Hacktoberfest 2025, see 
[CONTRIBUTING.md](CONTRIBUTING.md). Follow those guidelines to ensure not just 
that your contribution is accepted for Hacktoberfest, but also that it persists 
in the main branch after Hacktoberfest.

For the rules of blackjack as I understand them, see 
[GameRules.md](GameRules.md).

This project started out as a Java 8 project. However, I've decided it's now 
going to be a Java 21 project. Seems kind of appropriate, doesn't it?

The main test dependency is JUnit 4. On January 21, 2025, I added TestFrame 1.0 
so that I can use `assertThrows()` in some tests.

## Sample plays of the console applicaton

### Win with natural blackjack

```
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes 
blackjack.BlackJack

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
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes 
blackjack.BlackJack

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
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes 
blackjack.BlackJack

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
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes 
blackjack.BlackJack

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
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes 
blackjack.BlackJack

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
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes 
blackjack.BlackJack

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
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes 
blackjack.BlackJack

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
C:\Users\AL\Documents\NetBeansProjects\BlackJack\build>java -cp classes 
blackjack.BlackJack

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

TODO: Once splitting enabled, add sample play with split

## Help with gambling problems

Although I intend this software to only be used with play money, I suppose I 
should still give phone numbers for problem gambling hotlines. The number for 
the National Problem Gambling Helpline (United States) is 1 (800) 522-4700. In 
Michigan, you can call 1 (800) 270-7117. There's also the website 
https://www.dontregretthebet.org/ from the Michigan Gaming Control Board.
