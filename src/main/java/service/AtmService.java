package service;

import java.util.HashMap;

public interface AtmService {

    String FIVE100 = "500";
    String TWO100 = "200";
    String ONE100 = "100";

    Long LIMIT = 40L;

    void putBillsToAtm(String inputFaceValue, Long quantity);

    void pickBillsFromAtm(String inputFaceValue, Long quantity);

    HashMap<String, Long> getFaceValuesQuantities();
}
