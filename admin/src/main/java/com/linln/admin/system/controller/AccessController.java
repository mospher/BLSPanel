package com.linln.admin.system.controller;

import com.linln.admin.system.domain.Access;
import com.linln.admin.system.service.AccessService;
import com.linln.admin.system.validator.AccessValid;
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
@RequestMapping("/system/access")
public class AccessController {

    @Autowired
    private AccessService accessService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:access:index")
    public String index(Model model, Access access) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", match -> match.contains());

        // 获取数据列表
        Example<Access> example = Example.of(access, matcher);
        Page<Access> list = accessService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/access/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:access:add")
    public String toAdd() {
        return "/system/access/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:access:edit")
    public String toEdit(@PathVariable("id") Access access, Model model) {
        model.addAttribute("access", access);
        return "/system/access/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:access:add", "system:access:edit"})
    @ResponseBody
    public ResultVo save(@Validated AccessValid valid, Access access) {
        // 复制保留无需修改的数据
        if (access.getId() != null) {
            Access beAccess = accessService.getById(access.getId());
            EntityBeanUtil.copyProperties(beAccess, access);
        }

        // 保存数据
        accessService.save(access);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:access:detail")
    public String toDetail(@PathVariable("id") Access access, Model model) {
        model.addAttribute("access",access);
        return "/system/access/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:access:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (accessService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}