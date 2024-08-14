package com.JustHealth.Health.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "purchase_table")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "bill_date")
    private Date billDate;

    @Column(name = "due_date")
    private Date dueDate;

    //To Be calculated
    @Column(name="total_amount")
    private Integer totalAmount;

    //To Be calculated
    @Column(name = "total_items")
    private Integer totalItems;

    @OneToMany(mappedBy = "purchase",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<PurchaseInventory> purchaseInventories;

    @ManyToOne()
    private Distributor purchaseDistributor;

    @Column(name = "purchase_payment_type")
    @Enumerated(EnumType.STRING)
    private paymentType purchasePaymentType;




    public enum paymentType{
        CASH,
        CREDIT,
        UPI,
        RTGS,
        NEFT,
    };



    @JsonManagedReference
    public List<PurchaseInventory> getPurchaseInventories() {
        return purchaseInventories;
    }


    @JsonBackReference
    public Distributor getPurchaseDistributor() {
        return purchaseDistributor;
    }






}
