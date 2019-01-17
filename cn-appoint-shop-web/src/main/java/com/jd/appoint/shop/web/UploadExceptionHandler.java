package com.jd.appoint.shop.web;

import com.jd.travel.base.soa.SoaResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

//@ControllerAdvice
public class UploadExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public SoaResponse handleException(MaxUploadSizeExceededException e) {

        return new SoaResponse(false, 500, "您上传的文件大于" + e.getMaxUploadSize());

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public SoaResponse handleException(Exception e) {

        return new SoaResponse(false, 500, "未知异常");

    }
}
