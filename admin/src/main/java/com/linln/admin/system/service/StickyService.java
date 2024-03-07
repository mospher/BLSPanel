package com.linln.admin.system.service;

import com.linln.admin.system.domain.SLogItem;
import com.linln.admin.system.domain.Sticky;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 岳天一
 * @date 2024/01/12
 */
public interface StickyService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Sticky> getPageList(Example<Sticky> example);

    //给前端一个包含三个表内容的日志项
    SLogItem fetchLogItemById(String systemId);
    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Sticky getById(Long id);

    /**
     * 保存数据
     * @param sticky 实体对象
     */
    Sticky save(Sticky sticky);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional(rollbackFor = Exception.class)
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}