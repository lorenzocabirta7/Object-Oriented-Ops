module org.java.powerchess.powerchess {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires annotations;

    opens org.java.powerchess.powerchess to javafx.fxml;
    exports org.java.powerchess.powerchess;
}