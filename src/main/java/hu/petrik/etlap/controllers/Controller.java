package hu.petrik.etlap.controllers;

import hu.petrik.etlap.EtlapApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    protected Stage stage;

    public Stage getStage() {
        return stage;
    }

    public static Controller valtas(String fxml, String title, int width, int height) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EtlapApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
        Controller controller = fxmlLoader.getController();
        controller.stage = stage;
        return fxmlLoader.getController();
    }
}
