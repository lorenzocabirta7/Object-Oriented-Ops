package operaciones

def delete(parsedJson: Map[String, Any], path: List[String]): Map[String, Any] = {
  removePath(parsedJson, path)
}

private def removePath(json: Map[String, Any], path: List[String]): Map[String, Any] = path match {
  case Nil => json
  case key :: Nil => json - key
  case key :: rest =>
    json.get(key) match {
      case Some(innerMap: Map[String, Any]) =>
        json.updated(key, removePath(innerMap, rest))
      case Some(innerList: List[Any]) =>
        val index = rest.head.toInt
        val updatedList = innerList.take(index) ++ innerList.drop(index + 1)
        json.updated(key, updatedList)
      case _ => json
    }
}
