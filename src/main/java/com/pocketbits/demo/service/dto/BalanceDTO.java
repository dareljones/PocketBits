package com.pocketbits.demo.service.dto;

import com.pocketbits.demo.domain.Balance;

import java.util.Date;

/**
 * A DTO representing a Balance, with only the public attributes.
 */
public class BalanceDTO {

    private Long id;

    private String coin;

    private Double balance;

    private String userId;

    private Date createdAt;

    private Date updatedAt;



    public BalanceDTO() {
        // Empty constructor needed for Jackson.
    }

    public BalanceDTO(Balance balance) {
        this.id = balance.getId();
    }

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

    @Override
    public String toString() {
        return "BalanceDTO{" +
            "id=" + id +
            ", coin='" + coin + '\'' +
            ", balance=" + balance +
            ", userId='" + userId + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
