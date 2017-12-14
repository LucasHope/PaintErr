package PaintErr;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.*;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Optional;

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

    private String brushType = "brush";
    private double shapeStartX, shapeStartY, shapeEndX, shapeEndY;
    Image shapeImage;

    private ArrayDeque<Image> undoStack = new ArrayDeque<>();
    private ArrayDeque<Image> redoStack = new ArrayDeque<>();

    public Image getSnapshot() {
        return lastSnapshot;
    }

    public PaintErr.Image getActiveImage() {
        return activeImage;
    }

    public void setActiveImage(PaintErr.Image activeImage) {
        this.activeImage = activeImage;
    }

    // called automatically after Controller class instantiated
    public void initialize() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        undoStack.add(canvas.snapshot(null, null));

        //Enables canvas resizing
        canvas.widthProperty().bind(borderPane.widthProperty());
        canvas.heightProperty().bind(borderPane.heightProperty());


        //set black as default
        colorPicker.setValue(Color.BLACK);
        //set new color on stroke when chosen
        colorPicker.setOnAction(event -> {
            gc.setStroke(colorPicker.getValue());
            gc.setFill(colorPicker.getValue());
        });

        

        //set new brushsize when chosen
        gc.setLineWidth(slider.getValue());
        slider.valueProperty().addListener(e -> gc.setLineWidth(slider.getValue()));


        // if 'Fill' is toggled, on mouse click fill the area of clicked spot colour with the current colour

        //start with path when clicked
        canvas.setOnMousePressed(e -> {

            redoStack.clear();

            double size = slider.getValue();
            if (eraser.isSelected()) {
                gc.clearRect(e.getX() - (size / 2), e.getY() - (size / 2), size, size);
            } else {

                // if not erasing, check what is the brushtype and act accordingly
                switch (brushType) {

                    case "fill":

                        // if aim is to fill the whole canvas in the beginning, shortcut to a full fill w/out calculations
                        if(undoStack.size() == 1) PaintFunctions.fillEmpty(canvas, colorPicker.getValue());
                        else setCanvas(PaintFunctions.fill((int) e.getX(), (int) e.getY(), canvas, colorPicker.getValue()));

                        break;

                    case "line":
                    case "circle":
                    case "filledCircle":
                    case "rectangle":
                    case "filledRectangle":

                        undoStack.add(canvas.snapshot(null, null));
                        shapeImage = canvas.snapshot(null, null);

                        shapeStartX = e.getX();
                        shapeStartY = e.getY();
                        shapeEndX = e.getX();
                        shapeEndY = e.getY();
                        drawShape(brushType);

                        break;

                    case "brush":
                    default:

                        undoStack.add(canvas.snapshot(null, null));

                        gc.beginPath();
                        gc.lineTo(e.getX(), e.getY());
                        gc.stroke();

                        break;

                }

            }
        });

        //draw when dragged
        canvas.setOnMouseDragged(e -> {
            double size = slider.getValue();

            if (eraser.isSelected()) {
                gc.clearRect(e.getX() - (size / 2), e.getY() - (size / 2), size, size);
            } else {

                // if not erasing, check what is the brushtype and act accordingly
                switch (brushType) {

                    case "fill":
                        setCanvas(PaintFunctions.fill((int) e.getX(), (int) e.getY(), canvas, colorPicker.getValue()));
                        break;

                    case "line":
                    case "circle":
                    case "filledCircle":
                    case "rectangle":
                    case "filledRectangle":
                        setShapeCanvas(shapeImage);
                        shapeEndX = e.getX();
                        shapeEndY = e.getY();
                        drawShape(brushType);
                        break;

                    case "brush":
                    default:
                        gc.lineTo(e.getX(), e.getY());
                        gc.stroke();
                        break;
                }

            }
        });


        canvas.setOnMouseReleased(e -> {

            if (eraser.isSelected()) return;

            // if not erasing, check what is the brushtype and act accordingly
            switch (brushType) {

                case "line":
                case "circle":
                case "filledCircle":
                case "rectangle":
                case "filledRectangle":
                    setShapeCanvas(shapeImage);
                    shapeEndX = e.getX();
                    shapeEndY = e.getY();
                    drawShape(brushType);
                    break;

                case "fill":
                case "brush":
                default:
                    break;
            }

        });

        //Clear all button will 'clear all'
        button.setOnAction(e -> {
            undoStack.add(canvas.snapshot(null, null));
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        });



    }

    private void drawShape(String brushType) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

