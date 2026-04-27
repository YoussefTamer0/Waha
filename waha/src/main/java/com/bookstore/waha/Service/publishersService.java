package com.bookstore.waha.Service;

import com.bookstore.waha.model.Publisher;
import com.bookstore.waha.repository.publishersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class publishersService {
   private final publishersRepository publisherrepo;
   public publishersService( publishersRepository publisherrepo){
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
}
