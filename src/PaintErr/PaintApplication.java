package PaintErr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;

import static javafx.application.Application.launch;

public class PaintApplication extends Application {

    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public PaintApplication() {
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("Paint.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        Label label = new Label("This is the welcome page. Not much to see yet, but please do go on by clicking this button:");
        Button button = new Button("Continue to Paint_err!");
        vBox.getChildren().addAll(label, button);

        button.setOnAction(event -> {
                primaryStage.setScene(scene);

        });

        primaryStage.setScene(new Scene(vBox, 800,700));
        primaryStage.show();
    }
}
