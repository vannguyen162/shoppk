package com.developer.admin.backup;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.common.entity.Backup;

@Service
@Transactional
public class BackupService {
	public static final int BACKUP_PER_PAGE = 10;

	@Autowired
	private BackupRepository repo;

	public List<Backup> listAll() {
		return (List<Backup>) repo.findAll();
	}

	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, BACKUP_PER_PAGE, repo);
	}
}
