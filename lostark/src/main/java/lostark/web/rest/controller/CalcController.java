package lostark.web.rest.controller;

import lostark.calc.model.Calc;
import lostark.calc.model.EffRequest;
import lostark.calc.model.EffResponse;
import lostark.domain.Equipment;
import lostark.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalcController {
    @Autowired
    EquipmentService equipmentService;

    @GetMapping("/equipment/{id}/honeCalc}")
    @ResponseBody
    public ResponseEntity<EffResponse> effCalc(@PathVariable(value = "id") Long id, @RequestBody EffRequest effRequest) {
        Equipment equipment = equipmentService.findOne(id).get();
        if (equipment==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Double amountDiff = Calc.compareCost(equipment,
            effRequest.getBasePercent(),
            effRequest.getAdditionPercentPerFail(),
            effRequest.getFailLimit(),
            effRequest.getMaxPercentAfterMats(),
            effRequest.getFusionMat1Amount(),
            effRequest.getFusionMat2Amount(),
            effRequest.getFusionMat3Amount());
        return new ResponseEntity<>(new EffResponse(amountDiff), HttpStatus.OK);
    }
}
