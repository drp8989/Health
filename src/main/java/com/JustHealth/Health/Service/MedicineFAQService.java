package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.MedicineFAQRequestDTO;
import com.JustHealth.Health.DTO.MedicineFAQResponseDTO;
import com.JustHealth.Health.Entity.MedicineFAQ;

public interface MedicineFAQService {

    MedicineFAQ createMedicineFAQ(MedicineFAQRequestDTO medicineFAQRequestDTO) throws Exception;


    MedicineFAQResponseDTO updateMedicineFAQ(MedicineFAQRequestDTO medicineFAQRequestDTO) throws Exception;

    MedicineFAQ findMedicineFAQ(Long id) throws Exception;

}
