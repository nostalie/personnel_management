package cn.edu.jlu.personnel.management.web.controller;

import cn.edu.jlu.personnel.management.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理 Created by nostalie on 17-4-19.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult handleException(HttpServletRequest request, Exception ex) {
        String uri = request.getRequestURI();
        LOGGER.error("请求{}出错", uri, ex);
        return ResponseResult.error("请求失败");
    }
}
