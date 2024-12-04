package lector

import scala.io.Source
import java.io.File
import interprete.parse 

def procesarInput(argumentos: Array[String]): Map[String, Any] = {

  argumentos.match{
    case x if x.nonEmpty =>
      val jsonInput = System.console() match {
        case null =>
          Source.stdin.mkString
        case _ =>
          io.StdIn.readLine()
      }
      verificarJson(jsonInput)
    case _ => throw new Exception("Error: Cantidad de argumentos inválida.")
  }
  
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
