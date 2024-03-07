package com.linln.admin.system.service.impl;

import com.linln.admin.system.domain.Locking;
import com.linln.admin.system.repository.LockingRepository;
import com.linln.admin.system.service.LockingService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 岳天一
 * @date 2024/01/12
 */
@Service
public class LockingServiceImpl implements LockingService {

    @Autowired
    private LockingRepository lockingRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    public Locking getById(Long id) {
        return lockingRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Locking> getPageList(Example<Locking> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return lockingRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param locking 实体对象
     */
    @Override
    public Locking save(Locking locking) {
        return lockingRepository.save(locking);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return lockingRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}