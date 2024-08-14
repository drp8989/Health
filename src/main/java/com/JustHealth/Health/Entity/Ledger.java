package com.JustHealth.Health.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "voucher")
    private String voucher;

    @Column(name = "credit_balance")
    private Integer creditBalance;

    @Column(name = "debit_balance")
    private Integer debitBalance;

    @Column(name = "closing_balance")
    private Integer closingBalance;

}
