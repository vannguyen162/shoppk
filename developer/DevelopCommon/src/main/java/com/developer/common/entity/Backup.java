package com.developer.common.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "backup")
public class Backup extends IdBasedEntity {

	@Column(name = "path_file")
	private String pathFile;

	@Column(name = "count_download")
	private Integer countDownload;

	@Column(name = "time_create")
	private Date timeCreate;

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public Integer getCountDownload() {
		return countDownload;
	}

	public void setCountDownload(Integer countDownload) {
		this.countDownload = countDownload;
	}

	public Date getTimeCreate() {
		return timeCreate;
	}

	public void setTimeCreate(Date timeCreate) {
		this.timeCreate = timeCreate;
	}
	
	@Transient
	public String getUpDateFormat() {
		if(timeCreate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy | HH:ss");  
			String strDate = formatter.format(timeCreate);
			return strDate;
		}
		return "";
	}

}
