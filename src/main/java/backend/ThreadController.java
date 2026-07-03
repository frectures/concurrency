package backend;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    @GetMapping("/current")
    public String getCurrentThreadName() throws InterruptedException {
        Thread.sleep(1000);
        return Thread.currentThread().getName();
    }
}
