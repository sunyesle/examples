package com.sunyesle.spring_boot_excel.util.excel;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ExcelMetaData {
    private final Map<String, String> headerNamesMap;
    private final Map<String, Integer> widthMap;
    private final List<String> fieldNames;

    public ExcelMetaData(Map<String, String> headerNamesMap, Map<String, Integer> widthMap, List<String> fieldNames) {
        this.headerNamesMap = headerNamesMap;
        this.widthMap = widthMap;
        this.fieldNames = fieldNames;
    }
}
