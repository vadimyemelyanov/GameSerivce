package com.vadim.gameservice;


import com.vadim.gameservice.controller.GameController;
import com.vadim.gameservice.dao.BetEntity;
import com.vadim.gameservice.dao.PlayerEntity;
import com.vadim.gameservice.dto.Balance;
import com.vadim.gameservice.dto.BasicResponse;
import com.vadim.gameservice.dto.BetRequest;
import com.vadim.gameservice.exceptions.GameNotFoundException;
import com.vadim.gameservice.exceptions.NotEnoughMoneyOnBalanceException;
import com.vadim.gameservice.exceptions.PlayerNotFoundException;
import com.vadim.gameservice.service.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerTests {
    @MockBean
    GameService gameService;
    @Autowired
    private GameController gameController;
    private Balance balance;
    private BetRequest betRequest;
    private ArrayList<BetEntity> betEntities;


    @Before
    public void beforeTests() {
        balance = new Balance(100);
        betRequest = new BetRequest(1, 1, 100);
        betEntities = new ArrayList<>();
        betEntities.add(new BetEntity());
    }

    @Test
    public void testGetBetsSuccess() {
        when(gameService.getPlayerBets(anyLong())).thenReturn(betEntities);
        ResponseEntity<BasicResponse> playerBets = gameController.getPlayerBets(1L);
        Assert.assertEquals(playerBets.getStatusCode(), HttpStatus.OK);
        Assert.assertNull(playerBets.getBody().getError());
    }

    @Test
    public void testGetBetsPlayerNotFoundException() {
        when(gameService.getPlayerBets(anyLong())).thenThrow(new PlayerNotFoundException());
        ResponseEntity<BasicResponse> playerBets = gameController.getPlayerBets(1L);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, playerBets.getStatusCode());
        Assert.assertEquals(PlayerNotFoundException.class, playerBets.getBody().getError().getClass());
    }


    @Test
    public void testPlaceBetSuccess() {
        when(gameService.placeBet(betRequest)).thenReturn(balance);
        ResponseEntity<BasicResponse> response = gameController.placeBet(betRequest);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNull(response.getBody().getError());
        Balance actualBalance = (Balance) response.getBody().getResponse();
        Assert.assertEquals(balance.getAmount(), actualBalance.getAmount());
    }

    @Test
    public void testPlaceBetPlayerNotFound() {
        when(gameService.placeBet(betRequest)).thenThrow(new PlayerNotFoundException());
        ResponseEntity<BasicResponse> response = gameController.placeBet(betRequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(PlayerNotFoundException.class, response.getBody().getError().getClass());
    }

    @Test
    public void testPlaceBetNotEnoughMoneyException() {
        when(gameService.placeBet(betRequest)).thenThrow(new NotEnoughMoneyOnBalanceException());
        ResponseEntity<BasicResponse> response = gameController.placeBet(betRequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(NotEnoughMoneyOnBalanceException.class, response.getBody().getError().getClass());
    }

    @Test
    public void testPlaceBetGameNotFoundException() {
        when(gameService.placeBet(betRequest)).thenThrow(new GameNotFoundException());
        ResponseEntity<BasicResponse> response = gameController.placeBet(betRequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(GameNotFoundException.class, response.getBody().getError().getClass());
    }

}
