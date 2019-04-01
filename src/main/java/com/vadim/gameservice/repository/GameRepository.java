package com.vadim.gameservice.repository;


import com.vadim.gameservice.dao.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {


     @Modifying
     @Query("update PlayerEntity set amount=?2 where id = ?1")
     void depositMoneyByPlayerId(long id, long amount);


}
