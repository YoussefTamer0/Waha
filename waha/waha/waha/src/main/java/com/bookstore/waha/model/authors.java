package com.bookstore.waha.model;
import jakarta.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name="authors")

public class authors {
@Id
@Column(name="AuthorID")
private Integer AuthorID;
@OneToMany(mappedBy="author")
private ArrayList<books> Book;
@Column(name="firstName")
private String firstName;
@Column(name="lastName")
private String lastName;
public authors() {}
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
public ArrayList<books> getBook() {
	return Book;
}
public void setBooks(ArrayList<books> books) {
    Book = books;  
}
public void addBooks(books book) {
	Book.add(book);
}



}
