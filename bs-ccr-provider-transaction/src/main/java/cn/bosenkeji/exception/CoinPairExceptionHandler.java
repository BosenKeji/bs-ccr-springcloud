package cn.bosenkeji.exception;

import cn.bosenkeji.handler.CustomErrorResponse;
import cn.bosenkeji.handler.CustomGlobalExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CoinPairExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> notFoundExceptionHandler(Exception ex, WebRequest request){
        CustomGlobalExceptionHandler customGlobalExceptionHandler=new CustomGlobalExceptionHandler();
        return customGlobalExceptionHandler.customHandleNotFound(ex,request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomGlobalExceptionHandler customGlobalExceptionHandler=new CustomGlobalExceptionHandler();
        return customGlobalExceptionHandler.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
