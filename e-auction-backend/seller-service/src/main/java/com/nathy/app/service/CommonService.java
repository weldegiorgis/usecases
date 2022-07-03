package com.nathy.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nathy.app.feignclients.CommonFeintClient;
import com.nathy.app.request.CreateProductRequest;
import com.nathy.app.response.AddressResponse;
import com.nathy.app.response.BidResponse;
import com.nathy.app.response.ProductResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CommonService {
	
	Logger logger = LoggerFactory.getLogger(CommonService.class);
	
	long count = 1;	

 
	@Autowired
	CommonFeintClient commonFeintClient;

	@CircuitBreaker(name = "addressService",
			fallbackMethod = "fallbackGetAddressById")
	public AddressResponse getAddressById (String addressId) {
		logger.info("count = " + count);
		count++;
		AddressResponse addressResponse = 
				commonFeintClient.getAddressById(addressId);
		
		return addressResponse;
	}
	

	@CircuitBreaker(name = "sellerService",
			fallbackMethod = "fallbackDeleteProduct")
	public String deleteProduct(String id) {
		return commonFeintClient.deleteProduct(id);		
	}
	
	@CircuitBreaker(name = "sellerService",
			fallbackMethod = "fallbackGetProductById")
	public ProductResponse getProductById(String id) {
		return commonFeintClient.getProductById(id);
	
	}
	public ProductResponse fallbackGetProductById(String id, Throwable th){
		logger.error("Error = " + th);
		return new ProductResponse();
		
	}
	@CircuitBreaker(name = "sellerService",
			fallbackMethod = "fallbackGetAllBidsByProductById")
	public List<BidResponse> getAllBidsByProductId(String productId){
		return commonFeintClient.getAllBidsByProductId(productId);		
	}
	
	public List<BidResponse> fallbackGetAllBidsByProductById(String productId, Throwable th){
		logger.error("Error = " + th);
		return null;
	}
	public String fallbackDeleteProduct(String id, Throwable th) {
		logger.error("Error = " + th);
		return "Problem may be happened";		
	}
	public AddressResponse fallbackGetAddressById (String addressId, Throwable th) {
		logger.error("Error = " + th);
		return new AddressResponse();
	}
	
	
	
}