//        double[] startToEnd = {shapeStartX, shapeStartY, shapeEndX, shapeEndY};

        double endX, endY;

        if (shapeEndX > shapeStartX) endX = shapeEndX - shapeStartX;
        else endX = shapeStartX - shapeEndX;

        if (shapeEndY > shapeStartY) endY = shapeEndY - shapeStartY;
        else endY = shapeStartY - shapeEndY;

        switch(brushType) {

            case "line":
                gc.strokeLine(shapeStartX, shapeStartY, shapeEndX, shapeEndY);
                break;
            case "circle":
                gc.strokeOval(shapeStartX, shapeStartY, endX, endY);
                break;
            case "filledCircle":
                gc.fillOval(shapeStartX, shapeStartY, endX, endY);
                break;
            case "rectangle":
                gc.strokeRect(shapeStartX, shapeStartY, endX, endY);
                break;
            case "filledRectangle":
                gc.fillRect(shapeStartX, shapeStartY, endX, endY);
                break;

            default:
                break;
        }

    }

    // Called from application, draws wanted image on canvas
    public void setCanvas(Image img) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
        undoStack.add(canvas.snapshot(null, null));

    }

    public void setShapeCanvas(Image img) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());

    }

    //delete temporary files
    public void clearImgFolder() {

//        File imgFolder = new File("./src/img/");
        File imgFolder = new File(".");
        File[] files = imgFolder.listFiles();

        if (files == null) return;

        for (File f : files) {
            String filename = f.getName();
            if (f.isFile()
                    && "png".equals(filename.substring(filename.length() - 3))
                    && (
                            "load".equals(filename.substring(0,4))
                            || "save".equals(filename.substring(0,4))
                    )) {

                f.delete();

            }
        }
    }

    // Change cursor to brush when hovered over canvas.
    public void onMouseEntered() {

        if ("fill".equals(brushType)) {

            Image fillBrush = new Image("resources/paint-bucket.png");
            canvas.setCursor(new ImageCursor(fillBrush));

        } else if ("brush".equals(brushType)) {

            Image brush = new Image("resources/brush.png");
            canvas.setCursor(new ImageCursor(brush));

        } else {
            canvas.setCursor(Cursor.CROSSHAIR);
        }
    }

    public void undo() {
        if (!undoStack.isEmpty() && undoStack.peekLast() != null) {

            redoStack.add(canvas.snapshot(null, null));

            Image snap = undoStack.pollLast();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(snap, 0, 0, canvas.getWidth(), canvas.getHeight());

        }
    }

    public void redo() {
        if (!redoStack.isEmpty() && redoStack.peekLast() != null) {

            undoStack.add(canvas.snapshot(null, null));

            Image snap = redoStack.pollLast();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(snap, 0, 0, canvas.getWidth(), canvas.getHeight());

        }
    }


    // Toolbar buttons

    // Toggle brush
    public void onBrush() {
        brushType = "brush";
    }

    // Toggle fill-brush
    public void onFillBrush() {
        brushType = "fill";
    }

    // Toggle line-brush
    public void onLineBrush() {
        brushType = "line";
    }

    // Toggle circle-brush
    public void onCircleBrush() {
        brushType = "circle";
    }

    // Toggle filledCircle-brush
    public void onFilledCircleBrush() {
        brushType = "filledCircle";
    }

    // Toggle rectangle-brush
    public void onRectangleBrush() {
        brushType = "rectangle";
    }

    // Toggle filledRectangle-brush
    public void onFilledRectangleBrush() {
        brushType = "filledRectangle";
    }


    //Menubar actions:

    // Open a new picture to be drawn on
    public void onNew() {

        //clear activeimage and canvas
        activeImage = null;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }

    // Load local image as a File and add it to the current canvas
    public void onLocalOpen() {

        activeImage = null;

        File img = fileHandler.openLocalImage();

        //set wanted image to canvas if present
        if (img != null) {
            Image canvasImage = new Image(img.toURI().toString());
            setCanvas(canvasImage);
        }

    }

    // Load a database image, query for the ID
    public void onLoad() {

        // TODO
        // Query for ID
        int id = queryID();

        // Open the Image from database with ImageDAO.getById(id)
        PaintErr.Image img = null;

        try {

            img = ImageDAO.getById(id);

            if (img == null) {
                onNew();
                return;
            }

            activeImage = img;

            //set loaded image to canvas
            Image canvasImage = new Image(img.getImg().toURI().toString());
            setCanvas(canvasImage);

            clearImgFolder();

        } catch (Exception e) {

            System.out.println("Could not load image from database. Opening a new Image to draw.");
            onNew();

        }

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
                i.setName(queryString("author"));
                i.setDescription(queryString("desc"));
                ImageDAO.saveImg(i);
            }

            clearImgFolder();

        } catch (Exception e) {
            System.out.println("failed to save .png");
            e.printStackTrace();
        }
    }

    // Save image to database with a specific ID
    public void onSaveByID() {

        int id = queryID();

        PaintErr.Image i = null;
        try {

            if (ImageDAO.doesIDExist(id)) {
                i = ImageDAO.getById(id);
                i.setImg(ImageDAO.saveToFile(canvas));
                i.setThumbnail(ImageDAO.getThumbnail(i.getImg()));
                activeImage = i;
            } else {
                activeImage = null;
                i = new PaintErr.Image(canvas);
                i.setName(queryString("author"));
                i.setDescription(queryString("desc"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ImageDAO.saveImg(i);
            clearImgFolder();
        } catch (Exception e) {
            System.out.println("failed to save to that ID");
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

            clearImgFolder();

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

    // Show a popup to ask for id input
    private int queryID() {

        int id;

        // Show a popup to ask for the database ID of the Image as an int
        String parseable = "";

        // Query for the ID input
        TextInputDialog dialog = new TextInputDialog("0");

        dialog.setTitle("Enter Image ID");
        dialog.setHeaderText("Enter Image ID");
        dialog.setContentText("Please input the database ID for the image in question:");

        Optional<String> result = dialog.showAndWait();

        // Traditional way to get the response value.
        if (result.isPresent()) {

            parseable = result.get();

        }

        try {

            id = Integer.parseInt(parseable);

        } catch (NumberFormatException e) {

            Alert alert = new Alert(AlertType.INFORMATION);

            alert.setTitle("Please note!");
            alert.setHeaderText("You did not input a proper number. Will execute default (save as new or load a blank canvas).");
            alert.setContentText("");

            alert.showAndWait();

            return 0;
        }

        return id;
    }

    // Show a popup to ask for a string input
    private String queryString(String mode) {

        String input = "";

        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle("More information about the image");

        if ("author".equals(mode)) {
            dialog.setHeaderText("Who are you?");
            dialog.setContentText("Please input the name of the author:");
        } else {
            dialog.setHeaderText("What does this represent?");
            dialog.setContentText("Please input a description of the image:");
        }

        Optional<String> result = dialog.showAndWait();

        // Traditional way to get the response value.
        if (result.isPresent()) {
            input = result.get();
        }

        return input;

    }


}

