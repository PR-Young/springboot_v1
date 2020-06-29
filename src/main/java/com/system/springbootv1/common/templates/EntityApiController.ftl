package com.system.springbootv1.restful;

import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.BaseResult;
import ${entityPackage}.${entityName};
import com.system.springbootv1.project.model.page.TableDataInfo;
import ${servicePackage}.${entityName}Service;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/${entityName1}/*")
public class ${entityName}ApiController extends BaseController {

    @Resource
    ${entityName}Service ${entityName1}Service;


    @RequiresPermissions("${entityName1}:list")
    @RequestMapping("list")
    public TableDataInfo list(${entityName} ${entityName1}) {
        startPage();
        List<${entityName}> ${entityName1}List = ${entityName1}Service.list(${entityName1});
        return getDataTable(${entityName1}List);
    }

    @GetMapping("{${entityName1}Id}")
    public Object getInfo(@PathVariable String ${entityName1}Id) {
        return BaseResult.successData(${entityName1}Service.getById(${entityName1}Id));
    }

    @RequiresPermissions("${entityName1}:edit")
    @PostMapping("edit")
    public Object edit(@Validated @RequestBody ${entityName} ${entityName1}) {
        if (StringUtils.isEmpty(${entityName1}.getId())) {
            ${entityName1}Service.insert(${entityName1});
        } else {
            ${entityName1}Service.update(${entityName1});
        }
        return BaseResult.success();
    }

    @RequiresPermissions("${entityName1}:delete")
    @DeleteMapping("{${entityName1}Ids}")
    public Object remove(@PathVariable String[] ${entityName1}Ids) {
        ${entityName1}Service.deleteByIds(Arrays.asList(${entityName1}Ids));
        return BaseResult.success();
    }
}
