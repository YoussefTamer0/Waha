package com.bookstore.waha.model;
import jakarta.persistence.*;

@Entity
@Table(name="Author")

public class Author {
@Id
@GeneratedValue
@Column(name="AuthorID")
private Long AuthorID;

@ManyToOne
@JoinColumn(name="bookID")
private Book Book;
@Column(name="firstName")
private String firstName;
@Column(name="lastName")


private String lastName;


	public Author() {

	}

public Author(long authorId, String firstName, String lastName) {
	
	AuthorID= authorId;
	
	this.firstName = firstName;
	this.lastName = lastName;
}

	public Long getAuthorId() {
	return AuthorID;
}
public void setAuthorId(Long authorId) {
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
public Book getBook() {
	return Book;
}
public void setBook(Book book) {
	Book = book;
}



}
