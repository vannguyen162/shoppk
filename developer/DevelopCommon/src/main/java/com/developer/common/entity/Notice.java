package com.developer.common.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "notice")
public class Notice extends TimeWorkBasedEntity{
	
	@Column(name = "short_description", length = 512, nullable = false)
	private String shortDescription;
	
	@Lob
	@Column(name = "full_description")
	private String fullDescription;
	
	@Column(name="number_views")
	private BigDecimal numViews;
	
	private boolean enabled;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private LocalDate endDate;
	
	private boolean poppup;

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public BigDecimal getNumViews() {
		return numViews;
	}

	public void setNumViews(BigDecimal numViews) {
		this.numViews = numViews;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isPoppup() {
		return poppup;
	}

	public void setPoppup(boolean poppup) {
		this.poppup = poppup;
	}

	// format thời gian diễn ra
	@Transient
	public String getStartDateFormat() {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	return startDate.format(formatter);
	}
	@Transient
	public String getEndDateFormat() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return endDate.format(formatter);
	}
	@Transient
	public String getShortTitle() {
		if(shortDescription.length() > 50) {
			return shortDescription.substring(0, 50).concat("...");
		}
		return shortDescription;
	}
	// poppup
	@Transient
	public String getPoppupFormat() {
	    return poppup ? "Poppup" : "Dạng chữ";
	}
}
