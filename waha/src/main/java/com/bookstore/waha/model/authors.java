package com.bookstore.waha.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="authors")

public class authors {
@Id
@Column(name="AuthorID")
private Integer AuthorID;
@Column(name="bookID")
private books Book;
@Column(name="firstName")
private String firstName;
@Column(name="lastName")
private String lastName;
public authors(Integer authorId, String firstName, String lastName) {
	
	AuthorID= authorId;
	
	this.firstName = firstName;
	this.lastName = lastName;
}
public Integer getAuthorId() {
	return AuthorID;
}
public void setAuthorId(Integer authorId) {
	AuthorID = authorId;
}

public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public books getBook() {
	return Book;
}
public void setBook(books book) {
	Book = book;
}



}
