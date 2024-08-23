package com.JustHealth.Health.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_product")
@Builder
public class PurchaseInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

//    @ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

//    @ManyToOne
//    private Product productInventory;

    @Column(name = "product_qty")
    private Integer quantity;

    @Column(name = "discount")
    private Integer discount;



    @JsonBackReference
    public Purchase getPurchase() {
        return purchase;
    }

}
