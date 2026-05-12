package com.bookstore.waha.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisherID")
    private Long publisherID;

    @NotBlank(message = "Country is required")
    @Column(name = "Country")
    private String country;      // FIX: renamed field from "Country" to "country" (Java convention)

    @NotBlank(message = "Publisher name is required")
    @Column(name = "Name")
    private String name;         // FIX: renamed field from "Name" to "name" (Java convention)

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Publisher() {}

    public Publisher(Long publisherID, String country, String name, List<Book> books) {
        this.publisherID = publisherID;
        this.country = country;
        this.name = name;
        this.books = books;
    }

    public Long getPublisherID() { return publisherID; }
    public void setPublisherID(Long publisherID) { this.publisherID = publisherID; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // FIX: renamed getBook() → getBooks() to match the List return type
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

    public void addBook(Book book) {
        books.add(book);
        book.setPublisher(this);
    }
}
