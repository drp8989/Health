package com.JustHealth.Health.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="distributor")
@Builder
public class Distributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "distributor_store_name")
    private String distributorStoreName;


    @Column(name = "distributor_name")
    private String distributorName;

    @Column(name = "distributor_GSTIN")
    private String distributorGSTIN;

    @Column(name = "distributor_email")
    private String distributorEmail;

    @Column(name = "distributor_phone")
    private Integer distributorMobileNo;

    @Column(name = "distributor_address")
    private String distributorAddress;

    @Column(name = "distributor_account_balance")
    private Integer distributorAccountBalance;

    @OneToMany(mappedBy = "purchaseDistributor" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference
    private List<Purchase> purchase;



    @JsonManagedReference
    public List<Purchase> getPurchase() {
        return purchase;
    }

}
