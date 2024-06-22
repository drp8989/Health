package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.InvoiceDTO;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Invoice;
import com.JustHealth.Health.Repository.InventoryRepository;
import com.JustHealth.Health.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.List;

public class InvoiceServiceImp implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Invoice createInvoice(@RequestBody InvoiceDTO reqInvoiceDTO) throws Exception {

        Invoice invoice=new Invoice();
        invoice.setBill_date((Date) reqInvoiceDTO.getBill_date());

        invoice.setInvoiceOrderType(Invoice.orderType.valueOf(reqInvoiceDTO.getInvoiceOrderType()));
        invoice.setInvoicePaymentType(Invoice.paymentType.valueOf(reqInvoiceDTO.getInvoicePaymentType()));

//        List<Inventory> products = reqInvoiceDTO.getProducts();
//        for (Inventory product : products) {
//            product.setQuantityInStock(product.getQuantityInStock() - reqInvoiceDTO.getQty());
//        }

        Invoice savedInvoice=invoiceRepository.save(invoice);
        return savedInvoice;
    }

//    @Override
//    public List<Inventory> getInventoryByInvoiceId(Long invoiceId) {
//
//        return inventoryRepository.findByInvoiceId(invoiceId);
//
//    }
}
