package Err.Paint;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;

public class PaintPictureController {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private CheckBox eraser;

    // called automatically after Controller class instantiated
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        colorPicker.setValue(Color.BLACK);

        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if(eraser.isSelected()) {
                gc.clearRect(x, y, size, size);
            } else {
                gc.setFill(colorPicker.getValue());
                gc.fillRect(x, y, size, size);
            }
        });
    }

    String title = "paint_err";

    public void onSave() {
        try {
            Image snap = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snap, null), "png", new File(title + ".png"));
        } catch (Exception e) {
            System.out.println("failed to save .png");
            e.printStackTrace();
        }
    }

    public void onExit() {
        Platform.exit();
    }

}
