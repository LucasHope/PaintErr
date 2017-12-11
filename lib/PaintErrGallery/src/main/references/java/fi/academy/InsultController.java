package fi.academy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class InsultController {

    private static final String msg = "%s, you lousy cretin!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/insult")
    public Insult i(@RequestParam(value="name", defaultValue="Maggot") String name) {
        return new Insult(counter.incrementAndGet(),
                String.format(msg, name));
    }

}
