package com.JustHealth.Health.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class DistributorDTO {
    private String distributorStoreName;
    private String distributorName;
    private String distributorGSTIN;
    private String distributorEmail;
    private Integer distributorMobileNo;
    private String distributorAddress;
    private String distributorAccountBalance;
}
