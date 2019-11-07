package springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.model.FaceValue;
import springboot.repository.AccountRepository;
import springboot.repository.FaceValueRepository;
import springboot.service.AtmService;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AtmServiceImpl implements AtmService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FaceValueRepository faceValueRepository;

    @Override
    public void putBillsToAtm(String inputFaceValue, Long quantity) {
        Long currentQuantity = faceValueRepository
                .findByName(inputFaceValue)
                .getQuantity();
        faceValueRepository.findByName(inputFaceValue).setQuantity(currentQuantity + quantity);
    }

    @Override
    public void pickBillsFromAtm(String inputFaceValue, Long quantity) {
        Long currentQuantity = faceValueRepository
                .findByName(inputFaceValue)
                .getQuantity();
        faceValueRepository.findByName(inputFaceValue).setQuantity(currentQuantity - quantity);
    }

    @Override
    public HashMap<String, Long> getFaceValuesQuantities() {
        List<FaceValue> faceValues = faceValueRepository.findAll();
        HashMap<String, Long> mapQuantities = new HashMap<>();
        for (FaceValue faceValue : faceValues) {
            mapQuantities.put(faceValue.getName(), faceValue.getQuantity() * Integer.parseInt(faceValue.getName()));
        }
        return mapQuantities;
    }
}
