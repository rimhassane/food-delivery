package org.devoir.noteservice.client;

import org.devoir.noteservice.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", path = "/api/orders")
public interface OrderClient {
    
    @GetMapping("/{orderId}")
    OrderDTO getOrderById(@PathVariable Long orderId);
    
    // Ajoutez d'autres méthodes au besoin
}
