package operaciones

def getValueAtPath(parsedJson: Map[String, Any], path: String): Option[Any] = {
  val pathList = path.stripPrefix(".").split("\\.").toList
  get(parsedJson, pathList)
}
 
