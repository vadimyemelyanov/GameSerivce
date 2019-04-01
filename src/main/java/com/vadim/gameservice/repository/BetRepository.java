package com.vadim.gameservice.repository;

import com.vadim.gameservice.dao.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<BetEntity, Long> {

/*
Used Native query to avoid not necessary sql requests on inserting
 */
    @Modifying
    @Query(value = "insert into bet (player_id, game_id, bet_amount) values (?,?,?) ",nativeQuery = true)
    void depositMoneyByPlayerId(long playerId, long gameId, long amount);


}
