package com.linln.admin.system.service;

import com.linln.admin.system.domain.*;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 岳天一
 * @date 2024/01/12
 */
public interface TestService {

    Page<DeepInfo> getLogList(Example<DeepInfo> example);
    Page<ShallowInfo> getLogList1(Example<ShallowInfo> example);
    Page<UserInfo> getLogList2(Example<UserInfo> example);

    Page<LockInfo> getLockLogList(Example<LockInfo> example);

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Test> getPageList(Example<Test> example);

    // 新增查询某个日期数据数量的方法
    long countByCreateDate(Date date);
    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Test getById(Long id);

    /**
     * 保存数据
     * @param test 实体对象
     */
    Test save(Test test);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional(rollbackFor = Exception.class)
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}