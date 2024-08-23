package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.InvoiceDTO;
import com.JustHealth.Health.DTO.InvoiceResponseDTO;
import com.JustHealth.Health.Entity.Inventory;
import com.JustHealth.Health.Entity.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InvoiceService {

//  Features
//  Sales Dashboard
//        ->Show All Sales
//        ->Searh sale by bill no
//        ->Search Sales which are made today,yesterday,last 7 days,last 30 days,last 90 days,current fiscal year,previous fiscal year,custom range
//        ->Search sales by customer name/mobile no
//        ->Search sales by payment method
//        Filtered Search
//          ->Seach sales by OnlineOrder Type(ALL,Invoice,Draft,Quotation)
//          ->OnlineOrder Mode Online All(Pickup,Delivery)/Offline(Pickup,Delivery)
//          ->Show sales by staff
//  Also user can     
//  Sales Entry Data
//            ->Bill date
//            ->Customer Name/Mobile No
//            ->Billing for(family or self)
//            ->Doctor Name(Search Results or Add)
//             ->Entry By staff or any logged in user
//             ->Payment method(Default PickUp)OR delivery
//    ->Enter product name or content(The system will search for




    public InvoiceResponseDTO createInvoice(InvoiceDTO invoiceDTO) throws Exception;






}
