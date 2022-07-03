package com.nathy.app.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
public class BidCreatedEvent {

    private String bidId;
    private BigDecimal amount;
	private String name;
	private String email;
	private String mobile;
	private String productId;
	
	public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
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
	public void setName(String bidsName) {
		this.name = bidsName;
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
	public BidCreatedEvent(String bidId, BigDecimal amount, String name, String email, String mobile,
			String productId) {
		super();
		this.bidId = bidId;
		this.amount = amount;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.productId = productId;
	}
    
	public BidCreatedEvent() {
	 
	}
    
    
	 
    
    
    
}
