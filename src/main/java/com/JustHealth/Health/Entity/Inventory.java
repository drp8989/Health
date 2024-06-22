package com.JustHealth.Health.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_table")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Column(name = "reorder_quantity")
    private Integer reorderQuantity;

    @Column(name = "inventory_location")
    private String location;

    @Column(name = "min_qty")
    private Integer minQTY;

    @Column(name = "max_qty")
    private Integer maxQTY;

    @Column(name = "GST")
    private Integer GST=12;

    @OneToOne
    @JoinColumn(name = "inventory_product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Product product;

    @OneToMany(cascade = CascadeType.ALL )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Batch> inventoryBatch;

//    @ManyToOne()
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @JoinColumn(name = "purchase_inventory_id")
//    private Purchase inventoryPurchase;

    @ManyToOne()
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "inventory_invoice_id")
    private Invoice invoice;


}
