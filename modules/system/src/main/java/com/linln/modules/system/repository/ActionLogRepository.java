package com.linln.modules.system.repository;

import com.linln.modules.system.domain.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/10/19
 */
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {

    /**
     * 根据模型和数据ID查询日志列表
     * @param model 模型（表名）
     * @param recordId 数据ID
     * @return 日志列表
     */
    public List<ActionLog> findByModelAndRecordId(String model, Long recordId);

    // 新增查询某个日期数据数量的方法
    @Query(value = "SELECT COUNT(*) FROM sys_action_log WHERE DATE(create_date) = ?1", nativeQuery = true)
    long countByCreateDate(Date date);
}
