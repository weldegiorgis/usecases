package com.nathy.app.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nathy.app.controller.BuyerController;
import com.nathy.app.entity.Buyer; 
import com.nathy.app.feignclient.BuyerCommonFeignClient; 
import com.nathy.app.repository.BuyerRepository;
import com.nathy.app.request.BidRequest;
import com.nathy.app.request.CreateBuyerRequest;
import com.nathy.app.response.BidResponse;
import com.nathy.app.response.BuyerResponse;
import com.nathy.app.response.ProductResponse;
 

@Service
public class BuyerService {
	
	Logger logger = LoggerFactory.getLogger(BuyerService.class);
	Integer bidNumber = 0;
	@Autowired
	BuyerRepository buyerRepository;
	
//	@Autowired 
//	AddressFeignClient addressFeignClient;
//	
//	@Autowired 
//	BidFeignClient bidFeignClient;
//	
//	@Autowired 
//	ProductFeignClient productFeignClient;
	@Autowired
	BuyerCommonFeignClient buyerCommonFeignClient;

	public BuyerResponse createBuyer(CreateBuyerRequest createBuyerRequest) {
		
		Buyer buyer = new Buyer();
		buyer.setFirstName(createBuyerRequest.getFirstName());
		buyer.setLastName(createBuyerRequest.getLastName());
		buyer.setEmail(createBuyerRequest.getEmail());
		buyer.setPhone(createBuyerRequest.getPhone());
		buyer.setProductId(createBuyerRequest.getProductId());
		buyer.setBidamount(createBuyerRequest.getBidamount());
		buyer.setAddressId(createBuyerRequest.getAddressId());		 
		
		buyer = buyerRepository.save(buyer);
		
		BuyerResponse buyerResponse = new BuyerResponse(buyer);
		BidRequest bidRequest = new BidRequest();
		bidRequest.setAmount(buyerResponse.getBidamount());
		bidRequest.setEmail(buyerResponse.getEmail());
		bidRequest.setMobile(buyerResponse.getPhone());
		bidRequest.setName(buyerResponse.getLastName());
		//bidRequest.setName("Bid Number: "+bidNumber++);		
		//buyerResponse.setBidResponse(bidFeignClient.addBid(bidRequest));	
		//buyerResponse.setProductResponse(productFeignClient.addBid(bidRequest));
		
		//buyerResponse.setProductResponse(productFeignClient.addBid2Product(createBuyerRequest.getProductId(), bidFeignClient.addBid(bidRequest)));
		logger.info("a");
		//buyerResponse.setProductResponse(productFeignClient.addBid2Product(createBuyerRequest.getProductId(), bidRequest));
		buyerResponse.setProductResponse(buyerCommonFeignClient.addBid2Product(createBuyerRequest.getProductId(), bidRequest));
		
		logger.info("3");
		//buyerResponse.setAddressResponse(addressFeignClient.getById(buyer.getAddressId()));
		buyerResponse.setAddressResponse(buyerCommonFeignClient.getAddressById(buyer.getAddressId()));
		
		logger.info("4");
		return buyerResponse;
	}

	public BuyerResponse getById(String id) {
	 
		Buyer buyer = buyerRepository.findById(id).get();
		BuyerResponse buyerResponse = new BuyerResponse(buyer);
		
		//buyerResponse.setAddressResponse(addressFeignClient.getById(buyer.getAddressId()));
		buyerResponse.setAddressResponse(buyerCommonFeignClient.getAddressById(buyer.getAddressId()));
		
		
		return buyerResponse;
	}

	public ProductResponse updateBidAmount(String productId, String buyerEmail, BigDecimal newBidAmount) {
	    //productFeignClient.updateAmountBid(productId, buyerEmail, newBidAmount);
		//ProductResponse productResponse = productFeignClient.getById(productId);
		//BidResponse bidResponse = bidFeignClient.getById()
		//return bidFeignClient.updateBidAmount(productId, buyerEmail, newBidAmount);
		//ProductResponse productResponse = productFeignClient.updateAmountBid(productId, buyerEmail, newBidAmount);
		ProductResponse productResponse = buyerCommonFeignClient.updateAmountBid(productId, buyerEmail, newBidAmount);
		return productResponse;
	}

}
