%color(+color)
color("R").
color("B").

% suit(+suit)
suit("♥").
suit("♦").
suit("♣").
suit("♠").

% card(+Number, -Suit, -color)
card("A", Suit, Color)     :- suit(Suit), color(Color).
card(2, Suit, Color)       :- suit(Suit), color(Color).
card(3, Suit, Color)       :- suit(Suit), color(Color).
card(4, Suit, Color)       :- suit(Suit), color(Color).
card(5, Suit, Color)       :- suit(Suit), color(Color).
card(6, Suit, Color)       :- suit(Suit), color(Color).
card(7, Suit, Color)       :- suit(Suit), color(Color).
card(8, Suit, Color)       :- suit(Suit), color(Color).
card(9, Suit, Color)       :- suit(Suit), color(Color).
card(0, Suit, Color)       :- suit(Suit), color(Color).
card("J", Suit, Color)     :- suit(Suit), color(Color).
card("Q", Suit, Color)     :- suit(Suit), color(Color).
card("K", Suit, Color)     :- suit(Suit), color(Color).

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
checkOrderByValue([(N,_)]).
checkOrderByValue([(N1,_), (N2,_) | T]) :- integer(N1),
																			integer(N2),
																			X is N1 + 1,
                                      N2 =:= X, 
                                      checkOrderByValue([(N2,_) | T]).
                            
% validationQuarter(+Cards)
validationQuarter(L) :-
 												lengthList(L, X), X >= 3, X =< 4,
                        sameNumber(L),
                        differentSuit(L).


% validationChain(+Cards)
validationChain(L) :- lengthList(L, X), X >= 3, X =< 14,
                      sameSuit(L),
                      endSequence(L),
                      checkOrderByValue(L).

% quicksort(+ListToOrder, -SortedList) 
quicksort([],[]).
quicksort([(X,Sx)|Xs],Ys):-
												partition(Xs,(X,Sx),Ls,Bs),
												quicksort(Ls,LOs),
												quicksort(Bs,BOs),
												append(LOs,[(X,Sx)|BOs],Ys).
												
partition([],_,[],[]).
partition([(X,Sx)|Xs],(Y,Sy),[(X,Sx)|Ls],Bs):- X<Y, !, partition(Xs,(Y,Sy),Ls,Bs).
partition([(X,Sx)|Xs],(Y,Sy),Ls,[(X,Sx)|Bs]):- partition(Xs,(Y,Sy),Ls,Bs).

















