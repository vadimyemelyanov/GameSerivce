package com.vadim.gameservice.controller;

import com.vadim.gameservice.dao.BetEntity;
import com.vadim.gameservice.dto.Balance;
import com.vadim.gameservice.dto.BetRequest;

import com.vadim.gameservice.dto.BasicResponse;
import com.vadim.gameservice.exceptions.GameNotFoundException;
import com.vadim.gameservice.exceptions.NotEnoughMoneyOnBalanceException;

import com.vadim.gameservice.exceptions.PlayerNotFoundException;
import com.vadim.gameservice.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/game")
@Slf4j
@AllArgsConstructor
public class GameController {

    private final GameService gameService;


    @PostMapping("/bet")
    public ResponseEntity<BasicResponse> placeBet(@Valid @RequestBody BetRequest bet) {
        Balance balance = null;
        log.info("Place bet Request {}", bet);
        try {
            balance = gameService.placeBet(bet);
            return ResponseEntity.ok().body(new BasicResponse(balance, null));
        }catch (NotEnoughMoneyOnBalanceException e){
            log.info("Not enough money ", bet);
            return ResponseEntity.badRequest().body(new BasicResponse(balance, e));
        }catch (PlayerNotFoundException e) {
            log.info("Player {} not Found", bet);
            return ResponseEntity.badRequest().body(new BasicResponse(balance, e));
        }catch (GameNotFoundException e) {
            log.info("Game {} not Found", bet);
            return ResponseEntity.badRequest().body(new BasicResponse(balance, e));
        }
    }

    @GetMapping("/bets/{id}")
    public ResponseEntity<BasicResponse> getPlayerBets(@PathVariable Long id) {
        try {
            List<BetEntity> bets = gameService.getPlayerBets(id);
            return ResponseEntity.ok().body(new BasicResponse(bets, null));
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.badRequest().body(new BasicResponse(null, e));
        }
    }




}
