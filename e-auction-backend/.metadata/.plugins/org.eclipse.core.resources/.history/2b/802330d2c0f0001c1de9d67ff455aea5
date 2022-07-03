package com.nathy.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nathy.app.entity.Address;
import com.nathy.app.repository.AddressRepository;
import com.nathy.app.request.CreateAddressRequest;
import com.nathy.app.response.AddressResponse;

@Service
public class AddressService {

	Logger logger = LoggerFactory.getLogger(AddressService.class);
	
	@Autowired
	AddressRepository addressRepository;

	public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {
		
		Address address = new Address();
		address.setStreet(createAddressRequest.getStreet());
		address.setCity(createAddressRequest.getCity());
		address.setState(createAddressRequest.getState());
		address.setPin(createAddressRequest.getPin());
		logger.info("before saving address ");
		addressRepository.save(address);
		logger.info("after saving address ");
		
		return new AddressResponse(address);
		
	}
	
	public AddressResponse getById (String id) {
		
		logger.info("Inside getById " + id);
		
		Address address = addressRepository.findById(id).get();
		logger.info("redding address by id");
		
		return new AddressResponse(address);
	}
	
}
