package org.devoir.orderservice.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {


        private Long id;
        private String name;
        private String address;
        private String phone;
        private String email;
        private String cuisineType;
        private Double rating;
        public Boolean available;  // Changez de "isOpen" à "available"
        private Boolean isActive;
        private Double deliveryFee;
        private Double minimumOrderAmount;
    public Boolean isAvailable() {
        return available;
    }
    }

