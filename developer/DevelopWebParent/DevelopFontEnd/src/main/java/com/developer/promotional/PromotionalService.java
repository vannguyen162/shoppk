package com.developer.promotional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.developer.common.entity.Board;

@Service
public class PromotionalService {
	public static final int Board_PER_PAGE = 9;

	@Autowired
	private PromotionalRepository repo;

	public Page<Board> listBoard(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, Board_PER_PAGE);
		
		return repo.listBoard(pageable);
	}
	
	public Board get(Integer id) throws PromotionalNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new PromotionalNotFoundException("Không tìm thấy ID tin tức: " + id);
		}
	}
	
	@Transactional
	public void increaseNumViews(Integer id) {
	    Optional<Board> boardOpt = repo.findById(id);
	    if(boardOpt.isPresent()){
	        Board board = boardOpt.get();
	        BigDecimal numViews = board.getNumViews();
	        if(numViews == null){
	            numViews = new BigDecimal(0);
	        }
	        numViews = numViews.add(new BigDecimal(1));
	        board.setNumViews(numViews);
	        repo.save(board);
	    }
	}
}
