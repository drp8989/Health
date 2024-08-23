package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.Distributor;
import com.JustHealth.Health.Entity.Purchase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class PurchaseResponeDTO {

    private Integer billNo;
    private LocalDate billDate;
    private LocalDate dueDate;
    private List<PurchaseProduct> purchaseProducts;
    private Float totalAmount;
    private Integer totalItems;

    @JsonIgnore
    private Distributor purchaseDistributor;

    private Purchase.paymentType purchasePaymentType;
}
