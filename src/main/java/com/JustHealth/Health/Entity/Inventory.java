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
    private Long id;

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

    @Column(name = "default_discount")
    private Integer defaultDiscount;

    @Column(name = "lock_discount")
    private Boolean lockDiscount;

    @Column(name = "accept_online_order")
    private Boolean acceptOnlineOrder;

    @Column(name = "margin")
    private Float margin;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_category")
    private Category itemCategory;

    //Sum of all batch quantities.
    @Column(name = "current_stock")
    private Integer currentStock;

//    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
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

    public enum Category {
        TOPICAL_GEL,
        SYRUP,
        TABLET,
        CAPSULE,
        INJECTION,
        SUSPENSION,
    }


}
