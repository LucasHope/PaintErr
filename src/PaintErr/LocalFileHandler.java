package PaintErr;

import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FileHandler {

    Stage stage = PaintApplication.getStage();

    public File openLocalImage() {

        FileChooser fc = new FileChooser();

        // Set the Open-dialog title and starting directory
        fc.setTitle("Choose an image to be edited.");
        fc.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // .showOpenDialog for one File, .showOpenMultipleDialog for List<File>
        return fc.showOpenDialog(stage);

    }

    public void saveLocalImage(Image i) {

        FileChooser fc = new FileChooser();

        fc.setTitle("Save Image Locally");
        fc.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        File file = fc.showSaveDialog(stage);

        import javafx.scene.image.Image img = i.getImg().toURI().toString();

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(img,
                        null), "png", file);
            } catch (IOException ex) {
                System.out.println("Could not save Image locally!");
                System.out.println(ex.getMessage());
            }
        }

    }

}
