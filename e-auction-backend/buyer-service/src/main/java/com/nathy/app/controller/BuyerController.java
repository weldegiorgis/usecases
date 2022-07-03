package com.nathy.app.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nathy.app.request.CreateBuyerRequest;
import com.nathy.app.response.BidResponse;
import com.nathy.app.response.BuyerResponse;
import com.nathy.app.response.ProductResponse;
import com.nathy.app.service.BuyerService;

@RestController
@RequestMapping("/e-auction/api/v1/buyer")
@RefreshScope
public class BuyerController {
	
	Logger logger = LoggerFactory.getLogger(BuyerController.class);
	@Autowired
	BuyerService buyerService;
	

	@PostMapping("/place-bid")
	public ResponseEntity<BuyerResponse> createBuyer(@Valid @RequestBody CreateBuyerRequest createBuyerRequest) {
		 
		return new ResponseEntity<>(buyerService.createBuyer(createBuyerRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BuyerResponse> getById(@PathVariable String id) {
		return ResponseEntity.ok(buyerService.getById(id));
	}
 
	
	@PutMapping("/update-bid/{productId}/{buyerEmail}/{newBidAmount}")
	public ResponseEntity<ProductResponse> updateBidAmount(@PathVariable String productId, 
			@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) {
		return new ResponseEntity<>(buyerService.updateBidAmount(productId, buyerEmail, newBidAmount), HttpStatus.OK);
		
	}
 
}
