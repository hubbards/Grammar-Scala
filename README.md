# grammar-scala

This project contains an implementation of a context-free grammar in Scala.

## CYK Algorithm

The algorithm used to implement the `contains` method of the `Grammar` class is
the Cocke–Younger–Kasami ([CYK][cyk]) algorithm, which is a dynamic programming
algorithm. A `Map` is used to memoize solutions to subproblems. If `i` and `j`
are indices of a sub-word and `a` is a variable (non-terminal symbol), then we
memoize the key-value pair `(i, j, a) -> true` if and only if the sub-word from
symbol `i` to symbol `j` is derivable from `a`. A map is used so that we may
lookup the solution to previously computed subproblems in constant time. The
solution to the problem is the value associated with the key `(1, n, s)` where
`n` is the length of the word and `s` is the start symbol.

## Instructions

To use the program, you must first have a text file that defines a grammar in
the format described in the documentation for the factory method of the
`Grammar` companion object. Then you simply construct a `Grammar` object by
passing the file name to the factory method. You may call the `contains` and
`derivation` methods on the Grammar returned by the factory method. Please see
the documentation for the `contains` and `derivation` methods for more details.
`GrammarApp` is a complete example application that uses the `Grammar` class.

[cyk]: https://en.wikipedia.org/wiki/CYK_algorithm
