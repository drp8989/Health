package com.JustHealth.Health.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DistributorDTO {

    private String distributorStoreName;
    private String distributorName;
    private String distributorGSTIN;
    private String distributorEmail;
    private Integer distributorMobileNo;
    private String distributorAddress;
}
