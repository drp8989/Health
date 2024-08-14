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
    private Float inventoryIn;

    @Column(name="inventory_out")
    private Float inventoryOut;

    //Closing is calculated
    @Column(name = "closing")
    private Float closing;

//    @Column(name = "user_detail")
//    private String userDetail;

}
