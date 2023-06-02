package com.developer.common.entity.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.developer.common.entity.IdBasedEntity;

@Entity
@Table(name = "client_info")
public class ClientInfo extends IdBasedEntity {

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "mac_address")
	private String macAddress;

	@Column(name = "device_infor")
	private String deviceInformation;

    @Column(name = "browser_name")
    private String browserName;

    @Column(name = "browser_version")
    private String browserVersion;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getDeviceInformation() {
		return deviceInformation;
	}

	public void setDeviceInformation(String deviceInformation) {
		this.deviceInformation = deviceInformation;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

}
