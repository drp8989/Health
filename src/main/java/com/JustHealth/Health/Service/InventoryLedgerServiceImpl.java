package com.JustHealth.Health.Service;


import com.JustHealth.Health.Entity.InventoryLedger;
import com.JustHealth.Health.Repository.InventoryLedgerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    @Transactional
    public Page<InventoryLedger> getLedgerByInventoryId(int page, int size, Long inventoryId) {
        // Create pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated list of InventoryLedger by inventoryId
        Page<InventoryLedger> inventoryLedgerPage = inventoryLedgerRepository.findByInventoryId(inventoryId, pageable);

        // If the result is empty, throw an exception
        if (inventoryLedgerPage.isEmpty()) {
            throw new RuntimeException("No records found for inventory ID: " + inventoryId);
        }

        return inventoryLedgerPage;
    }
}
