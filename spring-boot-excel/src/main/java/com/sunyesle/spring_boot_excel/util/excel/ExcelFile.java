package com.sunyesle.spring_boot_excel.util.excel;

import java.io.OutputStream;
import java.util.List;

public class ExcelFile<T> {
    private final Class<T> type;

    public ExcelFile(Class<T> type) {
        this.type = type;
    }

    public void write(List<T> data, OutputStream stream) {
        try (ExcelEditor<T> excelEditor = new ExcelEditor<>(type)) {
            excelEditor.writeData(data);
            excelEditor.export(stream);
        }
    }
}
