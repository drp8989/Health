package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.InvoiceDTO;
import com.JustHealth.Health.DTO.InvoiceResponseDTO;
import com.JustHealth.Health.DTO.SalesProduct;
import com.JustHealth.Health.Entity.*;
import com.JustHealth.Health.Repository.BatchRepository;
import com.JustHealth.Health.Repository.InventoryRepository;
import com.JustHealth.Health.Repository.InvoiceInventoryRepository;
import com.JustHealth.Health.Repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImp implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InvoiceInventoryRepository invoiceInventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private BatchRepository batchRepository;


    public Integer calculateCurrentQTYinStock(List<Batch> batches){
        //Used to update qty in inventory
        Integer totalQTY = 0;
        for (Batch batch:batches){
            totalQTY+=batch.getQuantityInStock();
        }
        return totalQTY;
    }


    @Override
    @Transactional
    public InvoiceResponseDTO createInvoice(@RequestBody InvoiceDTO reqInvoiceDTO) throws Exception {

        LocalDate billDate=reqInvoiceDTO.getBillDate();
        String customerName=reqInvoiceDTO.getCustomerName();
        String billingFor=reqInvoiceDTO.getBillingFor();
        String invoicePaymentType=reqInvoiceDTO.getInvoicePaymentType();

        //Pick-Up or Delivery
        String invoiceOrderType= reqInvoiceDTO.getInvoiceOrderType();

        List<SalesProduct> salesProductList=reqInvoiceDTO.getSalesProducts();

        Float totalAmount= 0.00F;
        Integer totalItems=0;

        for(SalesProduct salesProduct:salesProductList){
            Long productId= salesProduct.getProductId();
            String batch= salesProduct.getBatch();
            Integer productSalesQTY= salesProduct.getProductSalesQTY();
            Integer discount=salesProduct.getDiscount();

            Inventory inventory=inventoryService.getInventoryFromProduct(productId);

            if(inventory==null){
                throw new Exception("Inventory is not in the stock");
            }else{

                List<Batch> inventoryBatches=inventory.getInventoryBatch();

                // Find the matching batch
                Batch matchingBatch = inventoryBatches.stream()
                        .filter(batch1 -> batch.equals(batch1.getBatch()))
                        .findFirst()
                        .orElseThrow(() -> new Exception("Batch is not matching"));

                if(matchingBatch==null){
                    throw new Exception("Batch doesnt exist in inventory");
                }
                matchingBatch.setQuantityInStock(matchingBatch.getQuantityInStock()-productSalesQTY);
                batchRepository.save(matchingBatch);

                inventory.setCurrentStock(calculateCurrentQTYinStock(inventoryBatches));
                inventoryRepository.save(inventory);

                List<InvoiceInventory> invoiceInventories=new ArrayList<>();
                InvoiceInventory invoiceInventory=new InvoiceInventory();
                invoiceInventory.setInventory(inventory);
                invoiceInventory.setQuantity(productSalesQTY);

                invoiceInventories.add(invoiceInventory);

                totalItems+=productSalesQTY;











            }
            Invoice invoice=new Invoice();
            invoice.setBillDate(billDate);
            invoice.setTotalItems(totalItems);


        }


        Invoice invoice=new Invoice();

        return null;

    }
//    @Override
//    public List<Inventory> getInventoryByInvoiceId(Long invoiceId) {
//
//        return inventoryRepository.findByInvoiceId(invoiceId);
//
//    }
}
