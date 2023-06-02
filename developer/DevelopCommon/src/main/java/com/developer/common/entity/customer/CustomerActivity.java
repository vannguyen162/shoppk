package com.developer.common.entity.customer;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.developer.common.entity.Customer;
import com.developer.common.entity.IdBasedEntity;

@Entity
@Table(name = "customer_activity")
public class CustomerActivity extends IdBasedEntity{

	@Column(name = "ativity")
	private String ativity;
	
	@Column(name = "logIn")
	private Date logIn;
	
	@Column(name = "logOut")
	private Date logOut;
	
	@Column(name = "urlLog")
	private String urlLog;
	
	@Column(name = "timeAtivity")
	private Date timeAtivity;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public String getAtivity() {
		return ativity;
	}

	public void setAtivity(String ativity) {
		this.ativity = ativity;
	}

	public Date getLogIn() {
		return logIn;
	}

	public void setLogIn(Date logIn) {
		this.logIn = logIn;
	}

	public Date getLogOut() {
		return logOut;
	}

	public void setLogOut(Date logOut) {
		this.logOut = logOut;
	}

	public Date getTimeAtivity() {
		return timeAtivity;
	}

	public void setTimeAtivity(Date timeAtivity) {
		this.timeAtivity = timeAtivity;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getUrlLog() {
		return urlLog;
	}

	public void setUrlLog(String urlLog) {
		this.urlLog = urlLog;
	}

	//	hiển thị thời gian hành động theo quy chuẩn ngày | giờ
	@Transient
	public String getTimeActivityFormat() {
		if(timeAtivity != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:ss | dd/MM/yyyy");  
			String time = formatter.format(timeAtivity);
			return time;
		}
		return "";
	}
	
//	hiển thị thời gian hành động theo quy chuẩn giờ
	@Transient
	public String getTimeActivityWithHourFormat() {
		if(timeAtivity != null) {
			Instant now = Instant.now();
		    Instant activityInstant = timeAtivity.toInstant();
		    Duration duration = Duration.between(activityInstant, now);
		    long seconds = duration.getSeconds();
		    if (seconds < 60) {
		        return "cách đây: " + seconds + " giây trước";
		    } else if (seconds < 3600) {
		        long minutes = seconds / 60;
		        return "cách đây: " + minutes + " phút trước";
		    } else if (seconds < 86400) {
		        long hours = seconds / 3600;
		        return "cách đây: " + hours + " giờ trước";
		    } else {
		    	SimpleDateFormat formatter = new SimpleDateFormat("HH:ss | dd/MM/yyyy");  
				String time = formatter.format(timeAtivity);
				return time;
		    }
		}
		return "";
	}
	
}
