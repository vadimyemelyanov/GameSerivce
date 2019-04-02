package com.vadim.gameservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetRequest {

    @Min(1)
    private long playerId;
    @Min(1)
    private long gameId;
    @Min(1)
    private long betAmount;
}
