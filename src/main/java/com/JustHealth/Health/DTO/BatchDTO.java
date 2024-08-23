package com.JustHealth.Health.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BatchDTO {

        private String batch;
        private Float batchPTR;
        private Float batchMRP;
        private Integer quantityInStock;
        private LocalDate expiryDate;
        private Integer inventoryId; // to reference the related Inventory

}
