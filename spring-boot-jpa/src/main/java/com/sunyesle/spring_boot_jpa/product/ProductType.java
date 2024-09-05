package com.sunyesle.spring_boot_jpa.product;

public enum ProductType implements CodedEnum<Integer> {
    FOOD(100),
    DRINK(200),
    CLOTHES(300),
    ELECTRONICS(400),
    OTHER(500);

    private final int code;

    ProductType(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends AbstractCodedEnumConverter<ProductType, Integer> {
        public Converter() {
            super(ProductType.class);
        }
    }
}
