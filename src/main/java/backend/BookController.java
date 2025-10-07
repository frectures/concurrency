package backend;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        books.add(new Book("Effective Java", "Joshua Bloch", 2017));
        books.add(new Book("Java Concurrency in Practice", "Brian Goetz", 2006));
    }

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{index}")
    public Book getBookAt(@PathVariable("index") int index) {
        return books.get(index);
    }

    @PostMapping
    public Iterable<Book> addBook(@RequestBody Book newBook) {
        books.add(newBook);
        return books;
    }
}
