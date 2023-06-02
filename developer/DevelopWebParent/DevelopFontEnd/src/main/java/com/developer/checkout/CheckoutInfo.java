package com.developer.checkout;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Transient;

public class CheckoutInfo {

	private float productCost;
	private float productTotal;
	private float shippingCostTotal;
	private float paymentTotal;
	private int deliverDays;
	private boolean codSupported;

	public float getProductCost() {
		return productCost;
	}

	public void setProductCost(float productCost) {
		this.productCost = productCost;
	}

	public float getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(float productTotal) {
		this.productTotal = productTotal;
	}

	public float getShippingCostTotal() {
		return shippingCostTotal;
	}

	public void setShippingCostTotal(float shippingCostTotal) {
		this.shippingCostTotal = shippingCostTotal;
	}

	public float getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(float paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	public int getDeliverDays() {
		return deliverDays;
	}

	public void setDeliverDays(int deliverDays) {
		this.deliverDays = deliverDays;
	}

	public Date getDeliverDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, deliverDays);
		return calendar.getTime();
	}
	
	@Transient
	public String getFormattedDeliverDate() {
		ZoneId vietnamZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
	    DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
	        .appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL_STANDALONE)
	        .appendLiteral(", ngày ")
	        .appendValue(ChronoField.DAY_OF_MONTH)
	        .appendLiteral(" tháng ")
	        .appendValue(ChronoField.MONTH_OF_YEAR)
	        .appendLiteral(" năm ")
	        .appendValue(ChronoField.YEAR, 4)
	        .toFormatter()
	        .withZone(vietnamZoneId);
	    return dateFormatter.format(getDeliverDate().toInstant());
	}

	public boolean isCodSupported() {
		return codSupported;
	}

	public void setCodSupported(boolean codSupported) {
		this.codSupported = codSupported;
	}

}
