package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Product;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Data
public class InventoryResponseDTO {

    //For reference only
    private Long inventoryId;
    private Integer reorderLevel;
    private Integer reorderQuantity;
    private String location;
    private Integer minQTY;
    private Integer maxQTY;
    private Integer GST;
    private Integer currentStock;
//    private Product product;

    private List<BatchResponseDTO> inventoryBatches;


}
