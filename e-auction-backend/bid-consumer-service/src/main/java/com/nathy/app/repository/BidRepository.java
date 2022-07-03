package com.nathy.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nathy.app.entity.Bid;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {
  public List<Bid> findByProductId(String productId); 
  public Bid findByProductIdAndEmail(String productId, String email);  
  public Bid findByEmail(String email); 
  //public Boolean existByEmail(String email); 
  
}
 

