package operaciones

def edit(parsedJson: Map[String, Any], path: List[String], value: String) = {
  editAtPath(parsedJson, path, value).asInstanceOf[Map[String, Any]]
}

private def editAtPath(json: Any, path: List[String], value: String): Any = path match {
  case Nil => json
  case head :: Nil => json match {
    case map: Map[String, Any] => map + (head -> value)
    case list: List[Any] if head.forall(_.isDigit) =>
      val index = head.toInt
      list.updated(index, value)
    case _ => json
  }
  case head :: tail => json match {
    case map: Map[String, Any] =>
      val res = editAtPath(map.getOrElse(head, Map.empty[String, Any]), tail, value)
      map + (head -> res)
    case list: List[Any] if head.forall(_.isDigit) =>
      val index = head.toInt
      val res = editAtPath(list(index), tail, value)
      list.updated(index, res)
    case _ => json
  }
}

