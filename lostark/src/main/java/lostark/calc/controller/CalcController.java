package lostark.calc.controller;

import lostark.calc.model.EffResponse;
import lostark.domain.Equipment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalcController {

    @GetMapping
    public EffResponse effCalc(Equipment equipment) {
        return new EffResponse();
    }
}
