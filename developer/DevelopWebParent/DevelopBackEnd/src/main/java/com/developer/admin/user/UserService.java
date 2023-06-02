package com.developer.admin.user;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.security.DevelopUserDetails;
import com.developer.common.entity.Role;
import com.developer.common.entity.User;

@Service
@Transactional
public class UserService {

	public static final int USERS_PER_PAGE = 10;

	@Autowired
	private UserReposiroty userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}

	public List<User> listAll() {
		return (List<User>) userRepo.findAll(Sort.by("fullName").ascending());
	}

	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, USERS_PER_PAGE, userRepo);
	}

	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
			user.setCreatedTime(existingUser.getCreatedTime());
			user.setUpdatedTime(new Date());
		} else {
			encodePassword(user);
			user.setCreatedTime(new Date());
			user.setUpdatedTime(new Date());
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DevelopUserDetails loggedUser = (DevelopUserDetails)auth.getPrincipal();
		user.setWorkUser(loggedUser.getFullname());
		
		return userRepo.save(user);

		
	}
	
	public User updateAccount(User userInform) {
		User userinDB = userRepo.findById(userInform.getId()).get();
		
		if(!userInform.getPassword().isEmpty()) {
			userinDB.setPassword(userInform.getPassword());
			encodePassword(userinDB);
		}
		if(userInform.getPhotos() != null) {
			userinDB.setPhotos(userInform.getPhotos());
		}
		userinDB.setFullName(userInform.getFullName());
		userinDB.setAdress(userInform.getAdress());
		userinDB.setPhone(userInform.getPhone());
		userinDB.setDate(userInform.getDate());
		userinDB.setBirthplace(userInform.getBirthplace());
		userinDB.setIdentity_card(userInform.getIdentity_card());
		userinDB.setDate_range(userInform.getDate_range());
		userinDB.setIssued_by(userInform.getIssued_by());
		userinDB.setDegrees(userInform.getDegrees());
		userinDB.setMaritals(userInform.getMaritals());
		userinDB.setSexs(userInform.getSexs());
		
		return userRepo.save(userinDB);
	}

	private void encodePassword(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
	}

	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);

		if (userByEmail == null)
			return true;

		boolean isCreatingNew = (id == null);

		if (isCreatingNew) {
			if (userByEmail != null)
				return false;
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}

		return true;
	}
	
	public boolean isPhoneUnique(Integer id, String phone) {
		User userByPhone = userRepo.getUserByPhone(phone);

		if (userByPhone == null)
			return true;

		boolean isCreatingNew = (id == null);

		if (isCreatingNew) {
			if (userByPhone != null)
				return false;
		} else {
			if (userByPhone.getId() != id) {
				return false;
			}
		}

		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();

		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Không tìm thấy ID người dùng: " + id);
		}

	}

	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Không tìm thấy ID người dùng: " + id);
		}
		userRepo.deleteById(id);
	}

	public void updateEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}

}
