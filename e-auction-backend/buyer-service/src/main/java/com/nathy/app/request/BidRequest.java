package com.nathy.app.request;

import java.math.BigDecimal;

 

public class BidRequest {
	private BigDecimal amount;
	private String name;
	private String email;
	private String mobile;

	 public BidRequest() {
		 
	 }
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}	

}
