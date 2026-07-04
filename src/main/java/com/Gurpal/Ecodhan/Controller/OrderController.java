package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.OrderRequestDto;
import com.Gurpal.Ecodhan.Dto.OrderResponseDto;
import com.Gurpal.Ecodhan.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrder(Authentication authentication)
    {
        String userName = authentication.getName();
        List<OrderResponseDto>list = orderService.getOrders(userName);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(Authentication authentication,@RequestBody OrderRequestDto orderRequestDto)
    {
        String userName = authentication.getName();
        String result = orderService.createOrder(orderRequestDto,userName);
        return ResponseEntity.ok(result);
    }

}
