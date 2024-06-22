package com.JustHealth.Health.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class BatchDTO {

        private String batch;
        private Integer batchPTR;
        private Integer batchMRP;
        private Integer quantityInStock;
        private Date expiryDate;
        private Integer inventoryId; // to reference the related Inventory

}
