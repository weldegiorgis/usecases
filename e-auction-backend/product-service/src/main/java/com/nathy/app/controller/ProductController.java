package com.nathy.app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nathy.app.entity.Product;
import com.nathy.app.exception.GlobalExceptionHandler;
import com.nathy.app.exception.ResourceNotFoundException;
import com.nathy.app.request.BidRequest;
import com.nathy.app.request.CreateProductRequest;
import com.nathy.app.response.BidResponse;
import com.nathy.app.response.ProductResponse;
import com.nathy.app.service.ProductService;

@RestController
@RequestMapping("/e-auction/api/v1/product")
@RefreshScope
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
		logger.info("before create product ");
		return new ResponseEntity<>(productService.createProduct(createProductRequest), HttpStatus.CREATED);		
	}
	@GetMapping("/getById/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String id) {
		// TODO Auto-generated method stub
				
		return ResponseEntity.ok(productService.getById(id));		
	}
//	@GetMapping
//	public ResponseEntity<List<ProductResponse>> getAllProducts() {
//		List<Product> productList = productService.getAllProducts(); 
//		if(productList.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}		
//		List<ProductResponse> productResponseList = new ArrayList<ProductResponse>();
//		
//		productList.stream().forEach(p->{
//			productResponseList.add(new ProductResponse(p));});
//		 
//		return ResponseEntity.ok(productResponseList	);		
//	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> productList = productService.getAllProducts(); 
		if(productList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}		
//		List<ProductResponse> productResponseList = new ArrayList<ProductResponse>();
//		
//		productList.stream().forEach(p->{
//			productResponseList.add(new ProductResponse(p));});
		 
		return ResponseEntity.ok(productList);		
	}
	
	
	
//	@PutMapping("/add-bid/{productId}")	
//	public ResponseEntity<ProductResponse> addBid2Product(@PathVariable String productId, @RequestBody BidResponse bidResponse) {
//		
//		return new ResponseEntity<>(productService.addBid2Product(productId, bidResponse), HttpStatus.OK); 
//	}
	@PutMapping("/{productId}/add-bid")	
	public ResponseEntity<ProductResponse> addBid2Product(@PathVariable String productId, @RequestBody BidRequest bidRequest) {
		logger.info("b");
		return new ResponseEntity<>(productService.addBid2Product(productId, bidRequest), HttpStatus.OK); 
	}
	
	//@PutMapping("/update-bid/{productId}/{buyerEmail}/{newBidAmount}")	
	//public ProductResponse updateBidAmount(@PathVariable String productId, @PathVariable String buyerEmail, BigDecimal newBidAmount ) {
	//return productService.updateBidAmount(productId, buyerEmail, newBidAmount);
	//		
	//}	
	
	@PutMapping("/{productId}/{buyerEmail}/{newBidAmount}")	
	public ResponseEntity<ProductResponse> updateAmountBid(@PathVariable String productId, @PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount ) {
		return new ResponseEntity<>(productService.updateBidAmount(productId, buyerEmail, newBidAmount), HttpStatus.OK);		 
		
	}
	
	
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
//		
//		productService.deleteProduct(id);	
//		
//		return new ResponseEntity<>(" Product deleted successfully.", HttpStatus.OK);		
//	}	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
			
		logger.info("delete product");
		Product product = productService.findById(id);
		if(product.getBidEndDate().before(new Date())) {
			logger.info("delete checking");
			 new ResourceNotFoundException("Its Bid date is over", id, 0);
			return new ResponseEntity<String>("Its Bid date is over", HttpStatus.BAD_REQUEST);	
		}
		else if(product.getBids().isEmpty() || product.getBids() == null)  {
			logger.info("delete null");
			productService.deleteProduct(id);
			 
			//new ResourceNotFoundException("At least one bid is placed", id, 0);
			return new ResponseEntity<String>(" Product deleted successfully.", HttpStatus.OK);
			//	
		}
//		else {		
//		
//		logger.info("deleting");
//		productService.deleteProduct(id);		
//		
//		
//		}
		//return new GlobalExceptionHandler();
		return new ResponseEntity<String>("At least one bid is placed", HttpStatus.BAD_REQUEST);
			
	}	

}
