package tp2junqtional

import scala.io.Source
import java.io.File
import scala.io.StdIn.readLine
import interprete._
import operaciones._

object Main {
  def main(args: Array[String]) = {
    if (args.isEmpty) throw Exception("Input invalido")

    val operacion = args(0)
    val argumentos = args.drop(1)

    val parsedJson = procesarInput(argumentos)
    val resultado = interpretarOperacion(parsedJson, operacion, argumentos)

    resultado.foreach(println)
  }
}
