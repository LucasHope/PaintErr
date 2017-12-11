package fi.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AforismController {

    @Autowired
    AforismDAO af;

    @GetMapping("/aforisms")
    public String afList(
            @RequestParam(value="nr", defaultValue="-1") String idstring,
            Model model) {

        int id = Integer.parseInt(idstring);

        if(id != -1) model.addAttribute("aforismlist", af.list(id));
        else model.addAttribute("aforismlist", af.list());

        return "Aforisms";

    }


    @PostMapping("/aforisms")
    public String afCreateChangeDelete(
            @RequestParam(value="nr", defaultValue="-1") String idstring,
            @RequestParam(value="text", defaultValue="") String text,
            @RequestParam(value="author", defaultValue="") String author,
            @RequestParam(value="type", defaultValue="") String reqtype,
            Model model) {

        int id = Integer.parseInt(idstring);

        switch(reqtype) {
            case "post":
                af.createAforism(text, author);
                break;
            case "put":
                af.updateAforism(id, text, author);
                break;
            case "delete":
                af.deleteAforism(id);
                break;
            default:
                break;
        }

        model.addAttribute("aforismlist", af.list());
        return "Aforisms";

    }


}
