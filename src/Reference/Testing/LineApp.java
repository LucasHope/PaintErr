/* ....Show License.... */
package ensemble.samples.graphics2d.shapes.line;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * A sample that demonstrates how various settings affect a line shape.
 */
public class LineApp extends Application {

    private static final int LINES_NUMBER = 3;
    private static final double WIDTH = 600;
    private static final double HEIGHT = 400;
    private Line exampleLine;

    public Line createRandomLine() {
        double startX = Math.random() * WIDTH;
        double startY = Math.random() * HEIGHT;
        double endX = Math.random() * WIDTH;
        double endY = Math.random() * HEIGHT;
        double width = Math.random() * 3 + 0.5;

        // Create line shape
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.RED);
        line.setStrokeWidth(width);
        return line;
    }

    public Parent createContent() {
        Pane root = new Pane();
        root.setMinSize(WIDTH, HEIGHT);
        root.setMaxSize(WIDTH, HEIGHT);

        // add many lines
        for (int i = 0; i < LINES_NUMBER; i++) {
            root.getChildren().add(createRandomLine());
        }

        // add line for the playground
        exampleLine = new Line(50, 50, 550, 350);
        exampleLine.setStroke(Color.GREEN);
        exampleLine.setStrokeWidth(3);
        root.getChildren().add(exampleLine);

        return root;
    }

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) { launch(args); }
}