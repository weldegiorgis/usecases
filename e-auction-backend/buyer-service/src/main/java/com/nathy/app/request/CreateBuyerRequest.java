package com.nathy.app.request;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBuyerRequest {
 
	@NotEmpty(message="first name should not be null or empty")
	@Size(min=5, max=30, message="First name size should be between 5 and 30 letters")
	private String firstName;
	@NotEmpty(message="Last name should not be null or empty")
	@Size(min=5, max=25, message="First name size should be between 5 and 25 letters")
	private String lastName;
	
	@NotEmpty(message="Email should not be null or empty")
	@Email
	private String email;
	
	@NotNull(message="Phone number shoud not be empty or null")
	@Size(min=10, max=10, message="Phone number should be 10 characters")
	private String phone;
	private String productId;
	private BigDecimal bidamount;
	private String addressId;
	private BidRequest bidRequest;	
	
	
	public BidRequest getBidRequest() {
		return bidRequest;
	}
	public void setBidRequest(BidRequest bidRequest) {
		this.bidRequest = bidRequest;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public BigDecimal getBidamount() {
		return bidamount;
	}
	public void setBidamount(BigDecimal bidamount) {
		this.bidamount = bidamount;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	

}
