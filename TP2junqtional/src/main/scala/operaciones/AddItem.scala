package operaciones

import scala.util.parsing.json._

def addItem(parsedJson: String, path: List[String], item: String) = {
  val json = JSON.parseFull(parsedJson).getOrElse(Map.empty[String, Any]) // no estoy seguro
  val resultado = addItemAtPath(json, path, item)
}

private def addItemAtPath(json: Any, path: List[String], item: String) = path match {
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
