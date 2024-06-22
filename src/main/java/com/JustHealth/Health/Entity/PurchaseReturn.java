package com.JustHealth.Health.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_return")
public class PurchaseReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bill_date")
    private Date billDate;

    @Column(name="total_amount")
    private Integer totalAmount;

    @Column(name = "total_items")
    private Integer totalItems;


//    @ManyToOne()
//    private Distributor purchaseDistributor;

    @Column(name = "purchase_payment_type")
    @Enumerated(EnumType.STRING)
    private Purchase.paymentType purchasePaymentType;




    public enum paymentType{
        CASH,
        CREDIT,
        UPI,
        RTGS,
        NEFT,
    };


}
