package operaciones

import scala.util.parsing.json._

def edit(parsedJson: String, path: List[String], value: String) = {
  val json = JSON.parseFull(parsedJson).getOrElse(Map.empty[String, Any]) // no estoy seguro
  val updatedJson = editAtPath(json, path, value)
}

private def editAtPath(json: Any, path: List[String], value: String) = path match{
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

