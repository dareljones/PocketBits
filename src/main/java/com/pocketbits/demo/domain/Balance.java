package com.pocketbits.demo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * coin balance.
 */
@Entity
@Table(name = "coin_balance")
public class Balance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, nullable = false)
    private String coin;

    @NotNull
    @Column(name = "balance", length = 60, nullable = false)
    private Double balance;

    @Size(max = 50)
    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "created_at", length = 50)
    private Date createdAt;

    @Column(name = "updated_at", length = 10)
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


}
