package com.linln.admin.system.repository;

import com.linln.admin.system.domain.LockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockInfoRepository extends JpaRepository<LockInfo, Long> {}