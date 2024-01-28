package com.enxolist.enxolist.advice;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex){ 
        ProblemDetail errorDetail = null;
        
        if(ex instanceof BadCredentialsException){ 
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("acess_denied_reason", "Authentication Failure");
        }

        if(ex instanceof AccessDeniedException){ 
            errorDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("acess_denied_reason", "Not Authorized!");
        }

        return errorDetail;
    }

}
