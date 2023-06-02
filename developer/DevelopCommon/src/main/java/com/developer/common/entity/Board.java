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
@Table(name = "board")
public class Board extends TimeWorkBasedEntity{
	
	@Column(name = "board_no")
	private String boardNo;
	
	@Column(name = "board_cate")
	private String boardCate;
	
	@Column(name = "kind_content")
	private String kindContent;
	
	@Column(length = 1024, nullable = false)
	private String title;
	
	@Column(name = "main_image", nullable = false)
	private String mainImage;
	
	@Lob
	@Column(name = "full_description")
	private String fullDescription;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private LocalDate endDate;
	
	private boolean enabled;
	
	@Column(name="number_views")
	private BigDecimal numViews;

	public Board() {
	}

	public Board(String boardCate, String kindContent, String title, String fullDescription) {
		this.boardCate = boardCate;
		this.kindContent = kindContent;
		this.title = title;
		this.fullDescription = fullDescription;
	}

	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardCate() {
		return boardCate;
	}

	public void setBoardCate(String boardCate) {
		this.boardCate = boardCate;
	}

	public String getKindContent() {
		return kindContent;
	}

	public void setKindContent(String kindContent) {
		this.kindContent = kindContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
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

	public BigDecimal getNumViews() {
		return numViews;
	}

	public void setNumViews(BigDecimal numViews) {
		this.numViews = numViews;
	}

	@Transient
	public String getShortTitle() {
		if(title.length() > 50) {
			return title.substring(0, 50).concat("...");
		}
		return title;
	}

	@Transient
	public String getBoardImagePath() {
		if(id == null || mainImage == null) return "/images/default-image.jpg";
		return "/board-images/" + this.id + "/" + this.mainImage;
	}
	
	@Transient
	public int getNumViewValue() {
	    return numViews != null ? numViews.intValue() : 0;
	}
	
//	format mã tin
	@Transient
	public String getKindFormat() {
	    if(kindContent != null) {
	        if(kindContent.equals("B01")) {
	            return "Tin Shop NHƯ NGỌC";
	        } else if(kindContent.equals("B02")) {
	            return "Tin ĐẤT VÀNG";
	        } else if(kindContent.equals("B03")) {
	            return "Tin hệ thống";
	        }
	        return "";
	    }
	    return "";
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
