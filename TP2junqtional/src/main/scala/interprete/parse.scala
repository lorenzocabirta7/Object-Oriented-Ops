package interprete

def parse(json: String): Any = {
  val trimmed = json.trim
  trimmed.headOption match {
    case Some('{') => parseObjeto(trimmed)._1
    case Some('[') => parseArray(trimmed)._1
    case _ => parseValor(trimmed)._1
  }
}

private def parseObjeto(json: String): (Map[String, Any], String) = {
  val (campos, resto) = parseCampos(json.drop(1).trim)
  (campos, resto.dropWhile(_ != '}').tail.trim)
}

private def parseCampos(restante: String): (Map[String, Any], String) = restante.headOption match {
  case Some('}') => (Map.empty, restante.tail.trim)
  case Some(_) =>
    val (clave, dspClave) = parseString(restante)
    val (_, dspCaracter) = consumeChar(dspClave, ':')
    val (valor, dspValor) = parseValor(dspCaracter)
    val (_, dspComa) = consumeChar(dspValor, ',')
    val (mapaRest, stringRest) = parseCampos(dspComa)
    (mapaRest + (clave -> valor), stringRest)
  case None => (Map.empty, "")
}

private def parseString(json: String): (String, String) = {
  val endIdx = json.indexOf('"', 1)
  (json.substring(1, endIdx), json.substring(endIdx + 1).trim)
}

private def consumeChar(json: String, expected: Char): (Unit, String) =
  json.headOption.filter(_ == expected) match {
    case Some(_) => ((), json.tail.trim)
    case None => ((), json)
  }

private def parseValor(json: String): (Any, String) = json.trim.headOption match {
  case Some('"') => parseString(json)
  case Some('{') => parseObjeto(json)
  case Some('[') => parseArray(json)
  case Some('t') if json.startsWith("true") => (true, json.drop(4).trim)
  case Some('f') if json.startsWith("false") => (false, json.drop(5).trim)
  case Some('n') if json.startsWith("null") => (null, json.drop(4).trim)
  case Some(_) => parseNumber(json)
  case None => (None, "")
}

private def parseArray(json: String): (List[Any], String) = {
  val (items, rest) = parseItems(json.drop(1).trim)
  (items, rest.trim)
}

private def parseItems(remaining: String): (List[Any], String) = remaining.headOption match {
  case Some(']') => (Nil, remaining.tail.trim)
  case Some(_) =>
    val (value, afterValue) = parseValor(remaining)
    val (_, afterComma) = consumeChar(afterValue, ',')
    val (restItems, restString) = parseItems(afterComma)
    (value :: restItems, restString)
  case None => (Nil, "")
}

private def parseNumber(json: String): (Any, String) = {
  val numberEnd = json.indexWhere(c => !c.isDigit && c != '.' && c != '-')
  val numberStr = if (numberEnd == -1) json else json.substring(0, numberEnd)
  val number = scala.util.Try(numberStr.toInt).getOrElse(numberStr.toDouble)
  (number, json.drop(numberStr.length).trim)
}