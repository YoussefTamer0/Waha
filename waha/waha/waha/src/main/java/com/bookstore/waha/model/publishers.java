package com.bookstore.waha.model;
import jakarta.persistence.*;


@Entity
@Table(name="publishers")
public class publishers {
@Id
@Column(name="publisherID")
private Integer publisherID;
@Column(name="Country")
private String Country;
@Column(name="bookID")
private books Book;
public publishers(Integer publisherID, String country) {
	
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
public books getBook() {
	return Book;
}
public void setBook(books book) {
	Book = book;
}

}
