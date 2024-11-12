package interprete

import operaciones._

def interpretarOperacion(parsedJson: String, operacion: String, argumentos: Array[String]) = operacion match {
  case //faltaria caso en el que piden algo mas simple como indexar .[i] o .key supongo, no estoy seguro
  case "add-key" => addKey(parsedJson, parsePath(argumentos(0)), argumentos(1), argumentos(2))
  case "add-item" => addItem(parsedJson, parsePath(argumentos(0)), argumentos(1))
  case "delete" => delete(parsedJson, parsePath(argumentos(0)))
  case "edit" => edit(parsedJson, parsePath(argumentos(0)), argumentos(1))
  case "get" => get(parsedJson, parsePath(argumentos(0)))
  case "depth" => depth(parsedJson, argumentos(0).toInt)
  case "exists_key" => existsKey(parsedJson, argumentos(0))
  case "exists_key_rec" => existsKeyRec(parsedJson, argumentos(0))
  case _ => throw Exception(s"Error: La operacion '$operacion' no existe.")
}

private def parsePath(path: String) = {
  path.split("\\.").toList
}
