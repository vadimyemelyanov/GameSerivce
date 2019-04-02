package com.vadim.gameservice.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class GameService {
    private final BetRepository betRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public GameService(BetRepository betRepository, GameRepository gameRepository, PlayerRepository playerRepository) {
        this.betRepository = betRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Balance placeBet(BetRequest betRequest) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(betRequest.getPlayerId());
        playerEntity.setAmount(betRequest.getBetAmount());
        log.info("Player {}", playerEntity);
        Balance balance = withdrawMoney(playerEntity);
        Optional<GameEntity> gameEntity = gameRepository.findById(betRequest.getGameId());
        if (gameEntity.isPresent()) {
            betRepository.depositMoneyByPlayerId(betRequest.getPlayerId(), betRequest.getGameId(), betRequest.getBetAmount());
            return balance;
        } else {
            log.error("Game not found on request {}", betRequest.toString());
            throw new GameNotFoundException();
        }

    }

    private Balance withdrawMoney(PlayerEntity player) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(player.getId());
        if (playerEntity.isPresent()) {
            long amountAfterWithdraw = playerEntity.get().getAmount() - player.getAmount();
            if (amountAfterWithdraw > 0) {
                playerRepository.depositMoneyByPlayerId(player.getId(), playerEntity.get().getAmount() - player.getAmount());
                return new Balance(amountAfterWithdraw);
            } else {
                log.error("Not enough money for player {}", player.toString());
                throw new NotEnoughMoneyOnBalanceException();
            }
        } else {
            log.error("Player {} not found {}", player.toString());
            throw new PlayerNotFoundException();
        }
    }

    public List<BetEntity> getPlayerBets(Long id) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(id);
        if (playerEntity.isPresent()) {
            return (List<BetEntity>) playerEntity.get().getBetsById();
        } else {
            log.error("Player  with id {} not found {}", id);
            throw new PlayerNotFoundException();
        }
    }
}
