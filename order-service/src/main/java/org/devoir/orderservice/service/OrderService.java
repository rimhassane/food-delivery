package org.devoir.orderservice.service;

import org.devoir.orderservice.model.Order;
import org.devoir.orderservice.model.OrderItem;
import org.devoir.orderservice.model.OrderStatus;
import org.devoir.orderservice.repository.OrderRepository;
import org.devoir.orderservice.client.RestaurantClient;
import org.devoir.orderservice.dto.MenuItemDTO;
import org.devoir.orderservice.dto.RestaurantDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantClient restaurantClient;

    // AJOUTEZ CETTE MÉTHODE
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Vos méthodes existantes restent inchangées...
    @Transactional
    public Order createOrder(Order order) {
        // Vérifier disponibilité du restaurant
        RestaurantDTO restaurant = restaurantClient.getRestaurant(order.getRestaurantId());
        if (!restaurant.isAvailable()) {
            throw new RuntimeException("Restaurant non disponible");
        }

        // Calculer prix total
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            MenuItemDTO menuItem = restaurantClient.getMenuItem(item.getMenuItemId());
            if (!menuItem.isAvailable()) {
                throw new RuntimeException("Article non disponible: " + menuItem.getName());
            }
            item.setMenuItemName(menuItem.getName());
            item.setPrice(menuItem.getPrice());
            item.setOrder(order);
            total = total.add(menuItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        order.setTotalPrice(total);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderTime(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrder(id);
        order.setStatus(status);
        if (status == OrderStatus.DELIVERED) {
            order.setDeliveryTime(LocalDateTime.now());
        }
        return orderRepository.save(order);
    }
}