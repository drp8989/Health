package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.BatchDTO;
import com.JustHealth.Health.DTO.InventoryDTO;
import com.JustHealth.Health.DTO.InventoryResponseDTO;
import com.JustHealth.Health.Entity.Batch;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Product;
import com.JustHealth.Health.Entity.Purchase;
import org.springframework.data.domain.Page;

import java.util.List;


public interface InventoryService {




//    Features to implement
//      ->Create Inventory when a purchase is made with data.
//      ->Search Inventory By Name,Brand.

    // Shortest Way to Add Inventory
//      ->Search for product name
//      ->Add Batch Name,Expiry,MRP,PTR,MARGIN IS CALCULATED,QTY
//      Inventory Data
//          Inventory has batches
//          Inventory details shows purchase where where inventory was bought
//          Also the purchase returns
//          Sales of the inventory
//          Sales return
//          Ledger

    //Inventory data stores location default discount,min stock,max stock,gst,lock disc(boolean),sell in loose(boolean),accept online order(boolean),item category


//Search Filter in Inventory Dashboard
//      Items contains all items,only newly added items, only not set min/max,discounted items,only not set hsn code,only not set categories
//      Category filter contains categories such as ayurvedic,cosmetic,drug,generic,nutraceticals,OTC,surgical
//      Dosage type tablet,capsule,cream,syrup,drop,injection,powder
//      Schedule type h1 schedul,h schedule,nrx schedule,tb bdisease
//      Expiry filter includes expired,next month,next 2 months,next 3 months
//      Stock filter shows low stock,high stock,positive stock,negative stock
//      Manufacturer it enables user to search inventory of a certain typed manufacturer
//      Content search products with composition
//      GST filter shows inventory based on gst input 0%,5%,12%,18%,28%
//      Location filter shows inventory products based on location
//      Margin filter shows inventory products in range of margin input
//      MRP
//      PTR
//      LP

    //Inventory Data to be calculated
//    Show current stock byPTR,byLP,byMRP

    //Expired Inventory Data to be calculated
//    Show expired stock byPTR,byLP,byMRP




//    public Inventory getInventoryById(Long id) throws Exception;

    public Inventory addBatch(BatchDTO batchDTO);

    public InventoryResponseDTO createInventory(InventoryDTO req) throws Exception;


    //Used in purchase for creating an inventory explicitly if inventory doesnt exist
    public void createInventoryForPurchase(Long productId)throws Exception;

    public InventoryResponseDTO getInventoryById(Long id) throws Exception;

    public InventoryResponseDTO updateInventory(InventoryDTO inventoryDTO, Long id)throws Exception;

    public void deleteInventory(Long id) throws Exception;

    public Page<InventoryResponseDTO> getAllInventoryPaginated(int page,int size);

    public Page<Batch> getBatchesForInventory(Long id,int page, int size) throws Exception;


    Inventory getInventoryFromProduct(Long productId);

    public List<Purchase> getPurchaseForInventory(Long id) throws Exception;

    public List<Inventory> getExpiredProducts();

    public List<Inventory> getExpiringProducts();

//    Product getProductByInventoryId(Long inventoryId) throws Exception;

    //    public Inventory createInventory(InventoryDTO inventoryDTO) throws Exception;
//    public List<Inventory> getAllInventory();


}
