package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.InvoiceDTO;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InvoiceService {

    public Invoice createInvoice(InvoiceDTO invoiceDTO) throws Exception;






}
