package interprete
import scala.util.Try
import scala.util.control.Breaks._
def parse(json: String): Any = {
  val trimmed = json.trim
  if (trimmed.startsWith("{")) parseObject(trimmed)
  else if (trimmed.startsWith("[")) parseArray(trimmed)
  else parseValue(trimmed)
}

def parseObject(json: String): Map[String, Any] = {
  var remaining = json.drop(1).trim
  val map = scala.collection.mutable.Map[String, Any]()
  while (!remaining.startsWith("}")) {
    val (key, restAfterKey) = parseString(remaining)
    remaining = restAfterKey.dropWhile(_ != ':').tail.trim
    val (value, restAfterValue) = parseValue(remaining)
    map += (key -> value)
    remaining = restAfterValue.dropWhile(c => c == ',' || c.isWhitespace)
  }
  map.toMap
}

def parseArray(json: String): List[Any] = {
  var remaining = json.drop(1).trim
  val list = scala.collection.mutable.ListBuffer[Any]()
  while (!remaining.startsWith("]")) {
    val (value, restAfterValue) = parseValue(remaining)
    list += value
    remaining = restAfterValue.dropWhile(c => c == ',' || c.isWhitespace)
  }
  list.toList
}

def parseValue(json: String): (Any, String) = {
  val trimmed = json.trim
  if (trimmed.startsWith("\"")) parseString(trimmed)
  else if (trimmed.startsWith("{")) (parseObject(trimmed), trimmed.drop(findMatchingBrace(trimmed)))
  else if (trimmed.startsWith("[")) (parseArray(trimmed), trimmed.drop(findMatchingBrace(trimmed)))
  else if (trimmed.startsWith("true")) (true, trimmed.drop(4))
  else if (trimmed.startsWith("false")) (false, trimmed.drop(5))
  else if (trimmed.startsWith("null")) (null, trimmed.drop(4))
  else parseNumber(trimmed)
}

def parseString(json: String): (String, String) = {
  val endIdx = json.indexOf('"', 1)
  (json.substring(1, endIdx), json.substring(endIdx + 1).trim)
}

def parseNumber(json: String): (Any, String) = {
  val numberEnd = json.indexWhere(c => !c.isDigit && c != '.' && c != '-')
  val numberStr = if (numberEnd == -1) json else json.substring(0, numberEnd)
  val number = Try(numberStr.toInt).getOrElse(numberStr.toDouble)
  (number, json.drop(numberStr.length).trim)
}

def findMatchingBrace(json: String): Int = {
  var count = 0
  var position = json.length

  breakable {
    for (i <- json.indices) {
      if (json(i) == '{' || json(i) == '[') count += 1
      else if (json(i) == '}' || json(i) == ']') count -= 1

      if (count == 0) {
        position = i + 1
        break
      }
    }
  }
  position
}

