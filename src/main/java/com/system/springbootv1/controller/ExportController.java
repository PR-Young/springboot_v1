package com.system.springbootv1.controller;

import com.github.liaochong.myexcel.core.DefaultStreamExcelBuilder;
import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @description: 文件导出测试
 * @author: yy 2020/04/13
 **/
@Controller
@RequestMapping("/export/*")
public class ExportController {

    @GetMapping("export")
    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (DefaultStreamExcelBuilder<data> streamExcelBuilder = DefaultStreamExcelBuilder.of(data.class)
                .threadPool(Executors.newFixedThreadPool(10))
                .start()) {
            streamExcelBuilder.asyncAppend(this::getData);
            Workbook workbook = streamExcelBuilder.build();
            AttachmentExportUtil.export(workbook, "test", response);
        }
    }

    public List<data> getData() {
        List<data> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data a = new data();
            a.setName("test" + i);
            a.setDescription("tt" + i);
            a.setTime(new Date());
            dataList.add(a);
        }
        return dataList;
    }

    @ExcelModel(sheetName = "测试")
    public class data {
        @ExcelColumn(title = "名称")
        private String name;
        @ExcelColumn(title = "描述")
        private String description;
        @ExcelColumn(title = "时间", format = "yyyy-MM-dd HH:mm:ss")
        private Date time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }
}
