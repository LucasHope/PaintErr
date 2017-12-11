package PaintErr;

import java.sql.Blob;

public class Img {

    int ID;
    String name, description;
    Blob thumbnail = null, img = null;

    public Img() { }

    public Img(int ID, String name, String description, Blob thumbnail, Blob img) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.img = img;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public Blob getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Blob thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }
}
