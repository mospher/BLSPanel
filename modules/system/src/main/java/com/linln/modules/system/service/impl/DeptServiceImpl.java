package com.linln.modules.system.service.impl;

import com.linln.common.enums.ResultEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
import com.linln.modules.system.domain.Dept;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.DeptRepository;
import com.linln.modules.system.repository.UserRepository;
import com.linln.modules.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据项目管理ID查询项目管理数据
     * @param id 项目管理ID
     */
    @Override
    @Transactional
    public Dept getById(Long id) {
        return deptRepository.findById(id).orElse(null);
    }

    /**
     * 获取项目列表数据
     * @param example 查询实例
     * @param sort 排序对象
     */
    @Override
    public List<Dept> getListByExample(Example<Dept> example, Sort sort) {
        return deptRepository.findAll(example, sort);
    }

    /**
     * 获取排序最大值
     * @param pid 父菜单ID
     */
    @Override
    public Integer getSortMax(Long pid){
        return deptRepository.findSortMax(pid);
    }

    /**
     * 根据父级项目ID获取本级全部项目
     * @param pid 父项目ID
     * @param notId 需要排除的项目ID
     */
    @Override
    public List<Dept> getListByPid(Long pid, Long notId){
        Sort sort = Sort.by(Sort.Direction.ASC, "sort");
        return deptRepository.findByPidAndIdNot(sort, pid, notId);
    }

    /**
     * 根据ID查找子孙项目
     * @param id [id]形式
     */
    @Override
    public List<Dept> getListByPidLikeOk(Long id){
        return deptRepository.findByPidsLikeAndStatus("%["+id+"]%", StatusEnum.OK.getCode());
    }

    /**
     * 保存项目管理
     * @param dept 项目管理实体类
     */
    @Override
    public Dept save(Dept dept){
        return deptRepository.save(dept);
    }

    /**
     * 保存多个项目
     * @param deptList 项目实体类列表
     */
    @Override
    public List<Dept> save(List<Dept> deptList){
        return deptRepository.saveAll(deptList);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> ids){
        // 获取与之关联的所有项目
        Set<Dept> treeDepts = new HashSet<>();
        List<Dept> depts = deptRepository.findByIdIn(ids);
        depts.forEach(dept -> {
            treeDepts.add(dept);
            treeDepts.addAll(deptRepository.findByPidsLikeAndStatus("%[" + dept.getId() + "]%", dept.getStatus()));
        });

        treeDepts.forEach(dept -> {
            if(statusEnum == StatusEnum.DELETE){
                List<User> users = userRepository.findByDept(dept);
                if(users.size() > 0){
                    throw new ResultException(ResultEnum.DEPT_EXIST_USER);
                }
            }
            // 更新关联的所有项目状态
            dept.setStatus(statusEnum.getCode());
        });

        return treeDepts.size() > 0;
    }
}

