package org.devoir.restaurantservice.dto;


import lombok.Data;

@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String cuisineType;
    private Double rating;
    private Boolean isOpen;
    private Boolean isActive;
    private Double deliveryFee;
    private Double minimumOrderAmount;
}
