package com.JustHealth.Health.Entity;


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

//    @Column(name="quantity")
//    private Integer qty;

    @Column(name="total_amount")
    private Integer totalAmount;

    @Column(name = "total_items")
    private Integer totalItems;

//
//    @ElementCollection
//    @CollectionTable(name = "my_purchase_product_qty", joinColumns = @JoinColumn(name = "product_quantity_purchase_id"))
//    @Column(name="product_qty")
//    private List<Integer> qty;

    //Relationships
//    @OneToMany()
//    private List<Inventory> purchaseProduct;


    @OneToMany(mappedBy = "purchase",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<PurchaseInventory> purchaseInventories;

    @OneToOne
    @JsonIgnore
    private Distributor purchaseDistributor;

    @Column(name = "purchase_payment_type")
    @Enumerated(EnumType.STRING)
    private paymentType purchasePaymentType;


    public enum paymentType{
        CASH,
        CREDIT,
        UPI,
    };




}
