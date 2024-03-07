package com.linln.admin.system.service.impl;

import com.linln.admin.system.domain.*;
import com.linln.admin.system.repository.StickyRepository;
import com.linln.admin.system.repository.DeepInfoRepository;
import com.linln.admin.system.repository.ShallowInfoRepository;
import com.linln.admin.system.service.StickyService;
import com.linln.admin.system.repository.UsersInfoRepository;
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
public class StickyServiceImpl implements StickyService {

    @Autowired
    private StickyRepository stickyRepository;

    @Autowired
    private DeepInfoRepository deepInfoRepo;
    @Autowired
    private ShallowInfoRepository shallowInfoRepo;
    @Autowired
    private UsersInfoRepository usersInfoRepo;

    //给前端一个包含三个表内容的日志项
    @Override
    public SLogItem fetchLogItemById(String systemId) {
        DeepInfo di = deepInfoRepo.findBySystemId(systemId);
        ShallowInfo si = shallowInfoRepo.findBySystemId(systemId);
        UserInfo ui = usersInfoRepo.findBySystemId(systemId);

        System.out.println(di);
        if (di != null || si != null || ui != null) {
            return new SLogItem(di, si, ui);
        } else {
            return null;
        }
    }

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    public Sticky getById(Long id) {
        return stickyRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Sticky> getPageList(Example<Sticky> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return stickyRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param sticky 实体对象
     */
    @Override
    public Sticky save(Sticky sticky) {
        return stickyRepository.save(sticky);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return stickyRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}