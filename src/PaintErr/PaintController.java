package PaintErr;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PaintController {
    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private CheckBox eraser;

    @FXML
    private Slider slider;

    // called automatically after Controller class instantiated
    public void initialize() {
        //Opens the example png so the user can modify it
//        try {
//            setCanvas(canvas,new javafx.scene.image.Image(getClass().getResource("paint_err.png").openStream()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        GraphicsContext gc = canvas.getGraphicsContext2D();

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
            }});

        //draw when dragged
        canvas.setOnMouseDragged(e -> {
            double size = slider.getValue();
//            double x = e.getX() - size / 2;
//            double y = e.getY() - size / 2;
//
            if(eraser.isSelected()) {
                gc.clearRect(e.getX(), e.getY(), size, size);
            } else {
//                gc.setFill(colorPicker.getValue());
//                gc.fillRoundRect(x, y, size, size,size,size);
//            }
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
        });
    }


    String title = "paint_err";

    public void onSave() {
        try {
            javafx.scene.image.Image snap = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snap, null), "png", new File(title + ".png"));
        } catch (Exception e) {
            System.out.println("failed to save .png");
            e.printStackTrace();
        }
    }
    private void setCanvas(Canvas canvas, Image img) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, 0, 0,canvas.getWidth(), canvas.getHeight());
    }
    public void onExit() {
        Platform.exit();
    }

    public static void main(String[] args) {
    }
}

