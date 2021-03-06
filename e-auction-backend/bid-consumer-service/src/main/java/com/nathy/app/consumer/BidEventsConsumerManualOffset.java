package com.nathy.app.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment; 

import lombok.extern.slf4j.Slf4j;


//@Component
@Slf4j
public class BidEventsConsumerManualOffset implements AcknowledgingMessageListener<Integer,String> {
	@Override
	 @KafkaListener(topics = {"bid-events"})
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment acknowledgment) {
		 log.info("ConsumerRecord : {} ", consumerRecord );
	     acknowledgment.acknowledge();
		
	}

}
