package com.linln.admin.system.repository;

import com.linln.admin.system.domain.DeepInfo;
import com.linln.admin.system.domain.ShallowInfo;
import com.linln.admin.system.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShallowInfoRepository extends JpaRepository<ShallowInfo, Long> {
    ShallowInfo findBySystemId(String systemId);
}
