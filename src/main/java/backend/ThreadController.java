package backend;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    @GetMapping("/current")
    public String getCurrentThreadName() throws InterruptedException {
        Thread.sleep(1000);
        return Thread.currentThread().getName();
    }

    List<Integer> list = new ArrayList<>();

    @GetMapping("/race")
    public List<Integer> provokeDataRace() {
        list.add(list.size());
        return list;
    }
}
