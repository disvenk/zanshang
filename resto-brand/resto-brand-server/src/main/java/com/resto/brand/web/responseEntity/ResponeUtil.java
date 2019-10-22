package com.resto.brand.web.responseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by disvenk.dai on 2018-05-24 13:58
 */
public class ResponeUtil {
    public static ResponseEntity returnDate(int responseStatus, String message, Object data,String expMsg){
        return new ResponseEntity(new RestResponseEntity(100,"成功",data,null), HttpStatus.OK);
    }
}
