package com.nathy.app.api.command.producer;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.kafka.support.SendResult;
 
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nathy.app.command.api.commands.CreateBidCommand;
 

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BidEventProducer {
	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;
	
	String topic = "bid-events";
	
	@Autowired
	ObjectMapper objectMapper;
	
	public void sendBidEvent(CreateBidCommand createBidCommand) throws JsonProcessingException {
		Integer key = createBidCommand.getBidEventId();
		String value = objectMapper.writeValueAsString(createBidCommand);
		ListenableFuture<SendResult<Integer,String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
		//kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
	}
	
	

    public void sendBidEvent_Approach2(CreateBidCommand createBidCommand) throws JsonProcessingException {

        Integer key = createBidCommand.getBidEventId();
        String value = objectMapper.writeValueAsString(createBidCommand);

        ProducerRecord<Integer,String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
    	
    	//List<Header> recordHeaders = List.of(new RecordHeader("event-source","buyer".getBytes()), new RecordHeader("event-source","buyer".getBytes()));
    	//return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    	return new ProducerRecord<>(topic, null, key, value, null);
    }
    
	public SendResult<Integer, String> sendBidEventSynchronous(CreateBidCommand createBidCommand) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        Integer key = createBidCommand.getBidEventId();
        String value = objectMapper.writeValueAsString(createBidCommand);
        SendResult<Integer,String> sendResult=null;
        try {
            sendResult = kafkaTemplate.sendDefault(key,value).get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            log.error("ExecutionException/InterruptedException Sending the Message and the exception is {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception Sending the Message and the exception is {}", e.getMessage());
            throw e;
        }

        return sendResult;

    }
	
	
	
	private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }

}
