package com.nathy.app.query.api.response;

import java.math.BigDecimal;


import com.nathy.app.command.api.data.Bid;

public class BidResponse {
	private String bidId;

	private BigDecimal amount;
	private String name;
	private String email;
	private String mobile;
	public BidResponse() {
		
	}
	 
	public BidResponse(Bid bid) {
		 
		this.bidId = bid.getBidId();
		this.amount = bid.getAmount();
		this.name = bid.getName();
		this.email = bid.getEmail();
		this.mobile = bid.getMobile();
	}
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
