package operaciones

def existsKey(parsedJson: Map[String, Any], key: String): Boolean = parsedJson match {
  case map: Map[String, Any] => map.contains(key)
  case _ => false
}
