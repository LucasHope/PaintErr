package PaintErr;

// Entity-class, "img"-table @ DB

import javafx.scene.Scene;

import java.io.File;

public class Image {

    private int ID = 0;
    private String name = "", description = "";
    private File thumbnail = null, img = null;

    public Image() { }

    public Image(int id, File img)  {
        this.ID = id;
        this.img = img;
    }

    public Image(int id, Scene scene)  {
        this.ID = id;
        try {
            this.img = ImageDAO.saveToFile(scene);
        } catch (Exception e) {
            System.out.println("Could not save Image!");
            e.printStackTrace();
        }
    }

    public Image(File img) {
        this.img = img;
    }

    public Image(Scene scene) {
        try {
            this.img = ImageDAO.saveToFile(scene);
        } catch (Exception e) {
            System.out.println("Could not save Image!");
            e.printStackTrace();
        }
    }

    public Image(String name, String description, File thumbnail, File img) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.img = img;
    }

    public Image(int ID, String name, String description, File thumbnail, File img) {

        this.ID = ID;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.img = img;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(File thumbnail) {
        this.thumbnail = thumbnail;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }
}
