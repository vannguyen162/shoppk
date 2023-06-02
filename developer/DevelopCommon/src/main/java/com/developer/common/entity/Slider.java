package com.developer.common.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "slider")
public class Slider extends TimeWorkBasedEntity {
	@Column(name = "image_url", nullable = false)
	private String mainImage;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private LocalDate endDate;

	private boolean enabled;

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public String getSliderImagePath() {
		if (id == null || mainImage == null)
			return "/images/default-image.jpg";
		return "/slider-images/" + this.id + "/" + this.mainImage;
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

}
