package operaciones

def addKey(parsedJson: Map[String, Any], path: List[String], key: String, value: Any) = {
  addKeyAtPath(parsedJson, path, key, value)
}

private def addKeyAtPath(json: Any, path: List[String], key: String, value: Any): Any = path match {
  case Nil => json
  case head :: Nil => json match {
    case map: Map[String, Any] =>
      val clave = head match {
        case "" => key
        case _ => head
      }
      val temporal = map.getOrElse(clave, Map.empty[String, Any])
      map.getOrElse(clave, Map.empty[String, Any]) match {
        case lista : List[_] => map + (clave -> (lista ::: List(value)))
        case vacio: Map[_, _] if vacio.isEmpty => map + (clave -> value)
        case _ => map + (clave -> (map.getOrElse(clave, Map.empty[String, Any]).asInstanceOf[Map[String, Any]] + (key -> value)))
      }
    case _ => json
  }
  case head :: tail => json match {
    case map: Map[String, Any] =>
      val res = addKeyAtPath(map.getOrElse(head, Map.empty[String, Any]), tail, key, value)
      map + (head -> res)
    case _ => json
  }
}

