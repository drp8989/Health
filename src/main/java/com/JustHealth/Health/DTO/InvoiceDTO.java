package com.JustHealth.Health.DTO;

import com.JustHealth.Health.Entity.Inventory;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class InvoiceDTO {

    private Date billDate;

    //Description To be derived from entry
//    private Integer qty;
//    private Integer totalItems;
//    private Integer netTotal;
    //

    private String invoicePaymentType;
    private String invoiceOrderType;

    private List<Integer> invoiceProducts;
    private List<Integer> invoiceProductsQTY;



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
