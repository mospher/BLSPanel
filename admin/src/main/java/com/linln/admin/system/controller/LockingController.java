package com.linln.admin.system.controller;

import com.linln.admin.system.domain.Locking;
import com.linln.admin.system.service.LockingService;
import com.linln.admin.system.validator.LockingValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 岳天一
 * @date 2024/01/12
 */
@Controller
@RequestMapping("/system/locking")
public class LockingController {

    @Autowired
    private LockingService lockingService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:locking:index")
    public String index(Model model, Locking locking) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", match -> match.contains());

        // 获取数据列表
        Example<Locking> example = Example.of(locking, matcher);
        Page<Locking> list = lockingService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/locking/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:locking:add")
    public String toAdd() {
        return "/system/locking/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:locking:edit")
    public String toEdit(@PathVariable("id") Locking locking, Model model) {
        model.addAttribute("locking", locking);
        return "/system/locking/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:locking:add", "system:locking:edit"})
    @ResponseBody
    public ResultVo save(@Validated LockingValid valid, Locking locking) {
        // 复制保留无需修改的数据
        if (locking.getId() != null) {
            Locking beLocking = lockingService.getById(locking.getId());
            EntityBeanUtil.copyProperties(beLocking, locking);
        }

        // 保存数据
        lockingService.save(locking);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:locking:detail")
    public String toDetail(@PathVariable("id") Locking locking, Model model) {
        model.addAttribute("locking",locking);
        return "/system/locking/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:locking:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (lockingService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}