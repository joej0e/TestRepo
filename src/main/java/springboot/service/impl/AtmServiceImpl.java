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
import java.util.Map;

@Service
@Transactional
public class AtmServiceImpl implements AtmService {

    @Autowired
    private FaceValueRepository faceValueRepository;

    @Override
    public FaceValue putBillToAtm(String inputFaceValue) {
        if(checkFaceValue(inputFaceValue)) {
            FaceValue faceValueFromBd = faceValueRepository
                    .findByName(inputFaceValue);
            Long currentQuantity = faceValueFromBd.getQuantity();
            faceValueFromBd.setQuantity(++currentQuantity);
            return faceValueRepository.save(faceValueFromBd);
        } else {
            throw new IllegalArgumentException("Wrong face value!!!");
        }
    }

    @Override
    public FaceValue pickBillsFromAtm(String faceValue, Long quantity) {
            FaceValue faceValueFromBd = faceValueRepository
                    .findByName(faceValue);
            Long currentQuantity = faceValueFromBd.getQuantity();
            faceValueFromBd.setQuantity(currentQuantity - quantity);
            return faceValueRepository.save(faceValueFromBd);

    }

    @Override
    public Map<String, Long> getFaceValuesQuantities() {
        List<FaceValue> faceValues = faceValueRepository.findAll();
        Map<String, Long> mapQuantities = new HashMap<>();
        for (FaceValue faceValue : faceValues) {
            mapQuantities.put(faceValue.getName(), faceValue.getQuantity());
        }
        return mapQuantities;
    }

    private static boolean checkFaceValue(String faceValue) {
        return faceValue.equals(AtmService.FIVE100) ||
                faceValue.equals(AtmService.TWO100) ||
                faceValue.equals(AtmService.ONE100);
    }
}
