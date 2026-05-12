package com.bookstore.waha.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID")
    private Long bookID;

    @Column(name = "Title")
    private String title;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "Genre")
    private String genre;

    @Column(name = "Type")
    private String type;

    @Column(name = "PublicationYear")
    private Integer publicationYear;

    @Column(name = "Price")
    private double price;

    @Column(name = "book_Condition")
    private String bookCondition;

    @ManyToOne
    @JoinColumn(name = "AuthorID")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "PublisherID")
    private Publisher publisher;

    public Long getBookID() { return bookID; }
    public void setBookID(Long bookID) { this.bookID = bookID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getBookCondition() { return bookCondition; }
    public void setBookCondition(String bookCondition) { this.bookCondition = bookCondition; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
}
