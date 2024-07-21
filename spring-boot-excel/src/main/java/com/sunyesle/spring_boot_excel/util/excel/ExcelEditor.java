package com.sunyesle.spring_boot_excel.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class ExcelEditor<T> implements AutoCloseable {
    private final Workbook workbook;
    private final ExcelCellStyle excelCellStyle;
    private final ExcelMetaData excelMetaData;

    public ExcelEditor(Class<T> type) {
        workbook = new SXSSFWorkbook();
        excelCellStyle = new ExcelCellStyle(workbook);
        excelMetaData = ExcelMetaDataFactory.createMetaData(type);
    }

    public void writeData(List<T> data) {
        Sheet sheet = workbook.createSheet();
        int rowIndex = 0;

        renderHeader(sheet, rowIndex++);

        for (T columnData : data) {
            renderBody(sheet, columnData, rowIndex++);
        }
    }

    public void export(OutputStream stream) {
        try {
            workbook.write(stream);
        } catch (IOException e) {
            throw new ExcelInternalException("Failed to write the workbook to the output stream", e);
        }
    }

    private void renderHeader(Sheet sheet, int rowIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = 0;
        for (String dataFieldName : excelMetaData.getFieldNames()) {
            sheet.setColumnWidth(columnIndex, excelMetaData.getWidthMap().get(dataFieldName));

            Cell cell = row.createCell(columnIndex);
            cell.setCellStyle(excelCellStyle.getCellStyle(excelMetaData.getHeaderStyleMap().get(dataFieldName)));
            cell.setCellValue(excelMetaData.getHeaderNamesMap().get(dataFieldName));

            columnIndex++;
        }
    }

    private void renderBody(Sheet sheet, Object data, int rowIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = 0;
        for (String dataFieldName : excelMetaData.getFieldNames()) {
            Cell cell = row.createCell(columnIndex++);
            try {
                Field field = data.getClass().getDeclaredField(dataFieldName);
                field.setAccessible(true);
                Object cellValue = field.get(data);

                cell.setCellStyle(excelCellStyle.getCellStyle(excelMetaData.getDataStyleMap().get(dataFieldName)));

                renderCellValue(cell, cellValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ExcelInternalException("Failed to access field: " + dataFieldName, e);
            }
        }
    }

    private void renderCellValue(Cell cell, Object cellValue) {
        if (cellValue == null) {
            return;
        }

        if (cellValue instanceof Number numberValue) {
            cell.setCellValue(numberValue.doubleValue());
        } else if (cellValue instanceof String stringValue) {
            cell.setCellValue(stringValue);
        } else if (cellValue instanceof Boolean booleanValue) {
            cell.setCellValue(booleanValue);
        } else if (cellValue instanceof Date dateValue) {
            cell.setCellValue(dateValue);
        } else {
            cell.setCellValue(cellValue.toString());
        }
    }

    @Override
    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            throw new ExcelInternalException("Failed to close the workbook", e);
        }
    }
}
