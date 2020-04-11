# blackjack
The blackjack, or 21, game

At first this will be a console application limited to a dealer and only one player.

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