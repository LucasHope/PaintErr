package PaintErr;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class ImageButton extends Button {
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageButton(Image image) {
        this.image = image;
    }

    public ImageButton(String text, Image image) {
        super(text);
        this.image = image;
    }

    public ImageButton(String text, Node graphic, Image image) {
        super(text, graphic);
        this.image = image;
    }

    public ImageButton() {
    }
}
