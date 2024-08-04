package com.nivektion.bookingmicroservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.nivektion.bookingmicroservice.client.StockClient;
import com.nivektion.bookingmicroservice.dto.OrderDTO;
import com.nivektion.bookingmicroservice.entity.OrderEntity;
import com.nivektion.bookingmicroservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
  private OrderRepository orderRepository;
  private StockClient stockClient;

  @Autowired
  public BookingController(OrderRepository orderRepository, StockClient stockClient) {
    this.orderRepository = orderRepository;
    this.stockClient = stockClient;
  }

  @PostMapping("/order")
  @HystrixCommand(fallbackMethod = "fallbackStockService")
  public String saveOrder(@RequestBody OrderDTO orderDTO) {
    boolean inStock = orderDTO.getOrderItems().stream().allMatch(orderItemEntity -> stockClient.stockAvailable(orderItemEntity.getCode()));
    if (inStock) {
      OrderEntity orderEntity = new OrderEntity();
      orderEntity.setOrderNumber(UUID.randomUUID().toString());
      orderEntity.setOrderItems(orderDTO.getOrderItems());
      orderRepository.save(orderEntity);
      return "Order saved successfully";
    }
    return "Order cannot be saved";
  }

  private String fallbackStockService() {
    return "Something went wrong, try after sometime";
  }
}
