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

% lengthList(+List, -LengthList)
lengthList([], 0).
lengthList([(_,_)|T],X) :- lengthList(T,N), X is N+1.

% sameNumber(+List)
sameNumber([(N,_)]).
sameNumber([(N1,_), (N2,_) | T]) :-  integer(N1),
                           					 integer(N2),
                           					 N1 =:= N2,
                           					 sameNumber([(N2,_)| T]).

% sameSuit(+List)
sameSuit([(_,S)]).
sameSuit([(_,S1), (_,S2) | T]) :- S1 == S2,
                           			  sameSuit([(_,S2)| T]).

% sameElementList(+ListSuit, +Suit)
sameElementList([], Suit).
sameElementList([H|T], Suit) :- H \== Suit,
                                sameElementList(T, Suit).

% differentSuit(+List)
differentSuit([], Suit).
differentSuit([(_, S1), (_, S2) |T]) :- S1 \== S2,
                                        append([], [S1, S2], ListSuit),
                                        differentSuit(T, ListSuit).
% differentSuit(+List, +Suit)                                                               
differentSuit([(_, Suit) |T], ListSuit) :- sameElementList(ListSuit, Suit),
                                           append(ListSuit, [Suit], NewListSuit),
                                           differentSuit(T, NewListSuit).

% endSequence(+Cards)
endSequence([(N,_)]).
endSequence([(N1,_), (N2,_) | T]):- ( N1 =:= 13, N2 =:= 14 -> lengthList(T, S), S =:= 0
																		  ; endSequence([(N2,_) | T])
																		).

% orderByValue(+List)
orderByValue([(N,_)]).
orderByValue([(N1,_), (N2,_) | T]) :- integer(N1),
																			integer(N2),
																			X is N1 + 1,
                                      N2 =:= X, 
                                      orderByValue([(N2,_) | T]).
                            
% validationQuarter(+Cards)	
validationQuarter(L) :- lengthList(L, X), X >= 3, X =< 4,
                        sameNumber(L),
                        differentSuit(L).

% validationSequence(+Cards)
validationSequence(L) :- lengthList(L, X), X >= 3, X =< 14,
                         sameSuit(L),
                         endSequence(L),
                         orderByValue(L).
