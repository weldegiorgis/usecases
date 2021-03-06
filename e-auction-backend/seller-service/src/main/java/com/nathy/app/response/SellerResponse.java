package com.nathy.app.response;
import com.nathy.app.entity.Seller;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerResponse {
	private String sellerId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String addressId;
	private AddressResponse addressResponse;
	
	private ProductResponse productResponse;
	
	

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public ProductResponse getProductResponse() {
		return productResponse;
	}

	public void setProductResponse(ProductResponse productResponse) {
		this.productResponse = productResponse;
	}

	public SellerResponse(Seller seller) {

		this.sellerId = seller.getSellerId();
		this.firstName = seller.getFirstName();
		this.lastName = seller.getLastName();
		this.email = seller.getEmail();
		this.phone = seller.getPhone();	
		this.addressId = seller.getAddressId();

	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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

	public AddressResponse getAddressResponse() {
		return addressResponse;
	}

	public void setAddressResponse(AddressResponse addressResponse) {
		this.addressResponse = addressResponse;
	}

}
