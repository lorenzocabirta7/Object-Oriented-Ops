package operaciones

def get(parsedJson: Map[String, Any], path: List[String]): Any = path match {
  case Nil => parsedJson
  case head :: tail => parsedJson.get(head) match {
    case Some(value: Map[String, Any]) => get(value, tail)
    case Some(value: List[Any]) if tail.nonEmpty && tail.head.forall(_.isDigit) =>
      val index = tail.head.toInt
      if (index >= 0 && index < value.length) get(value(index).asInstanceOf[Map[String, Any]], tail.tail)
      else None
    case Some(value) if tail.isEmpty => value
    case _ => None
  }
}