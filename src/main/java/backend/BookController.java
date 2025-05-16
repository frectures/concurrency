package backend;

import io.vavr.collection.Vector;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/books")
public class BookController {

    private AtomicReference<Vector<Book>> books = new AtomicReference(Vector.of(
            new Book("Effective Java", "Joshua Bloch", 2017),
            new Book("Java Concurrency in Practice", "Brian Goetz", 2006)
    ));

    public BookController() {
    }

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return books.get();
    }

    @GetMapping("/{index}")
    public Book getBookAt(@PathVariable("index") int index) {
        return books.get().get(index);
    }

    @PostMapping
    public Iterable<Book> addBook(@RequestBody Book newBook) {
        return books.updateAndGet(v -> v.append(newBook));
    }
}
