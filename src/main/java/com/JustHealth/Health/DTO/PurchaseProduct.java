package com.JustHealth.Health.DTO;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseProduct {

    private Long productId;
    //Batch Dto
    private String batch;
    private LocalDate expiry;
    private Float batchMRP;
    private Float batchPTR;


    private Integer productInventoryQTY;
    private Integer productFree;
    private Float schemeAmount;
    private Integer discount;
    //To be Calculated
    private Float base;
    private Float gst;

}
