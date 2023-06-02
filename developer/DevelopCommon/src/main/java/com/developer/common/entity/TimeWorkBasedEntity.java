package com.developer.common.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class TimeWorkBasedEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(name = "created_time")
	protected Date createdTime;
	
	@Column(name = "updated_time")
	protected Date updatedTime;
	
	@Column(name = "workUser")
	protected String workUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
//	format thời gian khởi tạo
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
	
//	tính năm thâm niên
	@Transient
	public String getSeniorityInYMD() {
	    if (createdTime == null) {
	        return "";
	    }
	    LocalDateTime createdLocalDateTime = createdTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	    Period period = Period.between(createdLocalDateTime.toLocalDate(), LocalDate.now());
	    return period.getYears() + " năm " + period.getMonths() + " tháng " + period.getDays() + " ngày";
	}
}
