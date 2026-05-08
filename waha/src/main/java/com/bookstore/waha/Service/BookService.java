package com.bookstore.waha.Service;

import com.bookstore.waha.model.Author;
import com.bookstore.waha.model.Book;
import com.bookstore.waha.model.Publisher;
import com.bookstore.waha.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        if (book.getBookID() != null && bookRepository.existsById(book.getBookID())) {
            throw new RuntimeException("Book already exists with ID: " + book.getBookID());
        }
        return bookRepository.save(book);
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Long id) {
        if (id == null) {
            throw new RuntimeException("Book ID cannot be null");
        }
        return bookRepository.findById(id);
    }

    public Book getBookById(Long id) {
        return findBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }

    public List<Book> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllBooks();
        }
        return bookRepository.findAll().stream()
                .filter(b -> b.getTitle() != null &&
                        b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public List<Book> filterByGenre(String genre) {
        return bookRepository.findAll().stream()
                .filter(b -> genre != null && genre.equalsIgnoreCase(b.getGenre()))
                .toList();
    }

    public List<Book> filterByType(String type) {
        return bookRepository.findAll().stream()
                .filter(b -> type != null && type.equalsIgnoreCase(b.getType()))
                .toList();
    }

    public List<Book> filterByCondition(String condition) {
        return bookRepository.findAll().stream()
                .filter(b -> condition != null && condition.equalsIgnoreCase(b.getBookCondition()))
                .toList();
    }

    public List<Book> filterByAuthor(Author author) {
        return bookRepository.findAll().stream()
                .filter(b -> author != null && author.equals(b.getAuthor()))
                .toList();
    }

    public List<Book> filterByPublisher(Publisher publisher) {
        return bookRepository.findAll().stream()
                .filter(b -> publisher != null && publisher.equals(b.getPublisher()))
                .toList();
    }


    @Transactional
    public Book updateBook(Book updatedBook) {
        if (updatedBook.getBookID() == null) {
            throw new RuntimeException("Book ID cannot be null for update");
        }
        if (!bookRepository.existsById(updatedBook.getBookID())) {
            throw new RuntimeException("Book not found with ID: " + updatedBook.getBookID());
        }
        return bookRepository.save(updatedBook);
    }

    @Transactional
    public Book updateBookFields(Long id, String title, String isbn, String genre,
                                 String type, Integer publicationYear,
                                 double price, String condition) {
        Book book = getBookById(id);
        if (title       != null) book.setTitle(title);
        if (isbn        != null) book.setIsbn(isbn);
        if (genre       != null) book.setGenre(genre);
        if (type        != null) book.setType(type);
        if (publicationYear != null) book.setPublicationYear(publicationYear);
        if (price       >= 0)   book.setPrice(price);
        if (condition   != null) book.setBookCondition(condition);
        return bookRepository.save(book);
    }



    public void deleteBook(Book book) {
        if (book.getBookID() == null || !bookRepository.existsById(book.getBookID())) {
            throw new RuntimeException("Cannot delete — book does not exist");
        }
        bookRepository.delete(book);
    }

    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete — no book found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }


    public boolean bookExists(Long id) {
        return id != null && bookRepository.existsById(id);
    }

    public long countBooks() {
        return bookRepository.count();
    }
}