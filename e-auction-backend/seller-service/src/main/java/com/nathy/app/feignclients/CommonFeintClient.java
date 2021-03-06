package com.nathy.app.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nathy.app.request.CreateProductRequest;
import com.nathy.app.response.AddressResponse;
import com.nathy.app.response.BidResponse;
import com.nathy.app.response.Product;
import com.nathy.app.response.ProductResponse;

@FeignClient(value = "api-gateway")
public interface CommonFeintClient {
	@PostMapping("/product-service/e-auction/api/v1/product")
	public ProductResponse createProduct(@RequestBody CreateProductRequest createProductRequest);
	
	@DeleteMapping("/product-service/e-auction/api/v1/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") String id); 
	
	@GetMapping("/product-service/e-auction/api/v1/product/getById/{id}")
	public ProductResponse getProductById(@PathVariable("id") String id);
	
//	@GetMapping("/getAllBids/{productId}")
//	public List<BidResponse> getAllBidsByProductId(@PathVariable String productId);
	
	@GetMapping("/bid-producer-service/e-auction/api/v1/bid/getAllBids/{productId}")
	public List<BidResponse> getAllBidsByProductId(@PathVariable String productId);
	
	@GetMapping("/product-service/e-auction/api/v1/product")
	public ResponseEntity<List<Product>> getAllProducts();
	
//	@GetMapping("/product-service/e-auction/api/v1/product")
//	public ResponseEntity<List<ProductResponse>> getAllProducts();
	//	@GetMapping("/getAllBids")
	//	public List<BidResponse> getAllBidsByProductId();
	
	@GetMapping("/address-service/e-auction/api/v1/address/getById/{id}")
	public AddressResponse getAddressById(@PathVariable("id") String id);
	


}
