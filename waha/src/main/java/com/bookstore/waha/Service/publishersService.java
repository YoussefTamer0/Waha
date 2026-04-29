package com.bookstore.waha.Service;

import com.bookstore.waha.model.Book;
import com.bookstore.waha.model.Publisher;
import com.bookstore.waha.repository.booksRepository;
import com.bookstore.waha.repository.publishersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class publishersService {
    private final booksRepository bookrepo;
   private final publishersRepository publisherrepo;
   public publishersService( publishersRepository publisherrepo, booksRepository bookrepo){
       this.bookrepo=bookrepo;
       this.publisherrepo=publisherrepo;
   }
   public void addPublisher(Publisher publisher){
       if(publisher.getPublisherID()!=null)
       {
           throw new RuntimeException("Publisher already exists.");
       }
       publisherrepo.save(publisher);
   }
   public void removePublisher(Publisher publisher){
       if(publisher.getPublisherID()==null)
       {
           throw new RuntimeException("Publisher doesnt exist.");
       }
       publisherrepo.deleteById(publisher.getPublisherID());
   }
   public List<Publisher> getAllPublisher(){
       List<Publisher> publishers= publisherrepo.findAll();
       return publishers;
   }
   public Publisher findPublisherbyID(Integer ID){
      Optional<Publisher> publisher= publisherrepo.findById(ID);
       return publisher.orElse(null);
   }
    @Transactional
    public void addExistingBookToPublisher(Integer publisherID, Integer bookID) {
        Publisher publisher = publisherrepo.findById(publisherID)
                .orElseThrow(() -> new RuntimeException("Publisher not found with ID: " + publisherID));

        Book book = bookrepo.findById(bookID)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookID));

        publisher.addBook(book);
        publisherrepo.save(publisher);
    }
}
