package com.JustHealth.Health.DTO;


import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class MedicineFAQRequestDTO {

    @Nullable
    private Long id;

    private String question;

    private String answer;

}


