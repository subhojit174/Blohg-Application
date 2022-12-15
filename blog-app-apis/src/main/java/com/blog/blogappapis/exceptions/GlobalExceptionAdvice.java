package com.blog.blogappapis.exceptions;

import com.blog.blogappapis.payloads.ApiResponse;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundEExceptionHandler(ResourceNotFoundException ex){
        ApiResponse apiResponse=new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> badCredentialEExceptionHandler(BadCredentialsException ex){
        ApiResponse apiResponse=new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> accessDeniedExceptionHandler(AccessDeniedException ex){
        ApiResponse apiResponse=new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String,String> map=new HashMap<>();
        //ApiResponse apiResponse=new ApiResponse(ex.getMessage(),false);
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String err=((FieldError) error).getField();
            String message=error.getDefaultMessage();
            map.put(err,message);
        });
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
    }
}
