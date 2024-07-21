package com.sunyesle.spring_boot_excel.util.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

@Getter
public enum StyleType {
    HEADER_STYLE(new Style("#dddddd", "", true, HorizontalAlignment.CENTER)),
    DATA_STYLE(new Style("#ffffff", "", false, HorizontalAlignment.GENERAL)),
    DATA_COMMA_STYLE(new Style("#ffffff", "#,##0", false, HorizontalAlignment.GENERAL)),
    DATA_DATE_STYLE(new Style("#ffffff", "yyyy-mm-dd h:mm", false, HorizontalAlignment.GENERAL)),
    ;

    private final Style style;

    StyleType(Style style) {
        this.style = style;
    }
}
