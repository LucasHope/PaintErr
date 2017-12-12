package PaintErr;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PaintFunctions {

    private static PixelWriter pw;
    private static PixelReader pr;
    private static WritableImage activeImage;
    private static Color newColour;
    private static Color oldColour;
    private static final int maxPixels = 5000;

    public static WritableImage fill(double x, double y, Canvas canvas, Color color) {

        // Canvas to ImageView/WritableImage for PixelWriter
        activeImage = canvas.snapshot(null, null);

        // Init PixelWriter and PixelReader
        pw = activeImage.getPixelWriter();
        pr = activeImage.getPixelReader();

        // get current and new pixel colour
        oldColour = pr.getColor((int) x, (int) y);
        newColour = color;

        // call "paintPixel(int x, int y, image)" to paint this pixel and loop through nearby pixels
        // if nearby pixel is same as current colour, call "paintPixel()" again
        fillShape((int) x, (int) y);

        // return the resulting WritableImage
        return activeImage;

    }

    private static void fillShape(int x, int y) {

    }

    private static void paintPixel(int x, int y) {

        pw.setColor(x, y, newColour);

        // check and paint the pixels to the top, right, bottom, left of the current pixel
        if (oldColour.equals(pr.getColor(x, y + 1))) {
            paintPixel(x, y + 1);
            if (oldColour.equals(pr.getColor(x + 1, y))) paintPixel(x + 1, y);
            if (oldColour.equals(pr.getColor(x, y - 1))) paintPixel(x, y - 1);
            if (oldColour.equals(pr.getColor(x - 1, y))) paintPixel(x - 1, y);

        }

    }

}
