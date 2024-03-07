package com.linln.admin.system.controller;

import com.linln.admin.system.domain.*;
import com.linln.admin.system.service.StickyService;
import com.linln.admin.system.service.TestService;
import com.linln.admin.system.validator.TestValid;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 岳天一
 * @date 2024/01/12
 */
@Controller
@RequestMapping("/system/test")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private StickyService stickyService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:test:index")
    public String index(Model model, Test test) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", match -> match.contains());

        // 获取数据列表
        Example<Test> example = Example.of(test, matcher);
        Page<Test> list = testService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/test/index";
    }

    /**
     * 跳转到log页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:sticky:edit")
    public String toEdit(Test test, Model model) {
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();
        // 获取日志列表
        Example<Test> example = Example.of(test, matcher);
        Page<Test> list = testService.getPageList(example);
        // 封装数据
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);

        return "/system/sticky/log";
    }

    /**
     * 粘随系统
     * 跳转到log页面
     */
    @GetMapping("/log/{id}")
    @RequiresPermissions("system:sticky:edit")
    public String toLog(DeepInfo deepInfo,Model model) {
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();
        // 获取日志列表
        Example<DeepInfo> example = Example.of(deepInfo, matcher);
        Page<DeepInfo> list = testService.getLogList(example);
        // 封装数据
        System.out.println("list"+list.getContent());

        model.addAttribute("list",list.getContent());

        model.addAttribute("page",list);

        return "/system/sticky/log";
    }
    @GetMapping("/log2/{id}")
    @RequiresPermissions("system:sticky:edit")
    public String toLog2(@PathVariable("id") Long id,Model model) {
        SLogItem sLogItem = stickyService.fetchLogItemById(String.valueOf(id));
        DeepInfo deepInfo = null;
        if(sLogItem!=null){
            deepInfo =  sLogItem.getDeepInfo();
        }
        model.addAttribute("deepInfo",deepInfo);
        System.out.println("d================="+ deepInfo);
        return "/system/sticky/log";
    }

    /**
     * 锁闭保护
     * 跳转到log页面
     */
    @GetMapping("/lock/log/{id}")
    @RequiresPermissions("system:sticky:edit")
    public String toLockLog(LockInfo lockInfo, Model model) {
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();
        // 获取日志列表
        Example<LockInfo> example = Example.of(lockInfo, matcher);
        Page<LockInfo> list = testService.getLockLogList(example);
        // 封装数据
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);

        return "/system/locking/log";
    }

    /**
     * 获取服务器容量历史数据
     */
    @GetMapping("/find/{id}")
    @RequiresPermissions("system:test:detail")
    public String toDetail(@PathVariable("id") Test test, Model model) {
        model.addAttribute("test",test);
        return "/system/locking/detail";
    }
    /**
     * 获取服务器容量历史数据
     */
    @GetMapping("/detail/{date}")
    @RequiresPermissions("system:test:detail")
    @ResponseBody
    public Map<String, Integer> getCapacityOnDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dates) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        Map<String, Integer> serverCapacities = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, -1); // 回溯一天
            Date date = calendar.getTime();
            String formattedDate = sdf.format(date);
            long count = testService.countByCreateDate(date);
            serverCapacities.put(formattedDate, (int) count);
        }

        System.out.println("serverCapacities:" + serverCapacities);
        return serverCapacities;
    }
    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:test:add")
    public String toAdd() {
        return "/system/test/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:test:add", "system:test:edit"})
    @ResponseBody
    public ResultVo save(@Validated TestValid valid, Test test) {
        // 复制保留无需修改的数据
        if (test.getId() != null) {
            Test beTest = testService.getById(test.getId());
            EntityBeanUtil.copyProperties(beTest, test);
        }

        // 保存数据
        testService.save(test);
        return ResultVoUtil.SAVE_SUCCESS;
    }


    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:test:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (testService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}