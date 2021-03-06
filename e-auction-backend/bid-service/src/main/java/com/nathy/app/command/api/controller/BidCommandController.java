package com.nathy.app.command.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid; 
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nathy.app.api.command.producer.BidEventProducer;
import com.nathy.app.command.api.commands.CreateBidCommand;
import com.nathy.app.command.api.data.Bid;
import com.nathy.app.command.api.data.BidEventType;

import com.nathy.app.response.BidResponse;
import com.nathy.app.service.BidService;

import lombok.extern.slf4j.Slf4j;
 

@RestController
@RequestMapping("/e-auction/api/v1/bid")
@Slf4j
public class BidCommandController {
	Logger logger = LoggerFactory.getLogger(BidCommandController.class);
	@Autowired
	BidService bidService;	
		
	@Autowired
	BidEventProducer bidEventProducer;
	
//	@PostMapping("/{productId}")
//	public ResponseEntity<BidResponse> addBid(@PathVariable String productId, @RequestBody BidRequest bidRequest) {
//		//logger.info("dd");
//		return new ResponseEntity<>(bidService.addBid(productId, bidRequest), HttpStatus.CREATED);
//		
//	}
	
	@PostMapping
	public ResponseEntity<CreateBidCommand> postBid( @RequestBody CreateBidCommand createBidCommand) throws JsonProcessingException {
		//invoke kafka producer
        log.info("before sendLibraryEvent");
        //libraryEventProducer.sendLibraryEvent(libraryEvent);
        createBidCommand.setBidEventType(BidEventType.NEW);
        bidEventProducer.sendBidEvent_Approach2(createBidCommand);
        //log.info("SendResult is {} ", sendResult.toString());
        log.info("after sendLibraryEvent");
        return ResponseEntity.status(HttpStatus.CREATED).body(createBidCommand);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createBidCommand);
		
	}
	
	//Put
	@PutMapping("/{bidId}")
    public ResponseEntity<?> putBidyEvent(@PathVariable String bidId, @RequestBody @Valid CreateBidCommand createBidCommand) throws JsonProcessingException, ExecutionException, InterruptedException {

        if(createBidCommand.getBidEventId()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the bidEventId");
        }

        createBidCommand.setBidEventType(BidEventType.UPDATE);
        createBidCommand.setBidId(bidId);
        bidEventProducer.sendBidEvent_Approach2(createBidCommand);
        return ResponseEntity.status(HttpStatus.OK).body(createBidCommand);
    }
		
	//addBid
	
	@PutMapping("/update-bid/{productId}/{buyerEmail}/{newBidAmount}")
	public ResponseEntity<BidResponse> updateBidAmount(@PathVariable String productId, 
			@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) {
		 
		Bid bid = bidService.updateBidAmount(productId, buyerEmail, newBidAmount);	 
		return new ResponseEntity<>(new BidResponse(bid), HttpStatus.OK); 	
		
	}
	
	@PutMapping("/{buyerEmail}/{newBidAmount}")
	public ResponseEntity<BidResponse> updateBidAmountUsingEmail (@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) {		
		 
		return new ResponseEntity<>(bidService.updateBidAmountUsingEmail(buyerEmail, newBidAmount), HttpStatus.OK); 
	}
	
	
	 
//	@GetMapping("/getAllBids")
//	public ResponseEntity<List<BidResponse>> getAllBids(){
//		List<Bid> bidList = bidService.getAllBids(); 
//		if(bidList.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}		
//		List<BidResponse> bidResponseList = new ArrayList<BidResponse>();
//		
//		bidList.stream().forEach(bid->{bidResponseList.add(new BidResponse(bid));});
//		return ResponseEntity.ok(bidResponseList);
//	}
	//@GetMapping("/getAllBids/{productId}")
	//public ResponseEntity<List<BidResponse>> getAllBidsByProductId(@PathVariable String productId){
//	@GetMapping("/getAllBids/{productId}")
//	public ResponseEntity<List<BidResponse>> getAllBidsByProductId(@PathVariable String productId){
//		logger.info("Test Inside bid before reading bidList");
//		List<Bid> bidList = bidService.getAllBidsByProductId(productId); 
//		logger.info("after");
//		logger.info("BidList"+bidList);
//		if(bidList.isEmpty() ) {
//			logger.info("inside empty checking");
//			return ResponseEntity.notFound().build();
//		}
//		logger.info("Test checking  bidList empty");
//		List<BidResponse> bidResponseList = new ArrayList<BidResponse>();
//		bidList.stream().filter(f->f!=null).sorted(Comparator.comparing(Bid::getAmount).reversed())
//		.forEach(bid->{bidResponseList.add(new BidResponse(bid));});
//		logger.info("Test checking  bidList filtering");
//		return ResponseEntity.ok(bidResponseList);
//	}
//	@GetMapping("/{email}")
//	public Boolean emailExist(@PathVariable String email){
//		logger.info("10");
//		return bidService.emailExist(email);		
//		
//	}
//	
	
	
//	@PostMapping
//    public String addBid(@RequestBody BidRestModel bidRestModel) {
//
//        CreateBidCommand createBidCommand =
//                CreateBidCommand.builder()
//                        .bidId(UUID.randomUUID().toString())
//                        .name(bidRestModel.getName())
//                        .amount(bidRestModel.getAmount())
//                        .mobile(bidRestModel.getMobile())
//                        .email(bidRestModel.getEmail())
//                        .productId(bidRestModel.getProductId())
//                        .build();
//        String result = commandGateway.sendAndWait(createBidCommand);
//        return result;
//    }
	
}

	
	
	

