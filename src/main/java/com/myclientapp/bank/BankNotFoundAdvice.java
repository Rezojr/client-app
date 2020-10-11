package com.myclientapp.bank;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BankNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(BankNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bankNotFoundAdvice(BankNotFoundException ex) {
        return ex.getMessage();
    }

}
