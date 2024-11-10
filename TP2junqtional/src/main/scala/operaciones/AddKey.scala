package operaciones

import scala.util.parsing.json._

def addKey(parsedJson: String, path: List[String], key: String, value: Any) = {
  val json = JSON.parseFull(parsedJson).getOrElse(Map.empty[String, Any]) // no estoy seguro
  val updatedJson = addKeyAtPath(json, pathList, key, value)
}

private def addKeyAtPath(json: Any, path: List[String], key: String, value: Any) = path match {
  case Nil => json
  case head :: Nil => json match {
    case map: Map[String, Any] =>
      map + (head -> (map.getOrElse(head, Map.empty[String, Any]).asInstanceOf[Map[String, Any]] + (key -> value)))
    case _ => json
  }
  case head :: tail => json match {
    case map: Map[String, Any] =>
      val res = addKeyAtPath(map.getOrElse(head, Map.empty[String, Any]), tail, key, value)
      map + (head -> res)
    case _ => json
  }
}


