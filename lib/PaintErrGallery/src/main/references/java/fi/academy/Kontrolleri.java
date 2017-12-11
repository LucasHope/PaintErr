package fi.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class Kontrolleri {

    @Autowired
    Numero num;

    @Autowired
    JdbcTemplate jdbc;

    @RequestMapping("/customerlist")
    public String customerlist(Model model) {

//        // print progress to console
//        System.out.println("\nDB preparation");
//
//        jdbc.execute("DROP TABLE customers IF EXISTS");
//        jdbc.execute("CREATE TABLE customer(" +
//                "id SERIAL, firstname VARCHAR(255), lastname VARCHAR(255))");
//
//        // name array to firsname/lastname array inside a list
//        List<Object[]> splitUpNames = Arrays.asList("Andy Zerkis", "Bill Goldblum", "Charlie Factory", "Daniela Oklahoma")
//                .stream()
//                .map(s -> s.split(" "))
//                .collect(Collectors.toList());
//
//        // print (to be inserted) name pairs from arrays in the list (console)
//        splitUpNames.forEach(s -> System.out.println("Customer: " + String.format("%s %s", s[0], s[1])));
//
//        // Jdbc: batchUpdate to insert all customers from list
//        jdbc.batchUpdate("INSERT INTO customer(firstname, lastname) VALUES (?,?)", splitUpNames);

        // Query for all records
        System.out.println("\nQuery for all records:");

        List<Customer> customers = new ArrayList<>();

        jdbc.query(
                "SELECT id, firstname, lastname FROM customer",
                (rs, rowNum) ->
                        new Customer(rs.getLong("id"), rs.getString("firstname"), rs.getString("lastname"))
        ).forEach(
                c -> customers.add(c)
        );

        model.addAttribute("customers", customers);

        return "customerlist";
    }

    @RequestMapping("/counter")
    public String counter(Model model) {

        model.addAttribute("arvo", num);

        return "laskuri";
    }

    @RequestMapping("/forminput")
    public String forminput(Model model) {

        Hlo hlo = new Hlo();

        hlo.nimi = "Tuntematon";
        hlo.nimimerkki = "tuntematon";
        hlo.pituus = 170;
        hlo.paino = 65;
        hlo.setBmi(hlo.pituus, hlo.paino);

        model.addAttribute("hlo", hlo);
        return "forminput";
    }

    @RequestMapping("/formoutput")
    public String formoutput(
            //      @RequestParam(value="hlo") Hlo hlo,
            //      Model model) {
            @ModelAttribute Hlo hlo) {

        hlo.setBmi(hlo.pituus, hlo.paino);
//
//        model.addAttribute("nimi", hlo.nimi);
//        model.addAttribute("nimimerkki", hlo.nimimerkki);
//        model.addAttribute("pituus", hlo.pituus);
//        model.addAttribute("paino", hlo.paino);
//        model.addAttribute("bmi", hlo.bmi);
//
//        model.addAttribute("hlo", hlo);
        return "formoutput";

    }

    @RequestMapping("/tervehdi")
    public String tervehdi(
            @RequestParam(value="nimi", required=false, defaultValue="Antti") String name,
            @RequestParam(value="nimim", required=false, defaultValue="devaaja") String nimim,
            Model model) {

        model.addAttribute("nimi", name);
        model.addAttribute("nimim", nimim);
        return "terve";

    }


}