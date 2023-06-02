package com.developer.common.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class User extends TimeWorkBasedEntity {

	@Column(length = 128, nullable = false, unique = true)
	private String email;

	@Column(name = "adress", length = 128)
	private String adress;

	@Column(length = 64, nullable = false)
	private String password;

	@Column(name = "full_name", length = 45, nullable = false)
	private String fullName;

	@Column(name = "phone", length = 55, nullable = false, unique = true)
	private String phone;

	@Column(name = "photos", length = 64)
	private String photos;

	@Column(name = "date")
	private String date;

	@Column(name = "birth_place")
	private String birthplace;

	@Column(name = "identity_card")
	private String identity_card; // so chứng minh nd

	@Column(name = "date_range")
	private String date_range; // ngày cấp

	@Column(name = "issued_by")
	private String issued_by; // nơi cấp

	@Column(name = "log_date")
	private String logDate;

	private boolean enabled;

	@Column(name = "degrees")
	private String degrees;

	@Column(name = "maritals")
	private String maritals;

	@Column(name = "sexs")
	private String sexs;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String email, String password, String fullName, String phone) {
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getIdentity_card() {
		return identity_card;
	}

	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}

	public String getDate_range() {
		return date_range;
	}

	public void setDate_range(String date_range) {
		this.date_range = date_range;
	}

	public String getIssued_by() {
		return issued_by;
	}

	public void setIssued_by(String issued_by) {
		this.issued_by = issued_by;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public String getDegrees() {
		return degrees;
	}

	public void setDegrees(String degrees) {
		this.degrees = degrees;
	}

	public String getMaritals() {
		return maritals;
	}

	public void setMaritals(String maritals) {
		this.maritals = maritals;
	}

	public String getSexs() {
		return sexs;
	}

	public void setSexs(String sexs) {
		this.sexs = sexs;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", adress=" + adress + ", password=" + password + ", fullName="
				+ fullName + ", phone=" + phone + ", photos=" + photos + ", date=" + date + ", birthplace=" + birthplace
				+ ", identity_card=" + identity_card + ", date_range=" + date_range + ", issued_by=" + issued_by
				+ ", logDate=" + logDate + ", enabled=" + enabled + ", roles=" + roles + ", degrees=" + degrees
				+ ", maritals=" + maritals + ", sexs=" + sexs + "]";
	}

	@Transient
	public String getPhotosImagePath() {
		if (id == null || photos == null)
			return "/images/default-image.jpg";
		return "/user-photos/" + this.id + "/" + this.photos;
	}

	public boolean hasRole(String roleName) {
		Iterator<Role> iterator = roles.iterator();

		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (role.getName().equals(roleName)) {
				return true;
			}

		}

		return false;
	}

	@Transient
	public String getRolesLocalName() {
		StringBuilder sb = new StringBuilder();
		for (Role role : roles) {
			if (role.getName().equals("Admin")) {
				sb.append("Quản trị");
			} else if (role.getName().equals("Salesperson")) {
				sb.append("Nhân viên bán hàng");
			} else if (role.getName().equals("Editor")) {
				sb.append("Quản lý");
			} else if (role.getName().equals("Shipper")) {
				sb.append("Nhân viên giao hàng");
			} else if (role.getName().equals("Assistant")) {
				sb.append("Trợ lý");
			}
			sb.append(", ");
		}
		// remove the last comma and space
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 2);
		}
		return sb.toString();
	}
	
	// format sex
	@Transient
	public String getFormatedSex() {
		switch (sexs) {
		case "S01":
			return "Nam";
		case "S02":
			return "Nữ";
		default:
			return "";
		}
	}
	
	// format sex
	@Transient
	public String getFormatedDegrees() {
		switch (degrees) {
		case "E01":
			return "Đại học/Cao đẳng";
		case "E02":
			return "Trung cấp";
		case "E03":
			return "Tốt nghệp phổ thông";
		case "E04":
			return "Khác";
		default:
			return "";
		}
	}
	
	// format sex
	@Transient
	public String getFormatedMaritals() {
		switch (maritals) {
		case "U01":
			return "Độc thân";
		case "U02":
			return "Đã kết hôn";
		case "U03":
			return "Góa";
		default:
			return "";
		}
	}
}
