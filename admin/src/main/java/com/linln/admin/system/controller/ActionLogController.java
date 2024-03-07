package com.linln.admin.system.controller;

import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.system.domain.ActionLog;
import com.linln.modules.system.service.ActionLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 小懒虫
 * @date 2018/10/19
 */
@Controller
@RequestMapping("/system/actionLog")
public class ActionLogController {

    @Autowired
    private ActionLogService actionLogService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:actionLog:index")
    public String index(Model model, ActionLog actionLog){

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取日志列表
        Example<ActionLog> example = Example.of(actionLog, matcher);
        Page<ActionLog> list = actionLogService.getPageList(example);

        // 封装数据
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);
        return "/system/actionLog/index";
    }
    /**
     * 获取服务器容量历史数据
     */
    @GetMapping("/dates/{date}")
    @RequiresPermissions("system:test:detail")
    @ResponseBody
    public Map<String, Integer> getCapacityOnDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dates) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        Map<String, Integer> serverCapacities = new TreeMap<>();

        // 遍历包括今天在内的过去6天
        for (int i = 0; i <= 5; i++) {
            if (i > 0) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }
            // 创建一个新的只包含日期部分的Date对象
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date dateWithoutTimePortion = calendar.getTime();

            String formattedDate = sdf.format(dateWithoutTimePortion);
            System.out.println("dateWithoutTimePortion:" + dateWithoutTimePortion);
            long count = actionLogService.countByCreateDate(dateWithoutTimePortion);
            serverCapacities.put(formattedDate, (int) count);
        }

        System.out.println("serverCapacities:" + serverCapacities);
        return serverCapacities;
    }
    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:actionLog:detail")
    public String toDetail(@PathVariable("id") ActionLog actionLog, Model model){
        model.addAttribute("actionLog",actionLog);
        return "/system/actionLog/detail";
    }

    /**
     * 删除指定的日志
     */
    @RequestMapping("/status/delete")
    @RequiresPermissions("system:actionLog:delete")
    @ResponseBody
    public ResultVo delete(
            @RequestParam(value = "ids", required = false) Long id){
        if (id != null){
            actionLogService.deleteId(id);
            return ResultVoUtil.success("删除日志成功");
        }else {
            actionLogService.emptyLog();
            return ResultVoUtil.success("清空日志成功");
        }
    }
}
