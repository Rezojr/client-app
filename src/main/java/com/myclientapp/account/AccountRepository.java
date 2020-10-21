package com.myclientapp.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findByClientId(Long clientId, Pageable pageable);

    Optional<Account> findByIdAndClientId(Long id, Long clientId);

}
