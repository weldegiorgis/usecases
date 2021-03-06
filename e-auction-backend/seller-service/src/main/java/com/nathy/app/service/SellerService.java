package com.nathy.app.service;

 

import com.nathy.app.entity.Seller;
import com.nathy.app.feignclients.CommonFeintClient;
import com.nathy.app.repository.SellerRepository;
import com.nathy.app.request.CreateSellerRequest;
import com.nathy.app.response.Product;
import com.nathy.app.response.ProductResponse;
import com.nathy.app.response.SellerResponse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class SellerService {
	
	
	Logger logger = LoggerFactory.getLogger(SellerService.class);

	@Autowired
	SellerRepository sellerRepository;

//	@Autowired
//	AddressFeignClient addressFeignClient;
//	
//	@Autowired
//	ProductFeignClient productFeignClient;
//	@Autowired
//	BidFeignClient bidFeignClient;
	
	@Autowired
	CommonFeintClient commonFeintClient;
	
	@Autowired
	CommonService commonService;

	public SellerResponse createSeller(CreateSellerRequest createSellerRequest) {

		Seller seller = new Seller();
		seller.setFirstName(createSellerRequest.getFirstName());
		seller.setLastName(createSellerRequest.getLastName());
		seller.setEmail(createSellerRequest.getEmail());
		seller.setPhone(createSellerRequest.getPhone());			
		seller.setAddressId(createSellerRequest.getAddressId());
		seller = sellerRepository.save(seller);
		
		SellerResponse sellerResponse = new SellerResponse(seller);
		
		//sellerResponse.setAddressResponse(addressFeignClient.getById(seller.getAddressId()));
		//sellerResponse.setAddressResponse(commonFeintClient.getAddressById(seller.getAddressId()));		
		sellerResponse.setAddressResponse(commonService.getAddressById(seller.getAddressId()));		

		return sellerResponse;
	}
	
	public SellerResponse getById (String id) {
		Seller seller = sellerRepository.findById(id).get();
		SellerResponse sellerResponse = new SellerResponse(seller);		
		//sellerResponse.setAddressResponse(commonFeintClient.getAddressById(seller.getAddressId()));		
		
		sellerResponse.setAddressResponse(commonService.getAddressById(seller.getAddressId()));		
		
		return sellerResponse;
	}

	public SellerResponse addProductBySeller(CreateSellerRequest createSellerRequest) {
		Seller seller = new Seller();
		seller.setFirstName(createSellerRequest.getFirstName());
		seller.setLastName(createSellerRequest.getLastName());
		seller.setEmail(createSellerRequest.getEmail());
		seller.setPhone(createSellerRequest.getPhone());			
		seller.setAddressId(createSellerRequest.getAddressId());
		seller = sellerRepository.save(seller);		
		SellerResponse sellerResponse = new SellerResponse(seller);
		logger.info("at seller service- before rest call");
		sellerResponse.setProductResponse(
				commonFeintClient.createProduct(createSellerRequest.getCreateProductRequest()));
		logger.info("after product rest call");
		sellerResponse.setAddressResponse(commonService.getAddressById(seller.getAddressId()));		
		logger.info("at seller service after rest call");
		return sellerResponse;
	}

	public String deleteProduct(String id) {
		return commonService.deleteProduct(id);		
	}
	public ProductResponse showBids(String productId) {
		logger.info("Test in service at the beginning");
		ProductResponse productResponse = commonService.getProductById(productId);
		logger.info("Test in service after productResponse");
		productResponse.setBidsList(commonService.getAllBidsByProductId(productId));
		logger.info("Test in service after setting setBidsList");
		//productResponse.setBids(productResponse.);
		return productResponse;
	}

	public ResponseEntity<List<Product>> showAllProducts() {
		return commonFeintClient.getAllProducts();
	}

}
