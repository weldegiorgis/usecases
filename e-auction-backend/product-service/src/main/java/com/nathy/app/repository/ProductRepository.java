package com.nathy.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nathy.app.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}
