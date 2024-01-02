package catalogoLibri;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CatalogoLibri extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CatalogoLibri.fxml"));

        stage.setScene(new Scene(root, 1200, 600));
        stage.setTitle("Catalogo libri");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}