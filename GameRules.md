## The rules of blackjack, as I understand them

This document describes the game as I believe it is usually played in American 
casinos. I have never actually played blackjack in a casino or mock casino, 
though I have observed a few hands at an actual casino. The content of this 
document is therefore mostly based on what I've read in books and seen on 
YouTube videos and TV (mostly dramas but also some comedies).

The blackjack table has a distinctive design that can't be used for other games. 
The table has verbiage giving one or two rules of the game and some information 
about payouts. Some blackjack variants require variant tables, sometimes 
including the name of the variant. See [Variants](Variants.md) for variations on 
the game.

Generally, blackjack is played with multiple decks of standard playing cards 
shuffled together and placed in a "shoe" for the dealer to deal from. The Jokers 
are removed (though there are a few variants using Jokers).

The concept of blackjack seems simple enough. You make a wager. The dealer gives 
you cards ("hit") until you want no more cards ("stand") or you win by having a 
score of 21 or you lose by going over 21 ("going bust"). You can also win with a 
score of 18, 19 or 20 if the dealer had to stand on 17.

The court cards (J&#9824;, Q&#9824;, K&#9824;, J&#9829;, etc.) are valued at 10 
each. Aces are valued at 1 or 11 at the player's discretion, though in practice 
it's assumed the player wants them valued to win.

For example, if a player gets two Aces, the dealer assumes the player wants one 
Ace valued at 11 and the other valued at 1 for a total of 12, rather than both 
valued at 11 for a total of 22, which would bust and the player would lose.

If you win, the dealer pays up. If you lose, the dealer collects your wager. 
There usually are other players at the table, but you're not in competition with 
them. The settlement of another player's wager at the same table does not affect 
the settlement of your wager other than that settling other players' wagers may 
delay the dealer from settling your wager. However, dealers usually work fast, 
so the short delays are not worth complaining about.

After the players make their initial wagers, the dealer gives each player two 
cards face up. The dealer also gets two cards, but one of them is face down. 
However, in British casinos, the dealer at first only gets one card face up and 
doesn't get a second card until the players don't want any more cards (see 
[European blackjack](Variants.md#european-blackjack) for more information on 
this variant).

If the dealer's face-up card is an Ace, players may make side bets ("insurance") 
that the dealer's face-down card is a Ten or a court card. As far as I know, 
insurance always pays 2 to 1, and every image of a blackjack table I have 
scrutinized has words to that effect. Insurance is not yet implemented in the 
console application.

When a player's first two cards add up to 9, 10 or 11, that player may make 
another wager equal to their original wager ("doubling down"). The dealer then 
gives that player a card face down, which stays face down until other bets are 
settled. Doubling down is also not yet implemented in the console application.

If a player's first two cards are a pair of equal rank (e.g., 7&#9830; and 
7&#9827;), the player may split them into separate hands. Some casinos don't 
allow certain splits, and other casinos allow some splits of cards of unequal 
rank. Although the `Hand` class has support for most splits of cards of equal 
rank, this is not yet used in any form in the console application.

Only the dealer may touch the cards, and that includes cards that have been 
dealt to the players. To request a hand be split, a player says "split," or 
perhaps makes some hand signal. The dealer moves the two cards apart and then 
deals one card to each of the new hands.

From there, game play proceeds the same as if the two hands were held by 
different players, though some casinos place limits on how many times a player 
may split. The dealer is never allowed to split the dealer's hand.

At first I misunderstood wagers for splits. Suppose that you have wagered $100 
and you decide to split your hand. I mistakenly thought that then both of your 
hands would have $50 wagers. But I've been told that in such a case, the player 
is expected to put a new wager equal to the original wager on the split off 
hand. That is, the player is expected to put more money on the table. So in the 
example, both of your hands would have $100 wagers each, not $50 each. As a 
consequence of this, a player who wants to split a pair may not be able to if he 
or she does not have enough money for the additional wager.

If any player hits 21 from the first two cards, they have a "natural" blackjack, 
and the dealer should pay 3/2 times the player's wager, provided the dealer does 
not also have natural blackjack. So if the dealer's face up card is an Ace, a 
Ten or a court card, the dealer can't pay out just yet.

Some players use the term "blackjack" alone to mean natural blackjack, and they 
don't regard other combinations that add up to 21 (such as three Sevens) as 
"blackjack."

Let's say you wager $100 on a hand and the dealer gives you an Ace and a Queen. 
That's natural blackjack and the dealer should pay you $150. However, some 
casinos have reduced the payout to 6/5, so in this example you would only get 
$120.

The suits of the cards only matter to the extent that blackjack is played with 
the same kind of cards used for most variants of poker. A natural blackjack with 
cards of the same suit wins just as much as a natural blackjack with two cards 
of two different suits.

It's not possible to go bust from the first two cards. Most likely no player has 
a natural blackjack at this point, so they must decide either to get more cards 
to try for 21 or stick to their cards in the hopes that the dealer goes bust or 
winds up with a lower score.

A player says "hit me" or taps the table to ask the dealer for another card. 
There's also a hand signal to indicate the dealer should not give the player 
another card.

Players who bust lose their wager even if the dealer also goes bust. Players who 
hit 21 at this stage are paid their wager by the dealer, subject to the dealer 
having or not having the possibility of natural blackjack.

At this point, if I'm understanding correctly, the dealer reveals their 
face-down card. If the dealer's score is under 17, the dealer must take cards 
until going over 16, even though this risks going bust. Once reaching or over 
17, the dealer must stand.

There's a lot of variability on what happens if the dealer has exactly 17, so 
it's important for players to pay attention to the verbiage on the table. Some 
tables state "Dealer must stand on all 17" or "Dealer must draw to 16, and stand 
on all 17s". Some tables state "Dealer must stand on hard 17 or soft 18". This 
might not be a complete listing even ignoring variations of wording or 
punctuation with the same meaning.

A hand is soft if it contains any Ace valued at 1, otherwise it is a hard hand. 
So if the dealer has an Ace and a Six, or a ten card and a Seven, he or she must 
stand. But if the table requires a hard 17 for the dealer to stand, and a dealer 
has, for example, two Eights and an Ace, then he or she must draw another card 
(remember that the dealer is not allowed to split).

If the dealer stands, the dealer pays up players who have a higher score without 
going over 21, and collects the wagers of players with a lower score. And if the 
dealer and a player have the same score without going over, it's a stand-off, 
and no wager is paid nor collected.

### References

* Bicycle Cards page for blackjack. 
https://bicyclecards.com/how-to-play/blackjack/
* Belinda Levez, *How to Win at Casino Games*, Chapters 4 and 5. London: Teach 
Yourself Books (1997)
