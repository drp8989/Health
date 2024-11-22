package com.JustHealth.Health.DTO;


import lombok.Data;

@Data
public class ProductResponseDTO {

    private String productName;
    private String productManufacturer;
    private String productType; // This could be "OTC" or "MEDICINE"
    private Long productCategoryId; // You may use IDs to represent relations in DTO
    private Long productSubCategoryId;

}
