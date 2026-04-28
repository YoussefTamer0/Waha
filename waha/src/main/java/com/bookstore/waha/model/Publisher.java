package com.bookstore.waha.model;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name="publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="publisherID")
    private Integer publisherID;
    @Column(name="Country")
    private String Country;
    @OneToMany(mappedBy="publisher")
    private List<Book> books=new ArrayList<Book>();
    public Publisher() {}
    public Publisher(Integer publisherID, String country) {

        this.publisherID = publisherID;
        Country = country;

    }
    public Integer getPublisherID() {
        return publisherID;
    }
    public void setPublisherID(Integer publisherID) {
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

}
