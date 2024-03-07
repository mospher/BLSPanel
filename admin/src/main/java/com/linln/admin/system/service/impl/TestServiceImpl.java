package com.linln.admin.system.service.impl;

import com.linln.admin.system.domain.*;
import com.linln.admin.system.repository.*;
import com.linln.admin.system.service.TestService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 岳天一
 * @date 2024/01/12
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private DeepInfoRepository deepInfoRepo;
    @Autowired
    private ShallowInfoRepository shallowInfoRepo;
    @Autowired
    private UsersInfoRepository usersInfoRepo;
    @Autowired
    private LockInfoRepository lcokInfoRepo;


    @Autowired
    private TestRepository testRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    public Test getById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Test> getPageList(Example<Test> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return testRepository.findAll(example, page);
    }

    //给前端一个包含三个表内容的日志项
    @Override
    public Page<DeepInfo> getLogList(Example<DeepInfo> deepInfoExample) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        // 分别查询三个表的数据
        return deepInfoRepo.findAll(deepInfoExample, page);
    }
    @Override
    public Page<ShallowInfo> getLogList1(Example<ShallowInfo> deepInfoExample) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        // 分别查询三个表的数据
        return shallowInfoRepo.findAll(deepInfoExample, page);
    }
    @Override
    public Page<UserInfo> getLogList2(Example<UserInfo> deepInfoExample) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        // 分别查询三个表的数据
        return usersInfoRepo.findAll(deepInfoExample, page);
    }

    @Override
    public Page<LockInfo> getLockLogList(Example<LockInfo> lcokInfoExample) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        // 分别查询三个表的数据
        return lcokInfoRepo.findAll(lcokInfoExample, page);
    }

    @Override
    public long countByCreateDate(Date date) {
        return testRepository.countByCreateDate(date);
    }

    /**
     * 保存数据
     * @param test 实体对象
     */
    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return testRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}