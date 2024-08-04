package com.nivektion.bookingmicroservice.dto;

import com.nivektion.bookingmicroservice.entity.OrderItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
  private List<OrderItemEntity> orderItems = new ArrayList<>();
}
