package com.sj.loans.exception;

import com.sj.loans.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handelMethodArgumentsNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpHeaders headers, HttpStatusCode httpStatusCode, WebRequest webRequest
    ){
        Map<String, String> validationErrors =new HashMap<>();
        List<ObjectError> validationErrorList = methodArgumentNotValidException.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) ->{
            String fieldName = (error.getDefaultMessage());
            String validationMessage =error.getDefaultMessage();
            validationErrors.put(fieldName, validationMessage);
        });
        return new ResponseEntity<>(validationErrors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
                return new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoanAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleLoanAlreadyExistsException(LoanAlreadyExistsException loanAlreadyExistsException, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                loanAlreadyExistsException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }


}
