package com.system.springbootv1.restful;

import com.system.springbootv1.common.autocode.FreeMarkerGeneratorUtil;
import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.page.TableDataInfo;
import com.system.springbootv1.project.service.GeneratorService;
import com.system.springbootv1.utils.DateUtils;
import com.system.springbootv1.utils.ServletUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/05/12
 **/
@RestController
@RequestMapping("/tool/gen/*")
public class GeneratorApiController extends BaseController {

    @Resource
    GeneratorService generatorService;

    @GetMapping("db/list")
    public TableDataInfo dataList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", ServletUtils.getParameter("tableName"));
        map.put("tableComment", ServletUtils.getParameter("tableComment"));
        map.put("beginTime", ServletUtils.getParameter("beginTime"));
        map.put("endTime", ServletUtils.getParameter("endTime"));
        startPage();
        List<Map<String, Object>> list = generatorService.tables(map);
        return getDataTable(list);
    }

    @GetMapping(value = "column/{tableName}")
    public TableDataInfo columnList(@PathVariable("tableName") String tableName) {
        TableDataInfo dataInfo = new TableDataInfo();
        List<Map<String, Object>> list = generatorService.selectTableColumnListByTableName(tableName);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    @GetMapping("genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = FreeMarkerGeneratorUtil.generator(tableName);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        String date = DateUtils.dateTimeNow();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code_" + date + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
