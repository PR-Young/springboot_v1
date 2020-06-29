package com.system.springbootv1.restful;

import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysTemplate;
import com.system.springbootv1.project.model.page.TableDataInfo;
import com.system.springbootv1.project.service.SysTemplateService;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/sysTemplate/*")
public class SysTemplateApiController extends BaseController {

    @Resource
    SysTemplateService sysTemplateService;

    @RequiresPermissions("sysTemplate:list")
    @RequestMapping("list")
    public TableDataInfo list(SysTemplate sysTemplate) {
        startPage();
        List<SysTemplate> sysTemplateList = sysTemplateService.list(sysTemplate);
        return getDataTable(sysTemplateList);
    }

    @GetMapping("{sysTemplateId}")
    public Object getInfo(@PathVariable String sysTemplateId) {
        return BaseResult.successData(sysTemplateService.getById(sysTemplateId));
    }

    @RequiresPermissions("sysTemplate:edit")
    @PostMapping("edit")
    public Object edit(@Validated @RequestBody SysTemplate sysTemplate) {
        if (StringUtils.isEmpty(sysTemplate.getId())) {
            sysTemplateService.insert(sysTemplate);
        } else {
            sysTemplateService.update(sysTemplate);
        }
        return BaseResult.success();
    }

    @RequiresPermissions("sysTemplate:delete")
    @DeleteMapping("{sysTemplateIds}")
    public Object remove(@PathVariable String[] sysTemplateIds) {
        sysTemplateService.deleteByIds(Arrays.asList(sysTemplateIds));
        return BaseResult.success();
    }
}
