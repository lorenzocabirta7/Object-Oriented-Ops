package operaciones

def depth(parsedJson: Map[String, Any], level: Int) = {
  obtainDepth(parsedJson, 0, level)
}

private def obtainDepth(json: Any, currentLevel: Int, targetLevel: Int): List[Any] = {
  if (currentLevel == targetLevel) {
    json match {
      case map: Map[String, Any] => map.values.toList
      case list: List[Any] => list
      case _ => List(json)
    }
  } else {
    json match {
      case map: Map[String, Any] => map.values.flatMap(obtainDepth(_, currentLevel + 1, targetLevel)).toList
      case list: List[Any] => list.flatMap(obtainDepth(_, currentLevel + 1, targetLevel))
      case _ => List.empty
    }
  }
}
