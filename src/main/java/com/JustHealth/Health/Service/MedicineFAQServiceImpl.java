package com.JustHealth.Health.Service;

import com.JustHealth.Health.DTO.MedicineFAQRequestDTO;
import com.JustHealth.Health.DTO.MedicineFAQResponseDTO;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineFAQ;
import com.JustHealth.Health.Repository.MedicineFAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicineFAQServiceImpl implements MedicineFAQService {

    @Autowired
    MedicineFAQRepository medicineFAQRepository;


    @Override
    public MedicineFAQ findMedicineFAQ(Long id) throws Exception {
        Optional<MedicineFAQ> medicineFAQ=medicineFAQRepository.findById(id);
        if(medicineFAQ.isEmpty()){
            throw new Exception("MEDICINE FAQ Not Found");
        }
        return medicineFAQ.get();

    }


    @Override
    public MedicineFAQ createMedicineFAQ(MedicineFAQRequestDTO medicineFAQRequestDTO) throws Exception {
        try {
            MedicineFAQ medicineFAQ=new MedicineFAQ();
            medicineFAQ.setQuestion(medicineFAQ.getQuestion());
            medicineFAQ.setAnswer(medicineFAQ.getAnswer());
            medicineFAQRepository.save(medicineFAQ);

            return medicineFAQ;
        }catch (Exception e){
            throw  new Exception("Error Occured");
        }

    }

    @Override
    public MedicineFAQResponseDTO updateMedicineFAQ( MedicineFAQRequestDTO medicineFAQRequestDTO) throws Exception {
        Long gotId=medicineFAQRequestDTO.getId();
        MedicineFAQ medicineFAQ=findMedicineFAQ(gotId);
        medicineFAQ.setQuestion(medicineFAQRequestDTO.getQuestion());
        medicineFAQ.setAnswer(medicineFAQRequestDTO.getAnswer());
        medicineFAQRepository.save(medicineFAQ);
        MedicineFAQResponseDTO medicineFAQResponseDTO=objToDto(medicineFAQ);
        return medicineFAQResponseDTO;
    }




    private MedicineFAQResponseDTO objToDto(MedicineFAQ medicineFAQ){
        MedicineFAQResponseDTO medicineFAQResponseDTO=new MedicineFAQResponseDTO();
        medicineFAQResponseDTO.setId(medicineFAQ.getId());
        medicineFAQResponseDTO.setQuestion(medicineFAQ.getQuestion());
        medicineFAQResponseDTO.setAnswer(medicineFAQ.getAnswer());
        return  medicineFAQResponseDTO;
    }
}
