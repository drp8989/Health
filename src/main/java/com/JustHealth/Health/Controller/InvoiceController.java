package com.JustHealth.Health.Controller;

import com.JustHealth.Health.DTO.InvoiceDTO;
import com.JustHealth.Health.Entity.Invoice;
import com.JustHealth.Health.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/create")
    private Invoice createInvoice(@RequestBody InvoiceDTO req) throws Exception{

        return invoiceService.createInvoice(req);

    }
}
