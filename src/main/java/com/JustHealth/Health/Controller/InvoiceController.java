package com.JustHealth.Health.Controller;

import com.JustHealth.Health.DTO.InvoiceDTO;
import com.JustHealth.Health.DTO.InvoiceResponseDTO;
import com.JustHealth.Health.Entity.Invoice;
import com.JustHealth.Health.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/create")
    private ResponseEntity<?> createInvoice(@RequestBody InvoiceDTO req) throws Exception{

        try {
            InvoiceResponseDTO invoiceResponse =invoiceService.createInvoice(req);
            return new ResponseEntity<>(invoiceResponse, HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception("Error "+e.getMessage());
        }


    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Invoice>> getAllInvoice(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "5") int size) throws Exception {
        try {
            Page<Invoice> invoices=invoiceService.getAllInvoices(page, size);
            return new ResponseEntity<>(invoices,HttpStatus.OK);
        }catch (Exception e){
            throw new Exception("Exception"+e.getMessage());
        }

    }
}
