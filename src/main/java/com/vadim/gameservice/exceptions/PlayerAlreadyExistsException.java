package com.vadim.gameservice.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@JsonRootName("error")
@JsonIgnoreProperties({"cause", "stackTrace"})
public class PlayerAlreadyExistsException extends RuntimeException {
    public int errorCode = 552;
    public String errorMessage = "Player already exists";
}
