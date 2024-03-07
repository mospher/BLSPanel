package com.linln.admin.system.controller;

import com.linln.admin.system.domain.SLogItem;
import com.linln.admin.system.domain.Sticky;
import com.linln.admin.system.service.StickyService;
import com.linln.admin.system.validator.StickyValid;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author 岳天一
 * @date 2024/01/12
 */
@Controller
@RequestMapping("/system/sticky")
public class StickyController {

    @Autowired
    private StickyService stickyService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:sticky:index")
    public String index(Model model, Sticky sticky) {
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", match -> match.contains());

        // 获取数据列表
        Example<Sticky> example = Example.of(sticky, matcher);
        Page<Sticky> list = stickyService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/sticky/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:sticky:add")
    public String toAdd() {
        return "/system/sticky/add";
    }


    /**
     * 粘随系统
     * 跳转到log页面
     */
    @GetMapping("/log/{id}")
    @RequiresPermissions("system:sticky:edit")
    public String toLog(@PathVariable("id")String id, Model model) {
        // 获取日志列表
        SLogItem sLogItem = stickyService.fetchLogItemById(id);
        System.out.println("id:   "+id);
        // 封装数据
        model.addAttribute("sLogItem",sLogItem);
        System.out.println("sLogItem:   "+sLogItem);

        return "/system/sticky/log";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:sticky:add", "system:sticky:edit"})
    @ResponseBody
    public ResultVo save(@Validated StickyValid valid, Sticky sticky) throws URISyntaxException, IOException, InterruptedException {
        // 复制保留无需修改的数据
        if (sticky.getId() != null) {
            Sticky beSticky = stickyService.getById(sticky.getId());
            EntityBeanUtil.copyProperties(beSticky, sticky);
        }
        // 保存数据
       //python
        // 构建Python脚本路径
        URL resource = getClass().getResource("/scripts/process_sticky_info.py");
        System.out.println("resource: "+ resource);
        String pythonScriptFile = Paths.get(resource.toURI()).toString();

        // 构建Python命令并执行
        StringBuilder pythonCommandBuilder = new StringBuilder("python ");
        pythonCommandBuilder.append(pythonScriptFile);
        pythonCommandBuilder.append(" ");
        pythonCommandBuilder.append(sticky.getId());

        Process p = Runtime.getRuntime().exec(pythonCommandBuilder.toString());

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
        BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream(), StandardCharsets.UTF_8));

        String line;
        while ((line = in.readLine()) != null) {
            System.out.println("Python Output: " + new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        }
        while ((line = err.readLine()) != null) {
            System.err.println("Python Error: " + line);
        }

        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new IOException("Python script returned non-zero exit code: " + exitCode);
        }
        //python

        stickyService.save(sticky);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:sticky:detail")
    public String toDetail(@PathVariable("id") Sticky sticky, Model model) {
        model.addAttribute("sticky",sticky);
        return "/system/sticky/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:sticky:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (stickyService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}