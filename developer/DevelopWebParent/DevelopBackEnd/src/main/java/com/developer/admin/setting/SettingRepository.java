package com.developer.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.developer.common.entity.setting.Setting;
import com.developer.common.entity.setting.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting, String> {

	public List<Setting> findByCategory(SettingCategory category);
	
	public Setting findByKey(String key);
}
