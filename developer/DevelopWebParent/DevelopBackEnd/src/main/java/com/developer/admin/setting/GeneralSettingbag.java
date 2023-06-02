package com.developer.admin.setting;

import java.util.List;

import com.developer.common.entity.setting.Setting;
import com.developer.common.entity.setting.SettingBag;

public class GeneralSettingbag extends SettingBag {

	public GeneralSettingbag(List<Setting> listSettings) {
		super(listSettings);
	}
	
	public void updateCurrencySymbol(String value) {
		super.update("CURRENCY_SYMBOL", value);
	}
	
	public void updateSiteLogo(String value) {
		super.update("SITE_LOGO", value);
	}

}
