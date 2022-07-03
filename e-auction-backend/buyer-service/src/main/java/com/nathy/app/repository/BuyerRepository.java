package com.nathy.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nathy.app.entity.Buyer;

@Repository
public interface BuyerRepository extends MongoRepository<Buyer, String>{

}
