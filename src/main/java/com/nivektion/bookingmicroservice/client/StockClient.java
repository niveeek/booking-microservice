package com.nivektion.bookingmicroservice.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient("stock-microservice")
public interface StockClient {
  @RequestMapping(value = "/api/stock/{code}", method = RequestMethod.GET)
  boolean stockAvailable(@PathVariable("code") String code);
}
