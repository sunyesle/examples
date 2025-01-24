package com.sunyesle.spring_boot_mybatis;

import com.sunyesle.spring_boot_mybatis.enums.OrderStatus;
import com.sunyesle.spring_boot_mybatis.infra.CodeEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CodeEnumTest {

    @Test
    void fromCode_validCode_returnsEnum() {
        OrderStatus result1000 = CodeEnum.fromCode(OrderStatus.class, "1000");
        assertThat(result1000).isEqualTo(OrderStatus.PENDING);

        OrderStatus result2000 = CodeEnum.fromCode(OrderStatus.class, "2000");
        assertThat(result2000).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        assertThatThrownBy(() -> CodeEnum.fromCode(OrderStatus.class, "9999"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid code");
    }
}
