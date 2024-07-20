package com.sunyesle.spring_boot_excel.util.excel;

import java.lang.reflect.Field;
import java.util.*;

public class ExcelMetaDataFactory {
    private static final Map<Class<?>, ExcelMetaData> cache = new HashMap<>();

    private ExcelMetaDataFactory() {
    }

    public static ExcelMetaData createMetaData(Class<?> type) {
        if (cache.containsKey(type)) {
            return cache.get(type);
        }

        Map<String, String> headerNamesMap = new LinkedHashMap<>();
        Map<String, Integer> widthMap = new LinkedHashMap<>();
        List<String> fieldNames = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn columnAnnotation = field.getAnnotation(ExcelColumn.class);
                headerNamesMap.put(field.getName(), columnAnnotation.headerName());
                widthMap.put(field.getName(), columnAnnotation.width());

                fieldNames.add(field.getName());
            }
        }

        if (headerNamesMap.isEmpty()) {
            throw new NoExcelColumnAnnotationsException(String.format("Class %s has not @ExcelColumn at all", type));
        }

        ExcelMetaData result = new ExcelMetaData(headerNamesMap, widthMap, fieldNames);
        cache.put(type, result);
        return result;
    }
}