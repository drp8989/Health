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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        List<InvoiceInventory> invoiceInventories=new ArrayList<>();

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

                Float salePriceMRP=matchingBatch.getBatchMRP();



                inventory.setCurrentStock(calculateCurrentQTYinStock(inventoryBatches));
                inventoryRepository.save(inventory);


                InvoiceInventory invoiceInventory=new InvoiceInventory();
                invoiceInventory.setInventory(inventory);
                invoiceInventory.setQuantity(productSalesQTY);

                invoiceInventories.add(invoiceInventory);

                totalItems+=productSalesQTY;

                if(discount==null||discount.equals(0)){
                    totalAmount+=salePriceMRP;
                }else{
                    totalAmount+=salePriceMRP-(salePriceMRP*discount/100);
                }





            }

        }


        Invoice invoice=new Invoice();
        invoice.setBillDate(billDate);
        invoice.setCustomerName(customerName);
        invoice.setTotalItems(totalItems);
        invoice.setNetTotal(totalAmount);


        for (InvoiceInventory invoiceInventory:invoiceInventories ){
            invoiceInventory.setInvoice(invoice);
            invoiceInventoryRepository.save(invoiceInventory);
        }

        invoice.setInvoiceInventories(invoiceInventories);
        invoiceRepository.save(invoice);


        InvoiceResponseDTO invoiceResponseDTO=new InvoiceResponseDTO();
        invoiceResponseDTO.setBillDate(invoice.getBillDate());
        invoiceResponseDTO.setCustomerName(invoice.getCustomerName());
//        invoiceResponseDTO.setBillingFor();
        invoiceResponseDTO.setSalesProducts(salesProductList);
        invoiceResponseDTO.setTotalItems(totalItems);
        invoiceResponseDTO.setNetTotal(totalAmount);


        return invoiceResponseDTO;

    }

    @Override
    @Transactional
    public Page<Invoice> getAllInvoices(int page, int size) throws Exception {
        try {
            Pageable pageable= PageRequest.of(page,size);
            return invoiceRepository.findAll(pageable);
        }catch (Exception e){
            throw new Exception("Exception"+e.getMessage(),e);
        }

    }
//    @Override
//    public List<Inventory> getInventoryByInvoiceId(Long invoiceId) {
//
//        return inventoryRepository.findByInvoiceId(invoiceId);
//
//    }
}
