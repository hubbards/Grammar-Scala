package grammar

/**
 * `ProductionRule` is an extractor for a production rule of a grammar.
 *
 * @author Spencer Hubbard
 */
object ProductionRule {
  // TODO: write injection method (optional)

  // extraction method
  def unapplySeq(production: String): Option[(String, Seq[String])] = {
    val parts = production split " -> "
    if (parts.length == 2)
      Some(parts(0), parts(1) split " \\| ")
    else
      None
  }
}
