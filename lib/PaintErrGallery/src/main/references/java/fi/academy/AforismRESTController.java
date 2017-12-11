package fi.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/af")
public class AforismRESTController {

    @Autowired
    AforismDAO af;

    @GetMapping
    public @ResponseBody List<Aforism> findAll() {
        return af.list();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createAforism(
            @RequestParam(value="text", defaultValue="") String text,
            @RequestParam(value="author", defaultValue="") String author,
            Model model) {

        af.createAforism(text, author);

    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateAforism(
            @RequestParam(value="id", defaultValue="-1") String id,
            @RequestParam(value="text", defaultValue="") String text,
            @RequestParam(value="author", defaultValue="") String author,
            Model model) {

        af.updateAforism(Integer.parseInt(id), text, author);

    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAforism(
            @RequestParam(value="id", defaultValue= "-1") String id,
            Model model) {

        af.deleteAforism(Integer.parseInt(id));

    }

}
