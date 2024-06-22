package com.JustHealth.Health.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PurchaseDTO {

    private Date billDate;
    private Date dueDate;


    private List<Integer> inventoryPurchaseProductsQTY;
    private List<Integer> inventoryPurchaseProducts;
//
//    private Integer totalAmount;
//    private Integer totalItems;
//
    private Long purchaseDistributor;
    private String purchasePaymentType;

}
