package com.pocketbits.demo.service;

import com.pocketbits.demo.config.Constants;
import com.pocketbits.demo.domain.Authority;
import com.pocketbits.demo.domain.Balance;
import com.pocketbits.demo.domain.User;
import com.pocketbits.demo.repository.AuthorityRepository;
import com.pocketbits.demo.repository.BalanceRepository;
import com.pocketbits.demo.repository.UserRepository;
import com.pocketbits.demo.security.AuthoritiesConstants;
import com.pocketbits.demo.security.SecurityUtils;
import com.pocketbits.demo.service.dto.AdminUserDTO;
import com.pocketbits.demo.service.dto.BalanceDTO;
import com.pocketbits.demo.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing balances.
 */
@Service
@Transactional
public class BalanceService {

    private final Logger log = LoggerFactory.getLogger(BalanceService.class);

    private final BalanceRepository balanceRepository;


    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }


    /**
     * Add Coin balance for a specific user, and return the modified user.
     *
     * @param balanceDTO user to inserted.
     * @return inserted balance.
     */
    public Balance addNewBalance(BalanceDTO balanceDTO) {
        balanceRepository
            .findOneByUserIdAndAndCoin(balanceDTO.getUserId().toLowerCase(), balanceDTO.getCoin().toLowerCase())
            .ifPresent(existingCoin -> {
                    throw new CoinAlreadyPresentException();
            });

        Balance balance = new Balance();
        balance.setId(balanceDTO.getId());
        balance.setCoin(balanceDTO.getCoin());
        balance.setBalance(balanceDTO.getBalance());
        balance.setUserId(balanceDTO.getUserId());
        balance.setCreatedAt(new Date());
        balance.setUpdatedAt(new Date());

        balanceRepository.save(balance);
        log.debug("Created Information for Coin Balance: {}", balance);
        return balance;
    }


    /**
     * Update balance for a specific user, and return the modified user.
     *
     * @param balanceDTO user to update.
     * @return updated balance.
     */
    public Optional<BalanceDTO> updateBalance(BalanceDTO balanceDTO) {
        return balanceRepository.findOneByUserIdAndAndCoin(balanceDTO.getUserId(),balanceDTO.getCoin())
            .map(balance -> {
                balance.setUserId(balanceDTO.getUserId());
                balance.setCoin(balanceDTO.getCoin());
                balance.setBalance(balanceDTO.getBalance());
                balance.setCreatedAt(balanceDTO.getCreatedAt());
                balance.setUpdatedAt(new Date());
                balanceRepository.save(balance);
                log.debug("Changed Information for Balance: {}", balance.getCoin());
                return balance;
            })
            .map(BalanceDTO::new);
    }

    /**
     * Get all Coin balances for a specific user.
     *
     * @param userId user to update.
     * @return list of balances.
     */
    public Optional<List<Balance>> getAllCoinBalances(String userId){
        return (balanceRepository
            .findAllByUserId(userId));
    }

    /**
     * Get specific Coin balance for a specific user.
     *
     * @param userId,coin user to get.
     * @return balance of specified coin.
     */
    public Optional<Balance> getCoinBalance(String userId, String coin){
        return (balanceRepository
            .findBalanceByUserIdAndCoin(userId,coin));
    }

    /**
     * Delete specific Coin balance for a specific user.
     *
     * @param userId,coin  coin to delete.
     */
    public void deleteBalance(String userId,String coin) {
        balanceRepository
            .findOneByUserIdAndAndCoin(userId,coin)
            .ifPresent(balance -> {
                balanceRepository.deleteBalanceByUserIdAndCoin(userId,coin);
            });
    }

}
