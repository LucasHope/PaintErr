package PaintErr;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ResizableCanvas extends Canvas {

    static PaintController paintController;
    public ResizableCanvas() {

        // Redraw canvas when size changes.
        widthProperty().addListener(evt ->{
            if (!(paintController == null))
            draw(paintController.getSnapshot());
        });
        heightProperty().addListener(evt -> {
            if (!(paintController == null))
            draw(paintController.getSnapshot());
        });
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Paint.fxml"));
        paintController = (PaintController)loader.getController();


    }

    private void draw(Image snap) {

        double width = getWidth();
        double height = getHeight();


        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(snap, 0, 0, width, height);

    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

    }

    @Override
    public boolean isResizable() {
        return true;
    }


}
