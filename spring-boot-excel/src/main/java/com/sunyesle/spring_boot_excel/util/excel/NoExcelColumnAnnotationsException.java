package com.sunyesle.spring_boot_excel.util.excel;

public class NoExcelColumnAnnotationsException extends RuntimeException {
    public NoExcelColumnAnnotationsException(String message) {
        super(message);
    }
}
