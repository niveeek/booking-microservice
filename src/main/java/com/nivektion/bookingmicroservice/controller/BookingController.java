package com.nivektion.bookingmicroservice.controller;

import com.nivektion.bookingmicroservice.client.StockClient;
import com.nivektion.bookingmicroservice.dto.OrderDTO;
import com.nivektion.bookingmicroservice.entity.OrderEntity;
import com.nivektion.bookingmicroservice.entity.OrderItemEntity;
import com.nivektion.bookingmicroservice.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {
  private final OrderRepository orderRepository;
  private final StockClient stockClient;

  @GetMapping()
//  @HystrixCommand(fallbackMethod = "fallbackStockService")
  public String saveOrder() {
    OrderItemEntity orderItemEntity = new OrderItemEntity();
    orderItemEntity.setCode("97");
    orderItemEntity.setPrice(BigDecimal.valueOf(100));
    orderItemEntity.setOrderQuantity(1);
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setOrderItems(List.of(orderItemEntity));
    boolean inStock = orderDTO.getOrderItems().stream()
        .allMatch(orderItemEntity2 -> stockClient.stockAvailable(orderItemEntity2.getCode()));
    if (inStock) {
      OrderEntity orderEntity = new OrderEntity();
      orderEntity.setOrderNumber(UUID.randomUUID().toString());
      orderEntity.setOrderItems(orderDTO.getOrderItems());
      orderRepository.save(orderEntity);
      return "Order saved successfully";
    }
    return "Order cannot be saved";
  }

  private String fallbackStockService(OrderDTO orderDTO) {
    return "Something went wrong, try after sometime";
  }
}
