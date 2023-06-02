package com.developer;

import java.util.Calendar;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.developer.common.entity.customer.CustomerActivity;
import com.developer.customer.activity.CustomerActivityRepository;

@Component
public class ScheduleClearJob {

	private final CustomerActivityRepository customerActivityRepository;

	public ScheduleClearJob(CustomerActivityRepository customerActivityRepository) {
		super();
		this.customerActivityRepository = customerActivityRepository;
	}
	
    @Scheduled(cron = "0 0 * * * *") // run every day at midnight
    public void cleanupOldCustomerActivities() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7); // cutoff date is 7 days ago
        List<CustomerActivity> oldActivities = customerActivityRepository.findOlderThan(cal.getTime());
        customerActivityRepository.deleteAll(oldActivities);
    }
}
