package com.vadim.gameservice.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@JsonRootName("error")
@JsonIgnoreProperties({"cause", "stackTrace"})
public class PlayerNotFoundException extends RuntimeException {
    public int errorCode = 553;
    public String errorMessage = "Player not found";
}
