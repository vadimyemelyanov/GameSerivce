package com.vadim.gameservice.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("error")
@JsonIgnoreProperties({"cause", "stackTrace"})
public class GameNotFoundException extends RuntimeException {
    public int errorCode = 550;
    public String errorMessage = "Game not found";
}
