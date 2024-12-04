package operaciones

def getValueAtPath(parsedJson: Map[String, Any], path: String): Any = {
  val pathList = path.stripPrefix(".").split("\\.").toList
  get(parsedJson, pathList)
}
 
