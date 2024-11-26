package com.JustHealth.Health.DTO;

import com.JustHealth.Health.Entity.Product;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InventoryDTO {

    private Integer reorderLevel;

    private Integer reorderQuantity;

    private String location;

    private Integer minQTY;
    private Integer maxQTY;
    private Float GST;

    private Float defaultDiscount;

    private Long productId;

    private Boolean lockDiscount;

    private Boolean acceptOnlineOrder;

    private List<BatchDTO> batch;

}
