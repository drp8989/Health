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

    @Column(name = "gst")
    private Integer GST=0;

    //Sum of all batch quantities.
    @Column(name = "current_stock")
    private Integer currentStock;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private InventoryLedger inventoryLedger;




}
