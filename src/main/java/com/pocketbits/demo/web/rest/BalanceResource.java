package com.pocketbits.demo.web.rest;

import com.pocketbits.demo.domain.Balance;
import com.pocketbits.demo.repository.BalanceRepository;

import com.pocketbits.demo.service.BalanceService;
import com.pocketbits.demo.service.CoinAlreadyPresentException;

import com.pocketbits.demo.service.dto.BalanceDTO;
import com.pocketbits.demo.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing balances.
 * <p>
 * This class accesses the {@link Balance} entity, and performs CRUD operations.
 * <p>
 **/

@RestController
@Validated
@RequestMapping("/api")
public class BalanceResource {

    private final Logger log = LoggerFactory.getLogger(BalanceResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BalanceService balanceService;

    private final BalanceRepository balanceRepository;



    public BalanceResource(BalanceService balanceService,BalanceRepository balanceRepository) {
        this.balanceService = balanceService;
        this.balanceRepository=balanceRepository;
    }

    /**
     * {@code POST  /users}  : Creates a new coin balance.
     * <p>
     * Creates a new coin balance if the coin is not present for the specified user
     *
     * @param coin,balance the data to create.
     * @header userId for balance request.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coin balance, or with status {@code 400 (Bad Request)} if the coin is already present for the user.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)}  if the coin is already present for the user.
     */
    @PostMapping("/balances")
    public ResponseEntity<Balance> createBalance(@Valid @NotBlank @RequestParam("coin") String coin,
                                                 @NotNull @RequestParam("balance") Double balance,
                                                 @NotBlank @RequestHeader("userId") String userId) throws URISyntaxException {
        log.debug("REST request to save Balance : {} {} {}", coin,balance,userId);

        if (balanceRepository.findOneByUserIdAndAndCoin(userId.toLowerCase(),coin.toLowerCase()).isPresent()) {
            throw new CoinAlreadyPresentException();
        }else {
            BalanceDTO balanceDTO = new BalanceDTO();
            balanceDTO.setId((long)(Math.random()*20));
            balanceDTO.setUserId(userId);
            balanceDTO.setCoin(coin);
            balanceDTO.setBalance(balance);
            balanceDTO.setCreatedAt(new Date());
            balanceDTO.setUpdatedAt(new Date());

            Balance newBalance = balanceService.addNewBalance(balanceDTO);
            return ResponseEntity
                .created(new URI("/api/balances/" + newBalance.getId()))
                .headers(
                    HeaderUtil.createAlert(applicationName, "A new Coin balance is created with identifier " + newBalance.getId(), newBalance.getUserId())
                )
                .body(newBalance);
        }
    }

    /**
     * {@code PUT /balances} : Updates an existing coin balance .
     *
     * @param coin,balance balance to update.
     * @header userId for balance request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
     * @throws URISyntaxException {@code 400 (Bad Request)} if the email is already in use.
     */
    @PutMapping("/balances")
    public ResponseEntity<BalanceDTO> updateUser(@Valid @NotBlank @RequestParam("coin") String coin,
                                                   @NotNull @RequestParam("balance") Double balance,
                                                 @NotBlank @RequestHeader("userId") String userId) throws URISyntaxException{
        log.debug("REST request to update coin balance : {} {} {}", coin,balance,userId);
        Optional<Balance> existingBalance = balanceRepository.findOneByUserIdAndAndCoin(userId,coin);

        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setId((long)(Math.random()*20));
        balanceDTO.setUserId(existingBalance.get().getUserId());
        balanceDTO.setCoin(existingBalance.get().getCoin());
        balanceDTO.setBalance(balance);
        balanceDTO.setCreatedAt(existingBalance.get().getUpdatedAt());
        balanceDTO.setUpdatedAt(new Date());
        System.out.println("test "+balanceDTO);

        Optional<BalanceDTO> updatedBalance = balanceService.updateBalance(balanceDTO);
        System.out.println("updatedBalance "+updatedBalance);


        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(
                HeaderUtil.createAlert(applicationName, "A new Coin balance is updated with identifier " + balanceDTO.getId(), balanceDTO.getUserId())
            )
            .body(balanceDTO);
    }

    /**
     * {@code GET /balances} : get all coins with all the details.
     *
     * @header userId for balance request.
     * @header userId for balance request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all coins.
     */
    @GetMapping("/all-balances")
    public ResponseEntity<Optional<List<Balance>>> getBalance(@NotBlank @RequestHeader("userId") String userId) {
        log.debug("REST request to get all coin balances for an user");

        final Optional<List<Balance>> listOfCoins = balanceService.getAllCoinBalances(userId);

        return ResponseEntity
        .status(HttpStatus.OK)
            .headers(
                HeaderUtil.createAlert(applicationName, "Retrieved all Coin Balances of user " ,userId)
            )
            .body(listOfCoins);
    }

    /**
     * {@code GET /balances} : get specific coins with all the details.
     *
     * @params coin for balance request.
     * @header userId for balance request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body of specific coin.
     */
    @GetMapping("/balances")
    public ResponseEntity<Optional<Balance>> getAllBalances(@Valid @NotBlank @RequestParam("coin") String coin,
                                                            @NotBlank @RequestHeader("userId") String userId) {
        log.debug("REST request to get specific coin balances for an user");

        final Optional<Balance> coinBalance = balanceService.getCoinBalance(userId,coin);
        if(coinBalance.isEmpty())
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .headers(
                    HeaderUtil.createAlert(applicationName, "The specified Coin balance cannot be found " ,coin)
                )
                .body(coinBalance);

        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(
                HeaderUtil.createAlert(applicationName, "Retrieved Balance of the specified coin " ,coin)
            )
            .body(coinBalance);
    }



    /**
     * {@code DELETE /balances: coin } : delete the specified coin.
     * @header userId for balance request.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/balances/{coin}")
    public ResponseEntity<Void> deleteUser(@PathVariable String coin,
                                           @RequestHeader("userId") String userId) {
        log.debug("REST request to delete Balance: {}", coin);
        balanceService.deleteBalance(userId,coin);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(applicationName, "A Coin balance is deleted with identifier " , coin))
            .build();
    }

}
