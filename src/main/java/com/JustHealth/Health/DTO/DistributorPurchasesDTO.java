package com.JustHealth.Health.DTO;


import com.JustHealth.Health.Entity.Purchase;
import lombok.Data;

import java.util.List;

@Data
public class DistributorPurchasesDTO {

    private List<Purchase> distributorPurchases;

}
