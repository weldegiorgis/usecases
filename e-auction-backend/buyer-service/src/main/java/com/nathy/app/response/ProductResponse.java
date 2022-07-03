package com.nathy.app.response;

import java.math.BigDecimal;
import java.util.Date;


public class ProductResponse {
	private String productId;	
	private String name;	
	private String shortDescription;
	private String detailDescription;
	private String category;
	private BigDecimal startingPrice;
	private Date bidEndDate;
	private BidResponse bidResponse;
	
	
	
	public BidResponse getBidResponse() {
		return bidResponse;
	}
	public void setBidResponse(BidResponse bidResponse) {
		this.bidResponse = bidResponse;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getDetailDescription() {
		return detailDescription;
	}
	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	 
	public Date getBidEndDate() {
		return bidEndDate;
	}
	public void setBidEndDate(Date bidEndDate) {
		this.bidEndDate = bidEndDate;
	}
	public BigDecimal getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(BigDecimal startingPrice) {
		this.startingPrice = startingPrice;
	}	

}
