package operaciones

def addItem(parsedJson: Map[String, Any], path: List[String], item: String) = {
  addItemAtPath(parsedJson, path, item)
}

private def addItemAtPath(json: Any, path: List[String], item: String): Any = path match {
  case Nil => json
  case head :: Nil => json match {
    case map: Map[String, Any] =>
      map.get(head) match {
        case Some(list: List[Any]) => map + (head -> (list :+ item))
        case _ => map
      }
    case _ => json
  }
  case head :: tail => json match {
    case map: Map[String, Any] =>
      val res = addItemAtPath(map.getOrElse(head, Map.empty[String, Any]), tail, item)
      map + (head -> res)
    case _ => json
  }
}
