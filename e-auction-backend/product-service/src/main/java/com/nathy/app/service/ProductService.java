package com.nathy.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nathy.app.entity.Product;
import com.nathy.app.exception.ResourceNotFoundException;
import com.nathy.app.openfeignclient.BidFeignClient;
import com.nathy.app.repository.ProductRepository;
import com.nathy.app.request.BidRequest;
import com.nathy.app.request.CreateProductRequest;
import com.nathy.app.response.BidResponse;
import com.nathy.app.response.ProductResponse;

@Service
public class ProductService {
	Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	BidFeignClient bidFeignClient;

	public ProductResponse createProduct(CreateProductRequest createProductRequest) {
		Product product = new Product();
		product.setName(createProductRequest.getName());
		product.setShortDescription(createProductRequest.getShortDescription());
		product.setDetailDescription(createProductRequest.getDetailDescription());
		product.setCategory(createProductRequest.getCategory());
		product.setBidEndDate(createProductRequest.getBidEndDate()); //exception
		product.setStartingPrice(createProductRequest.getStartingPrice());
		logger.info("before saving the product");
		product = productRepository.save(product);
		
		ProductResponse productResponse = new ProductResponse(product);		
		
		return productResponse;
	}

	public ProductResponse getById(String id) {
		Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", "id", 0));
		ProductResponse productResponse = new ProductResponse(product);	
		//productResponse.setBidIdList(product.getBids());
		return productResponse;		
		 
	}
	public Product findById(String id) {
		Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", "id", 0));
		//ProductResponse productResponse = new ProductResponse(product);	
		return product;	
		 
	}
 
 

	public void deleteProduct(String id) {
 
			productRepository.deleteById(id);
 	
	}
	public ProductResponse addBid2Product(String productId, BidRequest bidRequest) {
		logger.info("c");
		//BidResponse bidResponse = bidFeignClient.addBid(bidRequest);
		BidResponse bidResponse = bidFeignClient.addBid(productId, bidRequest);
		bidResponse = bidFeignClient.searchBidByEmail(bidResponse.getEmail());
		//logger.info(bidResponse.getEmail());
		logger.info("1");
		if( !productRepository.findById(productId).isEmpty())  {
			logger.info("1.1");
			Product product = productRepository.findById(productId).get();
			logger.info("1.2");
			
			if(getById(productId).getBidEndDate().before(new Date())) {
				 new ResourceNotFoundException("Its Bid date is over", productId, 0);
			}
			logger.info("1.3");
			
//			if( !bidFeignClient.emailExist(bidResponse.getEmail())) {
//				 new ResourceNotFoundException("You can't bid by the email morethan one time: ", bidResponse.getEmail(), 0);
//			}	
			logger.info("1.4");
			if(bidResponse!=null) {
				product.setBids(bidResponse.getBidId());
			}
			logger.info("1.5");
			product = productRepository.save(product);
			logger.info("1.6");
			ProductResponse productResponse = new ProductResponse(product);
			logger.info("1.7");			
			productResponse.setBidResponse(bidResponse);
			return productResponse;
			
		}
		else {
			new ResourceNotFoundException("Product Id is not found",productId , 0);
			return null; 
			 
		}			
		
		
	}

	public ProductResponse updateBidAmount(String productId, String buyerEmail, BigDecimal newBidAmount) {
		BidResponse bidResponse = new BidResponse();
	 
		if( !productRepository.findById(productId).isEmpty())  {			 
			Product product = productRepository.findById(productId).get();			 
			if(!product.getBids().isEmpty()) {
				 bidResponse = bidFeignClient.updateBidAmountUsingEmail(productId, buyerEmail, newBidAmount);	
				 bidResponse = bidFeignClient.searchBidByEmail(bidResponse.getEmail());
				 
			}			
			ProductResponse productResponse = new ProductResponse(product);		
			productResponse.setBidResponse(bidResponse); //setBid(bidResponse);			 	
			return productResponse;
		}
		else {
			return null;
		}
	}

	public List<Product> getAllProducts() {		
		return productRepository.findAll();
	}

}
