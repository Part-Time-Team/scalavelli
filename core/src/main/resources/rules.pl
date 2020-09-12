%color(+Color)
color("R").
color("B").

% suit(+Suit)
suit("H").
suit("D").
suit("C").
suit("S").

% priority(+Suit, -Value)
priority("H", X) 	:- X is 1.
priority("D", X)    :- X is 2.
priority("C", X) 	:- X is 3.
priority("S", X) 	:- X is 4.

% card(+Number, -Suit, -color)
card(1, Suit, Color)    :- suit(Suit), color(Color).
card(2, Suit, Color)    :- suit(Suit), color(Color).
card(3, Suit, Color)    :- suit(Suit), color(Color).
card(4, Suit, Color)    :- suit(Suit), color(Color).
card(5, Suit, Color)    :- suit(Suit), color(Color).
card(6, Suit, Color)    :- suit(Suit), color(Color).
card(7, Suit, Color)    :- suit(Suit), color(Color).
card(8, Suit, Color)    :- suit(Suit), color(Color).
card(9, Suit, Color)    :- suit(Suit), color(Color).
card(10, Suit, Color)   :- suit(Suit), color(Color).
card(11, Suit, Color)   :- suit(Suit), color(Color).
card(12, Suit, Color)   :- suit(Suit), color(Color).
card(13, Suit, Color)   :- suit(Suit), color(Color).

% lengthList(+List, -LengthList)
lengthList([], 0).
lengthList([(_,_)|T],X) :- lengthList(T,N), X is N+1.

% sameNumber(+List)
sameNumber([(_,_,_)]).
sameNumber([(N1,_,_), (N2,_,_) | T]) :- integer(N1),
                           			    integer(N2),
                           			    N1 =:= N2,
                           			    sameNumber([(N2,_,_)| T]).

% sameSuit(+List)
sameSuit([(_,_,_)]).
sameSuit([(_,S1,_), (_,S2,_) | T]) :- S1 == S2,
                           		      sameSuit([(_,S2,_)| T]).

% sameElementList(+ListSuit, +Suit)
sameElementList([], _).
sameElementList([H|T], Suit) :- H \== Suit,
                                sameElementList(T, Suit).

% differentSuit(+List)
differentSuit([], _).
differentSuit([(_, S1, _), (_, S2, _) |T]) :- S1 \== S2,
                                              append([], [S1, S2], ListSuit),
                                              differentSuit(T, ListSuit).
% differentSuit(+List, +Suit)                                                               
differentSuit([(_, Suit, _) |T], ListSuit) :- sameElementList(ListSuit, Suit),
                                              append(ListSuit, [Suit], NewListSuit),
                                              differentSuit(T, NewListSuit).

% endSequence(+Cards)
endSequence([(_,_,_)]).
endSequence([(13,_,_), (14,_,_) | T]):- lengthList(T, S), S =:= 0.
endSequence([(_,_,_), (N2,_,_) | T]) :- endSequence([(N2,_,_) | T]).


% orderByValue(+List)
checkOrderByValue([(_,_,_)]).
checkOrderByValue([(N1,_,_), (N2,_,_) | T]) :-  integer(N1),
										        integer(N2),
											    X is N1 + 1,
                                      		    N2 =:= X,
                                      		    checkOrderByValue([(N2,_,_) | T]).
                            
% validationQuarter(+Cards)
validationQuarter(L) :- lengthList(L, X), X >= 3, X =< 4,
                        sameNumber(L),
                        differentSuit(L).

% validationChain(+Cards)
validationChain(L) :- lengthList(L, X), X >= 3, X =< 14,
                      sameSuit(L),
                      endSequence(L),
                      checkOrderByValue(L).

% quicksortValue(+ListToOrder, -SortedList) 
quicksortValue([],[]).
quicksortValue([(X,Sx,Cx)|Xs],Ys) :- partitionValue(Xs,(X,Sx,Cx),Ls,Bs),
								     quicksortValue(Ls,LOs),
								     quicksortValue(Bs,BOs),
								     append(LOs,[(X,Sx,Cx)|BOs],Ys).
												
partitionValue([],_,[],[]).
partitionValue([(X,Sx,Cx)|Xs],(Y,Sy,Cy),[(X,Sx,Cx)|Ls],Bs):- X<Y,
                                                             !,
                                                             partitionValue(Xs,(Y,Sy,Cy),Ls,Bs).
partitionValue([(X,Sx,Cx)|Xs],(Y,Sy,Cy),Ls,[(X,Sx,Cx)|Bs]):- partitionValue(Xs,(Y,Sy,Cy),Ls,Bs).

% quicksortSuit(+ListToOrder, -SortedList) 
quicksortSuit([],[]).
quicksortSuit([(X,Sx,Cx)|Xs],Ys):- partitionSuit(Xs,(X,Sx,Cx),Ls,Bs),
								   quicksortSuit(Ls,LOs),
								   quicksortSuit(Bs,BOs),
								   append(LOs,[(X,Sx,Cx)|BOs],Ys).
												
partitionSuit([],_,[],[]).
partitionSuit([(X,Sx,Cx)|Xs],(Y,Sy,Cy),[(X,Sx,Cx)|Ls],Bs):- priority(Sx,Px),
                                                            priority(Sy,Py),
                                                            compareCard((X,Px),(Y,Py)),
                                                            !,
                                                            partitionSuit(Xs,(Y,Sy,Cy),Ls,Bs).
partitionSuit([(X,Sx,Cx)|Xs],(Y,Sy,Cy),Ls,[(X,Sx,Cx)|Bs]):- partitionSuit(Xs,(Y,Sy,Cy),Ls,Bs).

%compareCard(+Tuple1, +Tuple2)
compareCard((_, S1),(_, S2)):- S1<S2.
compareCard((N1, S1),(N2, S2)):- S1 =:= S2, N1<N2.





















