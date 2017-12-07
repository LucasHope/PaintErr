package PaintErr;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ResizableCanvas extends Canvas {

    public ResizableCanvas() {

        // Redraw canvas when size changes.
        widthProperty().addListener(evt ->{
            draw();
        });
        heightProperty().addListener(evt -> {
            draw();
        });
    }

    private void draw() {

        ResizableCanvas canvas = new ResizableCanvas();
        double width = getWidth();
        double height = getHeight();
        javafx.scene.image.Image snap = canvas.snapshot(null, null);


        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(snap, 0, 0, width, height);
//        gc.clearRect(0, 0, width, height);

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
