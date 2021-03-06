package com.nathy.app.query.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nathy.app.command.api.data.Bid;
import com.nathy.app.request.BidRequest;
import com.nathy.app.response.BidResponse;
import com.nathy.app.service.BidService;
 

@RestController
@RequestMapping("/e-auction/api/v1/bids")
public class BidQueryController {
	Logger logger = LoggerFactory.getLogger(BidQueryController.class);
	@Autowired
	BidService bidService;	
	
	
//	@Autowired
//	private QueryGateway queryGateway;	
//	public BidsQueryController(QueryGateway queryGateway) {
//        this.queryGateway = queryGateway;
//    }
//    @GetMapping
//    public List<BidRestModel> getAllBids() {
//        GetBidsQuery getBidsQuery =
//                new GetBidsQuery();
//
//        List<BidRestModel> bidRestModels =
//        queryGateway.query(getBidsQuery,
//                ResponseTypes.multipleInstancesOf(BidRestModel.class))
//                .join();
//
//        return bidRestModels;
//    }

	
//    @PostMapping("/{productId}")
//	public ResponseEntity<BidResponse> addBid(@PathVariable String productId, @RequestBody BidRequest bidRequest) {
//		//logger.info("dd");
//		return new ResponseEntity<>(bidService.addBid(productId, bidRequest), HttpStatus.CREATED);
//		
//	}
	//addBid
	
//	@PutMapping("/update-bid/{productId}/{buyerEmail}/{newBidAmount}")
//	public ResponseEntity<BidResponse> updateBidAmount(@PathVariable String productId, 
//			@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) {
//		 
//		Bid bid = bidService.updateBidAmount(productId, buyerEmail, newBidAmount);	 
//		return new ResponseEntity<>(new BidResponse(bid), HttpStatus.OK); 	
//		
//	}
//	@PutMapping("/{buyerEmail}/{newBidAmount}")
//	public ResponseEntity<BidResponse> updateBidAmountUsingEmail (@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) {		
//		 
//		return new ResponseEntity<>(bidService.updateBidAmountUsingEmail(buyerEmail, newBidAmount), HttpStatus.OK); 
//	}
	 
		
	
	//@GetMapping("/getAllBids/{productId}")
	//public ResponseEntity<List<BidResponse>> getAllBidsByProductId(@PathVariable String productId){
	@GetMapping("/getAllBids/{productId}")
	public ResponseEntity<List<BidResponse>> getAllBidsByProductId(@PathVariable String productId){
		logger.info("Test Inside bid before reading bidList");
		List<Bid> bidList = bidService.getAllBidsByProductId(productId); 
		logger.info("after");
		logger.info("BidList"+bidList);
		if(bidList.isEmpty() ) {
			logger.info("inside empty checking");
			return ResponseEntity.notFound().build();
		}
		logger.info("Test checking  bidList empty");
		List<BidResponse> bidResponseList = new ArrayList<BidResponse>();
		bidList.stream().filter(f->f!=null).sorted(Comparator.comparing(Bid::getAmount).reversed())
		.forEach(bid->{bidResponseList.add(new BidResponse(bid));});
		logger.info("Test checking  bidList filtering");
		return ResponseEntity.ok(bidResponseList);
	}
	@GetMapping("/{email}")
	public Boolean emailExist(@PathVariable String email){
		logger.info("10");
		return bidService.emailExist(email);		
		
	}
	@GetMapping("/getAllBids")
	public ResponseEntity<List<BidResponse>> getAllBids(){
		logger.info("Befor retrieving");
		List<Bid> bidList = bidService.getAllBids(); 
		logger.info("After retrieving");
		if(bidList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}		
		List<BidResponse> bidResponseList = new ArrayList<BidResponse>();
		
		bidList.stream().forEach(bid->{bidResponseList.add(new BidResponse(bid));});
		return ResponseEntity.ok(bidResponseList);
	}
	
	@GetMapping("/hello/{bidId}")
	public ResponseEntity<BidResponse> getBidById(@PathVariable String bidId){
		Bid bid = bidService.getBidById(bidId);
		return ResponseEntity.ok(new BidResponse(bid));
 
	}

	
}
	

	
	

