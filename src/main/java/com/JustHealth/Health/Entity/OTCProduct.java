package com.JustHealth.Health.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("OTC")
public class OTCProduct extends Product{


    @Column(name = "otc_product_name")
    private String OTCProductName;

    @Column(name = "otc_product_manufacturer")
    private String OTCProductManufacturer;

//    @OneToOne
//    @JoinColumn(name="id", nullable=false)
//    private Inventory inventory;

}
