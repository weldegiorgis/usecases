package com.nathy.app.service;

 

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.ResourceNotFoundException;
//import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nathy.app.entity.Bid;
import com.nathy.app.entity.CreateBidCommand;
import com.nathy.app.repository.BidRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BidService {
	
	Logger logger = LoggerFactory.getLogger(BidService.class);
	@Autowired
	BidRepository bidRepository;
	
	@Autowired
    ObjectMapper objectMapper; 

    public void processBidEvent(ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
       log.info("Hello world");
    	CreateBidCommand createBidCommand = objectMapper.readValue(consumerRecord.value(), CreateBidCommand.class);
        log.info("BidEvent : {} ", createBidCommand);

        switch(createBidCommand.getBidEventType()){
            case NEW:
            	if(createBidCommand.getProductId()==null) {
            		save(createBidCommand);
            	}
            	else {
            		addBid(createBidCommand);
            	}
                break;
            case UPDATE:
                //update operation
            	//validate the bid event
                validate(createBidCommand);
                //Bid bid = bidRepository.findById(createBidCommand.getBidId()).get();
                if(createBidCommand.getName()==null && createBidCommand.getMobile()==null) {
                	updateBidAmountUsingEmail(createBidCommand);
                }
                else {
                	save(createBidCommand);
                }
                break;
            default:
                log.info("Invalid Bid Event Type");
        }

    }

    private void save(CreateBidCommand createBidCommand) {
    	Bid bid = new Bid();
    	bid.setAmount(createBidCommand.getAmount());
    	bid.setEmail(createBidCommand.getEmail());
    	bid.setMobile(createBidCommand.getMobile());
    	bid.setProductId(createBidCommand.getProductId());
    	bid.setName(createBidCommand.getName());
    	//bid.setBidEventId(createBidCommand.getBidEventId());
        //libraryEvent.getBook().setLibraryEvent(libraryEvent);
    	bid = bidRepository.save(bid);
    	log.info(bid.getBidId());
        log.info("Successfully Persisted the bid Event {} ", createBidCommand);
    }
    
    private void validate(CreateBidCommand createBidCommand) {
        if(createBidCommand.getBidEventId()==null){
            throw new IllegalArgumentException("LBid Event Id is missing");
        }

//        Optional<Bid> bidOptional = bidRepository.findById(createBidCommand.getBidId());
//        if(!bidOptional.isPresent()){
//            throw new IllegalArgumentException("Not a valid bid Event");
//        }
//        log.info("Validation is successful for the bid Event : {} ", bidOptional.get());
//        
        Optional<Bid> bidOptional = Optional.ofNullable(bidRepository.findByEmail(createBidCommand.getEmail()));
        if(!bidOptional.isPresent()){
            throw new IllegalArgumentException("Not a valid bid Event");
        }
        log.info("Validation is successful for the bid Event : {} ", bidOptional.get());
        
    }
	
	@SuppressWarnings("unchecked")
	public void addBid(CreateBidCommand createBidCommand) {	 
		
		logger.info("Before bid added");
		Bid bid = new Bid();
		if(getAllBids()==null) {
			
			bid.setAmount(createBidCommand.getAmount());
			bid.setName(createBidCommand.getName());		
			bid.setEmail(createBidCommand.getEmail());
			bid.setMobile(createBidCommand.getMobile());
			//bid.setProductId(bidRequest.getProductId());
			bid.setProductId(createBidCommand.getProductId());
			bid = bidRepository.save(bid);
			//BidResponse bidResponse = new BidResponse(bid);	
			logger.info("First bid created");
			//return bidResponse;
		}
		else {
			
			long count = getAllBids().stream().filter(f->f.getEmail().equalsIgnoreCase(createBidCommand.getEmail())).count();
				
			if(count == 0){
				logger.info("ff");
				bid.setAmount(createBidCommand.getAmount());
				bid.setName(createBidCommand.getName());		
				bid.setEmail(createBidCommand.getEmail());
				bid.setMobile(createBidCommand.getMobile());
				bid.setProductId(createBidCommand.getProductId());
				
				bid = bidRepository.save(bid);
				//BidResponse bidResponse = new BidResponse(bid);	
				logger.info("Second bid created");
				//return bidResponse;	
				
				}
			else {
				logger.info("can't add another bid using this email, already exits a bid with this email");
				
				 new ResourceNotFoundException("can't add another bid using this email, already bid with this email", createBidCommand.getEmail());
				// new ResourceNotFoundException("can't add another bid using this email, already bid with this email", createBidCommand.getEmail(), 0);
					
				}

		}
		//return new BidResponse(bid);	
				
					
		
	}
	
	//public BidResponse updateBidAmountUsingEmail(CreateBidCommand createBidCommand) {
	public Bid updateBidAmountUsingEmail(CreateBidCommand createBidCommand) {
		
		long count = getAllBids().stream().filter(f->f.getEmail().equalsIgnoreCase(createBidCommand.getEmail())).count();
		logger.info(String.valueOf(count));
		if(count == 0) {
			logger.info("No bid using the email: "+createBidCommand.getEmail());
			return null;
		}
		else {
			logger.info("bid before retrieving: ");
			Bid bid = bidRepository.findByEmail(createBidCommand.getEmail());	
			logger.info("bid after retrieving: "+bid.toString());
			bid.setAmount(createBidCommand.getAmount());
			bid = bidRepository.save(bid);
			logger.info("bid after update: "+bid.toString());
			//BidResponse bidResponse = new BidResponse(bid);
			//return bidResponse;
			return bid;
		}
	}
	
	public List<Bid> getAllBids() {
	
		return bidRepository.findAll();
	}
	
//	public List<Bid> getAllBidsByProductId(String productId) {
//
//		return bidRepository.findByProductId(productId);
//	}

	public Bid updateBidAmount(CreateBidCommand createBidCommand) {		
	 
		Bid bid = bidRepository.findByProductIdAndEmail(createBidCommand.getProductId(), createBidCommand.getEmail());		
		bid.setAmount(createBidCommand.getAmount());	

		return bidRepository.save(bid);
}

//	public Bid getByEmail(String email) {
//	logger.info("11");
//	//Bid bid = bidRepository.findByEmail(email);
//	logger.info("12");
//	//BidResponse bidResponse = new BidResponse(bid);		
//	return null;
//}
	
	
	
	
	
	
	

//	@SuppressWarnings("unchecked")
//	public BidResponse addBid(String productId, BidRequest bidRequest) {	 
//		
//		logger.info("Before bid added");
//		Bid bid = new Bid();
//		if(getAllBids()==null) {
//			
//			bid.setAmount(bidRequest.getAmount());
//			bid.setName(bidRequest.getName());		
//			bid.setEmail(bidRequest.getEmail());
//			bid.setMobile(bidRequest.getMobile());
//			//bid.setProductId(bidRequest.getProductId());
//			bid.setProductId(productId);
//			
//			bid = bidRepository.save(bid);
//			//BidResponse bidResponse = new BidResponse(bid);	
//			logger.info("First bid created");
//			//return bidResponse;
//		}
//		else {
//			//List<Bid> bids = bidRepository.findAll();
//			long count = getAllBids().stream().filter(f->f.getEmail().equalsIgnoreCase(bidRequest.getEmail())).count();
//				
//			if(count == 0){
//				logger.info("ff");
//				bid.setAmount(bidRequest.getAmount());
//				bid.setName(bidRequest.getName());		
//				bid.setEmail(bidRequest.getEmail());
//				bid.setMobile(bidRequest.getMobile());
//				bid.setProductId(productId);
//				
//				bid = bidRepository.save(bid);
//				//BidResponse bidResponse = new BidResponse(bid);	
//				logger.info("Second bid created");
//				//return bidResponse;	
//				
//				}
//			else {
//				logger.info("can't add another bid using this email, already bid with this email");
//				
//				 new ResourceNotFoundException("can't add another bid using this email, already bid with this email", bidRequest.getEmail(), 0);
//				//	
//				}
//
//		}
//		return new BidResponse(bid);	
//				
//					
//		
//	}

//	public List<Bid> getAllBids() {
//		
//		return bidRepository.findAll();
//	}
//
//	public List<Bid> getAllBidsByProductId(String productId) {
//		
////		 List<Bid> bid =bidRepository.findAll();
////		 if(bid!=null) {
////			 return null;
////		 }
////		 List<Bid> bidsOfProduct = new ArrayList<>();
////		bid.stream().filter(f->f.getProductId().equalsIgnoreCase(productId)).forEach(e->bidsOfProduct.add(e));
////		return bidsOfProduct;
//		return bidRepository.findByProductId(productId);
//	}
//
//	public Bid updateBidAmount(String productId, String buyerEmail, BigDecimal newBidAmount) {		
//		 
//		Bid bid = bidRepository.findByProductIdAndEmail(productId, buyerEmail);
//		bid.setAmount(newBidAmount);	
//	 
//		return bidRepository.save(bid);
//	}
//
//	public Bid getByEmail(String email) {
//		logger.info("11");
//		//Bid bid = bidRepository.findByEmail(email);
//		logger.info("12");
//		//BidResponse bidResponse = new BidResponse(bid);		
//		return null;
//	}
//	public Boolean emailExist(String email) {
////		logger.info("10.1");
////		Bid bid = bidRepository.findByEmail(email);
////		logger.info("10.2");
////		if(bid.getEmail().equals(email)) {
////			return true;
////		}
////		else {
////		return false;
////		}		
//		return null;
//	}
//	public BidResponse updateBidAmountUsingEmail(String email,BigDecimal newBidAmount) {
//		
//		long count = getAllBids().stream().filter(f->f.getEmail().equalsIgnoreCase(email)).count();
//		if(count == 0) {
//			logger.info("No bid using the email: "+email);
//			return null;
//		}
//		else {
//			 
//			Bid bid = bidRepository.findByEmail(email);			
//			bid.setAmount(newBidAmount);
//			bid = bidRepository.save(bid);
//			BidResponse bidResponse = new BidResponse(bid);
//			return bidResponse;
//		}
//	}
//

	
}
