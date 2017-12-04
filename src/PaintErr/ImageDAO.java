package PaintErr;

import PaintErr.Image;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO {

    public static List<Image> list = new ArrayList<>();

    private static Connection con;

    public static List<Image> getAll() {

        // TODO

        return list;
    }

    public static Image getById(int id) {
        Image i = new Image();

        // TODO

        return i;
    }

    public static boolean saveImg(Image i) {

        // TODO

        return true;
    }

    

}
