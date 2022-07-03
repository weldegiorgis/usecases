package com.nathy.app.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.nathy.app.entity.Product;

public class ProductResponse {
	private String productId;	
	private String name;	
	private String shortDescription;
	private String detailDescription;
	private String category;
	private BigDecimal startingPrice;
	private Date bidEndDate;	
	private List<String> bidIdList;	
	private List<BidResponse> bidsList;
	private BidResponse bidResponse;
	
	public ProductResponse(Product product) {
		 
		this.productId = product.getProductId();
		this.name = product.getName();
		this.shortDescription = product.getShortDescription();
		this.detailDescription = product.getDetailDescription();
		this.category = product.getCategory();
		this.startingPrice = product.getStartingPrice();
		this.bidEndDate = product.getBidEndDate();
		this.bidIdList =product.getBids();
		
		 
	}	
	
   public BidResponse getBidResponse() {
		return bidResponse;
	}

	public void setBidResponse(BidResponse bidResponse) {
		this.bidResponse = bidResponse;
	}



	//	public List<String> getBids() {
//		return bids;
//	}
	//public void setBids(BidResponse bidResponse) {
	public void setBids(BidResponse bidResponse) {
		//this.bids = bids;
		//this.bidIdList.add(bidResponse);
	}
//	public void setBids(List<String> bids) {
//		this.bids = bids;
//		//this.bids.add(bidResponse);
//	}
	
	public List<String> getBidIdList() {
		return bidIdList;
	}

	public void setBidIdList(List<String> bidIdList) {
		this.bidIdList.addAll(bidIdList);
		//this.bidIdList = bidIdList;
	}	 

	public List<BidResponse> getBidsList() {
		return bidsList;
	}

	public void setBidsList(List<BidResponse> bidsList) {
		this.bidsList = bidsList;
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
