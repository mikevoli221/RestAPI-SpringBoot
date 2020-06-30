package com.wirecard.math;

import com.wirecard.exception.MathOperationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum (@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (isNumber(numberOne) && isNumber(numberTwo)){
            return Double.parseDouble(numberOne) + Double.parseDouble(numberTwo);
        }else {
            throw new MathOperationException("Please set a numeric value");
        }
    }

    private boolean isNumber(String str){
        try{
            Double.parseDouble(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}

