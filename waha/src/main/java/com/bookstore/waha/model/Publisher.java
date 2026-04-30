package com.bookstore.waha.model;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name="publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="publisherID")
    private Long publisherID;

    @NotBlank(message = "Country is required")
    @Column(name="Country")
    private String Country;

    @NotBlank(message = "Publisher name is required")
    @Column(name="Name")
    private String Name;

    @OneToMany(mappedBy="publisher")
    private List<Book> books=new ArrayList<Book>();

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public Long getPublisherID() {
        return publisherID;
    }
    public void setPublisherID(Long publisherID) {
        this.publisherID = publisherID;
    }
    public String getCountry() {
        return Country;
    }
    public void setCountry(String country) {
        Country = country;
    }
    public List<Book> getBook(){
        return books;
    }
    public void setBook(List<Book> books) {
        this.books=books;
    }
    public void addBook(Book book) {
        books.add(book);
        book.setPublisher(this);

    }

    public Publisher(Long publisherID, String country, String name, List<Book> books) {
        this.publisherID = publisherID;
        Country = country;
        Name = name;
        this.books = books;
    }
    public Publisher(){}
}
