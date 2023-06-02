package com.developer.admin.backup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.developer.admin.paging.SearchRepository;
import com.developer.common.entity.Backup;

public interface BackupRepository extends SearchRepository<Backup, Integer> {
	
	@Query("SELECT b FROM Backup b WHERE CONCAT(b.timeCreate, ' ', b.countDownload, ' ') LIKE %?1%")
	public Page<Backup> findAll(String keyword, Pageable pageable);
	
	Backup findByPathFile(String pathFile);
}
