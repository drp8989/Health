package com.JustHealth.Health.DTO;

import com.JustHealth.Health.Entity.Inventory;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class InvoiceDTO {

    //Description To be derived from entry
//    private Integer qty;



    private LocalDate billDate;
    private String customerName;
    private String billingFor;


    private String invoicePaymentType;
    private String invoiceOrderType;

    private List<SalesProduct> salesProducts;

    private Integer totalItems;
    private Integer netTotal;

    public enum paymentType{
        CASH,
        CREDIT,
        UPI,
    };
    public enum orderType{
        SELF_PICKUP,
        DELIVERY

    }

}
