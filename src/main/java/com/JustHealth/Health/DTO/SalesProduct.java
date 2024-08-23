package com.JustHealth.Health.DTO;


import lombok.Data;

import java.time.LocalDate;

@Data
public class SalesProduct {

    //private LocalDate expiry;

//    private Float batchPTR;
//    private Integer productFree;
//    private Float schemeAmount;

    private Long productId;
    private String batch;
    private Float batchMRP;
    private Integer productSalesQTY;
    private Integer discount;
//    private Integer gst;
    private Float totalAmount;
    //To be Calculated

}
