package com.bookstore.waha.model;
import java.util.ArrayList;

import jakarta.persistence.*;


@Entity
@Table(name="publishers")
public class publishers {
@Id
@Column(name="publisherID")
private Integer publisherID;
@Column(name="Country")
private String Country;
@OneToMany(mappedBy="publisher")
private ArrayList<books> Book;
public publishers() {}
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
public ArrayList<books> getBook(){
	return Book;
}
public void setBook(ArrayList<books> books) {
	Book=books;
}
public void addBook(books book) {
	Book.add(book);
	}

}
