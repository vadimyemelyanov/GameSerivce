package com.vadim.gameservice;

import com.vadim.gameservice.controller.RestResponseEntityExceptionHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerAdviceTests {
    @Autowired
    RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;

    @Test
    public void testHandleUnrecognizedException(){
        ResponseEntity<Object> objectResponseEntity = restResponseEntityExceptionHandler.handleUnrecognizedException(new RuntimeException());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,objectResponseEntity.getStatusCode());
        Assert.assertNull(objectResponseEntity.getBody());
    }
}
