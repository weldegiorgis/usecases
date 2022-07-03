package com.nathy.app.command.api.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

//@Data
@Builder
public class BidRestModel {
	private BigDecimal amount;
	private String name;
	private String email;
	private String mobile;
	private String productId;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setBidsName(String name) {
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}    
	
    
    
}
