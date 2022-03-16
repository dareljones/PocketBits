package com.pocketbits.demo.repository;

import com.pocketbits.demo.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link Balance} entity.
 */
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance save(Balance balance);
    Optional<List<Balance>> findAllByUserId(String userId);
    Optional<Balance> findBalanceByUserIdAndCoin(String userId, String coin);
    Optional<Balance> findOneByUserIdAndAndCoin(String userId, String coin);
    Optional<Balance> deleteBalanceByUserIdAndCoin(String userId,String coin);
}
