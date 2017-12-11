package fi.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AforismDAO {

    @Autowired
    JdbcTemplate jdbc = new JdbcTemplate();

    List<Aforism> aforisms = new ArrayList<>();


    public void createAforism(String text, String author) {
        jdbc.update("INSERT aforism (text, author) values (?, ?)", new Object[] {text, author});
    }

    public void updateAforism(int id, String text, String author) {
        jdbc.update("UPDATE aforism SET text=?, author=? WHERE id = ?",
                new Object[] {text, author, id});
    }

    public void deleteAforism(int id) {
        jdbc.update("DELETE FROM aforism WHERE id = ?", new Object[] {id});
    }

    // query for the full list
    public List<Aforism> list() {

        aforisms = new ArrayList<>();

        jdbc.query(
                "SELECT id, text, author FROM aforism",
                (rs, rowNum) ->
                        new Aforism(
                                rs.getInt("id"),
                                rs.getString("text"),
                                rs.getString("author")
                        )
        ).forEach(
                c -> {
                    aforisms.add(c);
                }
        );

        return aforisms;
    }

    public Object list(int id) {

        aforisms = new ArrayList<>();

        jdbc.query(
                "SELECT id, text, author FROM aforism where id=?", new Object[] {id},
                (rs, rowNum) ->
                        new Aforism(
                                rs.getInt("id"),
                                rs.getString("text"),
                                rs.getString("author")
                        )
        ).forEach(
                c -> {
                    aforisms.add(c);
                }
        );

        return aforisms;
    }
}
