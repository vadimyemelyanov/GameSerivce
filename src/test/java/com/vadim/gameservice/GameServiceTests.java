package com.vadim.gameservice;


import com.vadim.gameservice.dao.BetEntity;
import com.vadim.gameservice.dao.GameEntity;
import com.vadim.gameservice.dao.PlayerEntity;
import com.vadim.gameservice.dto.Balance;
import com.vadim.gameservice.dto.BetRequest;
import com.vadim.gameservice.exceptions.GameNotFoundException;
import com.vadim.gameservice.exceptions.NotEnoughMoneyOnBalanceException;
import com.vadim.gameservice.exceptions.PlayerNotFoundException;
import com.vadim.gameservice.repository.BetRepository;
import com.vadim.gameservice.repository.GameRepository;
import com.vadim.gameservice.repository.PlayerRepository;
import com.vadim.gameservice.service.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTests {
    @MockBean
    GameRepository gameRepository;
    @MockBean
    PlayerRepository playerRepository;
    @MockBean
    BetRepository betRepository;
    @Autowired
    GameService gameService;
    PlayerEntity playerEntity;
    BetRequest betRequest;
    GameEntity gameEntity;
    BetEntity betEntity;
    ArrayList<BetEntity> betEntityArratList = new ArrayList<>();

    @Before
    public void init() {
        playerEntity = new PlayerEntity();
        playerEntity.setId(1);
        playerEntity.setAmount(1000);
        betRequest = new BetRequest();
        betRequest.setPlayerId(1);
        betRequest.setGameId(1);
        betRequest.setBetAmount(100);
        gameEntity = new GameEntity();
        gameEntity.setId(1);
        gameEntity.setName("testName");
        betEntity = new BetEntity();
        betEntity.setId(1);
        betEntity.setBetAmount(100);
        betEntity.setPlayerByPlayerId(playerEntity);
        betEntity.setGameByGameId(gameEntity);
        betEntityArratList.add(betEntity);
        playerEntity.setBetsById(betEntityArratList);
        when(playerRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(playerEntity));
        when(gameRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(gameEntity));
    }

    @Test
    public void testPlaceBet() {
        Balance balance = gameService.placeBet(betRequest);
        Assert.assertEquals(900, balance.getAmount());
    }

    @Test(expected = NotEnoughMoneyOnBalanceException.class)
    public void testPlaceBetNotEnoughMoney() {
        betRequest.setBetAmount(2000);
        gameService.placeBet(betRequest);
    }

    @Test(expected = GameNotFoundException.class)
    public void testPlaceBetGameNotFound() {
        when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        gameService.placeBet(betRequest);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void testPlacePlayerNotFound() {
        when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        gameService.placeBet(betRequest);
    }

    @Test
    public void testGetPlayerBets() {
        ArrayList<BetEntity> betEntities =
                (ArrayList<BetEntity>) gameService.getPlayerBets(1L);
        Assert.assertEquals(betEntity.getBetAmount(), betEntities.get(0).getBetAmount());
        Assert.assertEquals(betEntity.getPlayerByPlayerId(), betEntities.get(0).getPlayerByPlayerId());
        Assert.assertEquals(betEntity.getGameByGameId(), betEntities.get(0).getGameByGameId());
    }

    @Test(expected = PlayerNotFoundException.class)
    public void testGetPlayerBetsPlayerNotFoundException() {
        when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ArrayList<BetEntity> betEntities =
                (ArrayList<BetEntity>) gameService.getPlayerBets(1L);

    }


}
