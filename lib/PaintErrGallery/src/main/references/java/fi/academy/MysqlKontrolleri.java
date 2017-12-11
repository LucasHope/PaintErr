package fi.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MysqlKontrolleri {

    @Autowired
    JdbcTemplate jdbc;

    // Country: code, name, continent, region, headofstate, population

    @RequestMapping("/countrylist")
    public String customerlist(Model model) {

        // Query for all records
        System.out.println("\nQuery for all records:");

        List<Country> countries = new ArrayList<>();

        // Country: code, name, continent, region, headofstate, population

        jdbc.query(
                "SELECT Code, Name, Continent, Region, HeadOfState, Population FROM country",
                (rs, rowNum) ->
                        new Country(
                                rs.getString("Code"),
                                rs.getString("Name"),
                                rs.getString("Continent"),
                                rs.getString("Region"),
                                rs.getString("HeadOfState"),
                                rs.getInt("Population")
                        )
        ).forEach(
                c -> countries.add(c)
        );

        model.addAttribute("countries", countries);

        return "myslilista";
    }


}