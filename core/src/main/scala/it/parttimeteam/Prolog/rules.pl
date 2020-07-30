% Game suit
suit(hearts, "♥").
suit(diamond,"♦").
suit(clubs,  "♣").
suit(spades, "♠").

% Card-start of the game
startHand(13).

% Cards
% number | seed | points(?)
card(1, X, Y) :- suit(X, Y).
card(2, X, Y) :- suit(X, Y).
card(3, X, Y) :- suit(X, Y).
card(4, X, Y) :- suit(X, Y).
card(5, X, Y) :- suit(X, Y).
card(6, X, Y) :- suit(X, Y).
card(7, X, Y) :- suit(X, Y).
card(8, X, Y) :- suit(X, Y).
card(9, X, Y) :- suit(X, Y).
card(10, X, Y) :- 	suit(X, Y).
card(11, X, Y) :- 	suit(X, Y).
card(12, X, Y) :- 	suit(X, Y).
card(13, X, Y) :- 	suit(X, Y).
