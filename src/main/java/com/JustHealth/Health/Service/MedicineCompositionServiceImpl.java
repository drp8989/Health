package com.JustHealth.Health.Service;


import com.JustHealth.Health.DTO.MedicineCompositionRequestDTO;
import com.JustHealth.Health.DTO.MedicineCompositionResponseDTO;
import com.JustHealth.Health.DTO.MedicineFAQRequestDTO;
import com.JustHealth.Health.DTO.MedicineFAQResponseDTO;
import com.JustHealth.Health.Entity.MedicineComposition;
import com.JustHealth.Health.Entity.MedicineFAQ;
import com.JustHealth.Health.Exception.MedicineCompositionNotFoundException;
import com.JustHealth.Health.Repository.MedicineCompositionRepository;
import com.JustHealth.Health.Repository.MedicineFAQRepository;
import com.github.slugify.Slugify;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicineCompositionServiceImpl implements MedicineCompositionService {


    @Autowired
    MedicineCompositionRepository medicineCompositionRepository;

    @Autowired
    MedicineFAQRepository medicineFAQRepository;

    @Autowired
    MedicineFAQService medicineFAQService;




    //Used Internally for getting medicineCompositionId.
    @Override
    public MedicineComposition findMedicineCompositionById(Long id) throws Exception {
        Optional<MedicineComposition> medicineComposition=medicineCompositionRepository.findById(id);
        if(medicineComposition.isEmpty()){
            throw new MedicineCompositionNotFoundException("MEDICINE Compostion Not Found");
        }
        return medicineComposition.get();

    }




    @Override
    @Transactional
    public MedicineCompositionResponseDTO createMedicineComposition(MedicineCompositionRequestDTO req) throws Exception {


        // Create a new MedicineComposition entity
        MedicineComposition medicineComposition = new MedicineComposition();
        medicineComposition.setMedicineCompositionName(req.getMedicineCompositionName());
        medicineComposition.setCompositionTherapeuticClass(req.getCompositionTherapeuticClass());
        medicineComposition.setCompositionUse(req.getCompositionUse());
        medicineComposition.setCompositionSideEffects(req.getCompositionSideEffects());
        medicineComposition.setCompositionWorking(req.getCompositionWorking());
        medicineComposition.setCompositionExpertAdvice(req.getCompositionExpertAdvice());

        List<MedicineFAQ> savedFAQs = new ArrayList<>();
        // Check if FAQ list is not null and not empty
        if (req.getMedicineFAQ() != null && !req.getMedicineFAQ().isEmpty()) {
            // Iterate through each MedicineFAQRequestDTO and save them to the repository
            for (MedicineFAQRequestDTO medicineFAQ : req.getMedicineFAQ()) {
                MedicineFAQ FAQ = new MedicineFAQ();
                FAQ.setQuestion(medicineFAQ.getQuestion());
                FAQ.setAnswer(medicineFAQ.getAnswer());
                savedFAQs.add(FAQ);  // Add saved FAQ to the list
            }

        }
        Slugify slugify=new Slugify();
        String slug=slugify.slugify(req.getMedicineCompositionName());
        medicineComposition.setSlug(slug);
        medicineComposition.setCompositionFAQ(savedFAQs);
        // Save the medicine composition entity
        medicineCompositionRepository.save(medicineComposition);
        // Map and save FAQs, associating them with the saved MedicineComposition
        savedFAQs.forEach(medicineFAQ -> {
            // Set the reference to the saved MedicineComposition in each FAQ
            medicineFAQ.setMedicineComposition(medicineComposition);
            // Save the FAQ entity
            medicineFAQRepository.save(medicineFAQ);
        });





        // Create a response DTO
        MedicineCompositionResponseDTO medicineCompositionResponseDTO = objectToResponseDTO(medicineComposition);

        return medicineCompositionResponseDTO;



    }


    @Override
    public MedicineCompositionResponseDTO getMedicineComposition(Long id) throws Exception {
        MedicineComposition medicineComposition=findMedicineCompositionById(id);
        MedicineCompositionResponseDTO responseDTO=objectToResponseDTO(medicineComposition);
        return responseDTO;
    }




    @Override
    public Page<MedicineCompositionResponseDTO> getAllCompositions(int page, int size, String sortBy) {

        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));

        Page<MedicineComposition> medicineCompositions=medicineCompositionRepository.findAll(pageable);

        Page<MedicineCompositionResponseDTO> response = medicineCompositions.map(medicineComposition -> {
            MedicineCompositionResponseDTO dto = new MedicineCompositionResponseDTO();
            dto.setMedicineCompositionId(medicineComposition.getId());
            dto.setMedicineCompositionName(medicineComposition.getMedicineCompositionName());
            dto.setCompositionTherapeuticClass(medicineComposition.getCompositionTherapeuticClass());
            dto.setCompositionUse(medicineComposition.getCompositionUse());
            dto.setCompositionSideEffects(medicineComposition.getCompositionSideEffects());
            dto.setCompositionWorking(medicineComposition.getCompositionWorking());
            dto.setCompositionExpertAdvice(medicineComposition.getCompositionExpertAdvice());
            // Map MedicineFAQ to MedicineFAQResponseDTO
            if (medicineComposition.getCompositionFAQ() != null) {
                List<MedicineFAQResponseDTO> faqResponseDTOList = medicineComposition.getCompositionFAQ().stream().map(faq -> {
                    MedicineFAQResponseDTO faqDTO = new MedicineFAQResponseDTO();
                    faqDTO.setId(faq.getId());
                    faqDTO.setQuestion(faq.getQuestion());
                    faqDTO.setAnswer(faq.getAnswer());
                    return faqDTO;
                }).collect(Collectors.toList());

                dto.setMedicineFAQ(faqResponseDTOList);
            }
            dto.setSlug(medicineComposition.getSlug());

            return dto;
        });

        return response;
    }

    @Override
    @Transactional
    public MedicineCompositionResponseDTO updateMedicineComposition(Long id, MedicineCompositionRequestDTO medicineCompositionRequestDTO) throws Exception {

        MedicineComposition medicineComposition=findMedicineCompositionById(id);
        medicineComposition.setMedicineCompositionName(medicineCompositionRequestDTO.getMedicineCompositionName());
        medicineComposition.setCompositionTherapeuticClass(medicineCompositionRequestDTO.getCompositionTherapeuticClass());
        medicineComposition.setCompositionUse(medicineCompositionRequestDTO.getCompositionUse());
        medicineComposition.setCompositionSideEffects(medicineCompositionRequestDTO.getCompositionSideEffects());
        medicineComposition.setCompositionWorking(medicineCompositionRequestDTO.getCompositionWorking());


        //Four cases a user can do

        //1.He can update all the faq.
        //2.He can update a single faq.
        //3.He can delete one and update rest.
        //4.He can update all and insert new.


        List<MedicineFAQ> savedFAQs=medicineComposition.getCompositionFAQ();
        List<MedicineFAQRequestDTO> gotFAQS=medicineCompositionRequestDTO.getMedicineFAQ();
//        List<MedicineFAQ> newlyCreatedFAQ = new ArrayList<>();

        for (MedicineFAQ faq : new ArrayList<>(savedFAQs)) {
            faq.setMedicineComposition(null); // Clear the back-reference
            medicineComposition.removeFAQ(faq); // Remove the FAQ from the composition
        }

        if (gotFAQS == null || gotFAQS.isEmpty()) {
            medicineComposition.getCompositionFAQ().clear();
            medicineFAQRepository.deleteAll(savedFAQs);
            medicineComposition.setCompositionFAQ(savedFAQs);
        }
        else if (gotFAQS.size() > savedFAQs.size()) {


            gotFAQS.forEach(medicineFAQRequestDTO -> {
                Long faqId = medicineFAQRequestDTO.getId();

                if (faqId == null) {
                    // Handle creation of new FAQ since id is null.
                    try {
//                        MedicineFAQ newFAQ = medicineFAQService.createMedicineFAQ(medicineFAQRequestDTO);
                            MedicineFAQ newFAQ = new MedicineFAQ();
                            newFAQ.setQuestion(medicineFAQRequestDTO.getQuestion());
                            newFAQ.setAnswer(medicineFAQRequestDTO.getAnswer());
                            newFAQ.setMedicineComposition(medicineComposition);
                            medicineFAQRepository.save(newFAQ);
                            //For maintaning the realtion
                            medicineComposition.addFAQ(newFAQ);
//                            newlyCreatedFAQ.add(newFAQ);
                    } catch (Exception e) {
                        throw new RuntimeException("Error in creating new FAQ", e);
                    }
                } else {
                        try {
                                MedicineFAQ medicineFAQ = medicineFAQService.findMedicineFAQ(faqId);
                                medicineFAQ.setQuestion(medicineFAQRequestDTO.getQuestion());
                                medicineFAQ.setAnswer(medicineFAQRequestDTO.getAnswer());
                                medicineFAQ.setMedicineComposition(medicineComposition);
                                medicineFAQRepository.save(medicineFAQ);
//                                newlyCreatedFAQ.add(medicineFAQ);
                        } catch (Exception e) {
                            throw new RuntimeException("Error in updating FAQ", e);
                        }
                    }
            });
        }
        else if (gotFAQS.size() == savedFAQs.size()) {
            // Update all FAQs since sizes match.
            gotFAQS.forEach(medicineFAQRequestDTO -> {
                Optional<MedicineFAQ> medicineFAQ = medicineFAQRepository.findById(medicineFAQRequestDTO.getId());
                if (medicineFAQ.isEmpty()) {
                    throw new RuntimeException("MEDICINE FAQ ID incorrect.");
                } else {
                    try {
                        MedicineFAQ updateMedicineFAQ = medicineFAQ.get();
                        updateMedicineFAQ.setQuestion(medicineFAQRequestDTO.getQuestion());
                        updateMedicineFAQ.setAnswer(medicineFAQRequestDTO.getAnswer());
                        medicineFAQRepository.save(updateMedicineFAQ);
                    } catch (Exception e) {
                        throw new RuntimeException("Error in inserting MedicineFAQ", e);
                    }
                }
            });
        }
        else if (gotFAQS.size() < savedFAQs.size()) {
            // Update and delete if necessary.
            gotFAQS.forEach(medicineFAQRequestDTO -> {
                Optional<MedicineFAQ> medicineFAQ = medicineFAQRepository.findById(medicineFAQRequestDTO.getId());
                if (medicineFAQ.isPresent()) {
                    try {
                        MedicineFAQ updateMedicineFAQ = medicineFAQ.get();
                        updateMedicineFAQ.setQuestion(medicineFAQRequestDTO.getQuestion());
                        updateMedicineFAQ.setAnswer(medicineFAQRequestDTO.getAnswer());
                        medicineFAQRepository.save(updateMedicineFAQ);
                    } catch (Exception e) {
                        throw new RuntimeException("Error in inserting MedicineFAQ", e);
                    }
                } else {
                    medicineFAQRepository.deleteById(medicineFAQRequestDTO.getId());
                }
            });
        }



        // Set the updated list of FAQs to the composition.
        medicineComposition.setCompositionFAQ(savedFAQs);
        medicineCompositionRepository.save(medicineComposition);

        MedicineCompositionResponseDTO medicineCompositionResponseDTO=objectToResponseDTO(medicineComposition);

        return medicineCompositionResponseDTO;
    }

    @Override
    public void deleteMedicineComposition(Long id) throws Exception {

        Optional<MedicineComposition> medicineComposition= medicineCompositionRepository.findById(id);
        if(medicineComposition.isEmpty()){
            throw new MedicineCompositionNotFoundException("MEDICINE Compostion Not Found");
        }
        medicineCompositionRepository.delete(medicineComposition.get());
    }

    @Override
    @Transactional
    public List<MedicineFAQ> getMedicineFAQsByCompositionId(Long id) {
        return medicineFAQRepository.findFAQsByCompositionId(id);
    }

    @Override
    public Long totalCount() throws Exception {
        return medicineCompositionRepository.count();
    }




    public List<MedicineFAQ> getListOfFAQfromDTO(List<MedicineFAQRequestDTO> medicineFAQRequestDTOS) {

        List<MedicineFAQ> toSend = new ArrayList<>();
        for (MedicineFAQRequestDTO medicineFAQRequestDTO : medicineFAQRequestDTOS) {
            MedicineFAQ medicineFAQ = convertToMedicineFAQ(medicineFAQRequestDTO);
            toSend.add(medicineFAQ);
        }
        return toSend;
    }


    public List<MedicineFAQResponseDTO> getFAQlistResponseDTO(List<MedicineFAQ> medicineFAQS){

        List<MedicineFAQResponseDTO> medicineFAQResponseDTOS=new ArrayList<>();
        for (MedicineFAQ medicineFAQ:medicineFAQS){
            MedicineFAQResponseDTO responseDTO =convertToMedicineFAQResponseDTO(medicineFAQ);
            medicineFAQResponseDTOS.add(responseDTO);
        }
        return medicineFAQResponseDTOS;

    }


    private MedicineFAQ convertToMedicineFAQ(MedicineFAQRequestDTO faqDTO) {
        // Assuming MedicineFAQ has a constructor or setters to populate fields
        MedicineFAQ medicineFAQ = new MedicineFAQ();
        medicineFAQ.setQuestion(faqDTO.getQuestion());
        medicineFAQ.setAnswer(faqDTO.getAnswer());
        // Add more fields as necessary

        return medicineFAQ;
    }

    private MedicineFAQResponseDTO convertToMedicineFAQResponseDTO(MedicineFAQ medicineFAQ) {
        // Assuming MedicineFAQ has a constructor or setters to populate fields
        MedicineFAQResponseDTO medicineFAQResponseDTO = new MedicineFAQResponseDTO();
        // Add more fields as necessary
        medicineFAQResponseDTO.setId(medicineFAQ.getId());
        medicineFAQResponseDTO.setQuestion(medicineFAQ.getQuestion());
        medicineFAQResponseDTO.setAnswer(medicineFAQ.getAnswer());

        return medicineFAQResponseDTO;
    }




    //Object to Dto for medicine Composition
    public  MedicineCompositionResponseDTO objectToResponseDTO(MedicineComposition medicineComposition){

        MedicineCompositionResponseDTO medicineCompositionResponseDTO=new MedicineCompositionResponseDTO();

        medicineCompositionResponseDTO.setMedicineCompositionId(medicineComposition.getId());
        medicineCompositionResponseDTO.setMedicineCompositionName(medicineComposition.getMedicineCompositionName());
        medicineCompositionResponseDTO.setCompositionTherapeuticClass(medicineComposition.getCompositionTherapeuticClass());
        medicineCompositionResponseDTO.setCompositionUse(medicineComposition.getCompositionUse());
        medicineCompositionResponseDTO.setCompositionSideEffects(medicineComposition.getCompositionSideEffects());
        medicineCompositionResponseDTO.setCompositionWorking(medicineComposition.getCompositionWorking());
        medicineCompositionResponseDTO.setCompositionExpertAdvice(medicineComposition.getCompositionExpertAdvice());
        if (medicineComposition.getCompositionFAQ() == null || medicineComposition.getCompositionFAQ().isEmpty()) {
            medicineCompositionResponseDTO.setMedicineFAQ(new ArrayList<>());
        } else {
            medicineCompositionResponseDTO.setMedicineFAQ(getFAQlistResponseDTO(medicineComposition.getCompositionFAQ()));
        }
        medicineCompositionResponseDTO.setSlug(medicineComposition.getSlug());
        return medicineCompositionResponseDTO;

    }


    //Dto to Object
    public MedicineComposition DTOtoObject(MedicineCompositionRequestDTO medicineCompositionRequestDTO){
        MedicineComposition medicineComposition = new MedicineComposition();
        medicineComposition.setMedicineCompositionName(medicineCompositionRequestDTO.getMedicineCompositionName());
        medicineComposition.setCompositionTherapeuticClass(medicineCompositionRequestDTO.getCompositionTherapeuticClass());
        medicineComposition.setCompositionUse(medicineCompositionRequestDTO.getCompositionUse());
        medicineComposition.setCompositionSideEffects(medicineCompositionRequestDTO.getCompositionSideEffects());
        medicineComposition.setCompositionWorking(medicineCompositionRequestDTO.getCompositionWorking());
        medicineComposition.setCompositionExpertAdvice(medicineCompositionRequestDTO.getCompositionExpertAdvice());
        return medicineComposition;

    }



}
