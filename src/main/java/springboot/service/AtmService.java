package springboot.service;

import springboot.model.FaceValue;

import java.util.HashMap;
import java.util.Map;

public interface AtmService {

    String FIVE100 = "500";
    String TWO100 = "200";
    String ONE100 = "100";

    Long FIVE100LIMIT = 500*40L;
    Long TWO100LIMIT = 200*40L;
    Long ONE100LIMIT = 100*40L;

    FaceValue putBillToAtm(String inputFaceValue);

    FaceValue pickBillsFromAtm(String inputFaceValue, Long quantity);

    Map<String, Long> getFaceValuesQuantities();
}
