package lector

import scala.io.Source
import java.io.File

def procesarInput(argumentos: Array[String]) = {
  // me fijo si el primer argumento es un archivo, sino leo por entrada estandar
  val jsonInput = if (argumentos.nonEmpty && new File(argumentos(0)).exists) {
    val filePath = argumentos(0)
    Source.fromFile(filePath).getLines().mkString
  } else {
    io.StdIn.readLine()
  }
  verificarJson(jsonInput)
}

private def verificarJson(jsonInput: String) = {
  val json = parseJson(jsonInput) match {
    case Some(parsedJson) => parsedJson
    case _ => throw Exception("Error: formato de JSON invalido.")
  }
}

private def parseJson(input: String) = {
}