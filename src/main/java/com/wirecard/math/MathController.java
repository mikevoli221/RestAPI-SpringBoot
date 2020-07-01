package com.wirecard.math;

import com.wirecard.exception.MathOperationException;
import com.wirecard.util.Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum (@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (Utils.isNumber(numberOne) && Utils.isNumber(numberTwo)){
            return Double.parseDouble(numberOne) + Double.parseDouble(numberTwo);
        }else {
            throw new MathOperationException("Please set a numeric value");
        }
    }

    @GetMapping("/subtract/{numberOne}/{numberTwo}")
    public Double subtract (@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (Utils.isNumber(numberOne) && Utils.isNumber(numberTwo)){
            return Double.parseDouble(numberOne) - Double.parseDouble(numberTwo);
        }else {
            throw new MathOperationException("Please set a numeric value");
        }
    }

    @GetMapping("/multiply/{numberOne}/{numberTwo}")
    public Double multiply (@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (Utils.isNumber(numberOne) && Utils.isNumber(numberTwo)){
            return Double.parseDouble(numberOne) * Double.parseDouble(numberTwo);
        }else {
            throw new MathOperationException("Please set a numeric value");
        }
    }

    @GetMapping("/divide/{numberOne}/{numberTwo}")
    public Double divide (@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (Utils.isNumber(numberOne) && Utils.isNumber(numberTwo)){
            return Double.parseDouble(numberOne) / Double.parseDouble(numberTwo);
        }else {
            throw new MathOperationException("Please set a numeric value");
        }
    }

    @GetMapping("/average/{numberOne}/{numberTwo}")
    public Double average (@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (Utils.isNumber(numberOne) && Utils.isNumber(numberTwo)){
            return (Double.parseDouble(numberOne) + Double.parseDouble(numberTwo))/2;
        }else {
            throw new MathOperationException("Please set a numeric value");
        }
    }

    @GetMapping("/squareRoot/{number}")
    public Double squareRoot (@PathVariable("number") String number) throws Exception {
        if (Utils.isNumber(number)){
            return Math.sqrt(Double.parseDouble(number));
        }else {
            throw new MathOperationException("Please set a numeric value");
        }
    }

}

