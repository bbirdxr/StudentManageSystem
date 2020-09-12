package cn.edu.seu.historycontest.config;

import cn.edu.seu.historycontest.exception.ForbiddenException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionConfig {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenException(ForbiddenException forbiddenException) {
        return forbiddenException.getMessage();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationException(AuthenticationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ExcelDataConvertException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleExcelDataConvertException(ExcelDataConvertException e) {
        return "数据格式错误，请重试";
    }
}
