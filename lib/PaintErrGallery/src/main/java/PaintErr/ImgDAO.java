package PaintErr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Base64;
import java.util.Base64.Encoder;

@Service
public class ImgDAO {

    @Autowired
    JdbcTemplate jdbc = new JdbcTemplate();

    List<Img> images= new ArrayList<>();

    // query for the full list
    public List<Img> getAll() {

        images = new ArrayList<>();

        jdbc.query(
                "SELECT * FROM img;",
                (rs, rowNum) ->
                        new Img(
                                rs.getInt("ID"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getBlob("thumbnail"),
                                rs.getBlob("img")
                        )
        ).forEach(
                c -> {
                    images.add(c);
                }
        );

        return images;
    }

    public List<Img64> to64(List<Img> list) {

        List<Img64> list64 = new ArrayList<>();

        for (Img i : list) {

            String thumb64 = encodeTo64(i.getThumbnail());

            String img64 = encodeTo64(i.getImg());

            Img64 i64 = new Img64(
                    i.getID(),
                    i.getName(),
                    i.getDescription(),
                    thumb64,
                    img64
            );

            list64.add(i64);
        }

        return list64;

    }

    private String encodeTo64(Blob blob) {

        byte[] byteArray = new byte[0];

        try {
            byteArray = blob.getBytes(1, (int)blob.length());
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }

        Encoder e = Base64.getEncoder();
        byte[] img64 = e.encode(byteArray);
        String string64 = new String(img64);

        return string64;
    }

}
