package com.vadim.gameservice.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@JsonRootName("error")
@JsonIgnoreProperties({"cause", "stackTrace"})
public class NotEnoughMoneyOnBalanceException extends RuntimeException {
    public int errorCode = 551;
    public String errorMessage = "Not enough money";

}
