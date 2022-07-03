package com.nathy.app.feignclient;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nathy.app.request.BidRequest;
import com.nathy.app.response.AddressResponse;
import com.nathy.app.response.BidResponse;
import com.nathy.app.response.ProductResponse;

@FeignClient(value="api-gateway")
public interface BuyerCommonFeignClient {
	@GetMapping("/product-service/e-auction/api/v1/product/getById/{id}")
	public ProductResponse getProductById(@PathVariable("id") String id);
	
//	@PostMapping
//	public ProductResponse addBid(@RequestBody BidRequest bidRequest);
	
	@PutMapping("/product-service/e-auction/api/v1/product/{productId}/add-bid")	
	public ProductResponse addBid2Product(@PathVariable String productId, @RequestBody BidRequest bidRequest);
	
//	@PutMapping("update-bid/{productId}/{buyerEmail}/{newBidAmount}")	
//	public ProductResponse updateAmountBid(@PathVariable String productId, @RequestBody BidResponse bidResponse);
	
	@PutMapping("/product-service/e-auction/api/v1/product/{productId}/{buyerEmail}/{newBidAmount}")	
	public ProductResponse updateAmountBid(@PathVariable("productId") String productId, 
			@PathVariable("buyerEmail") String buyerEmail, @PathVariable("newBidAmount") BigDecimal newBidAmount);

	//public ProductResponse updateAmountBid(String productId, String buyerEmail, BigDecimal newBidAmount);
	
	@PutMapping("/bid-service/e-auction/api/v1/bid/update-bid/{productId}/{buyerEmail}/{newBidAmount}")
	public BidResponse updateBidAmount(@PathVariable String productId, 
			@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount);
	@GetMapping("/address-service/e-auction/api/v1/address/getById/{id}")
	public AddressResponse getAddressById(@PathVariable("id") String id);

}
