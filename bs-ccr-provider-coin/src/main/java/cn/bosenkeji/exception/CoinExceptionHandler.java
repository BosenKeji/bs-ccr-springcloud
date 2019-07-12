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


/**
 * @Author CAJR
 * @create 2019/7/12 15:15
 */
@RestControllerAdvice
public class CoinExceptionHandler extends ResponseEntityExceptionHandler {

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
