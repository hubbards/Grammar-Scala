package grammar

import java.io.File
import java.util.Scanner

import scala.collection.mutable.{ Set, Map, HashMap, MultiMap }

/**
 * `Grammar` represents a context-free grammar in Chompsky normal form.
 *
 * Symbols are strings of ASCII characters except for the white space character
 * and comma. The symbols "->" and "|" are not allowed to be terminal symbols or
 * variables.
 *
 * @author Spencer Hubbard
 */
class Grammar private (
    start: String,
    up: List[(String, String)],
    np: List[(String, String)],
    mm: HashMap[String, Set[String]]) {
  // uses CYK algorithm
  private def memoize(word: List[String]): Map[(Int, Int, String), Boolean] = {
    // bottom-up computation of memo
    val memo = Map[(Int, Int, String), Boolean]()
    for {
      i <- 0 until word.length
      (lhs, rhs) <- up
    } {
      val key = (i, i, lhs)
      val value = rhs == word(i)
      memo(key) = value
    }
    for {
      l <- 2 to word.length
      i <- 0 to word.length - l
      j = i + l - 1
      k <- i until j
      (lhs, RightHandSide(a, b)) <- np
    } {
      val key = (i, j, lhs)
      val value = memo(i, k, a) && memo(k + 1, j, b)
      memo(key) = if (memo contains key) memo(key) || value else value
    }
    memo
  }

  /**
   * Checks if a given word is a sentence in the language for this grammar.
   *
   * @param word the given word.
   * @return `true` if the word `word` is a sentence in the language for this
   * grammar, otherwise `false`.
   */
  def contains(word: List[String]): Boolean =
    memoize(word)(0, word.length - 1, start)

  /**
   * Finds a left-most derivation for a given word.
   *
   * @param word the given word.
   * @return list of productions in a left-most derivation for the word `word`.
   */
  def derivation(word: List[String]): List[(String, String)] = {
    val memo = memoize(word)
    // helper function; recursive back-tracking through memo
    def helper(i: Int, j: Int, x: String): List[(String, String)] =
      if (i == j) {
        List((x, word(i)))
      } else {
        val temp =
          for {
            k <- i until j
            RightHandSide(y, z) <- mm(x) if memo(i, k, y) && memo(k + 1, j, z)
          } yield (k, y, z)
        val (k, y, z) = temp(0)
        (x, RightHandSide(y, z)) :: helper(i, k, y) ::: helper(k + 1, j, z)
      }
    helper(0, word.length - 1, start)
  }
}

/**
 * Factory object for `Grammar` class.
 *
 * @author Spencer Hubbard
 */
object Grammar {
  /**
   * Factory method for `Grammar` class.
   *
   * The first line of the text file should contain a comma separated list of
   * terminal symbols. The second line of the text file should contain a comma
   * separated list of variables. The third line of the text file should contain
   * the start symbol. Each of the remaining lines of the text file should be
   * a list of productions. The left-hand side and right-hand sides of a list of
   * productions should be separated by the string " -> " and right-hand sides
   * should be separated by the string " | ". The symbols on the right-hand side
   * of a production should be separated by a single space.
   *
   * @param name the given name of a text file that defines a grammar.
   * @return the grammar defined by the text file with name `name`.
   */
  def apply(name: String): Grammar = {
    // helper function extracts productions from given line
    def helper(line: String): Seq[(String, String)] =
      line match {
        case ProductionRule(lhs, rhs @ _*) => rhs map ((lhs, _))
        case _ => Nil
      }
    // read grammar file
    val input = new Scanner(new File(name))
    val terminals = input.nextLine() split ","
    val variables = input.nextLine() split ","
    val start = input.nextLine()
    var temp = List[(String, String)]()
    while (input.hasNextLine()) { temp ++= helper(input.nextLine()) }
    // TODO: verify that grammar is in Chompsky normal form
    // instantiate grammar
    val (up, np) =
      temp partition { case (_, rhs) => terminals contains rhs }
    val mm =
      (new HashMap[String, Set[String]] with MultiMap[String, String] /: temp) {
        (m, p) => m.addBinding(p._1, p._2)
      }
    new Grammar(start, up, np, mm)
  }
}
