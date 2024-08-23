package com.JustHealth.Health.DTO;

import com.JustHealth.Health.Entity.Purchase;
import lombok.Data;

import java.util.List;

@Data
public class DistributorResponseDTO {

    private String distributorStoreName;
    private String distributorName;
    private String distributorGSTIN;
    private String distributorEmail;
    private Integer distributorMobileNo;
    private String distributorAddress;
    private Float distributorAccountBalance;
    private List<Purchase> distributorPurchase;
}
