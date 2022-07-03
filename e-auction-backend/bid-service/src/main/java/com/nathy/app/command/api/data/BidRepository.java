package com.nathy.app.command.api.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {
  public List<Bid> findByProductId(String productId); 
  public Bid findByProductIdAndEmail(String productId, String email);  
  public Bid findByEmail(String email); 
  public List<Bid> findAll();
  //public Boolean existByEmail(String email); 
  
}
 

