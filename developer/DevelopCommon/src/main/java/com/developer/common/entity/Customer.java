package com.developer.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "customers")
public class Customer extends TimeWorkBasedEntity{
	
	@Column(length = 45, nullable = false, unique = true)
	private String email;
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(name = "first_name", length = 45, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 45, nullable = false)
	private String lastName;
	
	@Column(name = "phone_number", length = 15, nullable = false)
	private String phoneNumber;
	
	@Column(length = 64,name = "address_line_1")
	private String addressLine1;
	
	@Column(name = "address_line_2", length = 64)
	private String addressLine2;
	
	@Column(length = 45)
	private String city;
	
	@Column(nullable = false, length = 45)
	private String state;
	
	@Column(name = "postal_code", length = 10)
	private String postalCode;
	
	@Column(name = "verification_code", length = 64)
	private String verificationCode;
	
	private boolean enabled;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "authentication_type", length = 10)
	private AuthenticationType authenticationType;

	@Column(name = "reset_password_token", length = 30)
	private String resetPasswordToken;
	
	public Customer() {
	}
	
	public Customer(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		if(country != null) {
			String address = "[" + firstName;
			if(lastName != null && !lastName.isEmpty()) address += " " + lastName;
			if(!addressLine1.isEmpty()) address += "] " + addressLine1;
			if(addressLine2 != null && !addressLine2.isEmpty()) address += " " + addressLine2;
			if(!city.isEmpty()) address += ", " + city;
			if(state != null && !state.isEmpty()) address += ", " + state;
			address += ", " + country.getName();
			
			if(!postalCode.isEmpty()) address += ". Mã bưu điện: " + postalCode;
			if(!phoneNumber.isEmpty()) address += ". Số điện thoại: " + phoneNumber;
			
			return address;
			
		}
		return "";
	}
	
	public String getFullName() {
		return firstName == null ? "" : firstName + " " + (lastName == null ? "" : lastName);
	}
	
	public String getNameCountry() {
		if(country != null) {
			return country.getName();
		}
		return "";
	}
	
	public String getAddr() {
		if(country != null) {
			return addressLine1 == null ? "" : addressLine1 + " " + (addressLine2 == null ? "" : addressLine2) + " " + state + " " + country.getName();
		}
		return "";
	}
	
	public String getHouseStreet() {
		return addressLine1 == null ? "" : addressLine1 + " " + (addressLine2 == null ? "" : addressLine2);
	}

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
	
//	loại tài khoản đã đăng kí
	@Transient
	public String getNameAuthenType() {
	    if(authenticationType != null) {
	        if(this.authenticationType == AuthenticationType.DATABASE) {
	            return "Hệ thống";
	        }else if (this.authenticationType == AuthenticationType.FACEBOOK) {
	        	return "FACEBOOK";
			}else if (this.authenticationType == AuthenticationType.GOOGLE) {
	        	return "GOOGLE";
			}
	    }
	    return null;
	}
	
	// địa chỉ giao hàng
	@Transient
	public String getAddress() {
		if(country != null) {
			String address = "";
			if(!addressLine1.isEmpty()) address += addressLine1;
			if(addressLine2 != null && !addressLine2.isEmpty()) address += " " + addressLine2;
			if(!city.isEmpty()) address += ", " + city;
			if(state != null && !state.isEmpty()) address += ", " + state;
			address += ", " + country.getName();
			if(!postalCode.isEmpty()) address += ". Mã bưu điện: " + postalCode;
//		if(!phoneNumber.isEmpty()) address += ". Số điện thoại: " + phoneNumber;
			
			return address;
			
		}
		return "";
	}
	
}
