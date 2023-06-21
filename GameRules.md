## The rules of blackjack, as I understand them

The concept seems simple enough. You make a wager. The dealer gives you cards 
("hit") until you want no more cards ("stand") or you win by having a score of 
21 or you lose by going over 21 ("going bust").

The court cards (J&#9824;, Q&#9824;, K&#9824;, J&#9829;, etc.) are valued at 10 
each. Aces are valued at 1 or 11 at the player's discretion (though in practice 
it's assumed the player wants them valued to win).

If you win, the dealer pays up. If you lose, the dealer collects your wager. 
Simple, right? Well, there are plenty of subtleties that threaten to make a 
blackjack program into a mess of heavily indented nested If statements (the 
current state of the console application source).

After the players make their initial wagers, the dealer gives each player two 
cards face up. The dealer also gets two cards, but one of them is face down. 
However, in British casinos, the dealer at first only gets one card face up (see 
[Variants.md](Variants.md) for more information on different ways of playing 
blackjack).

If the dealer's face-up card is an Ace, players may make side bets ("insurance") 
that the dealer's face-down card is a Ten or a court card. Insurance is not yet 
implemented in the console application.

When a player's first two cards add up to 9, 10 or 11, that player may make 
another wager equal to their original wager ("doubling down"). The dealer then 
gives that player a card face down, which stays face down until other bets are 
settled. Doubling down is also not yet implemented in the console application.

If a player's first two cards are a pair of equal rank (e.g., 7&#9830; and 
7&#9827;), the player may split them into separate hands. Some casinos don't 
allow certain splits, and other casinos allow some splits of cards of unequal 
rank. Although the `Hand` class has support for most splits of cards of equal 
rank, this is not yet used in any form in the console application.

At first I misunderstood wagers for splits. Suppose that you have wagered $100 
and you decide to split your hand. I mistakenly thought that then both of your 
hands would have $50 wagers. But I've been told that in such a case, the player 
is expected to put a new wager equal to the original wager on the split off 
hand. That is, the player is expected to put more money on the table. So in the 
example, both of your hands would have $100 wagers each, not $50 each.

If any player hits 21 from the first two cards, they have a "natural" blackjack, 
and the dealer should pay 3/2 times the player's wager. However, some players 
use the term "blackjack" alone to mean natural blackjack, and they don't regard 
other combinations that add up to 21 (such as three Sevens) as "blackjack."

For example, you wager $100 on a hand and the dealer gives you an Ace and a 
Queen, that's natural blackjack and the dealer should pay you $150.

It's not possible to go bust from the first two cards. Most likely no player has 
a natural blackjack at this point, so they must decide either to get more cards 
to try for 21 or stick to their cards in the hopes that the dealer goes bust. 
Players who bust lose their wager even if the dealer also goes bust. Players who 
hit 21 at this stage are paid their wager by the dealer.

At this point, if I'm understanding correctly, the dealer reveals their 
face-down card. If the dealer is under 17, the dealer must take cards until 
going over 16, even though this risks going bust. Once over 17, the dealer must 
stand.

If the dealer stands, the dealer pays up players who have a higher score without 
going over 21, and collects the wagers of players with a lower score. And if the 
dealer and a player have the same score without going over, it's a stand-off, 
and no wager is paid nor collected.

### References

* Bicycle Cards page for blackjack. 
https://bicyclecards.com/how-to-play/blackjack/
* Belinda Levez, *How to Win at Casino Games*, Chapters 4 and 5. London: Teach 
Yourself Books (1997)
