package com.JustHealth.Health.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.lang.model.element.Name;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_table")
public class Invoice {

    //Dr user to be added as a field

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bill_date")
    private Date billDate;


    @Column(name = "total_items")
    private Integer totalItems;

    @Column(name = "net_total")
    private Integer netTotal;


    @Enumerated(EnumType.STRING)
    private paymentType InvoicePaymentType;

    @Enumerated(EnumType.STRING)
    private orderType InvoiceOrderType;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InvoiceInventory> invoiceInventories;

////    Invoice can have many products from inventory
//    @OneToMany(mappedBy = "invoice")
//    private List<Inventory> products;

//    @ManyToOne
//    private Inventory invoiceInventory;


    @JsonManagedReference
    public List<InvoiceInventory> getInvoiceInventories(){
        return invoiceInventories;
    }



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
