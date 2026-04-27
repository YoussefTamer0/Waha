package com.bookstore.waha.model;
import java.util.ArrayList;

import jakarta.persistence.*;


@Entity
@Table(name="publishers")
public class Publisher {
    @Id
    @Column(name="publisherID")
    private Integer publisherID;
    @Column(name="Country")
    private String Country;
    @OneToMany(mappedBy="publisher")
    private ArrayList<Book> books;
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
    public ArrayList<Book> getBook(){
        return books;
    }
    public void setBook(ArrayList<Book> books) {
        this.books=books;
    }
    public void addBook(Book book) {
        books.add(book);
    }

}
