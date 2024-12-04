package tp2junqtional

import scala.io.Source
import java.io.File
import scala.io.StdIn.readLine
import interprete._
import lector._
import operaciones._

object Main {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) throw new Exception("Input inválido: no se especificó ninguna operación.")

    val operacion = args(0)
    val argumentos = args.drop(1)

    val parsedJson = procesarInput(argumentos)

    val resultadoComoMap = interpretarOperacion(parsedJson, operacion, argumentos)

    val resultadoEnJSONString = imprimirResultadoEnJson(resultadoComoMap.asInstanceOf[Map[String, Any]]) 
    
    println( resultadoEnJSONString )
  }
}
