package com.JustHealth.Health.DTO;

import com.JustHealth.Health.Entity.Inventory;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class InvoiceDTO {

    private Date bill_date;

    //Description To be derived from entry
    private Integer qty;
    private Integer totalItems;
    private Integer netTotal;
    //

    private String invoicePaymentType;
    private String invoiceOrderType;

    private List<Inventory> products;



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
