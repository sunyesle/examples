package com.sunyesle.spring_boot_excel.util.excel;

import org.apache.poi.ss.usermodel.*;

public class ExcelCellStyle {
    private final Workbook workbook;

    private CellStyle headerCellStyle;
    private CellStyle dataCellStyle;
    private CellStyle dateDataCellStyle;

    public ExcelCellStyle(Workbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle getHeaderCellStyle() {
        if (headerCellStyle == null) {
            headerCellStyle = createHeaderCellStyle();
        }
        return headerCellStyle;
    }

    private CellStyle createHeaderCellStyle() {
        CellStyle cellStyle = createCellStyle(IndexedColors.GREY_25_PERCENT, HorizontalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        cellStyle.setFont(headerFont);

        return cellStyle;
    }

    public CellStyle getDataCellStyle() {
        if (dataCellStyle == null) {
            dataCellStyle = createDataCellStyle();
        }
        return dataCellStyle;
    }

    private CellStyle createDataCellStyle() {
        return createCellStyle(IndexedColors.WHITE, HorizontalAlignment.GENERAL);
    }

    public CellStyle getDateDataCellStyle() {
        if (dateDataCellStyle == null) {
            dateDataCellStyle = createDateDataCellStyle();
        }
        return dateDataCellStyle;
    }

    private CellStyle createDateDataCellStyle() {
        CellStyle cellStyle = createCellStyle(IndexedColors.WHITE, HorizontalAlignment.GENERAL);
        cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd h:mm"));
        return cellStyle;
    }

    private CellStyle createCellStyle(IndexedColors color, HorizontalAlignment align) {
        CellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setFillForegroundColor(color.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(align);

        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }
}
