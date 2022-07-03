package com.nathy.app.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nathy.app.entity.Seller;

@Repository
public interface SellerRepository extends MongoRepository<Seller, String> {

	
	
}
