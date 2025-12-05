package org.devoir.orderservice.controller;

import org.devoir.orderservice.client.RestaurantClient;
import org.devoir.orderservice.dto.RestaurantDTO;
import org.devoir.orderservice.model.Order;
import org.devoir.orderservice.model.OrderStatus;
import org.devoir.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // AJOUTEZ CETTE MÉTHODE - Liste tous les orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Vos méthodes existantes restent inchangées...
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
    @Autowired
    private RestaurantClient restaurantClient;

    @GetMapping("/test-restaurant/{id}")
    public ResponseEntity<RestaurantDTO> testRestaurantClient(@PathVariable Long id) {
        RestaurantDTO restaurant = restaurantClient.getRestaurant(id);
        return ResponseEntity.ok(restaurant);
    }
}