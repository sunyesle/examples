package com.sunyesle.spring_boot_excel.util.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Objects;

@Getter
public class Style {
    private final String backgroundColor;
    private final String dataFormat;
    private final boolean isBold;
    private final HorizontalAlignment horizontalAlignment;

    public Style(String backgroundColor, String dataFormat, boolean isBold, HorizontalAlignment horizontalAlignment) {
        this.backgroundColor = backgroundColor;
        this.dataFormat = dataFormat;
        this.isBold = isBold;
        this.horizontalAlignment = horizontalAlignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Style style = (Style) o;
        return Objects.equals(backgroundColor, style.backgroundColor) && Objects.equals(dataFormat, style.dataFormat) && horizontalAlignment == style.horizontalAlignment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(backgroundColor, dataFormat, horizontalAlignment);
    }
}
