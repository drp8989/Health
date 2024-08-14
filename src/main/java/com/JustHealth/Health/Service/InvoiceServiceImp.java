package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.InvoiceDTO;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Invoice;
import com.JustHealth.Health.Entity.InvoiceInventory;
import com.JustHealth.Health.Entity.PurchaseInventory;
import com.JustHealth.Health.Repository.InventoryRepository;
import com.JustHealth.Health.Repository.InvoiceInventoryRepository;
import com.JustHealth.Health.Repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
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

    @Override
    @Transactional
    public Invoice createInvoice(@RequestBody InvoiceDTO reqInvoiceDTO) throws Exception {

        Invoice invoice = new Invoice();

        invoice.setBillDate(reqInvoiceDTO.getBillDate());

        List<Integer> invoiceProducts = reqInvoiceDTO.getInvoiceProducts();
        List<Integer> invoiceProductsQTY = reqInvoiceDTO.getInvoiceProductsQTY();
        Integer totalItems=0;
        Integer totalAmount=0;
        Integer baseAmount=0;

        List<InvoiceInventory> invoiceInventories = new ArrayList<>();
        List<Inventory> inventories = new ArrayList<>();

        for (int i = 0; i < invoiceProducts.size(); i++) {
            Integer id = invoiceProducts.get(i);
            Integer quantity = invoiceProductsQTY.get(i);

            Inventory inventory = inventoryRepository.findById(Long.valueOf(id)).orElse(null);
            inventories.add(inventory);

            if(inventory!=null){
                Integer batchMRP=inventory.getInventoryBatch().getLast().getBatchMRP();
                Integer batchQtyStock=inventory.getInventoryBatch().getLast().getQuantityInStock();
                inventory.getInventoryBatch().getLast().setQuantityInStock(batchQtyStock-quantity);
                Integer GST=inventory.getGST();
                totalItems=totalItems+quantity;
                baseAmount=batchMRP*quantity;
                totalAmount=totalAmount+(baseAmount+(batchMRP*GST/100));

                InvoiceInventory invoiceInventory= new InvoiceInventory();
                invoiceInventory.setInventory(inventory);
                invoiceInventory.setInvoice(invoice);
                invoiceInventory.setQuantity(quantity);
                invoiceInventories.add(invoiceInventory);
                invoiceInventoryRepository.save(invoiceInventory);
            }

        }
        invoice.setInvoiceInventories(invoiceInventories);
        invoice.setTotalItems(totalItems);
        invoice.setNetTotal(totalAmount);
        invoice.setInvoiceOrderType(Invoice.orderType.valueOf(reqInvoiceDTO.getInvoiceOrderType()));
        invoice.setInvoicePaymentType(Invoice.paymentType.valueOf(reqInvoiceDTO.getInvoicePaymentType()));
        return invoiceRepository.save(invoice);
    }
//    @Override
//    public List<Inventory> getInventoryByInvoiceId(Long invoiceId) {
//
//        return inventoryRepository.findByInvoiceId(invoiceId);
//
//    }
}
