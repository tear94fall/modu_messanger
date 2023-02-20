package com.example.modumessenger.common.decoder;

import com.example.modumessenger.common.exception.CustomException;
import com.example.modumessenger.common.exception.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                return new CustomException(ErrorCode.USERID_NOT_FOUND, "");
            case 404:
                if(methodKey.contains("UserId")){
                    return new CustomException(ErrorCode.USERID_NOT_FOUND_ERROR, "");
                }
                break;
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
