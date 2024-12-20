package com.JustHealth.Health.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory_ledger")
@Data
public class InventoryLedger {

    @Id
    @Column(name = "inventory_ledger_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "transaction")
    private String ledgerTransaction;

    @Column(name="inventory_in")
    private Integer inventoryIn;

    @Column(name="inventory_out")
    private Integer inventoryOut;

    //Closing is calculated
    @Column(name = "closing")
    private Integer closing;

    @ManyToOne
    @JoinColumn(name = "inventory_id") // Foreign key column in inventory_ledger table
    private Inventory inventory;

//    @Column(name = "user_detail")
//    private String userDetail;

}
