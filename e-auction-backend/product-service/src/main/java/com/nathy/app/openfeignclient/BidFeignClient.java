package com.nathy.app.openfeignclient;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nathy.app.request.BidRequest;
import com.nathy.app.response.BidResponse;

//@FeignClient(url="${bid.service.url}", value="bido-feign-client")
@FeignClient(value="api-gateway")
public interface BidFeignClient {
	//@PostMapping
//	public BidResponse addBid(@RequestBody BidRequest bidRequest);
    @PostMapping("/bid-producer-service/e-auction/api/v1/commandBids/{productId}")
	public BidResponse addBid(@PathVariable String productId, @RequestBody BidRequest bidRequest);
    
	@PutMapping("/bid-producer-service/e-auction/api/v1/commandBids/{productId}/{buyerEmail}/{newBidAmount}")
	public BidResponse updateBidAmountUsingEmail (@PathVariable String productId, @PathVariable("buyerEmail") String email, 
			@PathVariable("newBidAmount") BigDecimal newBidAmount);
	@GetMapping("/bid-producer-service/e-auction/api/v1/queryBids/{email}")
	public Boolean emailExist(@PathVariable String email);
	
	@GetMapping("/bid-producer-service/e-auction/api/v1/bid/searchBidByEmail/{email}")
	public BidResponse searchBidByEmail(@PathVariable String email);
//	@PutMapping("/{buyerEmail}/{newBidAmount}")
//	public BidResponse updateBidAmountUsingEmail (@PathVariable String email, @PathVariable BigDecimal newBidAmount) {		
	//public Boolean emailExist(String email);
		

}
