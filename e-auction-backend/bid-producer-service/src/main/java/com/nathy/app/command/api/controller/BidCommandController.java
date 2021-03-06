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
import com.nathy.app.command.api.commands.BidRequest;
import com.nathy.app.command.api.data.Bid;
import com.nathy.app.command.api.data.BidEventType;
import com.nathy.app.query.api.response.BidResponse;
import com.nathy.app.query.api.service.BidService;

import lombok.extern.slf4j.Slf4j;
 

@RestController
@RequestMapping("/e-auction/api/v1/commandBids")
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
	@PostMapping("/{productId}")
	public ResponseEntity<BidResponse> addBid( @PathVariable String productId, @RequestBody BidRequest createBidCommand) throws JsonProcessingException {
		//invoke kafka producer
        log.info("before Bid Event");
        //libraryEventProducer.sendLibraryEvent(libraryEvent);
        createBidCommand.setBidEventType(BidEventType.NEW);
        createBidCommand.setProductId(productId);
        bidEventProducer.sendBidEvent_Approach2(createBidCommand);
        //log.info("SendResult is {} ", sendResult.toString());
        log.info("after send Bid Event");
        BidResponse bidResponse = new BidResponse();
        bidResponse.setAmount(createBidCommand.getAmount());
        bidResponse.setName(createBidCommand.getName());
        bidResponse.setEmail(createBidCommand.getEmail());
        bidResponse.setMobile(createBidCommand.getMobile());
        return ResponseEntity.status(HttpStatus.CREATED).body(bidResponse);
		
	}
	
	//Put
	@PutMapping("/update-bid/{productId}/{buyerEmail}/{newBidAmount}")
    public ResponseEntity<?>  updateBidAmount(@PathVariable String productId, 
			@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) throws JsonProcessingException, ExecutionException, InterruptedException {
		BidRequest createBidCommand = new BidRequest();
		
		createBidCommand.setBidEventId(1);

        if(createBidCommand.getBidEventId()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the bidEventId");
        }
        createBidCommand.setProductId(productId);
		createBidCommand.setEmail(buyerEmail);
		createBidCommand.setAmount(newBidAmount);
        createBidCommand.setBidEventType(BidEventType.UPDATE);
       // createBidCommand.setBidId(bidId);
        bidEventProducer.sendBidEvent_Approach2(createBidCommand);
        return ResponseEntity.status(HttpStatus.OK).body(createBidCommand);
    }	
	
//	@PutMapping("/update-bid/{productId}/{buyerEmail}/{newBidAmount}")
//	public ResponseEntity<BidResponse> updateBidAmount(@PathVariable String productId, 
//			@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) {
//		 
//		Bid bid = bidService.updateBidAmount(productId, buyerEmail, newBidAmount);	 
//		return new ResponseEntity<>(new BidResponse(bid), HttpStatus.OK); 	
//		
//	}
	
	
	
	//Put
	
		@PutMapping("/{productId}/{buyerEmail}/{newBidAmount}")
	    public ResponseEntity<?> updateBidAmountUsingEmail(@PathVariable String productId, @PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) throws JsonProcessingException, ExecutionException, InterruptedException {
			BidRequest createBidCommand = new BidRequest();
			
			createBidCommand.setBidEventId(1);

	        if(createBidCommand.getBidEventId()==null){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the bidEventId");
	        }
	       // createBidCommand.setProductId(productId);
			createBidCommand.setEmail(buyerEmail);
			createBidCommand.setAmount(newBidAmount);
	        createBidCommand.setBidEventType(BidEventType.UPDATE);
	        createBidCommand.setProductId(productId);
	       // createBidCommand.setBidId(bidId);
	        bidEventProducer.sendBidEvent_Approach2(createBidCommand);
	        return ResponseEntity.status(HttpStatus.OK).body(createBidCommand);

	    }
	 
	
//	@PutMapping("/{buyerEmail}/{newBidAmount}")
//	public ResponseEntity<BidResponse> updateBidAmountUsingEmail (@PathVariable String buyerEmail, @PathVariable BigDecimal newBidAmount) {		
//		 
//		return new ResponseEntity<>(bidService.updateBidAmountUsingEmail(buyerEmail, newBidAmount), HttpStatus.OK); 
//	}
	
		
//	//Put
//		@PutMapping("/{bidId}")
//	    public ResponseEntity<?> putBidyEvent(@PathVariable String bidId, @RequestBody @Valid CreateBidCommand createBidCommand) throws JsonProcessingException, ExecutionException, InterruptedException {
//
//	        if(createBidCommand.getBidEventId()==null){
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the bidEventId");
//	        }
//
//	        createBidCommand.setBidEventType(BidEventType.UPDATE);
//	        createBidCommand.setBidId(bidId);
//	        bidEventProducer.sendBidEvent_Approach2(createBidCommand);
//	        return ResponseEntity.status(HttpStatus.OK).body(createBidCommand);
//	    }
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

	
	
	

