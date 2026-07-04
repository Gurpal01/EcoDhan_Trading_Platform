package com.Gurpal.Ecodhan.Repository;

import com.Gurpal.Ecodhan.Entity.Orders;
import com.Gurpal.Ecodhan.Enum.OrderStatus;
import com.Gurpal.Ecodhan.Enum.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> findByTypeAndPriceGreaterThanEqualAndStatusInOrderByPriceDescCreatedAtAsc(OrderType type, Double price, List<OrderStatus> statuses);
    List<Orders> findByTypeAndPriceLessThanEqualAndStatusInOrderByPriceAscCreatedAtAsc(OrderType type, Double price, List<OrderStatus> statuses);
}
