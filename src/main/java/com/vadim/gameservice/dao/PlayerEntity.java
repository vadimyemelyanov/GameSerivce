package com.vadim.gameservice.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "player", schema = "public", catalog = "cmbxnhir")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity {
    private long id;
    private long amount;
    private Collection<BetEntity> betsById;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "amount", nullable = false)
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerEntity that = (PlayerEntity) o;
        return id == that.id &&
                amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @OneToMany(mappedBy = "playerByPlayerId", cascade = CascadeType.ALL)
    public Collection<BetEntity> getBetsById() {
        return betsById;
    }

    public void setBetsById(Collection<BetEntity> betsById) {
        this.betsById = betsById;
    }
}
