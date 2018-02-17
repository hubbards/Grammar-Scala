import grammar.Grammar

/**
 * `GrammarApp` is an application that demonstrates how to use the `Grammar`
 * class.
 *
 * @author Spencer Hubbard
 */
object GrammarApp extends App {
  // helper method
  def helper(grammar: Grammar, ws: List[String]*): String = {
    var s = ""
    var i = 0
    for (w <- ws) {
      val temp = grammar contains w
      i += 1
      s += s"word $i in language? $temp"
      if (temp)
        s += s", derivation: ${grammar derivation w}\n"
      else
        s += "\n"
    }
    s + "\n"
  }

  val grammar1 = Grammar("./resources/Test1.txt")
  val grammar2 = Grammar("./resources/Test2.txt")
  val word1 = List("b", "a", "a", "b", "a")
  val word2 = List("a", "a", "a", "a")
  val word3 = List("b", "a", "b", "b", "c")
  val word4 = List("a", "a", "a", "a", "b", "b")

  println(s"grammar 1:\n${helper(grammar1, word1, word2)}")
  println(s"grammar 2:\n${helper(grammar2, word3, word4)}")
}
