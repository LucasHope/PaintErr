package PaintErr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    static PaintController paintController;

    static Stage stage;

    private Scene scene;

    private static Stage primaryStage;

    public PaintApplication() {}

    public static Stage getPrimaryStage() {return primaryStage;}

    public static void setPrimaryStage(Stage primaryStage) {
        PaintApplication.primaryStage = primaryStage;
    }

    public static Stage getStage() {return stage;}

    public Scene getScene() {
        return scene;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Load paint.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Paint.fxml"));
        Parent root = loader.load();

        //Set controller
        paintController = (PaintController)loader.getController();

        // ensure there are no leftover images from earlier program runs
        paintController.clearImgFolder();

        //Make sure canvas is white
        root.setStyle("-fx-background-color: white");

        scene = new Scene(root);

        //Make primarystage usable for other methods/classes
        setPrimaryStage(primaryStage);

        stage = PaintApplication.getPrimaryStage();

        //Build welcome page
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label("Welcome to Paint_Err! What would you like to do?");

        Button startNew = new Button("Draw a new image");
        Button editOld = new Button("Edit an old image ...");

        vBox.getChildren().addAll(label, startNew, editOld);

        //Set scene for new image
        startNew.setOnAction(e -> {
            primaryStage.setScene(scene);
            paintController.clearImgFolder();
        });

        editOld.setOnAction((e -> {
            primaryStage.setScene(scene);
            paintController.onLocalOpen();
            paintController.clearImgFolder();
        }));

        //Show old images as buttons if they exist
        makeButtons(vBox);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(vBox);
        primaryStage.setScene(new Scene(stackPane, 800,700));
        primaryStage.show();

        paintController.clearImgFolder();

        primaryStage.setOnCloseRequest(event -> {
            paintController.clearImgFolder();
            System.out.println("Close request.");
        });

    }

    public void editOldPicture(PaintErr.Image image){

        stage.setScene(scene);
        //set active image object to prevent multiple files
        paintController.setActiveImage(image);
        //set wanted image to canvas
        Image canvasImage = new Image(image.getImg().toURI().toString());
        paintController.setCanvas(canvasImage);

        paintController.clearImgFolder();

    }

    private void makeButtons(VBox vBox){
        HBox imageHbox = new HBox(10);
        imageHbox.setAlignment(Pos.CENTER);

        //Load image objects from database
        List<PaintErr.Image> list = null;
        try {
            list = ImageDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create imagebuttons
        ImageButton[] imageButtons= new ImageButton[4];

        Label emptyLabel = new Label("");

        Label galleryLabel = new Label("Here you can view (and edit) some of the latest database creations:");

        if (!list.isEmpty()) {

            if (list.size() > 3) {

                int counter = 0;
                for (int i = list.size() - 1; i > list.size() - 5; i--) {

                    //Get image, image object, and thumbnail
                    PaintErr.Image imageObject = list.get(i);
                    File img = list.get(i).getThumbnail();
                    Image image = new Image(img.toURI().toString());

                    //make buttons
                    imageButtons[counter] = new ImageButton(imageObject);
                    imageButtons[counter].setGraphic(new ImageView(image));
                    imageButtons[counter].setOnAction(event -> editOldPicture(imageObject));

                    imageHbox.getChildren().add(imageButtons[counter]);

                    counter++;
                }

                //add buttons to vbox
                vBox.getChildren().addAll(emptyLabel, galleryLabel, imageHbox);

            } else {

                int counter = 0;
                for (int i = list.size() - 1; i >= 0; i--) {

                    //same as above, need to find a way to make this cleaner
                    PaintErr.Image imageObject = list.get(i);
                    File img = list.get(i).getThumbnail();
                    Image image = new Image(img.toURI().toString());

                    imageButtons[counter] = new ImageButton(imageObject);
                    imageButtons[counter].setGraphic(new ImageView(image));
                    imageButtons[counter].setOnAction(event -> editOldPicture(imageObject));

                    imageHbox.getChildren().add(imageButtons[counter]);

                    counter++;
            }

                vBox.getChildren().addAll(emptyLabel, galleryLabel, imageHbox);

            }
        }
    }
}
