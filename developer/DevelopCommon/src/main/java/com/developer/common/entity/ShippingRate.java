package com.developer.common.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "shipping_rates")
public class ShippingRate extends IdBasedEntity{

	private float rate;
	private int days;
	
	@Column(name = "cod_supported")
	private boolean codSupported;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	@Column(nullable = false, length = 45)
	private String state;

	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "updated_time")
	private Date updatedTime;
	
	@Column(name = "workUser")
	private String workUser;
	
	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public boolean isCodSupported() {
		return codSupported;
	}

	public void setCodSupported(boolean codSupported) {
		this.codSupported = codSupported;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getWorkUser() {
		return workUser;
	}

	public void setWorkUser(String workUser) {
		this.workUser = workUser;
	}

	@Override
	public String toString() {
		return "ShippingRate [id=" + id + ", rate=" + rate + ", days=" + days + ", codSupported=" + codSupported
				+ ", country=" + country + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShippingRate other = (ShippingRate) obj;
		if (id == null) {
			if(other.id != null)
				return false;
		}else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Transient
	public String getCreateDateFormat() {
		if(createdTime != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			String strDate = formatter.format(createdTime);
			return strDate;
		}
		return "";
	}
	
//	format thời gian cập nhật
	@Transient
	public String getUpDateFormat() {
		if(updatedTime != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy | HH:ss");  
			String strDate = formatter.format(updatedTime);
			return strDate;
		}
		return "";
	}
	
}
