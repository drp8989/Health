package com.JustHealth.Health.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Integer id;

    @Column(name = "batch")
    private String Batch;

    @Column(name = "batch_ptr")
    private Float batchPTR;

    @Column(name = "batch_mrp")
    private Float batchMRP;

    //LP is the price with GST(ptr+gst(Amount))
    @Column(name = "batch_lp")
    private Float batchLP;

    @Column(name = "batch_quantity_in_stock")
    private Integer quantityInStock;

    @Column(name = "batch_expiry")
    private LocalDate expiryDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "inventory_batch_id")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Inventory batchInventory;
}
