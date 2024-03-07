package com.linln.admin.system.service.impl;

import com.linln.admin.system.domain.Access;
import com.linln.admin.system.repository.AccessRepository;
import com.linln.admin.system.service.AccessService;
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
public class AccessServiceImpl implements AccessService {

    @Autowired
    private AccessRepository accessRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    public Access getById(Long id) {
        return accessRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Access> getPageList(Example<Access> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return accessRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param access 实体对象
     */
    @Override
    public Access save(Access access) {
        return accessRepository.save(access);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return accessRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}