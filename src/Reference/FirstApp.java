package Err.Paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FirstApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // create the panel to become the parent root for the stage
        Pane root = new Pane();

        // set window title
        primaryStage.setTitle("First app!");

        // create the primary scene as a new scene with the parent-root of "root" and width/height
        primaryStage.setScene(new Scene((Parent)root, 500, 390));

        // display the prepared primary stage scene
        primaryStage.show();
    }

    // psvm for launching the application; launch runs "start"
    public static void main(String[] args) { launch(args); }

}

