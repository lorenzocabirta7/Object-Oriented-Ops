package operaciones

def existsKeyRec(parsedJson: Map[String, Any], key: String) = {
  verifyKeyRec(parsedJson, key)
}

private def verifyKeyRec(json: Any, key: String): Boolean = json match {
  case map: Map[String, Any] =>
    map.contains(key) || map.values.exists(verifyKeyRec(_, key))
  case list: List[Any] =>
    list.exists(verifyKeyRec(_, key))
  case _ => false
}
 
