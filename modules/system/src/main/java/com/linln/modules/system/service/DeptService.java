package com.linln.modules.system.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.Dept;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
public interface DeptService {

    /**
     * 获取项目列表数据
     * @param example 查询实例
     * @param sort 排序对象
     * @return 项目列表
     */
    List<Dept> getListByExample(Example<Dept> example, Sort sort);

    /**
     * 获取排序最大值
     * @param pid 父菜单ID
     * @return 最大值
     */
    Integer getSortMax(Long pid);

    /**
     * 根据父级项目ID获取本级全部项目
     * @param pid 父项目ID
     * @param notId 需要排除的项目ID
     * @return 项目列表
     */
    List<Dept> getListByPid(Long pid, Long notId);

    /**
     * 保存多个项目
     * @param deptList 项目实体类列表
     * @return 项目列表
     */
    List<Dept> save(List<Dept> deptList);

    /**
     * 根据项目管理ID查询项目管理数据
     * @param id 项目管理ID
     * @return 项目信息
     */
    Dept getById(Long id);

    /**
     * 根据ID查找子孙项目
     * @param id [id]形式
     * @return 项目列表
     */
    List<Dept> getListByPidLikeOk(Long id);

    /**
     * 保存项目管理
     * @param dept 项目管理实体类
     * @return 项目信息
     */
    Dept save(Dept dept);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     * @param statusEnum 数据状态
     * @param idList 数据ID列表
     * @return 操作结果
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}

