package lector

import scala.io.Source
import java.io.File
import interprete.parse 

def procesarInput(argumentos: Array[String]): Map[String, Any] = {
  val jsonInput = if (argumentos.nonEmpty && new File(argumentos(0)).exists) {
    val filePath = argumentos(0)
    Source.fromFile(filePath).getLines().mkString
  } else {
    io.StdIn.readLine()
  }
  verificarJson(jsonInput)
}

private def verificarJson(jsonInput: String): Map[String, Any] = {
  parseJson(jsonInput) match {
    case Some(parsedJson) => parsedJson
    case None => throw new Exception("Error: formato de JSON inválido.")
  }
}

private def parseJson(input: String): Option[Map[String, Any]] = {
  try {
    Some(parse(input).asInstanceOf[Map[String, Any]])
  } catch {
    case _: Throwable => None
  }
}
