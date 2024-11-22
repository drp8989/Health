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



    @Lob
    @Column(name = "usage_instructions")
    private String usageInstructions;

    @Lob
    @Column(name = "warnings")
    private String warnings;

    @Lob
    @Column(name = "storage_instructions")
    private String storageInstructions;




    @Column(name = "weight")
    private Double weight;



//    @OneToOne
//    @JoinColumn(name="id", nullable=false)
//    private Inventory inventory;

}
