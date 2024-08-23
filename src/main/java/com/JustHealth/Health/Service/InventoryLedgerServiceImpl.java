package com.JustHealth.Health.Service;


import com.JustHealth.Health.Entity.InventoryLedger;
import com.JustHealth.Health.Repository.InventoryLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLedgerServiceImpl implements InventoryLedgerService {

    @Autowired
    InventoryLedgerRepository inventoryLedgerRepository;

    @Override
    public Page<InventoryLedger> getLedger(int page,int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<InventoryLedger> inventoryLedger=inventoryLedgerRepository.findAll(pageable);
        return inventoryLedger;
    }

    @Override
    public Page<InventoryLedger> getLedgerByInventoryId(int page, int size, Long inventoryId) {
        Pageable pageable= PageRequest.of(page, size);
        Page<InventoryLedger> inventoryLedger=inventoryLedgerRepository.findAll(pageable);
        return inventoryLedger;
    }
}
