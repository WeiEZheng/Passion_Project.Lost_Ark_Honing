package lostark.calc.controller;

import lostark.calc.model.Calc;
import lostark.calc.model.EffResponse;
import lostark.domain.Equipment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalcController {

    @GetMapping
    public ResponseEntity<EffResponse> effCalc(Equipment equipment, Double basePercent, Double additionPercentPerFail,
                                               Integer failLimit, Double maxPercentAfterMats,
                                               Integer fusionMat1Amount, Integer fusionMat2Amount, Integer fusionMat3Amount) {
        Double amountDiff = Calc.compareCost(equipment,
            basePercent,
            additionPercentPerFail,
            failLimit,
            maxPercentAfterMats,
            fusionMat1Amount,
            fusionMat2Amount,
            fusionMat3Amount);
        return new ResponseEntity<>(new EffResponse(amountDiff), HttpStatus.OK);
    }
}
