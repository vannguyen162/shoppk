package com.developer.admin.promotional;

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
import com.developer.common.entity.Board;
import com.developer.common.entity.Product;

@Service
@Transactional
public class PromotionalService {
	public static final int Board_PER_PAGE = 10;
	
	@Autowired
	private PromotionalRepository repo;
	
	public List<Board> listAll() {
		return (List<Board>) repo.findAll();
	}
	
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
	helper.listEntities(pageNum, Board_PER_PAGE, repo); }
	 
	public Board save(Board board) {
		if (board.getId() == null) {
			board.setCreatedTime(new Date());
			board.setUpdatedTime(new Date());
			
			// create boardNo code.
			String typeCode = board.getKindContent();
			List<Board> boards = repo.findByKindOrderByKindContentDesc(typeCode);
			int nextRandomNumber;
			String latestBoardtKey = null;
			if (!boards.isEmpty()) {
			  latestBoardtKey = boards.get(0).getBoardNo();
			}
			if (latestBoardtKey != null) {
			  int latestRandomNumber = Integer.parseInt(latestBoardtKey.substring(typeCode.length()));
			  nextRandomNumber = latestRandomNumber + 1;
			} else {
			  nextRandomNumber = 1;
			}
			String boardKey = generateBoardKey(typeCode, nextRandomNumber);
			board.setBoardNo(boardKey);

	        // end boardNo code.
	    } else {
	    	Board existingBoard = repo.findById(board.getId()).get();
	        board.setCreatedTime(existingBoard.getCreatedTime());
	        board.setUpdatedTime(new Date());
	    }
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DevelopUserDetails loggedUser = (DevelopUserDetails)auth.getPrincipal();
		board.setWorkUser(loggedUser.getFullname());
		return repo.save(board);
	}
	
	public String generateBoardKey(String typeCode, int nextRandomNumber) {
	    switch(typeCode) {
	        case "B01":
	            return typeCode + String.format("%05d", nextRandomNumber);
	        case "B02":
	            return typeCode + String.format("%05d", nextRandomNumber);
	        case "B03":
	            return typeCode + String.format("%05d", nextRandomNumber);
	        default:
	            return null;
	    }
	}
	
	public Board get(Integer id) throws PromotionalNotFoundException {
		try {
			return repo.findById(id).get();

		} catch (NoSuchElementException ex) {
			throw new PromotionalNotFoundException("Không tìm thấy ID tin tức: " + id);
		}

	}

	public void delete(Integer id) throws PromotionalNotFoundException {
		Long countById = repo.countById(id);
		if (countById == null || countById == 0) {
			throw new PromotionalNotFoundException("Không tìm thấy ID tin tức: " + id);
		}
		repo.deleteById(id);
	}
	
	public void updateEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
}
