package com.bookstore.waha.Service;

import com.bookstore.waha.Model.Author;
import com.bookstore.waha.Model.Book;
import com.bookstore.waha.Repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class AuthorService {

    private final BookRepository bookRepository;

    public AuthorService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Author> getAllAuthors() {
        return bookRepository.findAll().stream()
                .map(Book::getAuthor)
                .filter(a -> a != null)
                .distinct()
                .toList();
    }


    public Optional<Author> findAuthorById(Long authorId) {
        if (authorId == null) {
            throw new RuntimeException("Author ID cannot be null");
        }
        return bookRepository.findAll().stream()
                .map(Book::getAuthor)
                .filter(a -> a != null && authorId.equals(a.getAuthorId()))
                .findFirst();
    }


    public Author getAuthorById(Long authorId) {
        return findAuthorById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + authorId));
    }


    public List<Author> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllAuthors();
        }
        String kw = keyword.toLowerCase();
        return getAllAuthors().stream()
                .filter(a -> (a.getFirstName() != null && a.getFirstName().toLowerCase().contains(kw))
                        || (a.getLastName()  != null && a.getLastName().toLowerCase().contains(kw)))
                .toList();
    }


    public List<Book> getBooksByAuthor(Long authorId) {
        Author author = getAuthorById(authorId);
        return bookRepository.findAll().stream()
                .filter(b -> author.equals(b.getAuthor()))
                .toList();
    }



    @Transactional
    public Book assignAuthorToBook(Long bookId, Author author) {
        if (bookId == null) throw new RuntimeException("Book ID cannot be null");
        if (author == null) throw new RuntimeException("Author cannot be null");

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        book.setAuthor(author);
        author.setBook(book);
        return bookRepository.save(book);
    }


    @Transactional
    public Book removeAuthorFromBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));
        book.setAuthor(null);
        return bookRepository.save(book);
    }


    public String getFullName(Author author) {
        if (author == null) return "Unknown";
        String first = author.getFirstName() != null ? author.getFirstName() : "";
        String last  = author.getLastName()  != null ? author.getLastName()  : "";
        return (first + " " + last).trim();
    }

    public long countAuthors() {
        return getAllAuthors().size();
    }
}