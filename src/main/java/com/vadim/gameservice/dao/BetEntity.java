package com.vadim.gameservice.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bet", schema = "public", catalog = "cmbxnhir")
@Data
public class BetEntity {
    private long id;
    private long betAmount;
    private PlayerEntity playerByPlayerId;
    private GameEntity gameByGameId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "bet_amount", nullable = false)
    public long getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(long betAmount) {
        this.betAmount = betAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetEntity betEntity = (BetEntity) o;
        return id == betEntity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    public PlayerEntity getPlayerByPlayerId() {
        return playerByPlayerId;
    }

    public void setPlayerByPlayerId(PlayerEntity playerByPlayerId) {
        this.playerByPlayerId = playerByPlayerId;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnore
    public GameEntity getGameByGameId() {
        return gameByGameId;
    }

    public void setGameByGameId(GameEntity gameByGameId) {
        this.gameByGameId = gameByGameId;
    }
}
