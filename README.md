# Object-Oriented-Ops

Dependencias:

Maven: Instalar Maven en Linux usando apt: sudo apt update sudo apt install maven

Verificar instalacion: mvn -v

JavaFX: Para instalar JavaFX en linux dirigirse a https://gluonhq.com/products/javafx/. Luego seleccionar la versión de JavaFX para Linux y descarga el archivo ZIP en la sección JavaFX SDK. Descomprime el archivo descargado en un directorio de tu elección. Por ejemplo: mkdir ~/javafx unzip openjfx--linux.zip -d ~/javafx

Ejecutar el proyecto: Opcion 1:
Para compilar y ejecutar tu proyecto en la terminal con un solo comando, se puede usar javac para compilar los archivos y luego java para ejecutar el programa.

Comando para compilar y ejecutar:
javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -d out $(find ./src -name "*.java") && java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp out org.java.powerchess.powerchess.App

NOTA: Especificar la ruta donde tienes las librerías de JavaFX (reemplaza /path/to/javafx/lib con la ruta real)

Opcion 2:
Se debe estar posicionado en el directorio tp-1/PowerChess, luego utilizar el siguiente comando: mvn clean javafx:run
