package operaciones

def get(parsedJson: String, path: List[String]) = path match {
  case Nil => Some(parsedJson)
  case head :: tail => parsedJson match {
    case JsonObject(fields) => fields.get(head).flatMap(get(_, tail))
    case JsonArray(items) =>
      if (head.forall(_.isDigit)) {
        val index = head.toInt
        if (index >= 0 && index < items.length) get(items(index), tail)
        else None
      } else None
    case _ => None
  }
}