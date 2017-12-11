package PaintErr;

import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LocalFileHandler {

    Stage stage = PaintApplication.getStage();

    public File openLocalImage() {

        FileChooser fc = new FileChooser();

        // Set the Open-dialog title and starting directory
        fc.setTitle("Choose an image to be edited.");
        fc.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        // Set the possible file load extensions
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );

        // Show the Open-dialog
        // .showOpenDialog for one File, .showOpenMultipleDialog for List<File>
        return fc.showOpenDialog(stage);

    }

    public void saveLocalImage(Image i) {

        FileChooser fc = new FileChooser();

        // Set the Save-dialog title and starting directory
        fc.setTitle("Save Image Locally");
        fc.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        // Set the possible save extensions
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );

        // Show the Save-dialog
        File file = fc.showSaveDialog(stage);

        // File image of i to a JavaFx Image img
        javafx.scene.image.Image img = new javafx.scene.image.Image(i.getImg().toURI().toString());

        // If a File/Path was chosen (not null), save the image to the chosen File
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
            } catch (IOException ex) {
                System.out.println("Could not save Image locally!");
                System.out.println(ex.getMessage());
            }
        }

    }

}