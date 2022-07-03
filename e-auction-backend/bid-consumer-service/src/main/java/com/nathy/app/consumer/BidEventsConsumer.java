package com.nathy.app.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nathy.app.service.BidService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BidEventsConsumer {
	
	@Autowired
	BidService bidService;
	
	@KafkaListener(topics= {"bid-events"})
	public void onMessage(ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException{

        log.info("ConsumerRecord : {} ", consumerRecord );
        bidService.processBidEvent(consumerRecord);

    }
}


