package org.devoir.noteservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String status;
    private LocalDateTime orderDate;
}
