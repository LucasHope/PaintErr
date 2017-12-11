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
import java.sql.SQLException;
import java.util.List;


public class PaintApplication extends Application {

    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    private static Stage primaryStage;

    static Stage stage;

    public PaintApplication() {
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("Paint.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        PaintApplication.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        stage = PaintApplication.getPrimaryStage();

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label("This is the welcome page. Not much to see yet, but please do go on by clicking this button:");
//        Canvas canvas = new Canvas(500,500);
        Button welcomeButton = new Button("Continue to Paint_err!");
        vBox.getChildren().addAll(label, welcomeButton);

        welcomeButton.setOnAction(event -> primaryStage.setScene(scene));

        //Show old images as buttons if they exist
        makeButtons(vBox);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(vBox);
        primaryStage.setScene(new Scene(stackPane, 800,700));
        primaryStage.show();

    }

    private void setCanvas(Canvas canvas, Image img) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());

    }

    private void editOldPicture(PaintErr.Image image){
        Canvas c = new Canvas();

        try {
            stage.setScene(scene = new Scene(FXMLLoader.load(getClass().getResource("Paint.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
        Image canvasImage = new Image(image.getImg().toURI().toString());
        setCanvas(c, canvasImage);
    }

    private void makeButtons(VBox vBox){
        HBox imageHbox = new HBox(10);
        imageHbox.setAlignment(Pos.CENTER);

        List<PaintErr.Image> list = null;
        try {
            list = ImageDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageButton[] imageButtons= new ImageButton[4];

        if (!list.isEmpty()) {

            if (list.size() > 3) {

                int counter = 0;
                for (int i = list.size() - 1; i > list.size() - 5; i--) {

//                    newList.add(list.get(i));
                    PaintErr.Image imageObject = list.get(i);
                    File img = list.get(i).getThumbnail();
                    Image image = new Image(img.toURI().toString());
                    imageButtons[counter] = new ImageButton(imageObject);
                    imageButtons[counter].setGraphic(new ImageView(image));
                    imageButtons[counter].setOnAction(event -> editOldPicture(imageObject));
                    imageHbox.getChildren().add(imageButtons[counter]);

                    counter++;
                }

                vBox.getChildren().add(imageHbox);

            } else {
                int counter = 0;
                for (int i = list.size() - 1; i >= 0; i--) {

                    PaintErr.Image imageObject = list.get(i);
                    File img = list.get(i).getThumbnail();
                    Image image = new Image(img.toURI().toString());
                    imageButtons[counter] = new ImageButton(imageObject);
                    imageButtons[counter].setGraphic(new ImageView(image));
                    imageButtons[counter].setOnAction(event -> editOldPicture(imageObject));
                    imageHbox.getChildren().add(imageButtons[counter]);

                    counter++;
            }
                vBox.getChildren().add(imageHbox);

            }
    }
}}
