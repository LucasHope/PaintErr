package PaintErr;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class PaintFunctions {

    private static PixelWriter pw;
    private static PixelReader pr;
    private static WritableImage activeImage;
    private static Color newColour;
    private static Color oldColour;
    private static int counter = 1;

    public static WritableImage fill(int x, int y, Canvas canvas, Color color) {

        // Canvas to ImageView/WritableImage for PixelWriter
        activeImage = canvas.snapshot(null, null);

        // Init PixelWriter and PixelReader
        pw = activeImage.getPixelWriter();
        pr = activeImage.getPixelReader();

        // get current and new pixel colour
        oldColour = pr.getColor(x, y);
        newColour = color;

        // Flood fill algorithm and return the resulting WritableImage
        return fillShape(new Pixel(x, y), activeImage);

    }

    private static WritableImage fillShape(Pixel pixel, WritableImage img) {

        // Init PixelWriter and PixelReader
        pw = img.getPixelWriter();
        pr = img.getPixelReader();

        if (pr.getColor(pixel.getX(), pixel.getY()).equals(newColour)) return img;
        if (!(pr.getColor(pixel.getX(), pixel.getY()).equals(oldColour))) return img;

        ArrayDeque<Pixel> q = new ArrayDeque<>();
        Iterator i = q.iterator();

        // pixel, west, east
        Pixel p, w, e;

        q.add(pixel);

        while (q.peek() != null) {

            p = q.remove();
//            System.out.print("\n\npop: p: X " + p.getX() + " Y " + p.getY() + ", count: " + counter++);

            pw.setColor(p.getX(), p.getY(), newColour);

            // if north and south of pixel are of oldColour, add to q
            if (pr.getColor(p.getX(), p.getY() + 1).equals(oldColour)) q.add(new Pixel(p.getX(), p.getY() + 1));
            if (pr.getColor(p.getX(), p.getY() - 1).equals(oldColour)) q.add(new Pixel(p.getX(), p.getY() - 1));

            w = new Pixel(p.getX(), p.getY());

            // going west until border
            while (true) {

                w.setX(w.getX() - 1);
//                System.out.println("Going west");

                System.out.print("\n\n at West point: X " + w.getX() + " Y " + w.getY() + ", colour: " + pr.getColor(w.getX(), w.getY()));

                if (!(pr.getColor(w.getX(), w.getY()).equals(oldColour)) || w.getX() <= 0) break;
                else pw.setColor(w.getX(), w.getY(), newColour);

                if (pr.getColor(w.getX(), w.getY() + 1).equals(oldColour)) q.add(new Pixel(w.getX(), w.getY() + 1));
                if (pr.getColor(w.getX(), w.getY() - 1).equals(oldColour)) q.add(new Pixel(w.getX(), w.getY() - 1));

            }

            e = new Pixel(p.getX(), p.getY());

            // going east until border
            while (true) {

                e.setX(e.getX() + 1);
//                System.out.println("Going east");

                System.out.print(" - at East point: X " + e.getX() + " Y " + e.getY() + ", colour: " + pr.getColor(e.getX(), e.getY()));

                if (!(pr.getColor(e.getX(), e.getY()).equals(oldColour)) || e.getX() >= img.getWidth()) break;
                else pw.setColor(e.getX(), e.getY(), newColour);

                if (pr.getColor(e.getX(), e.getY() + 1).equals(oldColour)) q.add(new Pixel(e.getX(), e.getY() + 1));
                if (pr.getColor(e.getX(), e.getY() - 1).equals(oldColour)) q.add(new Pixel(e.getX(), e.getY() - 1));

            }

        }

        return img;

//        Flood-fill (node, target-color, replacement-color):
//        1. If target-color is equal to replacement-color, return.
//        2. If color of node is not equal to target-color, return.
//        3. Set Q to the empty queue.
//        4. Add node to Q.
//        5. For each element N of Q:
//        6.         Set w and e equal to N.
//        7.         Move w to the west until the color of the node to the west of w no longer matches target-color.
//        8.         Move e to the east until the color of the node to the east of e no longer matches target-color.
//        9.         For each node n between w and e:
//        10.             Set the color of n to replacement-color.
//        11.             If the color of the node to the north of n is target-color, add that node to Q.
//        12.             If the color of the node to the south of n is target-color, add that node to Q.
//        13. Continue looping until Q is exhausted.
//        14. Return.

    }

}

