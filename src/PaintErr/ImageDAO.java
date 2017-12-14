package PaintErr;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ImageDAO {

    public static List<Image> list = new ArrayList<>();

    private static String url = "jdbc:mysql://localhost:3306/world?useSSL=false";
    private static String u = "imguser";
    private static String p = "paint";

    private static Connection con;

    // to get all Image records from the database
    public static List<Image> getAll() throws SQLException, IOException {

        // open a connection to the database, in case of error return empty list
        try {
            con = DriverManager.getConnection(url, u, p);
        } catch (SQLException ex) {
            System.out.println("Could not connect to database!");
            return list;
        }

        // select all items in the "img"-table
        String sql = "SELECT * FROM img;";

        // create and execute statement
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // empty list for a refreshed list
        list = new ArrayList<>();

        File loadImg, loadThumbnail;
        FileOutputStream imgInput, thumbnailInput;

        byte[] b;
        Blob blob;

        int imgcounter = 0, thumbnailcounter = 0;

        while(rs.next()){

            imgcounter++;
            thumbnailcounter++;

//            loadImg = new File("./src/img/", "loadtemp" + imgcounter + ".png");
            loadImg = new File("./", "loadtemp" + imgcounter + ".png");
            imgInput = new FileOutputStream(loadImg);

//            loadThumbnail = new File("./src/img/", "loadthumbnailtemp" + thumbnailcounter + ".png");
            loadThumbnail = new File("./", "loadthumbnailtemp" + thumbnailcounter + ".png");
            thumbnailInput = new FileOutputStream(loadThumbnail);

            blob = rs.getBlob("img");
            b = blob.getBytes(1,(int)blob.length());
            imgInput.write(b);

            blob=rs.getBlob("thumbnail");
            b=blob.getBytes(1,(int)blob.length());
            thumbnailInput.write(b);

            Image i = new Image(
                    rs.getInt("ID"),
                    rs.getString("name"),
                    rs.getString("description"),
                    loadImg,
                    loadThumbnail
            );

            System.out.println("Found by All - " + i.getID() + " - Name: " + i.getName() + ", Description: " + i.getDescription());

            list.add(i);
        }

        // return the compiled list
        return list;
    }

    // to check if the ID is already in the database
    public static boolean doesIDExist(int id) throws SQLException {

        // try for a connection, in case of error return an empty object
        try {
            con = DriverManager.getConnection(url, u, p);
        } catch (SQLException ex) {
            System.out.println("Could not connect to database!");
            return false;
        }

        // query for a specific record
        String sql = "SELECT * FROM img WHERE ID=?;";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            return true;
        }
        return false;
    }

    // to get one Image record from the database, identified by ID
    public static Image getById(int id) throws SQLException, IOException {

        Image i = null;

        // try for a connection, in case of error return an empty object
        try {
            con = DriverManager.getConnection(url, u, p);
        } catch (SQLException ex) {
            System.out.println("Could not connect to database!");
            return i;
        }

        // query for a specific record
        String sql = "SELECT * FROM img WHERE ID=?;";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

//        File loadImg = new File("./src/img/", "loadtemp.png");
        File loadImg = new File("./", "loadtemp.png");
        FileOutputStream imgInput = new FileOutputStream(loadImg);

//        File loadThumbnail = new File("./src/img/", "loadthumbnailtemp.png");
        File loadThumbnail = new File("./", "loadthumbnailtemp.png");
        FileOutputStream thumbnailInput = new FileOutputStream(loadThumbnail);

        byte[] b;
        Blob blob;

        while(rs.next()){

            blob=rs.getBlob("img");
            b=blob.getBytes(1,(int)blob.length());
            imgInput.write(b);

            blob=rs.getBlob("thumbnail");
            b=blob.getBytes(1,(int)blob.length());
            thumbnailInput.write(b);

            i = new Image(
                    rs.getInt("ID"),
                    rs.getString("name"),
                    rs.getString("description"),
                    loadImg,
                    loadThumbnail
            );
        }

        System.out.println("Found by ID - " + i.getID() + " - Name: " + i.getName() + ", Description: " + i.getDescription());

        // return the found (or null) Image
        return i;
    }

    public static boolean saveImg(Image i) throws FileNotFoundException, SQLException {

        File img = i.getImg();
        FileInputStream imgInput = new FileInputStream(img);

        File thumbnail = i.getThumbnail();
        FileInputStream thumbInput = new FileInputStream(thumbnail);

        // try for a connection, in case of error return false for failed save
        try {
            con = DriverManager.getConnection(url, u, p);
        } catch (SQLException ex) {
            System.out.println("Could not connect to database!");
            System.out.println(ex.getMessage());
            return false;
        }

        // int to receive the result of create / update
        int result = 0;

        try {
            // check if the object has an ID (exists already)
            // create statement if no ID, update if has ID
            if (i.getID() == 0) {

                System.out.println("Saving - NEW - Name: " + i.getName() + ", Description: " + i.getDescription());

                // create statement to create a new record
                String sql = "INSERT img (name, description, thumbnail, img) VALUES (?, ?, ?, ?);";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, i.getName());
                ps.setString(2, i.getDescription());
                ps.setBinaryStream(3, thumbInput, (int)thumbnail.length());
                ps.setBinaryStream(4, imgInput, (int)img.length());

                result = ps.executeUpdate();

            } else {

                System.out.println("Saving - " + i.getID() + " - Name: " + i.getName() + ", Description: " + i.getDescription());

                // update statement to update an existing record in database
                String sql = "UPDATE img SET name = ?, description = ?, thumbnail = ?, img = ? WHERE ID=?;";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, i.getName());
                ps.setString(2, i.getDescription());
                ps.setBinaryStream(3, thumbInput, (int)thumbnail.length());
                ps.setBinaryStream(4, imgInput, (int)img.length());
                ps.setInt(5, i.getID());

                result = ps.executeUpdate();

            }
        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        // if the create or update affected 1 row (was successful), return true for a successful save
        if (result == 1) return true;
        else return false;
    }

    public static File saveToFile(ResizableCanvas canvas) throws FileNotFoundException {

        // Take a snapshot (WritableImage) from the given scene
        WritableImage writableImage = canvas.snapshot(null, null);

        // Write snapshot to file system as a .png image
//        File img = new File("./src/img/", "savetemp.png");
        File img = new File("./", "savetemp.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null),
                    "png", img);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return img;
    }

    public static File getThumbnail(File img) throws IOException {

        BufferedImage bufferedThumbnail = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        bufferedThumbnail.createGraphics().drawImage(ImageIO.read(img).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH),0,0,null);

//        File thumbImg = new File("./src/img/", "savethumbtemp.png");
        File thumbImg = new File("./", "savethumbtemp.png");
        ImageIO.write(bufferedThumbnail, "png", thumbImg);

        return thumbImg;
    }

    // loop through all files in "img"-folder and return the last (in alphabetical order) filename without extension
    public static String getCurrentLastImgName() {

//        File imgDirectory = new File("./src/img/");
        File imgDirectory = new File("./");
        File[] files = imgDirectory.listFiles();

        TreeSet<String> filenames = new TreeSet<>();

        for (File f : files) {
            String filename = f.getName();
            String extension = filename.substring(filename.length() - 3);
            if ("png".equals(extension)) {
                filenames.add(filename.substring(0, filename.length() - 3));
            }
        }

        if (!filenames.isEmpty())return filenames.last();
        else return "0";

    }

}
