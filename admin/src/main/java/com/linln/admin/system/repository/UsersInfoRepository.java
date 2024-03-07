package com.linln.admin.system.repository;

import com.linln.admin.system.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findBySystemId(String systemId);
}
