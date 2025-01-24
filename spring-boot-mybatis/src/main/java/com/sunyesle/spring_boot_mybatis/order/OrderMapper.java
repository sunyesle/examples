package com.sunyesle.spring_boot_mybatis.order;

import com.sunyesle.spring_boot_mybatis.order.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void insert(OrderVO order);

    List<OrderVO> selectAll();
}
