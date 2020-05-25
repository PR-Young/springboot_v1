package ${controllerPackage};

import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.model.BaseResult;
import ${entityPackage}.${entityName};
import com.system.springbootv1.project.model.TableResult;
import ${servicePackage}.${entityName}Service;
import com.system.springbootv1.utils.ServletUtils;
import com.system.springbootv1.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Api("")
@Controller
@RequestMapping("/${entityName1}/*")
public class ${entityName}Controller extends BaseController {

    @Resource
    ${entityName}Service ${entityName1}Service;

    private String prefix = "${entityName1}";

    @ApiOperation(value = "")
    @RequestMapping("list")
    @RequiresPermissions("${entityName1}:list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response) {
        PageInfo<${entityName}> pageInfo = ${entityName1}Service.list(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"), ServletUtils.getQueryParams());
        TableResult<${entityName}> tableResult = new TableResult<>(pageInfo.getPageNum(), pageInfo.getTotal(), pageInfo.getList());
        return tableResult;
    }

    @ApiOperation(value = "跳转新增页面")
    @GetMapping("add")
    public String add(ModelMap map) {
        map.put("${entityName1}", new ${entityName}());
        return prefix + "/edit";
    }

    @ApiOperation(value = "跳转编辑页面")
    @GetMapping("update/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map) {
        map.put("${entityName1}", ${entityName1}Service.getById(id));
        return prefix + "/edit";
    }

    @ApiOperation(value = "新增/修改保存")
    @RequestMapping("edit")
    @RequiresPermissions("${entityName1}:edit")
    @ResponseBody
    public Object edit(${entityName} ${entityName1}, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(${entityName1}.getId())) {
            ${entityName1}Service.insert(${entityName1});
        } else {
            ${entityName1}Service.update(${entityName1});
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "删除操作")
    @RequestMapping("delete")
    @RequiresPermissions("${entityName1}:delete")
    @ResponseBody
    public Object delete(HttpServletRequest request, HttpServletResponse response) {
        String ids = ServletUtils.getParameter("ids");
        if (null != ids) {
            ${entityName1}Service.deleteByIds(Arrays.asList(ids.split(",")));
        }
        return BaseResult.success();
    }
}