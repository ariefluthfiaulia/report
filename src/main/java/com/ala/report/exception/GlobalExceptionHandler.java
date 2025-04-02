package com.ala.report.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReportException.class)
    public ResponseEntity<String> handleReportException(ReportException ex) {
        log.error("Report error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
} 