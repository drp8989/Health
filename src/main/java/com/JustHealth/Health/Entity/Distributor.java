package com.JustHealth.Health.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="distributor")
public class Distributor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    @OneToOne
    private Purchase purchase;




}
