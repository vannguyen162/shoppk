package com.developer.admin.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.setting.Setting;
import com.developer.common.entity.setting.SettingCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {
	
	@Autowired
	private SettingRepository repo;
	
	@Test
	public void testCreateGeneralSettings() {
		Setting siteName = new Setting("SITE_NAME", "Shop", SettingCategory.GENERAL);
		Setting siteLogo = new Setting("SITE_LOGO", "Shop.png", SettingCategory.GENERAL);
		Setting copyright = new Setting("COPYRIGHT", "Copyright (C) 2022 by NghiaIT", SettingCategory.GENERAL);
		
		Setting savedSetting = repo.save(siteName);
		repo.saveAll(List.of(siteLogo, copyright));
		
		Iterable<Setting> iterable = repo.findAll();
		
//		assertThat(savedSetting).isNotNull();
		assertThat(iterable).size().isGreaterThan(0);
	}
	
	@Test
	public void testCreateCurrencySettings() {
		Setting COPYRIGHT 				       = new Setting("COPYRIGHT", "Copyright © 2022 ❤️ by NghiaIT - Shop Như Ngọc", SettingCategory.GENERAL);
		Setting CURRENCY_ID 			       = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
		Setting CURRENCY_SYMBOL 		       = new Setting("CURRENCY_SYMBOL", "đ", SettingCategory.CURRENCY);
		Setting CURRENCY_SYMBOL_POSITION       = new Setting("CURRENCY_SYMBOL_POSITION", "After price", SettingCategory.CURRENCY);
		Setting CUSTOMER_VERIFY_CONTENT        = new Setting("CUSTOMER_VERIFY_CONTENT", "COMMA", SettingCategory.MAIL_TEMPLATES);
		Setting CUSTOMER_VERIFY_SUBJECT        = new Setting("CUSTOMER_VERIFY_SUBJECT", "Xác nhận mật khẩu", SettingCategory.MAIL_TEMPLATES);
		Setting DECIMAL_DIGITS 			       = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
		Setting DECIMAL_POINT_TYPE 		       = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
		Setting MAIL_FROM 				       = new Setting("MAIL_FROM", "vannguyen002st@gmail.com", SettingCategory.MAIL_SERVER);
		Setting MAIL_HOST 				       = new Setting("MAIL_HOST", "smtp.gmail.com", SettingCategory.MAIL_SERVER);
		Setting MAIL_PASSWORD 			       = new Setting("MAIL_PASSWORD", "tyyagefvmjplwclw", SettingCategory.MAIL_SERVER);
		Setting MAIL_PORT 				       = new Setting("MAIL_PORT", "587", SettingCategory.MAIL_SERVER);
		Setting MAIL_SENDER_NAME 		       = new Setting("MAIL_SENDER_NAME", "Shop Team1", SettingCategory.MAIL_SERVER);
		Setting MAIL_USERNAME 			       = new Setting("MAIL_USERNAME", "vannguyen002st@gmail.com", SettingCategory.MAIL_SERVER);
		Setting SITE_LOGO				       = new Setting("SITE_LOGO", "/site-logo/logo.png", SettingCategory.GENERAL);
		Setting SITE_NAME 				       = new Setting("SITE_NAME", "Nghia IT", SettingCategory.GENERAL);
		Setting SMTP_AUTH 				       = new Setting("SMTP_AUTH", "true", SettingCategory.MAIL_SERVER);
		Setting SMTP_SECURED 			       = new Setting("SMTP_SECURED", "true", SettingCategory.MAIL_SERVER);
		Setting THOUSANDS_POINT_TYPE 	       = new Setting("THOUSANDS_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
	                                     
		repo.saveAll(List.of(
				COPYRIGHT 				       ,
				CURRENCY_ID 			       ,
				CURRENCY_SYMBOL 		       ,
				CURRENCY_SYMBOL_POSITION       ,
				CUSTOMER_VERIFY_CONTENT        ,
				CUSTOMER_VERIFY_SUBJECT        ,
				DECIMAL_DIGITS 			       ,
				DECIMAL_POINT_TYPE 		       ,
				MAIL_FROM 				       ,
				MAIL_HOST 				       ,
				MAIL_PASSWORD 			       ,
				MAIL_PORT 				       ,
				MAIL_SENDER_NAME 		       ,
				MAIL_USERNAME 			       ,
				SITE_LOGO				       ,
				SITE_NAME 				       ,
				SMTP_AUTH 				       ,
				SMTP_SECURED 			       ,
				THOUSANDS_POINT_TYPE 	       
				));
	}
	@Test
	public void testCreateCurrencySettings1() {
		Setting PHONE 				       = new Setting("PHONE", "0368023380", SettingCategory.GENERAL);
		Setting TEL 				       = new Setting("TEL", "02845445454", SettingCategory.GENERAL);
		Setting ADDRESS_COMPANY 	       = new Setting("ADDRESS_COMPANY", "93 Đường D1, khu dân cư Đông an, Dĩ An, Bình Dương", SettingCategory.GENERAL);
		Setting TIMES 				       = new Setting("TIMES", "9h - 21h", SettingCategory.GENERAL);
		Setting TAX_CODE 			       = new Setting("TAX_CODE", "0266485482", SettingCategory.GENERAL);
		Setting EMAIL_COMPANY 		       = new Setting("EMAIL_COMPANY", "nghia.nguyen1622@gmail.com", SettingCategory.GENERAL);
		repo.saveAll(List.of(
				PHONE 				       ,
				TEL,
				ADDRESS_COMPANY,
				TIMES,
				TAX_CODE,
				EMAIL_COMPANY
				
				));
	}
	@Test
	public void testCreateCurrencySettings2() {
		Setting LINK_ZALO 				       = new Setting("LINK_ZALO", "https://zalo.me/0368023380", SettingCategory.GENERAL);
		Setting LINK_FB 				       = new Setting("LINK_FB", "https://www.facebook.com/nguyenvannghia1622it/", SettingCategory.GENERAL);
		Setting LINK_YT 				       = new Setting("LINK_YT", "https://www.youtube.com/@it-developerwebsitejava2352", SettingCategory.GENERAL);
		Setting LINK_CONNECT 			       = new Setting("LINK_CONNECT", "https://www.datxanh.vn/", SettingCategory.GENERAL);
		repo.saveAll(List.of(
				LINK_ZALO,
				LINK_FB,
				LINK_YT,
				LINK_CONNECT
				
				));
	}
	@Test
	public void testCreateBackup() {
		Setting BACKUP_YN 				       = new Setting("BACKUP_YN", "Y", SettingCategory.GENERAL);
		repo.saveAll(List.of(
				BACKUP_YN
				));
	}
	@Test
	public void testCreateNewOrderAndCheckOut() {
		Setting ORDER_CONFIRMATION_SUBJECT 	       = new Setting("ORDER_CONFIRMATION_SUBJECT", "Confirmation of your order ID #[[orderId]]", SettingCategory.MAIL_TEMPLATES);
		Setting ORDER_CONFIRMATION_CONTENT 	       = new Setting("ORDER_CONFIRMATION_CONTENT", "Dear [[name]], this email is to confirm that...", SettingCategory.MAIL_TEMPLATES);
		Setting PAYPAL_API_BASE_URL 		       = new Setting("PAYPAL_API_BASE_URL", "https://api-m.sandbox.paypal.com", SettingCategory.PAYMENT);
		Setting PAYPAL_API_CLIENT_ID 		       = new Setting("PAYPAL_API_CLIENT_ID", "PAYPAL_CLIENT_ID", SettingCategory.PAYMENT);
		Setting PAYPAL_API_CLIENT_SECRET 	       = new Setting("PAYPAL_API_CLIENT_SECRET", "PAYPAL_CLIENT_SECRET", SettingCategory.PAYMENT);
		repo.saveAll(List.of(
				ORDER_CONFIRMATION_SUBJECT,
				ORDER_CONFIRMATION_CONTENT,
				PAYPAL_API_BASE_URL,
				PAYPAL_API_CLIENT_ID,
				PAYPAL_API_CLIENT_SECRET
				
				));
	}
	
	@Test
	public void testListSettingsByCategory() {
		List<Setting> settings = repo.findByCategory(SettingCategory.GENERAL);
		
		settings.forEach(System.out :: println);
	}
}
