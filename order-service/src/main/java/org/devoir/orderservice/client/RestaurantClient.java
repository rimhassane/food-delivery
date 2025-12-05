package org.devoir.orderservice.client;

import org.devoir.orderservice.dto.MenuItemDTO;
import org.devoir.orderservice.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", path = "/api")
public interface RestaurantClient {
    @GetMapping("/restaurants/{id}")
    RestaurantDTO getRestaurant(@PathVariable Long id);

    @GetMapping("/menu-items/{id}")
    MenuItemDTO getMenuItem(@PathVariable Long id);
}
