package com.developer.admin.shippingrate;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.security.DevelopUserDetails;
import com.developer.admin.setting.country.CountryRepository;
import com.developer.common.entity.Country;
import com.developer.common.entity.ShippingRate;

@Service
@Transactional
public class ShippingRateService {

	public static final int RATES_PER_PAGE = 10;
	
	@Autowired private ShippingRateRepository shipRepo;
	@Autowired private CountryRepository countryRepo;
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, RATES_PER_PAGE, shipRepo);
	}
	
	public List<Country> listAllCountries(){
		return countryRepo.findAllByOrderByNameAsc();
	}
	
	public void save(ShippingRate rateInform) throws ShippingRateAlreadyExistsException{
		ShippingRate rateInDB = shipRepo.findByCountryAndState(
				rateInform.getCountry().getId(), rateInform.getState());
		boolean foundExistingRateInNewMode = rateInform.getId() == null && rateInDB != null;
		boolean foundDifferentExistingRateInEditMode = rateInform.getId() != null && rateInDB != null;
		
		if(foundExistingRateInNewMode || foundDifferentExistingRateInEditMode) {
			throw new ShippingRateAlreadyExistsException("Đã có khu vực "
					+ rateInform.getCountry().getName() + ", " + rateInform.getState());
		}
		if(rateInform.getId() == null) {
			rateInform.setCreatedTime(new Date());
			rateInform.setUpdatedTime(new Date());
		}else {
			ShippingRate existingRate = shipRepo.findById(rateInform.getId()).get();
			rateInform.setCreatedTime(existingRate.getCreatedTime());
			rateInform.setUpdatedTime(new Date());
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DevelopUserDetails loggedUser = (DevelopUserDetails)auth.getPrincipal();
		rateInform.setWorkUser(loggedUser.getFullname());
		
		shipRepo.save(rateInform);
	}
	
	public ShippingRate get(Integer id) throws ShippingRateNotFoundException {
		try {
			return shipRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ShippingRateNotFoundException("Không thể tìm thấy điểm đến với id: " + id);
		}
	}
	
	public void updateCODSupport(Integer id, boolean codSupported) throws ShippingRateNotFoundException{
		Long count = shipRepo.countById(id);
		if(count == null || count == 0) {
			throw new ShippingRateNotFoundException("Không thể tìm thấy điểm đến với id: " + id);
		}
		
		shipRepo.updateCODSupport(id, codSupported);
	}
	
	public void delete(Integer id) throws ShippingRateNotFoundException{
		Long count = shipRepo.countById(id);
		if(count == null || count == 0) {
			throw new ShippingRateNotFoundException("Không thể tìm thấy khu vực: " + id);
		}
		shipRepo.deleteById(id);
	}
}
