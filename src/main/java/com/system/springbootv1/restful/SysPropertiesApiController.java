package com.system.springbootv1.restful;

import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysProperties;
import com.system.springbootv1.project.model.page.TableDataInfo;
import com.system.springbootv1.project.service.SysPropertiesService;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/sysProperties/*")
public class SysPropertiesApiController extends BaseController {

    @Resource
    SysPropertiesService sysPropertiesService;


    @RequiresPermissions("sysProperties:list")
    @RequestMapping("list")
    public TableDataInfo list(SysProperties sysProperties) {
        startPage();
        List<SysProperties> sysPropertiesList = sysPropertiesService.list(sysProperties);
        return getDataTable(sysPropertiesList);
    }

    @GetMapping("{sysPropertiesId}")
    public Object getInfo(@PathVariable String sysPropertiesId) {
        return BaseResult.successData(sysPropertiesService.getById(sysPropertiesId));
    }

    @RequiresPermissions("sysProperties:edit")
    @PostMapping("edit")
    public Object edit(@Validated @RequestBody SysProperties sysProperties) {
        if (StringUtils.isEmpty(sysProperties.getId())) {
            sysPropertiesService.insert(sysProperties);
        } else {
            sysPropertiesService.update(sysProperties);
        }
        return BaseResult.success();
    }

    @RequiresPermissions("sysProperties:delete")
    @DeleteMapping("{sysPropertiesIds}")
    public Object remove(@PathVariable String[] sysPropertiesIds) {
        sysPropertiesService.deleteByIds(Arrays.asList(sysPropertiesIds));
        return BaseResult.success();
    }
}
