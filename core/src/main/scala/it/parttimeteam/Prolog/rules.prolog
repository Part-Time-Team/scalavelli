% suit(+suit)
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

% startHand(+NumberCard)
startHand(13).

% sameElementList(+ListSuit, +Suit)
sameElementList([], Suit).
sameElementList([H|T], Suit) :- H \== Suit, sameElementList(T, Suit).

% validationTris(+Cards)
validationTris([(Number, Suit)], ListSuit).
validationTris([(Number1, Suit1), (Number2, Suit2) | T], ListSuit) :-
 								                                    integer(Number1),
 													                integer(Number2),
 													                Number1 == Number2,
 												                    sameElementList(ListSuit, Suit1),
 													                append(ListSuit, [Suit1], NewListSuit),
													                validationTris([(Number2, Suit2) | T], NewListSuit).





