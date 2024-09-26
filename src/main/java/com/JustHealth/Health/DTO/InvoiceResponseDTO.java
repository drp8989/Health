package com.JustHealth.Health.DTO;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceResponseDTO {

    private LocalDate billDate;
    private String customerName;
    private String billingFor;


    private String invoicePaymentType;
    private String invoiceOrderType;

    private List<SalesProduct> salesProducts;

    private Integer totalItems;
    private Float netTotal;
}
