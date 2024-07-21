package com.sunyesle.spring_boot_excel.util.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.util.HashMap;
import java.util.Map;

public class ExcelCellStyle {
    private final Map<Style, CellStyle> cacheMap = new HashMap<>();
    private final Workbook workbook;

    public ExcelCellStyle(Workbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle getCellStyle(Style style) {
        if (cacheMap.containsKey(style)) {
            return cacheMap.get(style);
        }

        CellStyle cellStyle = workbook.createCellStyle();

        // 배경색
        String hexColor = style.getBackgroundColor().replace("#", "0x");
        cellStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.decode(hexColor), new DefaultIndexedColorMap()));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 수평정렬
        cellStyle.setAlignment(style.getHorizontalAlignment());

        // 수직정렬
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 테두리
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        // 폰트
        Font font = workbook.createFont();
        font.setBold(style.isBold());
        cellStyle.setFont(font);

        // 표시형식
        DataFormat dataformat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataformat.getFormat(style.getDataFormat()));

        cacheMap.put(style, cellStyle);

        return cellStyle;
    }
}
