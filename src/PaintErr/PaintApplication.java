package PaintErr;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.util.List;


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
        HBox imageHbox = new HBox(10);
        imageHbox.setAlignment(Pos.CENTER);

        Label label = new Label("This is the welcome page. Not much to see yet, but please do go on by clicking this button:");
        Canvas canvas = new Canvas(500,500);
        Button button = new Button("Continue to Paint_err!");
        Button[] imageButtons= new Button[4];
        vBox.getChildren().addAll(label, button, canvas);

        button.setOnAction(event -> {
                primaryStage.setScene(scene);

        });

        List<PaintErr.Image> list = ImageDAO.getAll();
        List<PaintErr.Image> newList = null;
        if (!list.isEmpty()) {

            if (list.size() > 3) {

                int counter = 0;
                for (int i = list.size() - 1; i > list.size() - 5; i--) {

//                    newList.add(list.get(i));
                    File img = list.get(i).getImg();
                    Image image = new Image(img.toURI().toString(), 200,200,false,false);
                    imageButtons[counter] = new Button();
                    imageButtons[counter].setGraphic(new ImageView(image));
                    imageButtons[counter].setOnAction(event -> editOldPicture(image,primaryStage));
                    imageHbox.getChildren().add(imageButtons[counter]);

                    counter++;
                }


            } else {
                for (int i = list.size() - 1; i > 0; i--) {

                    File img = list.get(i).getImg();
                    Image image = new Image(img.toURI().toString(), 200,200,false,false);

                    imageHbox.getChildren().add(new ImageView(image));

                }
            }
        }

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(vBox, imageHbox);
        primaryStage.setScene(new Scene(stackPane, 800,700));
        primaryStage.show();

    }

    private void setCanvas(Canvas canvas, Image img) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());

    }

    private void editOldPicture(Image image, Stage stage){
        Canvas c = new Canvas();
        stage.setScene(scene);

        stage.show();
        PaintController.setCanvas(c, image);
    }
}
