package com.JustHealth.Health.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class PurchaseDTO {

    private LocalDate billDate;
    private LocalDate dueDate;
    private Integer billNo;

//    private List<Integer> inventoryPurchaseProductsQTY;
//    private List<Integer> inventoryPurchaseProducts;


    private List<PurchaseProduct> purchaseInventories;

    private Long purchaseDistributorId;
    private String purchasePaymentType;

}
