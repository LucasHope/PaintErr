package Err.Paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class FirstDrawingApp extends Application {

    // create the panel to put f.ex. canvas on
    StackPane pane = new StackPane();

    // set the program to consist of the StackPane and be of width/height x/y
    Scene scene = new Scene (pane, 800, 500);

    // set the "painting" canvas/paper to be of same w/h as the program (scene)
    // also used to get the graphic context for this canvas with ".getGraphicsContext2D()"
    Canvas c = new Canvas(800, 500);

    // Context to draw graphics (paint stuff), brush tool for the paper
    GraphicsContext gc = null;

    // create a grid to house all other panel elements
    GridPane grid = new GridPane();

    // ColorPicker for changing the brush colour
    ColorPicker cp = new ColorPicker();

    // Slider to change the brush width
    Slider slider = new Slider();
    Label l = new Label("2.0");

    // value for the (default) paint color
    Paint colour = Color.BLACK;

    @Override
    public void start(Stage stage) throws Exception {
    // also define the stage as a parameter of start

        try {
            // stage => scene => container => node

            // instantiate through canvas
            gc = c.getGraphicsContext2D();

            // prepare the default brush (color & size)
            // Stroke can be set with a Paint object
            // line width takes a Double
            gc.setStroke(colour);
            gc.setLineWidth(2);

            // set up the cp with the default colour
            cp.setValue((Color) colour);

            // set up for when a different colour is picked
            // set the color of the brush and var to the new color value
            cp.setOnAction(event -> {
                colour = cp.getValue();
                gc.setStroke(colour);
            });

            // set up the brush width slider
            slider.setMin(1);
            slider.setMax(25);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);

            // updating the slider label & brush width to represent the slider value
            slider.valueProperty().addListener(event -> {
                // LISTENER! When the value changes, the code is executed
                double value = slider.getValue();

                // update the label with the new value
                String v = String.format("%.1f", value);
                l.setText(v);

                // update the brush (gc) with the new width value
                gc.setLineWidth(value);
            });

            // set up of the grid element (grid for container elements)
            // first row to include color picker, slider and label
            // set to appear at top center with some additional styling
            grid.addRow(0, cp, slider, l);
            grid.setHgap(20);
            grid.setAlignment(Pos.TOP_CENTER);
            grid.setPadding(new Insets(20, 0, 0, 0));

            // what to do (draw) when the mouse is clicked
            scene.setOnMousePressed(event -> {

                // begin to draw a path (reset the path, coordinates)
                gc.beginPath();

                // create a line to the given coordinates starting from previous coordinates
                // for coordinates get the Scene x and y
                gc.lineTo(event.getSceneX(), event.getSceneY());

                // execute the stroke / line
                gc.stroke();

            });

            // what to do (draw) when the mouse is dragged (as pressed)
            scene.setOnMouseDragged(event -> {

                // create a line to the given coordinates starting from previous coordinates
                // for coordinates get the Scene x and y
                gc.lineTo(event.getSceneX(), event.getSceneY());

                // execute the stroke / line
                gc.stroke();

            });

            // attach the Canvas and other items as a grid (ColorPicker etc) to the Pane
            pane.getChildren().addAll(c, grid);

            // set and show the scene (and title) after everything is ready
            stage.setTitle("Paint_Err");
            stage.setScene(scene);

//            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("PaintPicture.fxml"))));

            stage.show();

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // psvm for launching the application; launch runs "start"
    public static void main(String[] args) {
        launch(args);
    }

}

