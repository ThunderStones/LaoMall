package org.csu.laomall;

import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.common.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<String> handleException(Exception e) {
        e.printStackTrace();
        return CommonResponse.createForError(ResponseCode.ERROR.getCode(), "Internal Server Error");
    }

}
