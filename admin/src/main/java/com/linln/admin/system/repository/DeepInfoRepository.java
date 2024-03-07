package com.linln.admin.system.repository;

import com.linln.admin.system.domain.DeepInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeepInfoRepository extends JpaRepository<DeepInfo, Long> {
    DeepInfo  findBySystemId(String systemId);
}