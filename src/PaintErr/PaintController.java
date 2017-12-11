package PaintErr;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static PaintErr.PaintApplication.stage;

public class PaintController {

    @FXML
    private ResizableCanvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private CheckBox eraser;

    @FXML
    private Slider slider;

    @FXML
    private Button button;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Image lastSnapshot;

    private LocalFileHandler fileHandler = new LocalFileHandler();

    private PaintErr.Image activeImage;

    public Image getSnapshot(){return lastSnapshot;}

    public PaintErr.Image getActiveImage() {
        return activeImage;
    }

    public void setActiveImage(PaintErr.Image activeImage) {
        this.activeImage = activeImage;
    }

    // called automatically after Controller class instantiated
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Enables canvas resizing
        canvas.widthProperty().bind(borderPane.widthProperty());
        canvas.heightProperty().bind(borderPane.heightProperty());


        //set black as default
        colorPicker.setValue(Color.BLACK);
        //set new color on stroke when chosen
        colorPicker.setOnAction(event -> gc.setStroke(colorPicker.getValue()));

        //set new brushsize when chosen
        slider.valueProperty().addListener(e-> gc.setLineWidth(slider.getValue()));

        //start with path when clicked
        canvas.setOnMousePressed(event -> {
            double size = slider.getValue();
            if(eraser.isSelected()) {
                gc.clearRect(event.getX(), event.getY(), size, size);
            } else {
                gc.beginPath();
                gc.lineTo(event.getX(), event.getY());
                gc.stroke();
            }
        });

        //draw when dragged
        canvas.setOnMouseDragged(e -> {
            double size = slider.getValue();

            if(eraser.isSelected()) {
                gc.clearRect(e.getX(), e.getY(), size, size);
            } else {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                lastSnapshot = canvas.snapshot(null, null);
            }
        });

        //Clear all button will 'clear all'
        button.setOnAction(e->{
            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());

        });


    }

    //Called from application, draws wanted image on canvas
    void setCanvas( Image img) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());

    }
    //delete temporary files
    private void clearImgFolder() {

        File imgFolder = new File("./src/img/");
        File[] files = imgFolder.listFiles();

        if (files == null) return;

        for (File f : files) {
            String filename = f.getName();
            if(f.isFile() && "png".equals(filename.substring(filename.length() - 3))) {
                f.delete();
            }
        }
    }

    //Menubar actions:

    public void onNew(){
        //clear activeimage and canvas
        activeImage = null;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());

    }

    // Load local image as a File and add it to the current canvas
    public void onLocalOpen() {

        File img = fileHandler.openLocalImage();

        //set wanted image to canvas
        Image canvasImage = new Image(img.toURI().toString());
        setCanvas(canvasImage);

    }

    // Load a database image, query for the ID
    public void onLoad() {

        // TODO
        // Query for ID
        // Open the Image from database with ImageDAO.getById(id)
    }

    // save image to database
    public void onSave() {

        try {

            if (activeImage != null) {
                activeImage.setImg(ImageDAO.saveToFile(canvas));
                activeImage.setThumbnail(ImageDAO.getThumbnail(activeImage.getImg()));
                ImageDAO.saveImg(activeImage);
            } else {
                PaintErr.Image i = new PaintErr.Image(canvas);
                ImageDAO.saveImg(i);
            }

        } catch (Exception e) {
            System.out.println("failed to save .png");
            e.printStackTrace();
        }
    }

    // Save image to a local file
    public void onLocalSave() {

        try {

            if (activeImage != null) {
                activeImage.setImg(ImageDAO.saveToFile(canvas));
                activeImage.setThumbnail(ImageDAO.getThumbnail(activeImage.getImg()));
                fileHandler.saveLocalImage(activeImage);
            } else {
                PaintErr.Image i = new PaintErr.Image(canvas);
                fileHandler.saveLocalImage(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Back to Welcome-scene
    public void onWelcome() throws Exception {
        //start again
        PaintApplication paintApplication = new PaintApplication();
        paintApplication.start(stage);
    }

    // Exit the program
    public void onExit() {

        //delete temp and exit
        clearImgFolder();
        Platform.exit();

    }

}

