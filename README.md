# Object-Oriented-Ops

Debemos asegurarnos de tener las siguientes dependencias:

Java JDK:
   sudo apt update
   sudo apt install openjdk-11-jdk

Scala:
  sudo apt install scala

SBT:
  echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
  curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x99E82A75642AC823" | sudo apt-key add
  sudo apt update
  sudo apt install sbt

---------------------------------------------------------------------------------------------------------------

Para compilar y Ejecutar el Programa

sbt "run <operacion> <argumentos>"

Ejemplo: sbt "run add-key .path.to.key newKey newValue"
