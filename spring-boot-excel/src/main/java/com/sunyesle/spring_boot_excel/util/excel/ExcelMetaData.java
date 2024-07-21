package com.sunyesle.spring_boot_excel.util.excel;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ExcelMetaData {
    private final Map<String, String> headerNamesMap;
    private final Map<String, Integer> widthMap;
    private final Map<String, Style> headerStyleMap;
    private final Map<String, Style> dataStyleMap;
    private final List<String> fieldNames;

    public ExcelMetaData(Map<String, String> headerNamesMap, Map<String, Integer> widthMap, Map<String, Style> headerStyleMap, Map<String, Style> dataStyleMap, List<String> fieldNames) {
        this.headerNamesMap = headerNamesMap;
        this.widthMap = widthMap;
        this.headerStyleMap = headerStyleMap;
        this.dataStyleMap = dataStyleMap;
        this.fieldNames = fieldNames;
    }
}
