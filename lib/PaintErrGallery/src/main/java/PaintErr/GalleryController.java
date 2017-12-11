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

}
