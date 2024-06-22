package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.Purchase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Mock
    private PurchaseRepository purchaseRepository;


    @Test
    public void PrintPurchases(){
        Optional<Purchase> purchases=purchaseRepository.findById(5L);
        System.out.println("purchase"+purchases);
    }



}