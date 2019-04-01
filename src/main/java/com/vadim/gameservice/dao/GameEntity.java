package com.vadim.gameservice.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "game", schema = "public", catalog = "cmbxnhir")
@Data
public class GameEntity {
    private long id;
    private String name;
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
    @Column(name = "name", nullable = false, length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity that = (GameEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "gameByGameId",cascade = CascadeType.ALL)
    public Collection<BetEntity> getBetsById() {
        return betsById;
    }

    public void setBetsById(Collection<BetEntity> betsById) {
        this.betsById = betsById;
    }
}
