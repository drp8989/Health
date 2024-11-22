package com.JustHealth.Health.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BatchResponseDTO {
    private String batch;
    private Float batchPTR;
    private Float batchMRP;
    private Float batchLP;
    private Integer quantityInStock;
    private LocalDate expiryDate;
}
