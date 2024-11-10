package operaciones

def existsKey(parsedJson: JsonValue, key: String): Boolean = parsedJson match {
  case JsonObject(entries) => entries.contains(key)
  case _ => false
}
