package PaintErr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("/gallery")
public class GalleryController {

    @Autowired
    ImgDAO dao;

    @GetMapping
    public String getAll(Model model) {

        List<Img> list = dao.getAll();

        List<Img64> list64 = dao.to64(list);

        model.addAttribute("images", list64);

        return "gallery";

    }

    @GetMapping("/gallery/{id}")
    public String getAll(
            @PathVariable int id,
            Model model) {

        Img img = dao.getById(id);

        Img64 img64 = dao.to64(img);

        model.addAttribute("img", img64);

        return "image";

    }

}
