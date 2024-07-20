package com.sunyesle.spring_boot_excel.controller;

import com.sunyesle.spring_boot_excel.util.excel.ExcelFile;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/excel")
    public void excel(HttpServletResponse response) throws IOException, ParseException {
        List<TestExcelDto> itemList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        itemList.add(new TestExcelDto("A001", "상품A", 10000, true, dateFormat.parse("2024-01-01")));
        itemList.add(new TestExcelDto("B002", "상품B", 15000, false, dateFormat.parse("2024-03-16")));
        itemList.add(new TestExcelDto("C003", "상품C", 20000, true, dateFormat.parse("2024-04-05")));
        itemList.add(new TestExcelDto("D004", "상품D", 25000, false, dateFormat.parse("2024-06-25")));
        itemList.add(new TestExcelDto("E005", "상품E", 30000, true, dateFormat.parse("2024-07-20")));

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");

        ExcelFile<TestExcelDto> excelFile = new ExcelFile<>(TestExcelDto.class);

        try (OutputStream outputStream = response.getOutputStream()) {
            excelFile.write(itemList, outputStream);
        }
    }
}
