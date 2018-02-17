package grammar

/**
 * `RightHandSide` is an extractor for the right-hand side of a non-unit
 * production rule of a context-free grammar in Chomsky normal form.
 *
 * @author Spencer Hubbard
 */
object RightHandSide {
  // injection method
  def apply(x: String, y: String): String =
    x + " " + y

  // extraction method
  def unapply(rhs: String): Option[(String, String)] = {
    val parts = rhs split " "
    if (parts.length == 2)
      Some(parts(0), parts(1))
    else
      None
  }

  // TODO: write test for duality of injection and extraction methods
}
