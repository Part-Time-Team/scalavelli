% suit game
suit("♥").
suit("♦").
suit("♣").
suit("♠").

% card(+Number, -Suit)
card("A", Suit)     :- suit(Suit).
card(2, Suit)       :- suit(Suit).
card(3, Suit)       :- suit(Suit).
card(4, Suit)       :- suit(Suit).
card(5, Suit)       :- suit(Suit).
card(6, Suit)       :- suit(Suit).
card(7, Suit)       :- suit(Suit).
card(8, Suit)       :- suit(Suit).
card(9, Suit)       :- suit(Suit).
card(0, Suit)       :- suit(Suit).
card("J", Suit)     :- suit(Suit).
card("Q", Suit)     :- suit(Suit).
card("K", Suit)     :- suit(Suit).

% Card start of the game
startHand(13).

% getTurn(+(Name, Id), -PLayer)
%getTurn([(Name, Id)| T],PLayer) :- ..


