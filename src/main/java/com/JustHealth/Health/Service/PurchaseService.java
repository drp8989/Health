package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.PurchaseDTO;
import com.JustHealth.Health.DTO.PurchaseResponeDTO;
import com.JustHealth.Health.Entity.Purchase;
import com.JustHealth.Health.Entity.PurchaseInventory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {

//    Features
//      An authorized personnel can make a purchase entry
//      Purchase Entry is responsible for setting up inventory data of a pharmacy.
//      Purchase entry details.
//      Distributor Name,Bill No,OnlineOrder No,Bill Date,Due Date .
//      Purchase searches from available product data .
//      Then the purchase is responsible for setting up the product data in inventory.
//      Inventory Data includes
//      Batch Data,Expiry Data,PTR,QTY,FREE,SCH. AMT,DISC%,BASE PRICE,GST%(It is taken from product),AMOUNT

//Invoice Breakdown showed in a different click
//    Shows PTR Total
//    Total Discount
//    GST Breakdown
//    Bill Amount
//    Adjusted Credit Note
//    Extra Charges
//    Adjustment Amount
//    Round Off
//    Net Amount
//    MRP Total

//    Purchase Bill Can be saved in draft which can be deleted
//    Purchase Bill payment methods
//    CASH,CREDIT,UPI,CHEQUE,RTGS/NEFT

    //Purchase can be searched by bill no,Distributor Name,Payment Methods
    //Date wise purchase can be retrieved Such as today,yesterday,last 7 days,last 30 days,current fiscal year,last fiscal year,custom range


    //Commented Methods
    //    public Purchase createPurchase(@RequestBody PurchaseDTO purchaseDTO) throws Exception;



    public PurchaseResponeDTO createPurchase(@RequestBody PurchaseDTO purchaseDTO) throws Exception;

    public Page<Purchase> findAllPurchasePaginated(int page,int size) throws Exception;

    public Purchase purchaseById(Long id) throws Exception;

    public void deletePurchaseById(Long id) throws Exception;

    public List<PurchaseInventory> getPurchaseInventoryByPurchaseId(Long id) throws Exception;

    public Purchase updatePurchaseById(@RequestBody PurchaseDTO purchaseDTO, Long id) throws Exception;



    //Methods not needed
    public List<Purchase> findAllPurchase() throws Exception;

}
