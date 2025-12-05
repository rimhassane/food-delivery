package org.devoir.deliveryservice.client;

import org.devoir.deliveryservice.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "order-service", path = "/api/orders")
public interface OrderClient {
    
    @GetMapping("/{orderId}")
    OrderDTO getOrderById(@PathVariable Long orderId);
    
    @PutMapping("/{orderId}/status")
    void updateOrderStatus(@PathVariable Long orderId, @PathVariable String status);
    
    // Ajoutez d'autres méthodes au besoin
}
