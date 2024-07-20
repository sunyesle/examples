package com.sunyesle.spring_boot_excel.controller;

import com.sunyesle.spring_boot_excel.util.excel.ExcelColumn;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class TestExcelDto {
    @ExcelColumn(headerName = "상품코드")
    private final String itemCode;

    @ExcelColumn(headerName = "상품명", width = 8000)
    private final String itemName;

    @ExcelColumn(headerName = "단가")
    private final int price;

    @ExcelColumn(headerName = "판매여부")
    private final boolean isAvailable;

    @ExcelColumn(headerName = "등록일")
    private final Date createdDate;
}
