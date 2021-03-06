package com.vadim.gameservice.repository;



import com.vadim.gameservice.dao.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

     PlayerEntity findFirstById(long playerId);

     @Modifying
     @Query("update PlayerEntity set amount=?2 where id = ?1")
     void depositMoneyByPlayerId(long id, long amount);


}
