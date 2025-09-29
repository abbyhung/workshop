package com.ah.workshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice // 告訴 Spring 這是一個全域的例外處理器
public class GlobalExceptionHandler {

    /**
     * 這個方法會捕捉所有 RuntimeException 類型的例外
     * @param ex 捕捉到的例外物件
     * @return 一個包含錯誤訊息的 ResponseEntity
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        // 在伺服器後台印出完整的錯誤堆疊，方便除錯
        ex.printStackTrace(); 
        
        // 建立一個要回傳給前端的 JSON 物件
        Map<String, String> errorResponse = Map.of("message", ex.getMessage());
        
        // 回傳 HTTP 400 Bad Request 狀態碼，並附上錯誤訊息
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 捕捉所有其他未被特定處理的例外
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        Map<String, String> errorResponse = Map.of("message", "伺服器發生未預期的錯誤");
        // 回傳 HTTP 500 Internal Server Error 狀態碼
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}