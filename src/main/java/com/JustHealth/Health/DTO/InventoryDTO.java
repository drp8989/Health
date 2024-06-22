package com.JustHealth.Health.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryDTO {

    private Integer reorderLevel;

    private Integer reorderQuantity;

    private String location;

    private Integer minQTY;
    private Integer maxQTY;
    private Integer GST;

    private Integer productId;

}
