package com.nivektion.bookingmicroservice.client;

import com.nivektion.bookingmicroservice.config.ClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock-microservice", configuration = ClientConfig.class)
public interface StockClient {
  @GetMapping(value = "/api/stock/{code}")
  boolean stockAvailable(@PathVariable("code") String code);
}
