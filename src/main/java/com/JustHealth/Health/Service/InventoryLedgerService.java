package com.JustHealth.Health.Service;


import com.JustHealth.Health.Entity.InventoryLedger;
import org.springframework.data.domain.Page;

public interface InventoryLedgerService {


    public Page<InventoryLedger> getLedger(int page,int size);


    public Page<InventoryLedger> getLedgerByInventoryId(int page,int size,Long inventoryId);



}
